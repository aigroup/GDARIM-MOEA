package moea;

import log.Log;
import log.Tipo_Log;
import logica.ag.*;
import java.util.*;
 
public class Individuo implements Comparator, Cloneable {
	public static Random rnd=new Random();
	private static double maxValBinaria,minValBinaria;
	private int cantBits;
	public double[] objetivo;
	public double[][] xBin;
	public int[] valorFnObjetivo;
	public double violacionRestriccion;//    ?????
	public int cantRestricciones;
	public double[] restriccion;//    ?????
	public double fitness;
	public double score;
	public double scoreAcum;
	public Gen[] gen;

	public Object clone() {
		Object obj=null;
		try {
			obj=super.clone();
		}catch(CloneNotSupportedException ex){
			System.err.println("No se puede cclonar el individuo");
		}
		return obj;
	}
	public Individuo() {}	
	public Individuo(Problema p) {
		configurar(p);
	}		
	public double[][] getXBin() {
		return xBin;
	}			
	public Individuo configurar(Problema p){
		for (int i=0,cantBits=0;i<p.cantBits.length;cantBits+=p.cantBits[i++]);
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
        gen = new Gen[datos.getCantAulas()];
		xBin=new double[datos.getCantAulas()][p.cantVarBinarias];
		objetivo=new double[p.cantObjetivos];
		valorFnObjetivo=new int[p.cantObjetivos];
		restriccion=new double[p.cantRestricciones];
	    for (int ind=0;ind<(datos.getCantAulas());ind++) {
	        gen[ind]=new Gen(new GrayCode[p.cantVarBinarias]);
	        for (int i=0;i<p.cantVarBinarias;i++)
	        	gen[ind].agregarParam(new GrayCode(new BitSet((int)p.cantBits[i])), i);
			// esta linea hay que modificarla por la longitud del bitset de cada parametro.
	    }
		return this;
	}
	public double calculoFitness() {
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "calculoFitness (del Individuo)");
		// por ahora solo sumamos las funciones de evaluacion
		fitness=0;
		for (int i=0; i<valorFnObjetivo.length;i++)
			fitness+=(double) valorFnObjetivo[i];
		l.loguear(Tipo_Log.DATOS, "Fitness individuo "+fitness);
		return fitness;
	}
	public Gen[] getGen() {
		if (gen==null) {
			Log l = Log.getInstance();
			l.loguear(Tipo_Log.DATOS, "gen que apunta a null...");
		}
		return gen;
	}
	public void inicializar(int dia){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "inicializar (al Individuo) menos los recursos");
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		datos.reiniciarAulas();
		datos.obtenerProxCombinacRandomSinNA();
		l.loguear(Tipo_Log.INFORMACION, "Generar nueva combinacion de parámetros");
        for (int ind=0;ind<(this.getGen().length);ind++, datos.obtenerProxCombinacRandomSinNA()) {
            gen[ind].agregarParam(new GrayCode((BitSet) datos.obtProxAula().getResult()), 0);
            for (int j=1;j<(gen[0].largo()-Constantes.CANT_RECURSOS);j++) {
            	//System.out.println(" vamos por el gen: "+ind+ ", y por el parametro: "+j);
            	if (j==9) //significa que voy a tomar un dia random. esta mal!!
            		gen[ind].agregarParam(new GrayCode(dia),j);
            	else
            		gen[ind].agregarParam(new GrayCode((BitSet) (((GrayCode) datos.getMemoriaDat()[j-1]).getResult())), j);
            // esta linea hay que modificarla por la longitud del bitset de cada parametro.
            }
        }
        l.loguear(Tipo_Log.INFORMACION, "Individuo generado");
		datos.obtenerProxCombinacRandomSinNA();
	}
	public void decodificar(){
		// decodifica el valor binario
		// acum tiene la cantidad de valores que se puede representar con cantBits.
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "decodificar (al Individuo)");
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
        for (int iAulas=0;iAulas<datos.getCantAulas();iAulas++)
          for (int j=0;j<gen[0].largo();j++)
              xBin[iAulas][j]=bitSetToDecimal(gen[iAulas].getGen()[j].getResult());
//		Double XBin[] = new Double[xBin.length];
//		for (int k=0;k<xBin.length;XBin[k]=(new Double(xBin[k])),k++);
       // System.out.println(""+Arrays.asList(xBin));
	}
	public void asignarObjetivos(Problema p) {
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "asignarObjetivos (al Individuo)");
		objetivo[0]=p.obj[0]; // objetico: cantidad de aulas sin usar
		//optimizacion de aulas: todas las aulas con profesores
        objetivo[1]=p.obj[1]; // objetivo horarios sin usar
        //optimizacion de profesores: todos sus horarios en uso
	}
	public void evaluarFuncionesObjetivo(Problema p) {
		Log l = Log.getInstance();
		int cantRest=0;
		l.loguear(Tipo_Log.NOMBRE_METODO, "evaluarFuncionesObjetivo (del Individuo)");
		cantRest=p.evaluarFuncionesObjetivo(xBin, valorFnObjetivo);
		cantRestricciones+=cantRest;
		if (cantRest>0)
			this.violacionRestriccion=1;
	}
	public int bitSetToDecimal(BitSet b)
	{		
		int number = 0;
	    for (int i=0; i < b.length(); i++)	
			if (b.get(i))
				number += Math.pow((double)2, (double)i);
		return number;
	}
	public int compare(Object o1, Object o2) {
		//TODO: agregar logica de que el primero sea mejor y el segundo peor.
		Individuo i1 = (Individuo) o1;
		Individuo i2 = (Individuo) o2;
		if ((i1.valorFnObjetivo[0] < i2.valorFnObjetivo[0]) &&
			(i1.valorFnObjetivo[1] < i2.valorFnObjetivo[1]))
			return -1;
		else
			if ((i1.valorFnObjetivo[0] == i2.valorFnObjetivo[0]) &&
				(i1.valorFnObjetivo[1] == i2.valorFnObjetivo[1]))
				return 0;
			else
				return 1;
	}
		
	public boolean equals(Object o1, Object o2) {
		return compare(o1, o2)==0;
	}
	public String toString() {
		return ""+Arrays.asList(gen);
	}
	public static void main(String[] args){
		Individuo i=new Individuo();
		for (int ind=0;ind<=3;ind++)
		    i.gen[ind]=new Gen(new GrayCode[3]);
		i.xBin=new double[2][3];
		i.inicializar(1);
		i.cantBits = 3;
		//i.minValBinaria = 0.0;
		//i.maxValBinaria = 1000.0;
		i.decodificar();
	}
}