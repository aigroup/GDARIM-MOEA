package moea;

import log.Log;
import log.Tipo_Log;
import logica.ag.*;
import java.util.*;
 
class SingletonDistanciador implements Distanciador {
	private static SingletonDistanciador distanciador = null;
	private Individuo i1;
	private Individuo i2;
	
	private SingletonDistanciador() {
    	Log l = Log.getInstance();
    	l.loguear(Tipo_Log.INFORMACION, "Creado el distanciador...");	
	}
	public static SingletonDistanciador getInstance() {
		if (distanciador == null)
			distanciador = new SingletonDistanciador();
		return distanciador;
	}
	public double calcularDistancia(Individuo i1, Individuo i2) {
		double dist=0.0;
		for (int i=0;i<i1.getGen().length;i++) {
			dist+=distAulaProf(i1.getGen()[i], i2.getGen()[i]);
			dist+=distSedeProf(i1.getGen()[i], i2.getGen()[i]);
//			dist+=distTurnoProf(i1.getGen()[i], i2.getGen()[i]);
			dist+=distAulaMat(i1.getGen()[i], i2.getGen()[i]);
			dist+=distAulaRecs(i1.getGen()[i], i2.getGen()[i]);
		}
		//System.out.println("Calculo de distancia entre individuos: "+dist);
		return dist;
	}
	private double distAulaProf(Gen g1, Gen g2) {
		if (((BitSet)g1.getAlelo(0) != (BitSet)g2.getAlelo(0))	&&
			((BitSet)g1.getAlelo(1) != (BitSet)g2.getAlelo(1)))
			return 100.0;
		else return 0.0;
	}
	private double distSedeProf(Gen g1, Gen g2) {
		if (((BitSet)g1.getAlelo(1) != (BitSet)g2.getAlelo(1)) &&
			((BitSet)g1.getAlelo(2) != (BitSet)g2.getAlelo(2)))	return 50.0;
		else return 0.0;
	}
	private double distTurnoProf(Gen g1, Gen g2) {
		if (((BitSet)g1.getAlelo(1) != (BitSet)g2.getAlelo(1)) &&
			((BitSet)g1.getAlelo(4) != (BitSet)g2.getAlelo(4)))	return 50.0;
		else return 0.0;
	}
	private double distAulaMat(Gen g1, Gen g2) {
		if (((BitSet)g1.getAlelo(0) != (BitSet)g2.getAlelo(3)) &&
			((BitSet)g1.getAlelo(0) != (BitSet)g2.getAlelo(3)))	return 100.0;
		else return 0.0;
	}
	private double distAulaRecs(Gen g1, Gen g2) {
		double ret=0.0;
		if (((BitSet)g1.getAlelo(0) != (BitSet)g2.getAlelo(0)))
			return 0.0;
		for (int i=10;i<21;i++)
			if ((BitSet)g1.getAlelo(i) != (BitSet)g2.getAlelo(i))	ret+=10.0;
		return ret;
	}
}
