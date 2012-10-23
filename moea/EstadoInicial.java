package moea;

import log.Log;
import log.Tipo_Log;

public class EstadoInicial extends Estado{
	public EstadoInicial(int dia) {
		super(dia);
	}
	public void procesarPoblacion(Poblacion p) {
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.CONSOLA, "Etapa Inicial");
		p.evaluarPoblacion(this.dia); // le pongo el fitness
		while(p.getIndiceRestric() > p.getProblema().indRestric) {
			l.loguear(Tipo_Log.CONSOLA, "Demasiadas restricciones: "+p.getIndiceRestric());
			l.loguear(Tipo_Log.CONSOLA, "mutar");
			mutar(p, this);
			l.loguear(Tipo_Log.CONSOLA, "Terminó de mutar");
			l.loguear(Tipo_Log.NOMBRE_METODO, "evaluarRestricciones");
			evaluarRestricciones(p);
		}
//		eliminarRestriccion(p);
		l.loguear(Tipo_Log.INFORMACION, "Poblacion estabilizada");
	}
	private void eliminarRestriccion(Poblacion p) {
		for (Individuo i: p.getPoblacion())
			i.violacionRestriccion=0;
	}
	public void evaluarRestricciones(Poblacion p) {
		for (Individuo i: p.getPoblacion()){
			i.evaluarFuncionesObjetivo(p.getProblema());
		}
	}
}
