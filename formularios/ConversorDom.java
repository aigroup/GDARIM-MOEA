package formularios;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import log.Log;
import log.Tipo_Log;
import logica.ag.ConfigAG;
import logica.ag.GrayCode;
import logica.ag.Parametro;
import logica.ag.ParametroStock;

public class ConversorDom extends JFrame{
//TODO: que no genere un registro vacio al final de cada archivo
//TODO: pasar N/A al principio de los .dom
	private ArrayList<String> domList = new ArrayList<String>();

	public void cargar(String path) {
		File f = new File(path);
		File files[] = f.listFiles();
		for(int i=0;i<files.length;i++)
			domList.add(files[i].getName());
		Collections.sort(domList);
	}
	public void generarGray() {
		Iterator<String> i = domList.iterator();
		while (i.hasNext())
			procesarArchivo((String)i.next());
	}
    private void procesarArchivo(String archDom) {
    	Log l = Log.getInstance();
    	String linea;
    	int indInt;
    	GrayCode indGray;
		try {
			String s;
			BufferedReader dom=new BufferedReader(new FileReader(logica.ag.ConfigAG.PATH_DOM+archDom));			
			String archSinPath = archDom.substring(0, archDom.indexOf("."));
			l.loguear(Tipo_Log.CONSOLA, archSinPath);
			
			PrintWriter pInt = new PrintWriter(new BufferedWriter(new FileWriter(logica.ag.ConfigAG.PATH_INT+archSinPath+".int")));
			PrintWriter pGray = new PrintWriter(new BufferedWriter(new FileWriter(logica.ag.ConfigAG.PATH_GRAY+archSinPath+".gray")));
			while ((linea=dom.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(linea,"\"");
				s=st.nextToken();
			//	System.out.println(s);
				indInt = Integer.parseInt(s);
				indGray = new GrayCode(indInt);	
				pInt.println(indInt);
				pGray.println(indGray.getResult());
			}
			pInt.close();
			pGray.close();
			dom.close();
		} catch (IOException e) {
			l.loguear(Tipo_Log.EXCEPCION, "El archivo de datos no tiene el formato requerido o no contiene la descripcion ingresada");
			e.printStackTrace();
		}
    }	
	public void convertir() {
		cargar(logica.ag.ConfigAG.PATH_DOM);
		generarGray();
	}
	
	public static void main(String[] args) {
    	Log l = Log.getInstance();
		ConversorDom c = new ConversorDom();
		c.convertir();
    	l.cerrar();    	
	}

}
