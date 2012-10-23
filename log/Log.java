package log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Log { 
	PrintWriter pLog;
	private static Log log;
	public static Log getInstance() {
		try {
			if (log==null)
				log = new Log();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return log;
	}
	private Log() throws Exception {
		pLog = new PrintWriter(new BufferedWriter(new FileWriter(logica.ag.ConfigAG.PATH_LOG+"file.log")));
	}
	public JLabel loguear(Tipo_Log tl, String texto) {
		Calendar c = Calendar.getInstance();
		String linea="\""+c.getTime()+"\", \""+tl+"\", \""+texto+"\"";	
		pLog.println(linea);
		if (tl.equals(Tipo_Log.CONSOLA)) {
			System.out.println(linea);
			return new JLabel(linea);
		}
		return null;
	}

	public void mostrar(Tipo_Log tl) {
		BufferedReader br = null;
		String linea;
		try {
			br=new BufferedReader(new FileReader(logica.ag.ConfigAG.PATH_LOG+"file.log"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			while ((linea=br.readLine())!=null) {
				if (filtroTipoLog(tl, linea))
					System.out.println(linea);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean filtroTipoLog(Tipo_Log tl, String linea) {
		return true;
	}
	public void mostrar() {
		cerrar();
		BufferedReader br = null;
		String linea;
		try {
			br=new BufferedReader(new FileReader(logica.ag.ConfigAG.PATH_LOG+"file.log"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			while ((linea=br.readLine())!=null) {
				System.out.println(linea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void cerrar() {
		pLog.close();
	}
	public static void main(String[] args) {
		Log l = Log.getInstance();
		l.loguear(Tipo_Log.INFORMACION, "Inicio");
		l.loguear(Tipo_Log.ERROR, "Error");
		l.loguear(Tipo_Log.DATOS, "Datos");
		l.loguear(Tipo_Log.NOMBRE_METODO, "Metodo");
		l.loguear(Tipo_Log.EXCEPCION, "Excepcion");
		l.loguear(Tipo_Log.NOMBRE_METODO, "Metodo2");
		l.loguear(Tipo_Log.INFORMACION, "Fin");
		l.mostrar();
	}
}
