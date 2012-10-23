package logica.ag;
 
import java.io.*;
import java.util.*;

import formularios.*;

public class Codificador {
	//public String archGenes=ConfigAG.pathInt+"genes.txt";
	public BitSet gen;
	public String info;
	public int nro; 
	

//intento leer un int y  por cada linea que leo
//voy guardando en el archivo con codificacion gray
	//genera el archivo duplicado con la ota codificacion
public static void leerArchivoPropEspecial(String nomArch) throws FileNotFoundException{
	String archivo=ConfigAG.PATH_INT+nomArch;
	BufferedReader br=new BufferedReader(new FileReader(archivo));
	//para leer
	String linea;
	boolean codificaraInt=false;
	String codigo;
	try {
		while ((linea=br.readLine())!=null) {
			System.out.println(linea);
			//este metodo hace el trabajo sucio
			//codifica la linea levantada que asumo es int
			if (linea=="int"){
				codificaraInt=true;				
			}
			if (codificaraInt=true){
				codigo=parser(linea);
				codificar(codigo);
			}				
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	
	}
	public static void codificar(String linea){
		//el archivo de prueba se llama gray
		String archOut="gray.txt";
		GrayCode gc = new GrayCode(Integer.parseInt(linea));
		FileUtil.grabarArchivoPropiedades(ConfigAG.PATH_GRAY,archOut,gc.getResult().toString());
	}
	//Ahora intento parsear una linea que paso como parametro
	//esta linea sería la que levanto del archivo
	//codigo-bits|descripcion
	//intento imprimir la descripcion separada del codigo
	public static String parser(String linea){
		return new StringTokenizer(linea, ("\"")).nextToken();
	}
	//intento levantar todos los archivos de un directorio
	public static void levantar(String path){
		File f = new File(path);
		File files[] = f.listFiles();
		for(int i=0;i<files.length;i++){
			System.out.println(files[i].getName());	
			// hago lo que quiero
			//levanto cada archivo int
			//genero su par  en codigo gray
			try {
				leerArchivoPropEspecial(files[i].getName());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public static void main(String[] args) {
		//parser("102|hola");
		levantar(ConfigAG.PATH_INT);
	
	}
}

