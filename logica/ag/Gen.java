package logica.ag;
import io.FacadeDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import log.Log;
import log.Tipo_Log;
import moea.Problema;
public class Gen {
	private GrayCode[] gen;
  
	public Gen() {
	}
	public Gen(GrayCode... p1){
		gen=p1;
	}
	public void setLargo(int largo) {
		if (gen==null)
			gen = new GrayCode[largo]; 
	}
	public GrayCode[] getGen() {
		return gen;
	}
	public BitSet getAlelo(int indice) {
		return (BitSet)gen[indice].result;
	}
	public void setAlelo(BitSet bitset, int indice) {
		gen[indice].result = bitset;
	}
	public void setAlelo(int valor, int indice) {
		gen[indice] = new GrayCode(valor);
	}	
	public void setGen(GrayCode[] gen) {
		this.gen = gen;
	}
	public int largo() {
		return gen.length;
	}
	public void agregarParam(GrayCode p, int indice) {
		gen[indice]=p;
	}
	public void setRecXMateria(Problema p) {
		//tomar la materia del gen y asignarle todos los recursos requeridos
		Log l = Log.getInstance();
		ResultSet rs=null;
		int valor = gen[p.buscarIndiceXCampo("idMat")].getNumber();
		int handler=0;
		FacadeDatos fd = FacadeDatos.getInstance();
		try {
			rs=fd.seleccionar("RelMateria", "idMat", valor);
			for (int i=Constantes.PRIMER_INDICE_REC;rs.next();i++) {
				setAlelo(rs.getInt(3), i);
			}
		    rs.close();
		} catch (ClassNotFoundException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error de base de datos");
			e.printStackTrace();
		} catch (SQLException e) {
			l.loguear(Tipo_Log.EXCEPCION, "Error en seleccion de recursos asociados a la materia");
			e.printStackTrace();
		}
		
	}
	public String toString(){
		return Arrays.asList(getGen())+"\n";
	}
	public static void main(String[] args){
	    Gen[] gen;
	    gen=new Gen[3];
	    gen[0]=new Gen(new GrayCode[21]);
	    
		Gen g=new Gen (new GrayCode(new BitSet(171)), 
					   new GrayCode(new BitSet(121)),
					   new GrayCode(new BitSet(51)),
					   new GrayCode(new BitSet(1111)));
		g.agregarParam(new GrayCode(10), 3);
		System.out.println(g);
	}
}
