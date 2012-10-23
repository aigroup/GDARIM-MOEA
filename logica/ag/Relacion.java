package logica.ag;

import java.util.Map;

public class Relacion {
	private String origen;
	private String destino;
	private Map<Integer, Integer> map;
	public Relacion() {}
	public Relacion(String o, String d, Map<Integer, Integer> map) {
		origen=o;
		destino=d;
		this.map=map;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Map<Integer, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Integer, Integer> map) {
		this.map = map;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	

}
