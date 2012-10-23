package moea;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.*;

class ErrorDeParametro extends Exception{}
class PoblacionDemasiadoChicaException extends ErrorDeParametro{}
class NumFuncionesEvalDemasiadoChicaException extends  ErrorDeParametro{}
class NumObjetivosDemasiadoChicoException extends ErrorDeParametro{}
class ValorEpsilonNegativoException extends ErrorDeParametro{}
class CantidadRestriccionesNegativoException extends ErrorDeParametro{}
class CantidadVarNegativaException extends ErrorDeParametro{}
class LimitesVarRealException extends ErrorDeParametro{}
class LimitesProbCruzaVarRealException extends ErrorDeParametro{}
class LimitesProbMutacVarRealException extends ErrorDeParametro{}
class LimitesEtaCruzaException extends ErrorDeParametro{}
class LimitesEtaMutacionException extends ErrorDeParametro{}
class CantidadVarBinariaException extends ErrorDeParametro{}
class CantidadBitsVarBinariaException extends ErrorDeParametro{}
class LimitesVarBinariaException extends ErrorDeParametro{} 
class NoActividadPendiente extends ErrorDeParametro{} 
class LimitesProbCruzaVarBinariaException  extends ErrorDeParametro{} 
class LimitesProbMutacionVarBinariaException  extends ErrorDeParametro{} 	
class LimitesUmbralMismoHiperBoxException  extends ErrorDeParametro{} 
class LimitesUmbralDistanciaException  extends ErrorDeParametro{} 
class LimitesUmbralDistFitnessException  extends ErrorDeParametro{} 
class LimitesUmbralIndiceRestr extends ErrorDeParametro{}

public abstract class Problema {
	// le agregue double restric[]
	//----------------------parametros asignables al problema
	public int tamPoblacion, cantObjetivos, numFEval, cantRestricciones,cantVarReales,cantVarBinarias;
	public double[] obj, epsilon, minValor,minValReal,maxValReal,minValBinaria,maxValBinaria,constr,cantBits;
	public double probCruzaVarBinarias,probMutacionVarBinarias,etaCruza,etaMutacion,umbralMismoHBox, umbralDistancia, umbralDistFitness;
	public float indRestric;
	private PrintWriter fPoblacionInicial,fPoblacionFinal,fPoblacionOptima,
						fDetalleTodasLasGeneraciones;
	private BufferedReader fParametrosDeTrabajo , fFitnessDeTrabajo;
	private static final String POBLACION_INICIAL="initial_pop.out",
								POBLACION_FINAL="final_pop.out",
								POBLACION_OPTIMA="final_archive.out",
								DETALLE_TODAS_LAS_GENERACIONES="all_archive.out",
								PARAMETROS_DE_TRABAJO="params.in",
								FITNESS_DE_TRABAJO="fitness.in",
								TAM_POBLACION="tamPoblacion";
	public abstract void asignarObjetivos (double[] xBin, double[] restriccion);
	public abstract void asignarObjetivos (double[] xBin);
	public abstract void asignarObjetivos (double[][] xBin);
	public abstract void evaluarFuncionesObjetivo (double[] restriccion,int[] valObj);
	public abstract int evaluarFuncionesObjetivo (double[][] xBin, int[] valorFnObjetivo);
	private class Indice {
		String parametro;
		int idTipoParam;
		Indice(String parametro, int idTipoParam){
			this.parametro=parametro;
			this.idTipoParam=idTipoParam;
		}
		public String toString(){ 
			return "Parametro ("+parametro+"). ID ("+idTipoParam+"). ";
		}
	}

	private class CompTypeComparator implements Comparator {
  		public int compare(Object o1, Object o2) {
    		return ((Indice)o1).parametro.compareToIgnoreCase(((Indice)o2).parametro);
  		}
  	}
	public Iterator<TFitness> dameIteratorFitness() {
		return defFitnessMoea.iterator();
	}
	private ArrayList<String> idCamposTablaRel=new ArrayList<String>();
	private Map<Indice,Object> valoresParamMoea=new TreeMap<Indice,Object>(new CompTypeComparator());
	private	String [][] defParametrosMoea={
		{"tamPoblacion", "java.lang.Integer"},
		{"numFEval","java.lang.Integer"		},
		{"cantObjetivos","java.lang.Integer"},
		{"epsilon","java.lang.Double"},
		{"minValor","java.lang.Double"	},
		{"cantRestricciones","java.lang.Integer"	},
		{"probCruzaVarBinarias","java.lang.Double"	},
		{"probMutacionVarBinarias","java.lang.Double"	},
		{"etaCruza","java.lang.Double"	},
		{"etaMutacion","java.lang.Double"	},
		{"cantVarBinarias","java.lang.Integer"	},
		{"cantBits","java.lang.Integer"	},	
		{"minValBinaria","java.lang.Double"	},
		{"maxValBinaria","java.lang.Double"	},
		{"umbralMismoHBox","java.lang.Double"	},		
		{"umbralDistancia","java.lang.Double"	},		
		{"umbralDistFitness","java.lang.Double"	},
		{"indRestric", "java.lang.Integer"},
	};
	protected ArrayList<TFitness> defFitnessMoea=new ArrayList<TFitness>();
	
	public void configurarProblema(){
		Log l = Log.getInstance();
		configurarArchivos();
		configurarParametros(Constantes.PARAMETROS);
		configurarParametros(Constantes.FITNESS);
		l.loguear(Tipo_Log.DATOS, "Fitness: " + defFitnessMoea.toString());
		asignarParametros();
		validarParametros();		
	}
	public int buscarIndiceXCampo(String campo) {
		for (int a=0;a<idCamposTablaRel.size();a++)
			if (idCamposTablaRel.get(a).equalsIgnoreCase(campo))
				return a;
		return 0;
	}
	private void configurarArchivos(){
		try {
			fPoblacionInicial=new PrintWriter(new BufferedWriter(new FileWriter(ConfigAG.PATH_MOEA+POBLACION_INICIAL)));
			fPoblacionInicial.println("Este archivo contiene la poblacion inicial");
		
			fPoblacionFinal=new PrintWriter(new BufferedWriter(new FileWriter(ConfigAG.PATH_MOEA+POBLACION_FINAL)));
			fPoblacionFinal.println("Este archivo contiene la polación final");
		
			fPoblacionOptima=new PrintWriter(new BufferedWriter(new FileWriter(ConfigAG.PATH_MOEA+POBLACION_OPTIMA)));
		
			fDetalleTodasLasGeneraciones=new PrintWriter(new BufferedWriter(new FileWriter(ConfigAG.PATH_MOEA+DETALLE_TODAS_LAS_GENERACIONES)));
			fDetalleTodasLasGeneraciones.println("Este archivo contiene el detalle de todas las poblaciones");
		
			fParametrosDeTrabajo=new BufferedReader(new FileReader(ConfigAG.PATH_MOEA+PARAMETROS_DE_TRABAJO));
			fFitnessDeTrabajo=new BufferedReader(new FileReader(ConfigAG.PATH_MOEA+FITNESS_DE_TRABAJO));
		
		} catch (IOException e){System.err.println("Error al abrir archivo"+e);
		}
	}
	PrintWriter verArchivoDETALLE_TODAS_LAS_GENERACIONES(){
		return fDetalleTodasLasGeneraciones;
	}
	PrintWriter verArchivoPOBLACION_FINAL(){
		return fPoblacionFinal;
	}
	PrintWriter verArchivoPOBLACION_OPTIMA(){
		return fPoblacionOptima;
	}
	private void configurarParametros(String tipo){
		String linea, parametro, muta;
		BufferedReader fileAux=null;
		int nroObjetivo=0;
		if (tipo==Constantes.PARAMETROS)
			fileAux=fParametrosDeTrabajo;
		else
			if (tipo==Constantes.FITNESS)
				fileAux=fFitnessDeTrabajo;
		try {
			while ((linea=fileAux.readLine())!=null){
				if (linea.length()==0) continue;
					switch (linea.charAt(0)){
						case '#': //es comentario de linea y se saltea
							break;
						case '@': //es parámetro y se procesa
							if (linea.indexOf("=")<0)  break; // formato invalido
							parametro=linea.substring(linea.indexOf("@")+1, linea.indexOf("="));
							linea=linea.substring(linea.indexOf("=")+1,linea.length());
							cargarParametro(parametro, linea, tipo, nroObjetivo, "");
							break;
						case '*': // relacion entre nombres de campos e indices del gen
							idCamposTablaRel.add(linea.substring(1, linea.indexOf("=")));
//							parametro=linea.substring(linea.indexOf("*")+1, linea.indexOf("="));
//							linea=linea.substring(linea.indexOf("=")+1,linea.length());
//							cargarParametro(parametro, linea, tipo, nroObjetivo);
							break;
						case '>': // fitness o restricciones
							if	(linea.substring(1,2).equalsIgnoreCase("1") ||
								 linea.substring(1,2).equalsIgnoreCase("0"))
								nroObjetivo=Integer.parseInt(linea.substring(1, 2));
							else
//							if (((linea.substring(linea.indexOf(">")+1, 1)).equalsIgnoreCase("1")) ||
//									 (linea.substring(linea.indexOf(">")+1, 1)).equalsIgnoreCase("2"))
//								nroObjetivo=Integer.parseInt(linea.substring(linea.indexOf(">")+1, 1));
//							else
								nroObjetivo=Integer.MAX_VALUE;
							parametro=linea.substring(3, linea.indexOf("="));
							linea=linea.substring(linea.indexOf("=")+1,linea.length());
							muta=linea.substring(linea.indexOf(";")+1,linea.length());
							cargarParametro(parametro, linea, tipo, nroObjetivo, muta);
							break;
						default:  // no es valido y se saltea
							break;
					}
			}			
		} catch (IOException e){
			System.err.println("Error al leer el archivo de parámetros"+e);
		}
	}
//	private void configurarParametros(String tipo){
//		String linea, parametro;
//		BufferedReader fileAux=null;
//		int nroObjetivo=0;
//		if (tipo==Constantes.PARAMETROS)
//			fileAux=fParametrosDeTrabajo;
//		else
//			if (tipo==Constantes.FITNESS)
//				fileAux=fFitnessDeTrabajo;
//		try {
//			while ((linea=fileAux.readLine())!=null){
//				if (linea.length()==0) continue;
//					switch (linea.charAt(0)){
//						case '#': //es comentario de linea y se saltea
//							break;
//						case '@': //es parámetro y se procesa
//							if (linea.substring(0, 2).equalsIgnoreCase("@@")) {
//							//relacionar campo de tablas con indice de Gen
//								idCamposTablaRel.add(linea.substring(linea.indexOf("@@")+2, linea.indexOf("=")));
//								//idCamposTablaRel.get(1);
//							} else {
//								if (linea.indexOf("=")<0)  break; // formato invalido
//								if (((linea.substring(linea.indexOf("@")+1, 1)).equalsIgnoreCase("1")) ||
//									 (linea.substring(linea.indexOf("@")+1, 1)).equalsIgnoreCase("2"))
//									nroObjetivo=Integer.parseInt(linea.substring(linea.indexOf("@")+1, 1));
//								else
//									nroObjetivo=Integer.MAX_VALUE;
//								parametro=linea.substring(linea.indexOf("@")+2, linea.indexOf("="));
//								linea=linea.substring(linea.indexOf("=")+1,linea.length());
//								cargarParametro(parametro, linea, tipo, nroObjetivo);
//							}
//						default:  // no es valido y se saltea
//							break;
//					}
//			}			
//		} catch (IOException e){
//			System.err.println("Error al leer el archivo de parámetros"+e);
//		}
//	}

	private void cargarParametro(String parametro, String valor, String tipo, int nroObjetivo, String muta){
    	Log l = Log.getInstance();
    	String valorPenalizado="";
    	int indice=0;
    	ArrayList<String> campos = new ArrayList<String>();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "cargarParametro");
    	l.loguear(Tipo_Log.DATOS, "parametro:"+parametro+", valor:"+valor+", tipo:"+tipo);
    	if (tipo==Constantes.PARAMETROS) {
    		for (int i=0;i<defParametrosMoea.length;i++)
    			if (parametro.contains(defParametrosMoea[i][0]))
    				try {
    					Constructor cc=Class.forName(defParametrosMoea[i][1]).getConstructor(String.class);
    					valoresParamMoea.put(new Indice(parametro,i),cc.newInstance(valor));
    				}catch(ClassNotFoundException re){
    					System.err.println("Error en declaracion de tipo de parametro al procesar el valor: "+valor+"del parametro:"+parametro);
    				}catch(NoSuchMethodException re){
    					System.err.println("Error de constructor al procesar el valor: "+valor+"del parametro:"+parametro);
    				}catch(InstantiationException re){
    					System.err.println("Error de instanciacion del tipo al procesar el valor: "+valor+"del parametro:"+parametro);
    				}catch(IllegalAccessException re){
    					System.err.println("Acceso ilegal al metodo constructor al procesar el valor: "+valor+"del parametro:"+parametro);
    				}catch(InvocationTargetException re){
    					System.err.println("Error de parametros de constructor al procesar el valor: "+valor+"del parametro:"+parametro);
    				}
    	}
    	if (tipo==Constantes.FITNESS) {
    		//cargarParametro(parametro, linea, tipo);
    		if (valor.indexOf(";")==-1)
    			valorPenalizado=valor.substring(valor.indexOf(":")+1, valor.length());
    		else {
    			valorPenalizado=valor.substring(valor.indexOf(":")+1, valor.indexOf(";"));
    			valor=valor.substring(0, valor.indexOf(";"));
    		}
    //		valorPenalizado=valor.substring(valor.indexOf(":")+1, valor.length());
    		for(int a=0;(!(valor.equalsIgnoreCase(valorPenalizado)));a++) {
    			indice=(valor.indexOf(",")==-1)? valor.indexOf(":") : valor.indexOf(",");
    			campos.add(valor.substring(0, indice));
    			valor=valor.substring(indice+1, valor.length());
    		}
    		this.defFitnessMoea.add(new TFitness(tipo, parametro, valorPenalizado, campos, nroObjetivo, muta));
    	}
	}
	private void asignarParametros(){
		tamPoblacion=(Integer)valoresParamMoea.get(getIndexValue("tamPoblacion"));
		cantObjetivos=(Integer)valoresParamMoea.get(getIndexValue("cantObjetivos"));
		numFEval=(Integer)valoresParamMoea.get(getIndexValue("numFEval"));
		cantRestricciones=(Integer)valoresParamMoea.get(getIndexValue("cantRestricciones"));
		cantVarBinarias=(Integer)valoresParamMoea.get(getIndexValue("cantVarBinarias"));
		cantBits=new double[cantVarBinarias];
		for (int i=1;i<=cantBits.length;i++)
			cantBits[i-1]=(Integer)valoresParamMoea.get(getIndexValue("cantBits"+i));
		obj=new double[cantObjetivos];
		epsilon=new double[cantObjetivos];
		for (int i=1;i<=epsilon.length;i++)
			epsilon[i-1]=(Double)valoresParamMoea.get(getIndexValue("epsilon"+i));
		minValor=new double[cantObjetivos];
		for (int i=1;i<=minValor.length;i++)
			minValor[i-1]=(Double)valoresParamMoea.get(getIndexValue("minValor"+i));
		minValBinaria=new double[cantVarBinarias];
		for (int i=1;i<=minValBinaria.length;i++)
			minValBinaria[i-1]=(Double)valoresParamMoea.get(getIndexValue("minValBinaria"+i));
		maxValBinaria=new double[cantVarBinarias];
		umbralMismoHBox=(Double)valoresParamMoea.get(getIndexValue("umbralMismoHBox"));
		umbralDistancia=(Double)valoresParamMoea.get(getIndexValue("umbralDistancia"));
		umbralDistFitness=(Double)valoresParamMoea.get(getIndexValue("umbralDistFitness"));
		for (int i=1;i<=maxValBinaria.length;i++)
			maxValBinaria[i-1]=(Double)valoresParamMoea.get(getIndexValue("maxValBinaria"+i));
		probCruzaVarBinarias=(Double)valoresParamMoea.get(getIndexValue("probCruzaVarBinarias"));
		probMutacionVarBinarias=(Double)valoresParamMoea.get(getIndexValue("probMutacionVarBinarias"));
		etaCruza=(Double)valoresParamMoea.get(getIndexValue("etaCruza"));
		etaMutacion=(Double)valoresParamMoea.get(getIndexValue("etaMutacion"));
		indRestric=(Integer)valoresParamMoea.get(getIndexValue("indRestric"));
	}
	private Indice getIndexValue(String v){
		for (Indice i:valoresParamMoea.keySet())
			if (i.parametro.contains(v)) return i;
		return null;//no hallado
	}
	private void validarParametros(){
		try {
			for (Indice i: valoresParamMoea.keySet()){
				if (defParametrosMoea[i.idTipoParam][0].equals("tamPoblacion")){
					if ((Integer)valoresParamMoea.get(i)<2)
						throw new PoblacionDemasiadoChicaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("numFEval")){
					if ((Integer)valoresParamMoea.get(i)<2)
						throw new NumFuncionesEvalDemasiadoChicaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("cantObjetivos")){
					if ((Integer)valoresParamMoea.get(i)<2)
						throw new NumObjetivosDemasiadoChicoException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].contains("epsilon")){
					if ((Double)valoresParamMoea.get(i)<=0.0d)
						throw new ValorEpsilonNegativoException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("cantRestricciones")){
					if ((Integer)valoresParamMoea.get(i)<0)
						throw new CantidadRestriccionesNegativoException();
					continue;
				}								
				if (defParametrosMoea[i.idTipoParam][0].equals("etaCruza")){
					if ((Double)valoresParamMoea.get(i)<5.0d||
						(Double)valoresParamMoea.get(i)>20.0d)
						throw new LimitesEtaCruzaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("etaMutacion")){
					if ((Double)valoresParamMoea.get(i)<5.0d||
						(Double)valoresParamMoea.get(i)>50.0d)
						throw new LimitesEtaMutacionException();
					continue;
				}	
				if (defParametrosMoea[i.idTipoParam][0].equals("cantVarBinarias")){
					if ((Integer)valoresParamMoea.get(i)<0)
						throw new CantidadVarBinariaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].contains("cantBits")){
					if (((Integer)valoresParamMoea.get(i))<1)
						throw new CantidadBitsVarBinariaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].contains("maxValBinaria")){
					if ((Double)valoresParamMoea.get(i)<=
						(Double)valoresParamMoea.get(getIndexValue("minValBinaria")))
						throw new LimitesVarBinariaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("probCruzaVarBinarias")){
					if ((Double)valoresParamMoea.get(i)<0.6d||
						(Double)valoresParamMoea.get(i)>1.0d)
						throw new LimitesProbCruzaVarBinariaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("probMutacionVarBinarias")){
					if ((Double)valoresParamMoea.get(i)<0.0d||
						(Double)valoresParamMoea.get(i)>1.0d)
						throw new LimitesProbMutacionVarBinariaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("umbralMismoHBox")){
					if ((Double)valoresParamMoea.get(i)<0.0d||
						(Double)valoresParamMoea.get(i)>10000.0d)
						throw new LimitesUmbralMismoHiperBoxException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("umbralDistancia")){
					if ((Double)valoresParamMoea.get(i)<0.0d||
						(Double)valoresParamMoea.get(i)>100.0d)
						throw new LimitesUmbralDistanciaException();
					continue;
				}
				if (defParametrosMoea[i.idTipoParam][0].equals("umbralDistFitness")){
					if ((Double)valoresParamMoea.get(i)<0.0d||
						(Double)valoresParamMoea.get(i)>100.0d)
						throw new LimitesUmbralDistFitnessException();
					continue;
				}				
			}	
			if ((Integer)valoresParamMoea.get(getIndexValue("cantVarBinarias"))==0)
				throw new NoActividadPendiente();
			if (((Integer)valoresParamMoea.get(getIndexValue("indRestric"))<0) ||
				((Integer)valoresParamMoea.get(getIndexValue("indRestric"))>1000))
				throw new LimitesUmbralIndiceRestr();
			
		} catch (PoblacionDemasiadoChicaException e){
			System.err.println("La poblacion debe ser mayor a 2.\n"+e);
		} catch (NumFuncionesEvalDemasiadoChicaException e){
			System.err.println("La cantidad de funciones de evaluacion debe ser al menos igual al tamaño de la poblacion.\n"+e);
		} catch (NumObjetivosDemasiadoChicoException e){
			System.err.println("La cantidad de objetivos debe ser mayor a 2.\n"+e);
		} catch (ValorEpsilonNegativoException e){
			System.err.println("El valor de tolerancia mínimo del objetivo debe ser mayor a 0.0.\n"+e);
		} catch (CantidadRestriccionesNegativoException e){
			System.err.println("La cantidad de restricciones debe ser mayor o igual a 0.\n"+e);
		} catch (LimitesEtaCruzaException e){
			System.err.println("Indice de distribución de variable real SBX para cruza debe estar en el rango [5.0 : 20.0].\n"+e);
		} catch (LimitesEtaMutacionException e){
			System.err.println("Indice de distribución de variable polinomial para mutacion debe estar en el rango [5.0 : 50.0].\n"+e);
		} catch (CantidadVarBinariaException e){
			System.err.println("Cantidad de variables binarias debe ser >=0.\n"+e);
		} catch (CantidadBitsVarBinariaException e){
			System.err.println("Cantidad de bits para las variables binarias debe ser >0.\n"+e);
		} catch (LimitesVarBinariaException e){
			System.err.println("El limite superior de las variables binarias debe ser un valor mayor estricto al inferior.\n"+e);
		} catch (LimitesProbCruzaVarBinariaException e){
			System.err.println("El rango para la probabilidad de cruza en variables primarias es [0.6 : 1.0].\n"+e);
		} catch (LimitesProbMutacionVarBinariaException e){
			System.err.println("El rango para la probabilidad de mutacion en variables primarias es [0.0 : 1.0].\n"+e);
		} catch (NoActividadPendiente e){
			System.err.println("No hay actividad por realizar (no se declararon variables binarias ni reales.\n");
		} catch (LimitesUmbralMismoHiperBoxException e){
			System.err.println("El limite del umbral en mismo hiperbox esta mal definido.\n");
		} catch (LimitesUmbralDistanciaException e){
			System.err.println("El limite del umbral en distancia entre individuos fue superado.\n");
		} catch (LimitesUmbralDistFitnessException e){
			System.err.println("El limite del umbral en distancia por fitness fue superado.\n");
		} catch (LimitesUmbralIndiceRestr e){
			System.err.println("El limite del umbral del indice de estabilizacion es erroneo.\n");
		}	
	}
	public ArrayList<String> getIdCamposTablaRel() {
		return idCamposTablaRel;
	}
	public void setIdCamposTablaRel(ArrayList<String> idCamposTablaRel) {
		this.idCamposTablaRel = idCamposTablaRel;
	}
}