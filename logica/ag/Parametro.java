package logica.ag;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import log.Log;
import log.Tipo_Log;

public class Parametro {
	private ArrayList<GrayCode> valoresGray = new ArrayList<GrayCode>();
	private ArrayList<Integer> valoresLeidos = new ArrayList<Integer>();
	private int pList;
	public Parametro() {}
	public Parametro(String path, String nomArch) throws FileNotFoundException {
		String nomCompleto=path+nomArch;
		BufferedReader br=new BufferedReader(new FileReader(nomCompleto));
		String linea;
		try {
			while ((linea=br.readLine())!=null) {
				GrayCode gc = new GrayCode(20);
				gc.setGrayCode(linea);
				valoresGray.add(gc);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		setPList(0);
	}
	public GrayCode proximo() {
    	Log l = Log.getInstance();
		if (hayMas())
			return valoresGray.get(pList++);
		else {
	    	l.loguear(Tipo_Log.ERROR, "No deberia llegar aca, no hay mas y pido proximo");
	    	l.loguear(Tipo_Log.INSTANCIA, this.actual().toString());
			return null;
		}
	}
	public GrayCode random() {
		Random rnd=new Random();
		return valoresGray.get(rnd.nextInt(valoresGray.size()));
	}
	public GrayCode randomSinNA() {
		//se elimina la posibilidad de devolver N/A
		Random rnd=new Random();
		if (getValoresLeidos().size() == (getValoresGray().size()-1))
			iniciarValoresLeido();
		Integer i = Integer.valueOf(1+(rnd.nextInt((valoresGray.size()-1))));
		while (valorUsado(i)) {
			i = Integer.valueOf(1+(rnd.nextInt((valoresGray.size()-1))));
		}
		setValorLeido(i);
		return valoresGray.get(i.intValue());
	}
	private boolean valorUsado(Integer i) {
		Iterator it = valoresLeidos.iterator();
		Integer iAux = new Integer(0);
		for (int m=0;it.hasNext();m++) {
			iAux = (Integer) it.next();
			if (iAux.compareTo(i)==0)
				return true;
		}
		return false;
	}
	private void iniciarValoresLeido() {
		valoresLeidos.clear();
	}
	private ArrayList<Integer> getValoresLeidos() {
		return valoresLeidos;
	}
	private void setValorLeido(Integer i) {
		valoresLeidos.add(i);
	}
	public GrayCode actual() {
		return valoresGray.get(pList);
	}
	public boolean hayMas() {
		return pList < (valoresGray.size());
	}
	public void reiniciar() {
		setPList(0);
	}
	public int getPList() {
		return pList;
	}
	public void setPList(int list) {
		pList = list;
	}
	public ArrayList<GrayCode> getValoresGray() {
		return valoresGray;
	}
	public void setValoresGray(ArrayList<GrayCode> valoresGray) {
		this.valoresGray = valoresGray;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		Parametro p = new Parametro(ConfigAG.PATH_GRAY, "001_xor_aulas.txt");
		System.out.println(p.hayMas());
		if (p.hayMas()) {
			System.out.println("1."+p.actual());
			System.out.println("2."+p.proximo());
			System.out.println("3."+p.actual());
			System.out.println("4."+p.proximo());
			System.out.println("5."+p.actual());
			System.out.println("6."+p.proximo());
			System.out.println("7."+p.actual());
			p.reiniciar();
			System.out.println("8."+p.actual());
			p.setPList(1);
			System.out.println("9."+p.actual());
		}
	}
}
