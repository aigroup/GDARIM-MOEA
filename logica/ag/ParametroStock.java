package logica.ag;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
 
public class ParametroStock extends Parametro {
	private ArrayList<Integer> valores = new ArrayList<Integer>();
	public ParametroStock() {}
	public ParametroStock(String path, String nomArch) throws FileNotFoundException {
		super(ConfigAG.PATH_GRAY, nomArch);
		String nomCompleto=path+nomArch;
		BufferedReader br=new BufferedReader(new FileReader(nomCompleto));
		String linea;
		try {
			while ((linea=br.readLine())!=null) {
				Integer i = new Integer(linea);
				valores.add(i);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getCapacidad(int indice) {
		return (int) valores.get(indice);
	}
	public int getCapacidadPorCodigo(GrayCode gray) {
		GrayCode g1 = new GrayCode(0);
		GrayCode g2 = new GrayCode(0);
		g2=gray;
		for(int i=0;i<this.getValoresGray().size();i++) {
			g1=this.getValoresGray().get(i);
			if ((g1.getResult()).equals(g2.getResult())) 
				return this.getCapacidad(i);
		}
		return 0;
	}
//	public int getIndice(int valor) {
//		for(int i=0;i<this.getValoresGray().size();i++) {
//			if 
//		}
//		return 3;
//	}
}
