package moea;
import io.FacadeDatos;

import java.sql.SQLException;
import java.util.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.*;

public class ProblemaGdarim extends Problema{
    public ProblemaGdarim( ){}
	public void asignarObjetivos (double[] xBin, double[] xRestric){}
	public void asignarObjetivos (double[] xBin) {}
    public void asignarObjetivos (double[][] xBin){
        obj[0]=0;
        obj[1]=0;
    }
	public void evaluarFuncionesObjetivo (double[] restriccion,int[] valObj){	
		Log l = Log.getInstance();
		Random rnd = new Random();	
		for (int i=0;i<obj.length;i++)
			valObj[i]=(int)(obj[i]-minValor[i])+rnd.nextInt(10);
		double vr=0.0;
		for (int i=0;i<restriccion.length;i++)
			vr+=(restriccion[i]<0.0?restriccion[i]:0.0);
		l.loguear(Tipo_Log.DATOS, "Valor de la funcion objetivo: "+valObj[0]+", "+valObj[1]);
	}
	public int evaluarFuncionesObjetivo (final double[][] xBin, int[] valorFnObjetivo){	
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "evaluarFuncionesObjetivo (del Problema)");
		Random rnd = new Random();
		int cantRest;
		double capAula, capMat, diferencia;
		capAula=0;
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		int valAux=0;
		//for(int i=0;i<xBin.length;i++) {		
			//segundo indice del gen = 1 son los docentes
			//sumo todas las aulas sin docentes (docentes==1 => N/A)
			//TODO ver si cambio a 0
		//	if (xBin[i][1] == 1) valAux++;
//			capMat=datos.getCapacidadMateriaPorValor((int) xBin[i][3]);
//			capAula=datos.getCapacidadAulaIndice(i);
//			diferencia=capMat-capAula;
//			if (diferencia<0) diferencia*=-1;
//			if (capMat>capAula)
//				valorFnObjetivo[0]+=(diferencia*2);
//			else
//				valorFnObjetivo[0]+=diferencia;
		//}
		//valorFnObjetivo[1]+=valAux;
		l.loguear(Tipo_Log.DATOS, "f(aulas)="+valorFnObjetivo[0]);	
		valorFnObjetivo[Constantes.OBJETIVO_1]=validaFitness(xBin, Constantes.OBJETIVO_1);
		valorFnObjetivo[Constantes.OBJETIVO_2]=validaFitness(xBin, Constantes.OBJETIVO_2);
		
		if ((cantRest=cumpleRestriccion(xBin)) != 0) {
//			valorFnObjetivo[1]+=Constantes.PENAL_RESTRIC;
			l.loguear(Tipo_Log.DATOS, "Restricciones: "+cantRest);
		}
		l.loguear(Tipo_Log.DATOS, "f(docentes)="+valorFnObjetivo[1]);		
		return cantRest;
	}
	private int cumpleRestriccionOld(double [][] xBin) {
		int retorno=0;
		retorno=restrDocDupl(xBin);
		retorno+=restrDocHoraNoDisp(xBin);
		retorno+=restrDocMatNoHabilita(xBin);
		retorno+=restrMatDuplica(xBin);
		return retorno;	
	}
	private int cumpleRestriccion(double [][] xBin) {
		int retorno=0;
		TFitness tr=null;
		Iterator<TFitness> itRest = defFitnessMoea.iterator();
		Log l = Log.getInstance();
		while (itRest.hasNext()) {
			tr=itRest.next();
			if ((((TFitness)tr).getTipo().equals(Constantes.FITNESS)) &&
				   ((TFitness)tr).getValorPenalizadoInt()==Constantes.PENAL_RESTRIC) {
				l.loguear(Tipo_Log.INFORMACION, "Proceso Restricciones");
				retorno+=cuentaRestriccion(tr.getFitness(), xBin);
				// procesar restriccion de duplicados.
			}
		}
		return retorno;	
	}
	private int validaFitness(double [][] xBin, int objetivo) {
		int retorno=0;
		TFitness tr=null;
		Iterator<TFitness> itRest = defFitnessMoea.iterator();
		Log l = Log.getInstance();
		while (itRest.hasNext()) {
			tr=itRest.next();
			if (((((TFitness)tr).getTipo().equals(Constantes.FITNESS)) &&
				 (((TFitness)tr).getValorPenalizadoInt()!=Constantes.PENAL_RESTRIC) &&
				 (((TFitness)tr).getNroObjetivo()==objetivo)))  {
				 //				 (((TFitness)tr).getNroObjetivo()!=objetivo)))  {
				l.loguear(Tipo_Log.INFORMACION, "Proceso Fitness");
				retorno+=cuentaRestriccion(tr.getFitness(), xBin);
				// procesar restriccion de duplicados.
			}
		}
		return retorno;	
	}
	private int cuentaRestriccion(TFitness tf, double [][] xBin) {
		int cant=0;
		Log l = Log.getInstance();
		FacadeDatos fd = FacadeDatos.getInstance();
		try {
			for(int i=0;i<xBin.length;i++) {
				if (!(fd.relacionValida(tf, xBin[i], this)))
					if (tf.getValorPenalizadoInt()==Constantes.PENAL_RESTRIC)
						cant++;
					else
						cant+=tf.getValorPenalizadoInt();
			}
		} catch (SQLException sqle) {
			l.loguear(Tipo_Log.INFORMACION, "SQLExcepcion");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cant;
	}
	private int cuentaDuplicados(String parametros, double [][] xBin) {
		Set<String> s = new HashSet<String>();
		int cant=0;
		String cadena;
		for(int i=0;i<xBin.length;i++) {
			cadena="";
			for (int g=0;g<xBin[0].length;g++)
				if (parametros.charAt(g)=='1')
					cadena+=xBin[i][g];
			s.add(cadena);
		}
		return (xBin.length - s.size());
	}
	//Alelo es una posicion y su valor en un Gen
	class Alelo {
		int posicion;
		double valor;
		Alelo(int p, double v) {
			posicion=p;
			valor=v;
		}
		Alelo(){}
	}
	//AlelosVinculados: Lista de Alelos de una relacion por formulario.
	class AlelosVinculados {
		List<Alelo> alelosVinculados = new ArrayList<Alelo>();
		List<Alelo> getAleloVinculado() {
			return alelosVinculados;
		}
	}
	private int cuentaRestrForm(String parametros, double [][] xBin) {
		int cant=0, indice=0;
		//List<AlelosVinculados> listaVinculos = new ArrayList<AlelosVinculados>();
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		//recorro String
		List<Integer> parmsRelacionados = formatearRestr(parametros);
		//recorro en xBin para buscar las relaciones
		//AlelosVinculados vinc = extraerVincXBin(xBin, parmsRelacionados);
		List<AlelosVinculados> listaVinculos = extraerVincXBin(xBin, parmsRelacionados);
		//estructura pseudoGenes
		AdmRelacion r = AdmRelacion.getInstance();
    	r.nuevaBusqueda();
    	for(AlelosVinculados vinc: listaVinculos) {
    		for(Alelo alelo: vinc.getAleloVinculado())
    			r=datos.buscarValor(alelo.posicion, alelo.valor);
    		if (!r.existeRelacion())
    			cant++;
    	}
//			for (int g=0;g<xBin[0].length;g++)
//				if (parametros.charAt(g)=='1') {
//					if (p.parDeAlelos[0]==null) {
//						p.parDeAlelos[0] = new Alelo();
//						p.parDeAlelos[0].posicion=g;
//						p.parDeAlelos[0].valor=xBin[i][g];
//					} else {
//						p.parDeAlelos[1] = new Alelo();
//						p.parDeAlelos[1].posicion=g;
//						p.parDeAlelos[1].valor=xBin[i][g];
//						listaAlelos.add(p);
//					}
//				}
			// recorro la lista de ParDeAlelos para ver si coinciden.
//			for(ParDeAlelos par: listaAlelos) {
//				cant+=relacionValida(par) ? 0 : 1;
//			}
//		}
		return cant;
	}
	private List<Integer> formatearRestr(String parametros) {
		List<Integer> parmsRelacionados = new ArrayList<Integer>();
		for(int q=0;q<parametros.length();q++)
			if (parametros.charAt(q)=='1')
				parmsRelacionados.add(new Integer(q));
		return parmsRelacionados;
	}
	private ArrayList<AlelosVinculados> extraerVincXBin(double [][] xBin, List<Integer> pRel) {
		int indice=0;
		ArrayList<AlelosVinculados> a = new ArrayList<AlelosVinculados>();
		AlelosVinculados vinc;
		for(int i=0;i<xBin.length;i++) {
			vinc = new AlelosVinculados();
			for (int m=0;m<pRel.size();m++) {
				indice=(int) (pRel.get(m));
				vinc.alelosVinculados.add(new Alelo(indice, xBin[i][indice]));
			}
			a.add(vinc);
		}
		return a;
	}
	
//	private static boolean relacionValida(ParDeAlelos par) {
//		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
//		ArrayList<Double> lista = new ArrayList<Double>();
//		lista=datos.buscarValor(par.parDeAlelos[0].posicion, par.parDeAlelos[0].valor);
//		return true;
//	}
	private int restrDocDupl(double [][] xBin) {
		Set<String> s = new HashSet<String>();
		int noAsignadas=0;
		for(int i=0;i<xBin.length;i++)
			if (xBin[i][0]!=0) //0= Aula "N/A"
				s.add(""+xBin[i][1]+xBin[i][7]);
			else noAsignadas++;		
		return ((xBin.length-noAsignadas) - s.size());
	}
	private int restrDocHoraNoDisp(double[][] xBin) {
		//TODO leer para cada docente si el horario esta libre.
		return 0;
	}
	private int restrDocMatNoHabilita(double[][] xBin) {	
//		Set<String> s = new HashSet<String>();
//		int noAsignadas=0;
//		for(int i=0;i<xBin.length;i++)
//			if (xBin[i][10]!=0)  { //0= Aula "N/A"
//				s.add(""+xBin[i][3]);
//				noAsignadas++;
//			}
//		return ((xBin.length-noAsignadas) != s.size());
		return 0;
	}
	private int restrMatDuplica(double[][] xBin) {
//		Set<String> s = new HashSet<String>();
//		for(int i=0;i<xBin.length;i++)
//			s.add(""+xBin[i][3]);
//		return (xBin.length != s.size());
		return 0;
	}
	public static void main(String[] args){
		ProblemaGdarim i=new ProblemaGdarim();
	}
}