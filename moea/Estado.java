package moea;

public abstract class Estado {
	public int dia;
	public Estado(){}
	public Estado(int dia) {
		this.dia=dia;
	}
	public abstract void procesarPoblacion(Poblacion p);
	void mutar(Poblacion p, Estado e){
		p.setPoblacionHijos(p.getPoblacion());
		p.mutar(p.getIndiceRestric(), e);
	}
	void actualizarPoblacion(Poblacion p){ 
		p.actualizarPoblacion();
	}
}
