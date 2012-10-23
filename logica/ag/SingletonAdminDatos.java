package logica.ag;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

import formularios.FileUtil;

import log.Log;
import log.Tipo_Log;

public class SingletonAdminDatos {
	//implements PuedeProcesarFormularios {
	private static SingletonAdminDatos adminDatos = null;
	private ArrayList<String> l = new ArrayList<String>();
	private ArrayList<Parametro> params = new ArrayList<Parametro>(); // todos los parametros menos las aulas
	//private ArrayList<ParametroStock> paramstock = new ArrayList<ParametroStock>();
	private Parametro aulas;  // solo las aulas
	//private ParametroStock aulastock;
	private GrayCode[] memoriaDat=null;

	private SingletonAdminDatos() {
		cargadorFiles(ConfigAG.PATH_GRAY);
		memoriaDat=new GrayCode[l.size()+11-2];
		cargadorParams();
		iniciarComb();
//		AdmRelacion.getInstance();
	}
	public Parametro getAulas() {
        return aulas;
    }
	public ArrayList<Parametro> getParams() {
		return params;
	}
	public int getCantAulas() {
	    return (int) aulas.getValoresGray().size();
	}
    public void setAulas(Parametro aulas) {
        this.aulas = aulas;
    }
    public static SingletonAdminDatos getInstance() {
		if (adminDatos == null)
			adminDatos = new SingletonAdminDatos();
		return adminDatos;
	}
    public AdmRelacion buscarValor(int file, double valor) {
    	AdmRelacion r = AdmRelacion.getInstance();
//    	r.getRelacion(file, valor);
    	return r;
    }
    //Este metodo se usa para aplicar filtro a File.listFiles();
    private static FilenameFilter filtro(final String regex) {
    	return new FilenameFilter() {
    		public boolean accept(File dir, String name) {
    			Pattern pattern = Pattern.compile(regex); 
    			String f = new File(name).getName(); 
    			return pattern.matcher(f).matches();
    		}
    	};
    }
	public void cargadorFiles(String path) {
		File f = new File(path);
		//File files[] = f.listFiles(filtro("*"));
		File files[] = f.listFiles();
		for(int i=0;i<files.length;i++)
			l.add(files[i].getName());
		Collections.sort(l);
	}
	public boolean hayAulas() {
		return aulas.getPList() < (aulas.getValoresGray().size());
	}
	public void reiniciarAulas() {
		aulas.reiniciar();
	}
	public void cargadorParams() {
    	Log l1 = Log.getInstance();
		Iterator<String> i = l.iterator();
		String ultimo=null;
		try {
			aulas = new Parametro(ConfigAG.PATH_GRAY, i.next());
		} catch (IOException io) {
			io.printStackTrace();
		}
//		if (i.hasNext()) 
//			try {
//				ultimo=i.next();
//				aulas = new ParametroStock(ConfigAG.pathStock, ultimo);
//			} catch (IOException io) {
//				io.printStackTrace();
//			}
		while (i.hasNext()) 
			try {
				ultimo=i.next();
//				if (ultimo.equals("004_materias.gray") ||
//					ultimo.equals("011_recursos.gray")) 
//					params.add(new ParametroStock(ConfigAG.pathStock, ultimo));
//				else
					params.add(new Parametro(ConfigAG.PATH_GRAY, ultimo));
				if (!i.hasNext()) repiteRecursos(ultimo);
			} catch (IOException io) { 
				io.printStackTrace();
			}
	    l1.loguear(Tipo_Log.INFORMACION, "..."+ultimo);
	}
	private void repiteRecursos(String ultimo) {
		try {
			for (int i=0;i<11;i++)
				params.add(new Parametro(ConfigAG.PATH_GRAY, ultimo));		
		} catch (IOException io) { 
			io.printStackTrace();
		}
	}
	public GrayCode[] getMemoriaDat() {
		return memoriaDat;
	}
//	public buscarValor(int idFile, double valor) {
//		
//	}
	public BitSet getParamAzar(int indice, BitSet bitset) {
// obtiene un parámetro al azar que no coincida con el que ingresa.
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "getParamAzar");
    	BitSet bs = new BitSet(bitset.size());
		bs.clear();
		GrayCode gc = new GrayCode(bs);
		Random rnd = new Random();
		if (indice == 0) {
			l.loguear(Tipo_Log.ERROR, "No se puede mutar a las aulas");
			return null;
		}
		Parametro parAux = new Parametro();
		//obtengo los valores de dominio del parametro en cuestion
		parAux = params.get(indice-1);
		ArrayList<GrayCode> genAux = new ArrayList<GrayCode>();
		genAux = parAux.getValoresGray();
		int tamGenAux = genAux.size();
		gc = genAux.get(rnd.nextInt(tamGenAux));
		while (gc.getResult().equals(bitset)) {
			gc = genAux.get(rnd.nextInt(tamGenAux));
		}
    	l.loguear(Tipo_Log.DATOS, "BitSet al azar: " + gc.getNumber());
    	return gc.getResult();
	}
	public GrayCode obtProxAula() {
		return aulas.proximo();
	}
	public void iniciarComb() {
		memoriaDat=new GrayCode[l.size()+11-2];
		aulas.reiniciar();
		Iterator<Parametro> itera = params.iterator();
		for (int i=0;i<params.size()-1;i++)
			memoriaDat[i]=itera.next().proximo();
	}
	public void obtenerProxCombinacSeq() {
		Iterator<Parametro> itera = params.iterator();
		Parametro p;
		for (int i=params.size()-1; i>=0;i--){
			p = itera.next();
			if (p.hayMas()) { 
				memoriaDat[i]=p.proximo();
				return;
			}
			else {
				p.reiniciar();
				memoriaDat[i]=p.proximo();
			}
		}
	}
	public void obtenerProxCombinacRandomSinNA() {
		Iterator<Parametro> itera = params.iterator();
		Parametro pAux;
		for (int i=0;i<params.size()-1;i++)
			memoriaDat[i]=itera.next().randomSinNA();
	}
	public static void main(String[] args){
		SingletonAdminDatos sad=SingletonAdminDatos.getInstance();

		System.out.println(sad.hayAulas());
		if (sad.hayAulas()) {
			System.out.println(sad.obtProxAula());
			sad.iniciarComb();
			sad.obtenerProxCombinacSeq();
			System.out.println("memoriaDat: "+Arrays.asList(sad.getMemoriaDat()));
		}
		System.out.println(sad.hayAulas());
		if (sad.hayAulas()) 
			System.out.println(sad.obtProxAula());

		System.out.println(sad.hayAulas());
		if (sad.hayAulas()) 
			System.out.println(sad.obtProxAula());

		System.out.println(sad.hayAulas());
		if (sad.hayAulas()) 
			System.out.println(sad.obtProxAula());

		System.out.println(sad.hayAulas());
		if (sad.hayAulas()) 
			System.out.println(sad.obtProxAula());
			
		sad.getMemoriaDat();
		
		sad.obtenerProxCombinacSeq();
		sad.getParamAzar(1, new BitSet(10));
	}
	/*public void cargadorFiles(String path, ) {
		File f = new File(path);
		File files[] = f.listFiles();
		for(int i=0;i<files.length;i++)
			l.add(files[i].getName());
		Collections.sort(l);
	}*/
	public OriDes[] dameDestinos(OriDes origen) {
		OriDes[] f = null;
		switch (origen.hashCode()) {
		case 0: //Docente
			f=new OriDes[2];
			f[0]=OriDes.MAT;
			f[1]=OriDes.HOR;
			break;
		case 2: //Materia
			f=new OriDes[4];
			f[0]=OriDes.T_A;
			f[1]=OriDes.REC;
			f[2]=OriDes.FAC;
			f[3]=OriDes.PLA;
			break;
		case 3: //Facultad
			f=new OriDes[2];
			f[0]=OriDes.SED;
			f[1]=OriDes.CAR;
			break;
		case 4: //Carrera
			f=new OriDes[1];
			f[0]=OriDes.PLA;
			break;
		default:
			break;
		}	
		return f;
	}
	public Map<Integer, Integer> dameRelacion(OriDes o, OriDes d) {
		String archivo=ConfigAG.PATH_REL+o.name()+d.name()+".Int";
		Map<Integer, Integer> relAux= new HashMap();
		String linea;
		String[] valores=new String[2];
		try {
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			while ((linea=br.readLine())!=null) {
				valores=linea.split(",");
				relAux.put(new Integer(valores[0]), new Integer(valores[1]));		
			}
		} catch (IOException e) {
				e.printStackTrace();
		}
		return relAux;
	}
	
//	public void cargarRelaciones(AdmRelacion r) {
//		//TODO recorrer los archivos y cargar de a 1
//		File f = new File(ConfigAG.PATH_REL);
//		Log l = Log.getInstance();
//		Gen genAux;
//		String linea="";
//		String patron="[\\]\\[,]";
//		String[] campos = new String[memoriaDat.length];
//		for(File q: f.listFiles()) {
//			System.out.println(q);
//			try {
//				BufferedReader br=new BufferedReader(new FileReader(q));
//				while ((linea=br.readLine())!=null) {
//					campos = linea.split(patron);
//					genAux = new Gen();
//					genAux.setLargo(campos.length);
//					for(int i=1;i<(genAux.largo()+1);i++) {
//						if (((String)campos[i]!=("null")) && (!(campos[i].equals(null))))
//							genAux.agregarParam(new GrayCode(Integer.parseInt(campos[i].trim())), i);
//					}
//				}
//			} catch (FileNotFoundException e) {
//				l.loguear(Tipo_Log.EXCEPCION, "No encuentro archivo "+q);					
//			} catch (IOException e) {
//				l.loguear(Tipo_Log.EXCEPCION, "Error en lectura de archivo "+q);						
//			}
//		}
//		//aulas, profesores, etc.
//		//...
//	}
	public double getCantidadRecursos(GrayCode rec) {
		return (double)(Math.random()*20);
	}
	public double getCapacidadAula(GrayCode aula) {
		return (double)(Math.random()*50);
	}
	public double getCapacidadMateria(GrayCode mat) {
		return (double)(Math.random()*50);
	}
//	public double getCantidadRecursosIndice(int indice) {
//		return ((ParametroStock) params.get(10)).getCapacidad(indice);
//	}
//	public double getCapacidadAulaIndice(int indice) {
//		return ((ParametroStock) aulas).getCapacidad(indice);
//	}
//	public double getCapacidadMateriaIndice(int indice) {
//		return ((ParametroStock) params.get(2)).getCapacidad(indice);
//	}
//	public double getCapacidadMateriaPorValor(int valor) {
//		return ((ParametroStock) params.get(2)).getCapacidadPorCodigo(new GrayCode(valor));		
//	}
}
