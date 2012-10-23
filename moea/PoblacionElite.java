package moea;

import java.util.*;

import log.Log;
import log.Tipo_Log;
import logica.ag.*;
class ErrorEliminacionException extends Exception{}
public class PoblacionElite {
	private LinkedList<Individuo> elite=new LinkedList<Individuo>();

    public PoblacionElite() {
    }	
	void agregarIndivElite(int pos, Individuo ind){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.DATOS, "Se agrega Elite.......!!!!");
		elite.add(pos, ind);
	}
	LinkedList<Individuo> getElite() {
		return elite;
	}
    void agregarIndivElite(Individuo ind){
      	//agrega al final
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.DATOS, "Se agrega ind. elite: \n" + ind);
      	elite.addLast(ind);
    }
	int tamanioElite(){
		return elite.size();
	}
	void eliminarIndivElite(int pos){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.DATOS, "Se elimina indiv.......!!!!");
		elite.remove(pos);
	}
	void eliminarIndivElite(Individuo ind){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.DATOS, "Se elimina Elite.......!!!!");
		try {
			int pos;
			if ((pos=elite.indexOf(ind))>=0)
				elite.remove(pos);
			else 
				throw new ErrorEliminacionException();
		} catch (ErrorEliminacionException e){
			l.loguear(Tipo_Log.EXCEPCION, "Error al intentar eliminar el nodo "+ind+ "de la lista de elite");
		}
	}
	int posElite=0;
	public void iniciarIterador(){
		posElite=0;
	}
	public Individuo iterarElite(){
		if (posElite<elite.size())
			return elite.get(posElite++);
		return null;
	}
/*	Individuo iterarElite(){
		if (li.hasNext())
			return li.next();
		else return null;
	}
*/	
/*	 void agregarElite(int pos){
		for (int i=0;li.hasNext();i++)
			agregarIndivElite(i,li.next());
			
	}
*/
    public void actualizarElite(Individuo ind, Problema p){
    	Log l = Log.getInstance();
      	try {
    		if (tamanioElite()==0){
    			agregarIndivElite(0,ind);
    			return;
    		}
    		for (int i=0;i<elite.size();i++){
    			TipoDominancia dom=verificarHiperBoxDominanciaNew(ind,elite.get(i), p);
    	    	l.loguear(Tipo_Log.DATOS, "Dominancia: "+dom.name());
    			if(dom.equals(TipoDominancia.INDIVIDUO_DOMINA)){
    				eliminarIndivElite(i);
    				continue;
    			}
    			if(dom.equals(TipoDominancia.INDIVIDUO_DOMINADO)){
    				return;
    			} 
    			if(dom.equals(TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX)){
    				verificarDominanciaDistintoHiperBox(ind,i, p);
    				return;
    			}
    			if(dom.equals(TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX)){
    			//tratamiento especial
    				verificarDominanciaEnHiperBox(ind,i, p);
    				return;
    			}
    			if(dom.equals(TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX)){
    			//tratamiento especial
    				verificarDominanciaEnHiperBox(ind,i, p);
    				return;
    			}
    			throw new ErrorCalculoDominanciaException();
    		}
      	}catch (ErrorCalculoDominanciaException e){
	    	l.loguear(Tipo_Log.EXCEPCION, "Error al calcular dominancia entre ["+ind+"] y [elite].");
      	}
      	//System.out.println("1-nuevo: "+ind);
      	agregarIndivElite(ind);
    }
    
    public TipoDominancia verificarHiperBoxDominanciaNew(Individuo indNvo,Individuo indElite, Problema p){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "verificarHiperBoxDominanciaNew");
    	TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
    	l.loguear(Tipo_Log.INFORMACION, "Nuevo calculo de cominancia");
		SingletonDistanciador distanciador = SingletonDistanciador.getInstance();
		double distancia = distanciador.calcularDistancia(indNvo, indElite);
		if (distancia < p.umbralDistancia)
			td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
		else
			if (distancia > p.umbralMismoHBox)
				td=verificarDominanciaDistintoHiperBoxNew(indNvo, indElite, p);
			else
				td=verificarDominanciaMismoHiperBoxNew(indNvo, indElite, p);
		
		return (TipoDominancia) td;
	}
    public TipoDominancia verificarDominanciaDistintoHiperBoxNew(Individuo indNvo,Individuo indElite, Problema p){
    	TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
    	double fitnessAux1=0, fitnessAux2=0;
    	//El fitness de aulas vale el doble que el de docentes.

    	fitnessAux1=indNvo.valorFnObjetivo[0]*2;
    	fitnessAux1+=indNvo.valorFnObjetivo[1];
    	fitnessAux2=indElite.valorFnObjetivo[0]*2;
    	fitnessAux2+=indElite.valorFnObjetivo[1];   		
    	if (fitnessAux1 < fitnessAux2) {
    		if ((fitnessAux2-fitnessAux1)<=(p.umbralDistFitness*2)) {
    			td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;
    			return (TipoDominancia) td;
    		}
    	}
    	else {
    		if ((fitnessAux1-fitnessAux2)<=(p.umbralDistFitness*2)) {
    			td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;  
    			return (TipoDominancia) td;
    		}
    	}    	
		if ((fitnessAux1*2) < fitnessAux2)
			td=TipoDominancia.INDIVIDUO_DOMINA;
		else
			td=TipoDominancia.INDIVIDUO_DOMINADO;
		return (TipoDominancia) td;
    }
    public TipoDominancia verificarDominanciaMismoHiperBoxNew(Individuo indNvo,Individuo indElite, Problema p){
    	TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
    	double fitnessAux=0;
    	//El fitness de aulas vale el doble que el de docentes.
		fitnessAux=(indNvo.valorFnObjetivo[0]-indElite.valorFnObjetivo[0])*2; // -10
		fitnessAux+=indNvo.valorFnObjetivo[1]-indElite.valorFnObjetivo[1]; // -10
		if ((fitnessAux < 0) && (fitnessAux > (p.umbralDistFitness * -1 )))
			td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
		if ((fitnessAux > 0) && (fitnessAux < p.umbralDistFitness))
			td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
		
		if (fitnessAux < 0)
			td=TipoDominancia.INDIVIDUO_DOMINA;
		else
			td=TipoDominancia.INDIVIDUO_DOMINADO;
    	
    	return (TipoDominancia) td;
    }

    public void verificarDominanciaEnHiperBox(Individuo indNvo,int indElite, Problema p){
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.NOMBRE_METODO, "verificarDominanciaEnHiperBox");
		SingletonDistanciador distanciador = SingletonDistanciador.getInstance();
	  	try {
			TipoDominancia dom=verificarDominanciaDentroHiperBox(indNvo,elite.get(indElite), p);
			if (dom.equals(TipoDominancia.INDIVIDUO_DOMINA)){
				eliminarIndivElite(indElite);
      			agregarIndivElite(indNvo);
      			return;			
			}
			if (dom.equals(TipoDominancia.INDIVIDUO_DOMINADO)){
				return;		
			}
			if (dom.equals(TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX)){
				if (errorIndividuo(indNvo, p)<=errorIndividuo(elite.get(indElite), p)){
					eliminarIndivElite(indElite);
      				agregarIndivElite(indNvo);				
				}
				return;	
			}
			throw new ErrorCalculoDominanciaEnBoxException();	
	   	} catch (ErrorCalculoDominanciaEnBoxException e){
	    	l.loguear(Tipo_Log.EXCEPCION, "Error al calcular la dominancia dentro del hiper box para"+ indNvo);
		}
	}
	public void verificarDominanciaDistintoHiperBox(Individuo indNvo,int indElite, Problema p){
	}
	public int errorIndividuo(Individuo ind, Problema p){
		double d=0.0d;
		for (int i=0;i<ind.objetivo.length;i++)
			d+=Math.pow((ind.objetivo[i]-ind.valorFnObjetivo[i])/p.epsilon[i],2);
		return -1;
	}
	public TipoDominancia verificarHiperBoxDominancia(Individuo indNvo,Individuo indElite, Problema p){
		//verifica si son dominados dentro del mismo hiper box
		//-1 3 variables a comparar. para determinar si estan n mismo hiperbox.
		//cercanos/fitness muya alto con respecto al otro.
		double fitnessAux=0;
		//System.out.println("Ya ni se que hay: "+indNvo);
		SingletonDistanciador distanciador = SingletonDistanciador.getInstance();
		TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
		double distancia = distanciador.calcularDistancia(indNvo, indElite);
		if (distancia > p.umbralMismoHBox) 
			td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;
		else
			if (distancia < p.umbralDistancia) 
				td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
			else {
//				El fitness de aulas vale el doble que el de docentes.
				fitnessAux=(indNvo.valorFnObjetivo[0]-indElite.valorFnObjetivo[0])*2; // -10
				fitnessAux+=indNvo.valorFnObjetivo[1]-indElite.valorFnObjetivo[1]; // -10
				if ((fitnessAux < 0) && (fitnessAux > (p.umbralDistFitness * -1 )))
					td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
				if ((fitnessAux > 0) && (fitnessAux < p.umbralDistFitness))
					td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
				
				if (fitnessAux < 0)
					td=TipoDominancia.INDIVIDUO_DOMINA;
				else
					td=TipoDominancia.INDIVIDUO_DOMINADO;
				
//				if ((fitnessAux > 0) && (fitnessAux > p.umbralDistFitness))
//						td=TipoDominancia.INDIVIDUO_DOMINA;
//				if (indNvo.objetivo[0] > indElite.objetivo[0])
//					fitnessAux=(indNvo.objetivo[0]-indElite.objetivo[0])*2;
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
	    
//-2        Random rnd=new Random();   
//-2        int valor=rnd.nextInt(4);
//-2        if (valor==0) td=TipoDominancia.INDIVIDUO_DOMINADO;
//-2        if (valor==1) td=TipoDominancia.INDIVIDUO_DOMINA;
//-2        if (valor==2) td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;
//-2        if (valor==3) td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;    
//-2        System.out.println("verificarHiperBoxDominancia que no hace nada");
			}
		return (TipoDominancia) td;
	}
	public TipoDominancia verificarDominanciaDentroHiperBox(Individuo indNvo,Individuo indElite, Problema p){
		//verifica si son dominados dentro del mismo hiper box
		//-1 3 variables a comparar. para determinar si estan n mismo hiperbox.
		//cercanos/fitness muya alto con respecto al otro.
		double fitnessAux=0;
		//System.out.println("Ya ni se que hay: "+indNvo);
		SingletonDistanciador distanciador = SingletonDistanciador.getInstance();
		TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
		double distancia = distanciador.calcularDistancia(indNvo, indElite);
		if (distancia > p.umbralMismoHBox) 
			td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;
		else
			if (distancia < p.umbralDistancia) 
				td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
			else {
//				El fitness de aulas vale el doble que el de docentes.
				fitnessAux=(indNvo.valorFnObjetivo[0]-indElite.valorFnObjetivo[0])*2; // -10
				fitnessAux+=indNvo.valorFnObjetivo[1]-indElite.valorFnObjetivo[1]; // -10
				if ((fitnessAux < 0) && (fitnessAux > (p.umbralDistFitness * -1 )))
					td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
				if ((fitnessAux > 0) && (fitnessAux < p.umbralDistFitness))
					td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
				
				if (fitnessAux < 0)
					td=TipoDominancia.INDIVIDUO_DOMINA;
				else
					td=TipoDominancia.INDIVIDUO_DOMINADO;
			}
		//verifica tipo dominio dentro del mismo hiper box
		//son pareto pero 1 puede ser mejor. podriamos reutilizar fitness
		// o otra funcion de fitness.
//        TipoDominancia td = TipoDominancia.INDIVIDUO_DOMINADO;
//        Random rnd=new Random();
//        int valor=rnd.nextInt(4);
//        if (valor==0) td=TipoDominancia.INDIVIDUO_DOMINADO;
//        if (valor==1) td=TipoDominancia.INDIVIDUO_DOMINA;
//        if (valor==2) td=TipoDominancia.NO_DOMINADOS_DISTINTOS_HIPER_BOX;
//        if (valor==3) td=TipoDominancia.NO_DOMINADOS_MISMO_HIPER_BOX;
//        
//        System.out.println("verificarDominanciaDentroHiperBox que no hace nada");
        return (TipoDominancia) td;
	}
}
class ErrorCalculoDominanciaException extends Exception{}
class ErrorCalculoDominanciaEnBoxException extends Exception{}