package moea;

import java.util.ArrayList;

public class TFitness {
	private String tipo;
	private int nroObjetivo;
	private String tablaRel;
	private ArrayList<String> campos;
	private String valorPenalizado;
	private String muta;
	public TFitness(String tipo, String tablaRel, String valor, ArrayList<String> campos, int obj, String muta) {
		this.tipo=tipo;
		this.tablaRel=tablaRel;
		this.valorPenalizado=valor;
		this.campos=campos;
		this.nroObjetivo=obj;
		this.muta=muta;
	}
	public String toString() {
		return nroObjetivo+", "+tipo+", "+tablaRel+", "+valorPenalizado+", "+campos.toString();
	}
	public ArrayList<String> getCampos() {
		return campos;
	}
	public void setCampos(ArrayList<String> campos) {
		this.campos = campos;
	}
	public String getTablaRel() {
		return tablaRel;
	}
	public void setTablaRel(String tablaRel) {
		this.tablaRel = tablaRel;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValorPenalizado() {
		return valorPenalizado;
	}
	public int getValorPenalizadoInt() {
		return Integer.parseInt(valorPenalizado);
	}
	public void setValorPenalizado(String valorPenalizado) {
		this.valorPenalizado = valorPenalizado;
	}
	public TFitness getFitness() {
		return this;
	}
	public int getNroObjetivo() {
		return nroObjetivo;
	}
	public void setNroObjetivo(int nroObjetivo) {
		this.nroObjetivo = nroObjetivo;
	}
	public String getMuta() {
		return muta;
	}
	public void setMuta(String muta) {
		this.muta = muta;
	}
}
