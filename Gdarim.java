import javax.swing.JFrame;

import log.Log;
import log.Tipo_Log;
import logica.ag.Constantes;
import moea.*;

public class Gdarim {
    public static void main(String[] args) {
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.CONSOLA, "Inicio del proceso");
    	l.loguear(Tipo_Log.INFORMACION, "Inicio del proceso");
    	l.loguear(Tipo_Log.INFORMACION, "==================");
    	l.loguear(Tipo_Log.INSTANCIA, "Moea");
    	Moea m = new Moea(new ProblemaGdarim(), Constantes.LUNES);
    	l.loguear(Tipo_Log.NOMBRE_METODO, "Configurar Moea");
		m.configurar(args); 
		l.loguear(Tipo_Log.NOMBRE_METODO, "Procesar Moea");
		m.procesarPoblacion();
		l.loguear(Tipo_Log.CONSOLA, "Proceso finalizado");
		l.cerrar();
	}
   
}