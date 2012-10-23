package moea;

import io.FacadeDatos;

import java.io.*;
import java.util.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.*;

public class Poblacion {
	private static Random rnd=new Random();
	private static PoblacionElite pElite=new PoblacionElite();
	private Individuo[] poblacion;
	private Individuo[] poblacionHijos;
	private int pHijos=0;
	private Problema p;
	private List<IndividuoElite> elite=new ArrayList<IndividuoElite>();
	private ArrayList<Integer> listaPadres;
	private double fitnessPoblacion;
	private int idxPadre1, idxPadre2;
	private float indiceRestric; 
	private final static int CANTIDAD_HIJOS=2;//cantidad hijos por cruza 
	private Individuo[] hijo=new Individuo[CANTIDAD_HIJOS];
	private List<Integer> indivNoDominados=new ArrayList<Integer>();
	
	private class IndividuoElite{
		Individuo ind, padre, hijo;
	}
	public Problema getProblema() {
		return p;
	}
	public Individuo[] getPoblacion() {
		return poblacion;
	}
	public void crearPoblacion(Problema p){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "crearPoblacion");
		this.p=p;
		indiceRestric=0;
		poblacion=new Individuo[p.tamPoblacion];
		for (int i=0;i<poblacion.length;i++)
			poblacion[i]=new Individuo(p);
		fitnessPoblacion=0;
		listaPadres=new ArrayList<Integer>();
	} 
	public void evaluarPoblacion(int dia){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "evaluarPoblacion");
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		for (Individuo i: poblacion){
 			i.inicializar(dia);
 			i.decodificar();
 			i.asignarObjetivos(p);
 			i.evaluarFuncionesObjetivo(p);
 			fitnessPoblacion+=i.calculoFitness();
 			l.loguear(Tipo_Log.DATOS, "Fitness poblacion acum. "+fitnessPoblacion);
		}
		calcularScorePoblacion();
		l.loguear(Tipo_Log.CONSOLA, "Sali de la evaluacion del for");
	}	
	// primero se debe calcular el fitness total de la poblacion
	// por eso hay que hacer una segunda repasada de la poblacion para este metodo
	public void calcularScorePoblacion() {
		Log l = Log.getInstance();
		for(int i=0;i<poblacion.length;i++) {
			indiceRestric+=poblacion[i].cantRestricciones;
			poblacion[i].score=poblacion[i].fitness/fitnessPoblacion;
			if (i == 0) poblacion[i].scoreAcum=poblacion[i].score;
			else if (i == poblacion.length)	poblacion[i].scoreAcum=1;
				 else poblacion[i].scoreAcum=poblacion[i].score+poblacion[i-1].score;
		}
		l.loguear(Tipo_Log.DATOS, "Score de la poblacion: " + fitnessPoblacion);
	}
	public void reportarPoblacion(PrintWriter fPoblacionInicial){
		// vuelca la poblacion actual en archivo
		for (int i=0;i<poblacion.length;i++){
			for (int j=0; j<poblacion[i].objetivo.length;j++)
				fPoblacionInicial.print(poblacion[i].objetivo[j]+"\t");
			for (int j=0; j<poblacion[i].restriccion.length;j++)
				fPoblacionInicial.print(poblacion[i].restriccion[j]+"\t");
			for (int j=0; j<poblacion[i].xBin.length;j++)
				fPoblacionInicial.print(poblacion[i].xBin[j]+"\t");
			for (int j=0; j<poblacion[i].gen.length;j++)
			    for (int k=0; k<poblacion[i].gen[0].getGen().length;k++)
					fPoblacionInicial.print(poblacion[i].gen[j].getGen()[k]+"\t");
			fPoblacionInicial.println(poblacion[i].violacionRestriccion);
		}	
	}
	public void reportarElite(PrintWriter fDetalleTodasLasGeneraciones){
		//vuelca población posible y no dominada en archivo
		Individuo i;pElite.iniciarIterador();
		while ((i=pElite.iterarElite())!=null){
			for (int j=0;j<i.objetivo.length;j++)
				fDetalleTodasLasGeneraciones.print(i.objetivo[j]+"\t");
			for (int j=0;j<i.restriccion.length;j++)
				fDetalleTodasLasGeneraciones.print(i.restriccion[j]+"\t");
			for (int j=0;j<i.xBin.length;j++)
				fDetalleTodasLasGeneraciones.print(i.xBin[j]+"\t");
			for (int j=0; j<i.gen.length;j++)
                for (int k=0; k<i.gen[0].getGen().length;k++)
                    fDetalleTodasLasGeneraciones.print(i.gen[j].getGen()[k]+"\t");            
			fDetalleTodasLasGeneraciones.println(i.violacionRestriccion);
		}		
	}
 	public void actualizarElite(){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "actualizarElite");
 		for (int i=0;i<poblacion.length;i++) {
 			pElite.actualizarElite(poblacion[i],  p);
 		}
    	l.loguear(Tipo_Log.DATOS, "Tamaño actual de la Elite: "+pElite.tamanioElite());
	}
//2008-02-11: Se modifica el método por uno similiar al de mutación
//	public void seleccionarPadres(){
//		idxPadre1=rnd.nextInt(poblacion.length);
//		while ((idxPadre2=rnd.nextInt(poblacion.length))==idxPadre1);		
//	}
 	public float getIndiceRestric() {
 		return indiceRestric;
 	}
	public void seleccionarPadres(){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "seleccionarPadres");
		double valor;
		int iNull=0;
		listaPadres.clear();
		l.loguear(Tipo_Log.DATOS, "*******************************************");
		l.loguear(Tipo_Log.DATOS, "Probabilidad de seleccion: " + p.probCruzaVarBinarias);
		l.loguear(Tipo_Log.DATOS, "Cantidad de individuos: " + poblacion.length);
		l.loguear(Tipo_Log.DATOS, "Individuos a seleccionar: " + (poblacion.length * p.probCruzaVarBinarias));
		l.loguear(Tipo_Log.DATOS, "*******************************************");
		l.loguear(Tipo_Log.DATOS, "poblacion" + poblacion);			
		for (int i=0;i<poblacion.length;i++){
			valor = rnd.nextFloat();				
			if (valor < p.probCruzaVarBinarias) {
				l.loguear(Tipo_Log.DATOS, "Seleccionar ind:" + valor + " individuo: "+i);
				try {
					if (poblacion[i] != null)
						listaPadres.add(new Integer(i));
					else
						l.loguear(Tipo_Log.ERROR, "Hay problemas 1");
				} catch (Exception e) {
					l.loguear(Tipo_Log.EXCEPCION, "Hay problemas 2");
				}
			}
		}
		l.loguear(Tipo_Log.DATOS, "Cantidad de padres seleccionados: "+listaPadres.size());
	}		
	private void iniciarDescendencia() {
		hijo[0] = new Individuo();
		hijo[1] = new Individuo();
		hijo[0].configurar(p);
		hijo[1].configurar(p);
	}
	private void emparejarPadres() {
		if (listaPadres.size()%2!=0) {
	    	Log l = Log.getInstance();
	    	l.loguear(Tipo_Log.NOMBRE_METODO, "emparejarPadres");	
	    	l.loguear(Tipo_Log.DATOS, "  Se emparejan... antes: "+listaPadres.size());
			listaPadres.add(listaPadres.get(rnd.nextInt(listaPadres.size())));
			l.loguear(Tipo_Log.DATOS, "  ... despues: "+listaPadres.size());
		}
	}
	public void desordenarPadres() {
		int[] listaPadresAux=new int[listaPadres.size()];
		Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "desordenarPadres");
		for (int i=0;i<listaPadres.size();i++) {
			listaPadresAux[buscarProxIndice(listaPadresAux)]=listaPadres.get(i);
		}
		l.loguear(Tipo_Log.DATOS, "ind ordenada: "+Arrays.asList(listaPadres).toString());
		listaPadres.clear();
        for (int i=0;i<listaPadresAux.length;i++)
            listaPadres.add(listaPadresAux[i]);
        l.loguear(Tipo_Log.DATOS, "ind desordenada: "+Arrays.asList(listaPadres));
	}
	public int buscarProxIndice(int[] listaPadresAux) {
		int i=rnd.nextInt(listaPadresAux.length);
		while (listaPadresAux[i] != 0)
			i=rnd.nextInt(listaPadresAux.length);
		return i;
	}
	private void iniciarPobHijos(int tamanio) {
		poblacionHijos=new Individuo[tamanio];
		pHijos=0;
		for (int i=0;i<tamanio;i++)
			poblacionHijos[i]=new Individuo(p);
	}
	public void cruzarPadres(){
		Log l1 = Log.getInstance();
    	l1.loguear(Tipo_Log.NOMBRE_METODO, "cruzarPadres");
		BitSet bsAux=new BitSet();
		int lugarDeCorte=0;
		emparejarPadres();
		desordenarPadres();
		iniciarPobHijos(listaPadres.size());
		for (int q=0;q<listaPadres.size()-1;q+=2) {
		// por cada par de individuos...
			iniciarDescendencia();
			lugarDeCorte=rnd.nextInt(p.cantVarBinarias);
			l1.loguear(Tipo_Log.DATOS, "    Seccion de corte, "+lugarDeCorte);
			for (int l=0;l<poblacion[0].gen.length;l++) {
			// por cada elemento Gen... (o sea, por cada aula) 
			    for (int k=0;k<poblacion[0].gen[0].getGen().length;k++) {
			    // por cada parametro de un gen...        
					if (k<lugarDeCorte) {
						bsAux=new BitSet();
						bsAux=poblacion[listaPadres.get(q)].getGen()[l].getAlelo(k);
			            hijo[0].getGen()[l].setAlelo(bsAux, k);
			            bsAux=poblacion[listaPadres.get(q+1)].getGen()[l].getAlelo(k);
			            hijo[1].getGen()[l].setAlelo(bsAux, k);
			        }
			        else {
			            bsAux=new BitSet();
			            bsAux=poblacion[listaPadres.get(q+1)].getGen()[l].getAlelo(k);
			            hijo[0].getGen()[l].setAlelo(bsAux, k);
			            bsAux=new BitSet();
			            bsAux=poblacion[listaPadres.get(q)].getGen()[l].getAlelo(k);
			            hijo[1].getGen()[l].setAlelo(bsAux, k);
			        }
			    }
			}		
			l1.loguear(Tipo_Log.DATOS, "  Lugar de corte: "+lugarDeCorte);
			l1.loguear(Tipo_Log.DATOS, "  Los padres reemplazados...");
			l1.loguear(Tipo_Log.DATOS, "    Padre1: "+poblacion[listaPadres.get(q)].getGen()[0]);
			l1.loguear(Tipo_Log.DATOS, "    Padre2: "+poblacion[listaPadres.get(q+1)].getGen()[0]);
			l1.loguear(Tipo_Log.DATOS, "  Los hijos que quedan...");
			l1.loguear(Tipo_Log.DATOS, "    Hijo1: "+hijo[0].getGen()[0]);
			l1.loguear(Tipo_Log.DATOS, "    Hijo2: "+hijo[1].getGen()[0]);
			agregarHijoCruzado(hijo[0]);
			agregarHijoCruzado(hijo[1]);
		}
	}
//	public void reemplazoPadrexHijo(Individuo padre, Individuo hijo) {
//		padre = hijo;
//	}
	public void agregarHijoCruzado(Individuo hijo) {
		poblacionHijos[pHijos++]=(Individuo)hijo;
	}
	public void mutar(float indice, Estado estado){
		int indMateria=0;
		Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "mutar");
		double valor;
		int iNull=0;
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		this.indiceRestric=0;
	   	l.loguear(Tipo_Log.DATOS, "*******************************************");
	   	l.loguear(Tipo_Log.DATOS, "Mutacion de hijos");
	   	l.loguear(Tipo_Log.DATOS, "Probabilidad de mutacion: " + (p.probMutacionVarBinarias*indice));
	   	l.loguear(Tipo_Log.DATOS, "Cantidad de individuos: " + poblacion.length);
	   	l.loguear(Tipo_Log.DATOS, "Cantidad de alelos: " + p.cantVarBinarias);
	   	l.loguear(Tipo_Log.DATOS, "Individuos a mutar: " + (poblacion.length * p.probMutacionVarBinarias));
	   	l.loguear(Tipo_Log.DATOS, "Cant. alelos a mutar: " + (poblacion.length * p.cantVarBinarias * p.probMutacionVarBinarias));
	   	l.loguear(Tipo_Log.DATOS, "*******************************************");
	   	if (estado.getClass().equals(EstadoInicial.class)) {
	   		l.loguear(Tipo_Log.CONSOLA, "Mutacion por restricciones");
	   		FacadeDatos fd = FacadeDatos.getInstance();
	   		this.indiceRestric=fd.mutarXRestriccion(this, p);
	   	}
	   	else
	   	{
	   		try {
	   			l.loguear(Tipo_Log.DATOS, "poblacion" + poblacion);
	   			for (Individuo i: poblacionHijos)
	   				if (i != null)
	   					for(int j=0;j<i.getGen()[0].largo();j++) { // por cada aula
	   						valor = rnd.nextFloat();
//						if (estado.getClass().equals(EstadoInicial.class)) {
//							l.loguear(Tipo_Log.CONSOLA, "Muta segun cada restriccion");
//							if (valor < (p.probMutacionVarBinarias*indice)) {
//								l.loguear(Tipo_Log.DATOS, "Mutar gen:" + valor);
//								mutaXRestriccion();
//							}
//							modificar por aca....							
//							
//							l.loguear(Tipo_Log.CONSOLA, "Solo muto materias");
//							if (valor < (p.probMutacionVarBinarias*indice)) {
//								l.loguear(Tipo_Log.DATOS, "Mutar gen:" + valor);
//								indMateria=p.buscarIndiceXCampo("idMat");
//								l.loguear(Tipo_Log.DATOS, "Reemplazo: "+(BitSet)(i.getGen()[j].getAlelo(indMateria)));
//								i.getGen()[j].setAlelo(datos.getParamAzar(indMateria, (BitSet)i.getGen()[j].getAlelo(indMateria)), indMateria);
//								i.getGen()[j].setRecXMateria(p);
//							}
//							
//						}
//						else
						for (int k=0;k<p.cantVarBinarias;k++) { // por cada alelo
					    	//no se puede mutar cualquier campo.
					    	//AULAS NO SE PUEDEN!!!
							if (k == Constantes.AULA)
								continue;
							valor = rnd.nextFloat();				
							if (valor < (p.probMutacionVarBinarias*indice)) {
								l.loguear(Tipo_Log.DATOS, "Mutar gen:" + valor);
								l.loguear(Tipo_Log.DATOS, "Reemplazo: "+(BitSet)(i.getGen()[j].getAlelo(k)));
								i.getGen()[j].setAlelo(datos.getParamAzar(k, (BitSet)i.getGen()[j].getAlelo(k)), k);
							}
						}
					}
				else 
					iNull++;
	   		} catch (Exception e) {
	   			l.loguear(Tipo_Log.EXCEPCION, "Error en calculo de mutacion." + e);
	   		}
	   		if (iNull>0)
	   			l.loguear(Tipo_Log.DATOS, "Cantidad de individuos en NULL: "+iNull);
	   	}
	}
//	public void mutaXRestriccion() {
//		FacadeDatos fd = FacadeDatos.getInstance();
//		TFitness tf=null;
//		for (TFitness tf: fd.)
//			indMateria=p.buscarIndiceXCampo("idMat");
//			l.loguear(Tipo_Log.DATOS, "Reemplazo: "+(BitSet)(i.getGen()[j].getAlelo(indMateria)));
//			i.getGen()[j].setAlelo(datos.getParamAzar(indMateria, (BitSet)i.getGen()[j].getAlelo(indMateria)), indMateria);
//			i.getGen()[j].setRecXMateria(p);
//		}		
//	}
	public void reportarPoblacionFinal(){
		Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "reportarPoblacionFinal");
		PrintWriter fPoblacionFinal=p.verArchivoPOBLACION_FINAL();
		for (int i=0;i<poblacion.length;i++)
			fPoblacionFinal.println(poblacion[i]);
		fPoblacionFinal.close();	
	}
	public void reportarSolucion(){
		PrintWriter fPoblacionOptima=p.verArchivoPOBLACION_OPTIMA();
		Individuo i;
		pElite.iniciarIterador();
		while ((i=pElite.iterarElite())!=null)
			fPoblacionOptima.println(i);
		fPoblacionOptima.close();
	}
	public void actualizarPoblacion(){
		//este metodo recalcula la poblacion en base a la anterior mas los seleccionados!!!
//		indivNoDominados.clear();
//		for (int i=0;i<poblacionHijos.length;i++){
//			for (int j=0;j<poblacion.length;j++)
//				verificarDominanciaEnPoblacion(poblacionHijos[i], j, p);
//			if (indivNoDominados.size()>0){
//				poblacion[indivNoDominados.get(rnd.nextInt(indivNoDominados.size()))]=
//					poblacionHijos[i];}
//		}
		Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "actualizarPoblacion");
    	l.loguear(Tipo_Log.DATOS, "Cantidad de individuos antes del proceso siniestro..." + poblacion.length);
		Individuo[] listaAux = new Individuo[poblacion.length+poblacionHijos.length];
		armarListaTotal(listaAux);
		ordenerListaPorFitness(listaAux);
		poblacion=null;
		poblacion=mejoresIndividuosDeLista(listaAux, p.tamPoblacion);
		l.loguear(Tipo_Log.DATOS, "Cantidad de individuos despues del proceso siniestro..." + poblacion.length);
		// ahora listaAux tiene poblacion + poblacionHijos
	}
	private void armarListaTotal(Individuo[] listaAux) {
		for(int i=0;i<listaAux.length;i++) {
			if (i<p.tamPoblacion)
				listaAux[i]=poblacion[i];
			else
				listaAux[i]=poblacionHijos[i-p.tamPoblacion];
		}		
	}
	private void ordenerListaPorFitness(Individuo[] listaAux) {
		// este metodo esta mal, hay que implementar Comparator para el fitness
		Arrays.sort(listaAux, new Individuo());
	}
	private Individuo[] mejoresIndividuosDeLista(Individuo[] listaAux, int cantidad) {
		Individuo[] nuevaPoblacion = new Individuo[p.tamPoblacion];
//		for (int i=0;i<nuevaPoblacion.length;i++)
//			nuevaPoblacion[i]=new Individuo(p);
		int j = 0;
		for(int i=0;(j<p.tamPoblacion)&&(i<listaAux.length);i++) {
			if ((listaAux[i]).violacionRestriccion==0) {
				nuevaPoblacion[j]=new Individuo(p);
				nuevaPoblacion[j++]=listaAux[i];
			}
		}
		if (j == 0) {
			Log l = Log.getInstance();
	    	l.loguear(Tipo_Log.ERROR, "Estamos en el horno, no hay individuos que sirvan!!!");
	    	l.cerrar();
	    	System.exit(1);
		}
		for (;j<p.tamPoblacion;j++) {
			nuevaPoblacion[j]=new Individuo(p);
			nuevaPoblacion[j]=(Individuo) (nuevaPoblacion[rnd.nextInt(j-1)]).clone();
		}
		return nuevaPoblacion;
	}
//	public void verificarDominanciaEnPoblacion(Individuo indNvo,int indPoblacion, Problema p){
//		try{
//		TipoDominancia dom=verificarDominanciaDentroPoblacion(indNvo,poblacion[indPoblacion]);
//		if (dom.equals(TipoDominancia.INDIVIDUO_DOMINA)){
//			poblacion[indPoblacion]=indNvo;	
//      		return;			
//		}
//		if (dom.equals(TipoDominancia.INDIVIDUO_DOMINADO)){
//			return;		
//		}
//		if (dom.equals(TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX)){
//			indivNoDominados.add(indPoblacion);
//			return;	
//		}
//		if (dom.equals(TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX)){
//			indivNoDominados.add(indPoblacion);
//			return;	
//		}
//		throw new ErrorCalculoDominanciaEnBoxException();	
//	   }catch (ErrorCalculoDominanciaEnBoxException e){
//		   System.err.println("Error al calcular la dominancia dentro del hiper box para "+ indNvo);
//	   }
//	}
	public int errorIndividuo(Individuo ind, Problema p){
		double d=0.0d;
		for (int i=0;i<ind.objetivo.length;i++)
			d+=Math.pow((ind.objetivo[i]-ind.valorFnObjetivo[i])/p.epsilon[i],2);
		return -1;
	}
//	public TipoDominancia verificarDominanciaDentroPoblacion(Individuo indNvo,Individuo indElite){
//		System.out.println("verificarDominanciaDentroPoblacion");
//		SingletonDistanciador distanciador = SingletonDistanciador.getInstance();
//		TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
//		double distancia = distanciador.calcularDistancia(indNvo, indElite);
//		if (distancia > p.umbralMismoHBox) 
//			td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;
//		else
//			if (distancia < p.umbralDistancia) 
//				td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
//			else
//				if (indNvo.fitness > indElite.fitness)
//					if ((indNvo.fitness - indElite.fitness) > p.umbralDistFitness)
//						td=TipoDominancia.INDIVIDUO_DOMINA;
//					else
//						td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
//				else
//					if ((indElite.fitness - indNvo.fitness) > p.umbralDistFitness)
//						td=TipoDominancia.INDIVIDUO_DOMINADO;
//					else
//						td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;	
//		return (TipoDominancia)td;
//	}
	public void evaluarHijos(){
		Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "evaluarHijos");
		for (Individuo i: poblacionHijos){
 			i.decodificar();
 			i.asignarObjetivos(p);
 			i.evaluarFuncionesObjetivo(p);
 			i.calculoFitness();
		}
		l.loguear(Tipo_Log.INFORMACION, "Sali de la evaluación de los hijos");
	}
			
//	public void actualizarEliteConHijos(){
//este metodo hace fruta!!!!!
//		System.out.println("Act Elite con hijos sin implementar");
//		indivNoDominados.clear();
//		for (int i=0;i<hijo.length;i++){
//			for (int j=0;j<poblacionHijos.length;j++)
//				verificarDominanciaEnPoblacion(poblacionHijos[i], j, p);
//			if (indivNoDominados.size()>0){
//				poblacion[indivNoDominados.get(rnd.nextInt(indivNoDominados.size()))]=
//					hijo[i];}
//		}
//	}
	public void actualizarEliteConHijos(){
		Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "actualizarEliteConHijos");
    	l.loguear(Tipo_Log.DATOS, "***************Actualizar Elite*********************");
 		for (int i=0;i<poblacionHijos.length;i++)
			pElite.actualizarElite(poblacionHijos[i],  p);
 		l.loguear(Tipo_Log.DATOS, "Tamaño actual de la Elite H: "+pElite.tamanioElite());
 		l.loguear(Tipo_Log.DATOS, "Tamaño actual de la Elite H: "+Arrays.asList(pElite.getElite()));
	}
	public static void main(String[] args){
		Poblacion i=new Poblacion();
	}
	public Individuo[] getPoblacionHijos() {
		return poblacionHijos;
	}
	public void setPoblacionHijos(Individuo[] poblacionHijos) {
		this.poblacionHijos = poblacionHijos;
	}
}