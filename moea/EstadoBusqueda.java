package moea;

import log.Log;
import log.Tipo_Log;
import logica.ag.Constantes;

public class EstadoBusqueda extends Estado{
	public void procesarPoblacion(Poblacion p) {
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.CONSOLA, "Etapa búsqueda");
		for (int i=0;i<p.getProblema().numFEval;i++){
			l.loguear(Tipo_Log.CONSOLA, "Nueva generacion");
			p.actualizarElite();
			cruzar(p); //seleccion de padres y cruza (se reemplazan los padres)
			mutar(p);  //sobre toda la poblacion
			evaluarHijos(p);
			p.evaluarPoblacion(Constantes.LUNES);
			actualizarEliteConHijos(p);
			actualizarPoblacion(p);
			reportarDatos(p);
		}
		l.loguear(Tipo_Log.INFORMACION, "Sali de la etapa de busqueda");
		p.reportarPoblacionFinal();
		p.reportarSolucion();
	}
	void cruzar(Poblacion p){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "cruzar (Etapa busqueda)");
		p.seleccionarPadres();
		p.cruzarPadres();
		l.loguear(Tipo_Log.DATOS, "Fin de la cruza");
	}
	void mutar(Poblacion p){
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.NOMBRE_METODO, "muta (Etapa busqueda)");
		p.mutar(0, this); //0: indice de restricciones
	}
	void evaluarHijos(Poblacion p){
		p.evaluarHijos();
	}
	void actualizarEliteConHijos(Poblacion p){
		p.actualizarEliteConHijos();
	}
	public void reportarDatos(Poblacion p){
		p.reportarElite(p.getProblema().verArchivoDETALLE_TODAS_LAS_GENERACIONES());
	}
}
