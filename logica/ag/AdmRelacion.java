package logica.ag;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
public class AdmRelacion {
	private static AdmRelacion admRelacion = null;
	private ArrayList<Relacion> rel;
	private AdmRelacion() {
		rel=new ArrayList<Relacion>();
		cargarRelaciones();
	}
	public static Map<Integer, Integer> dameMapa(OriDes o, OriDes d) {
		return null;
	}

	public void cargarRelaciones() {
		SingletonAdminDatos datos = SingletonAdminDatos.getInstance();
		OriDes[] destinos = new OriDes[OriDes.values().length];
		//datos.cargarRelaciones(this);
		for (OriDes o : OriDes.values()) {
			if ((destinos=datos.dameDestinos(o))!=null)
				for (OriDes d : destinos)
					rel.add(new Relacion(o.toString(), d.toString(),
							datos.dameRelacion(o, d)));
		}
	}
//	public AdmRelacion getRelacion(int clave, double valor) {
//		//r==null: La busqueda se hace sobre todo el repositorio.
//		if (pseudoGenesAux.size()==0) pseudoGenesAux=buscarRelaciones(pseudoGenes, clave, valor);	
//		else pseudoGenesAux=buscarRelaciones(pseudoGenesAux, clave, valor);
//		return this;
//	}
	public void nuevaBusqueda() {
//		pseudoGenesAux=new ArrayList<Gen>();
	}
	public boolean existeRelacion() {
//		return (pseudoGenesAux.size()>0);
		return false;
	}
	private ArrayList<Gen> buscarRelaciones(ArrayList<Gen> genes, int clave, double valor) {
		ArrayList<Gen> g = new ArrayList<Gen>();
		GrayCode gc=new GrayCode(0);
		//BitSet bs;
		for(int i=0;i<genes.size();i++) {
			gc.setResult(((Gen)genes.get(i)).getAlelo(clave));
			if (gc.getNumber()==(int)valor)
				g.add((Gen)genes.get(i));			
		}
		return g;
	}
	public static AdmRelacion getInstance() {
		if (admRelacion == null)
			admRelacion = new AdmRelacion();
		return admRelacion;
	}
	public static void main(String[] args) {
		AdmRelacion.getInstance();
	}
}
