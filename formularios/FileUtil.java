package formularios;

import java.io.*;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import log.Log;
import log.Tipo_Log;
import logica.ag.ConfigAG;
import logica.ag.GrayCode;

public class FileUtil {
	static String pathData=logica.ag.ConfigAG.PATH_MOEA;
	//////////////////////////////////////////////////////////////
	// Constantes////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	public static String ARCHIVO_DOM_AULAS="001_aulas_turnos.dom";
	public static String ARCHIVO_DOM_DOCENTES="002_docentes.dom";
	public static String ARCHIVO_DOM_SEDES="003_sedes.dom";
	public static String ARCHIVO_DOM_MATERIAS="004_materias.dom";
	public static String ARCHIVO_DOM_FACULTADES="005_facultades.dom";									
	public static String ARCHIVO_DOM_CARRERAS="006_carreras.dom";
	public static String ARCHIVO_DOM_TIPO_AULA="007_tipo_aula.dom";
	public static String ARCHIVO_DOM_HORARIOS="008_horarios.dom";
	public static String ARCHIVO_DOM_PLANES="009_planes.dom";
	public static String ARCHIVO_DOM_DIAS="010_dias.dom";
	public static String ARCHIVO_DOM_RECURSOS="012_recursos.dom";
	static String ARCHIVO_OUT=pathData+"/final_archive.out";
	static String ARCHIVO_GRAY_AULAS="001_aulas_turnos.gray";
	static String ARCHIVO_FORM_INSTAL_GRAY="formInstal.gray";
	static String ARCHIVO_FORM_INSTAL_INT="formInstal.int";
	static String ARCHIVO_FORM_INSTAL_DES="formInstal.des";
	static String ARCHIVO_FORM_ADMIN_DES="formAdmin.des";
	static String ARCHIVO_FORM_ADMIN_INT="formAdmin.int";
	static String ARCHIVO_FORM_ADMIN_GRAY="formAdmin.gray";
	static String ARCHIVO_MATERIAS_CARGADAS_DES="materiasCargadas.des";
	
	
	//leo del archivo propiedades
	//metodo base para otros metodos
	//no se si uso este metodo en algun lado
	public static  void leerArchivoPropiedades(String nomArch) throws FileNotFoundException{
		String archivo=ConfigAG.PATH+nomArch;
		BufferedReader br=new BufferedReader(new FileReader(archivo));
		//para leer
		String linea;
		try {
			while ((linea=br.readLine())!=null) {
				//System.out.println(linea);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	static void guardarInt(String resultado, String archivo){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.CONSOLA,"En codigo Int, se ingresó:"+ resultado);
		//FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_INT,archivo, resultado, true);
		FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_INT,archivo, resultado, true);
	}
	static void guardarGray(String resultado, String archivo){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.CONSOLA,"En codigo Gray, se ingresó:"+ resultado);
		FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_GRAY_DEPRECATED,archivo, resultado, true);
	}
	static void guardarDes(String resultado, String archivo){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.CONSOLA, "Datos ingresados: "+resultado);
		FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_DES,archivo, resultado, true);
	}
	
	//metodo con un parametro menos a grabarArchivoPropiedades()
	public static void grabarArchivoPropiedades(String path,String nomArch, String dato){
		grabarArchivoPropiedades(path, nomArch, dato, false);
	}
	//Metodo para guardar en el archivo DOM.
	//Si el ultimo parametro es true, implica que tiene que agregar (append)
	public static void grabarArchivoPropiedades(String path,String nomArch, String dato, boolean append){
			String archivo=path+nomArch;
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo,append)));
			} catch (IOException e) {
						e.printStackTrace();
			}
			//para grabar
			pw.println(dato);
			pw.close();
		}
	public static void grabarArchivoPropiedadesXML(String path, String nomArch, String dato, boolean append) {
		String archivo=path+nomArch;
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(archivo,append)));
		} catch (IOException e) {
					e.printStackTrace();
		}
		//para grabar
		pw.println(dato);
		pw.close();
	}
	//metodo que extrae la cantidad de lineas de un archivo	
	public static int getLineas(String path,String file)throws FileNotFoundException{
				String archivo=path+file;
				int cantLines=0;
				BufferedReader br=new BufferedReader(new FileReader(archivo));
				//para leer
				String linea;
				try {
					while ((linea=br.readLine())!=null) {
						cantLines++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				return cantLines;
		}
	
		public static String getLine(String path,String file, int nro)throws FileNotFoundException{
			String archivo=path+file;
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			//para leer
			int contador=0;
			String linea="";
			String resultado="*";
			try {
				while (((linea=br.readLine())!=null)&(contador<=nro)) {
					contador++;
					StringTokenizer st = new StringTokenizer(linea,"\"");
					st.nextToken();
					resultado=st.nextToken();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return resultado;
	}
		//metodo para levantar files .dom
		public static void leerDescDom(String nomArch) throws FileNotFoundException{
			String archivo=ConfigAG.PATH+nomArch;
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			Log l = Log.getInstance();
			//para leer
			String linea;
			try {
				while ((linea=br.readLine())!=null) {
					StringTokenizer st = new StringTokenizer(linea,"\"");
					st.nextToken();
					st.nextToken();					
				}
			} catch (IOException e) {
				l.loguear(Tipo_Log.ERROR, "El archivo de datos no tiene el formato requerido o no contiene la descripcion ingresada");
				e.printStackTrace();
			}
		}
		//metodo que devuelve el codigo de un file de dominio
		public static int getCodigo(String descripcion, String archivoDominio){
			String archivo=ConfigAG.PATH_DOM+archivoDominio;
			String resultado = "";
			String descripArchivo;	
			Log l = Log.getInstance();
			//para leer
			String linea;
			boolean encontrado=false;
			try {
				BufferedReader br=new BufferedReader(new FileReader(archivo));
				while ((encontrado==false)&&((linea=br.readLine())!=null)) {
					StringTokenizer st = new StringTokenizer(linea,"\"");
					resultado = st.nextToken();
					descripArchivo=st.nextToken();
					if (descripcion.equals(descripArchivo)){
						encontrado=true;
						}
				}
			} catch (IOException e) {
				l.loguear(Tipo_Log.ERROR,"El archivo de datos no tiene el formato requerido o no contiene la descripcion ingresada");
				e.printStackTrace();
			}
			return Integer.parseInt(resultado);
		}
		
		//	este metodo recibe un array de strings
		//lo tolkenizo por \n y devuelvo un String en codigo gray
		static String arrayParserGray( String lista){
			int cantElem,cont,aux;
			cont=0;
			String resultado = "";
			StringTokenizer st = new StringTokenizer(lista,"\n");
			cantElem=st.countTokens();
			while (cantElem>cont) {
				cont++;
				aux=FileUtil.getCodigo(st.nextToken(),ARCHIVO_DOM_RECURSOS );
				if (resultado==""){
					resultado=new GrayCode(aux).getResult().toString();							
					}else{
						resultado=resultado+"|"+new GrayCode(aux).getResult().toString();
				}
			}				
			return resultado;				
		}	
		
		//este metodo recibe un array de strings
		//lo tolkenizo por \n y devuelvo un String en codigo int
		static String arrayParserInt( String lista){
			int cantElem,cont,aux;
			cont=0;
			String resultado = "";
			StringTokenizer st = new StringTokenizer(lista,"\n");
			cantElem=st.countTokens();
			while (cantElem>cont) {
				cont++;
				aux=FileUtil.getCodigo(st.nextToken(),ARCHIVO_DOM_RECURSOS );
				if (resultado==""){
						resultado=String.valueOf(aux);							
					}else{
						resultado=resultado+"|"+aux;
				}
			}				
			return resultado;				
		}
		//SE usa???
		//metodo para leer el archivo final_archive.out y mostrar la salida
		//con split
		//TODO: arreglar para que devuelva la matriz del archivo
		/*public static void leerArchivoOut(String nomArch) throws FileNotFoundException{
			String archivo=pathData+nomArch;
			int nroLinea=0;
			String patron="[\\]\\[,]";
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			//para leer
			String linea;
			try {
				while ((linea=br.readLine())!=null) {
					nroLinea++;
					String[] campos = linea.split(patron);
					System.out.println("vector "+nroLinea);
					for (int i = 0; i < campos.length; i++)
					    System.out.println(campos[i]+"*");    
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		
		public static int getCodigoInt(int codigoGray){
			//paso el codigo gray a codigo int
			int codigo = new GrayCode(codigoGray).GrayToInt();
			return codigo;			
		}
		//dado un codigo int, devuelve una descripcion
		public static String getDescripcionArchivo(int codigoInt, String archivoDominio){
			String archivo=ConfigAG.PATH_DOM+archivoDominio;
			String resultado = "";
			String miEntero; 
			Log l = Log.getInstance();
			String descripArchivo = null;			
			//para leer
			String linea;
			boolean encontrado=false;
			try {
				BufferedReader br=new BufferedReader(new FileReader(archivo));
				while ((encontrado==false)&&((linea=br.readLine())!=null)) {
					StringTokenizer st = new StringTokenizer(linea,"\"");
					miEntero = st.nextToken();
					descripArchivo=st.nextToken();
					if (Integer.parseInt(miEntero)== codigoInt){						
						encontrado=true;
					}
				}
			} catch (IOException e) {
				l.loguear(Tipo_Log.ERROR, "El archivo de datos no tiene el formato requerido o no contiene la descripcion ingresada");
				e.printStackTrace();
			}
			return descripArchivo;
		}
				
		//posicion segun orden gen [aulas,materia,docente,horario]
		//dado un codigo gray, devuelve la descripcion
		public static String getDescripcion(int codigoInt, int posicion){
			String resultado = null;
			switch (posicion-1) {
				case 1:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_AULAS);
					break;
				case 2:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_DOCENTES);
					break;
				case 3:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_SEDES);
					break;
				case 4:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_MATERIAS);
					break;
				case 5:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_FACULTADES);
					break;					
				case 6:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_CARRERAS);
					break;
				case 7:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_TIPO_AULA);
					break;
				case 8:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_HORARIOS);
					break;
				case 9:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_PLANES);
					break;
				case 10:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_DIAS);
					break;
				default:
					resultado=getDescripcionArchivo(codigoInt,ARCHIVO_DOM_RECURSOS);
					break;
				}			
			
			return resultado;
		}
		//SE usa??
		//convierte los datos de final_archive.out a descripcion
		/*public String[][] obtenerMatrizDesc(String[][] matrizGray){
			int codigoInt;
			String[][] matrizDesc = null;
			String descripcion;
			//quiero que me cargue en memoria la matriz del archivo
			//matriz=FileUtil.getDescripcion("/final_archive.out"
			//recorro la primer solucion (usar un for)
			//campos es el primer gen solucion
			for (int j = 0; j < matrizGray.length; j++){
				for (int i = 0; i < matrizGray[j].length; i++){
					//obtengo el codigo en int
					codigoInt=FileUtil.getCodigoInt(Integer.parseInt(matrizGray[i][j]));
					//busco el int y obtengo su descripcion
					descripcion=FileUtil.getDescripcion(codigoInt, i);
					System.out.println(descripcion);
					matrizDesc[i][j]=descripcion;
				}
			}
			return matrizDesc;
		}*/
		double getCapacidadMateria(GrayCode materia){
			//tomo materia, lo tranformo a desc y busco su codigo en el file
			//busco su capacidad en el file
			int codigoMateria = materia.GrayToInt();
			int codigoInt=getCodigoInt(codigoMateria);
			return codigoInt;
			
		}
		  //static double getCapacidadAula(GrayCode aula);
		  //double getCantidadRecurso(GrayCode rec);
		
		static int getUltimoCodigo(String archivoDominio){
			String archivo=ConfigAG.PATH_DOM+archivoDominio;
			String miEntero = null;
			String linea;
			Log l = Log.getInstance();
			try {
				BufferedReader br=new BufferedReader(new FileReader(archivo));
				while ((linea=br.readLine())!=null) {
					StringTokenizer st = new StringTokenizer(linea,"\"");
					miEntero = st.nextToken();
					st.nextToken();					
				}
			} catch (IOException e) {
				l.loguear(Tipo_Log.ERROR, "El archivo de datos no tiene el formato requerido o no contiene la descripcion ingresada");
				e.printStackTrace();
			}		
			return Integer.parseInt(miEntero);			
		}
		
		/* Se usa en Instalaciones y agregarMateria
		 
		 */
		static void agregarTipoDeAula(JPanel jPanelTipoAula){
			int lineas=0;
			JRadioButton[] tipoDeAulas = null;
			ButtonGroup bg = new ButtonGroup();
			try {
				lineas = FileUtil.getLineas(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_TIPO_AULA);
				//creo los botones
				tipoDeAulas=new JRadioButton[lineas];
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i=0;i<lineas;i++){
				try {				
					tipoDeAulas[i]=new JRadioButton(FileUtil.getLine(ConfigAG.PATH_DOM,FileUtil.ARCHIVO_DOM_TIPO_AULA,i));
					jPanelTipoAula.add(tipoDeAulas[i]);
					bg.add(tipoDeAulas[i]);
					if (i==1){
						tipoDeAulas[i].setSelected(true);
					}				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
/*
 * Se usa en Agregar materia
 */				
		static void agregarCombo(String path,String file,JComboBox jComboBox){
			String archivo=path+file;
			try{
			BufferedReader br=new BufferedReader(new FileReader(archivo));
			//para leer
			String linea=" ";
			while ((linea=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(linea,"|\"");
				st.nextToken();
				jComboBox.addItem(st.nextToken());
				}
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
		
		
	public static void main(String[] args) {
		/*try {
			leerArchivoOut("/final_archive.out");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//getDescripcionArchivo(3, "dom/004_materias.dom");		
		System.out.println(getUltimoCodigo("004_materias.dom"));
	}

	
	
	
}