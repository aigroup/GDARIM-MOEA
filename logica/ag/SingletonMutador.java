package logica.ag;
 
import java.util.*;

public class SingletonMutador {
	private static SingletonMutador mutador = null;

	public static SingletonMutador getInstance() {
		if (mutador == null)
			mutador = new SingletonMutador();
		return mutador;
	}
    public BitSet mutarBitSet(BitSet bitset, int tamMaximo) {
    	BitSet bs = new BitSet(bitset.size());
    	Random rnd = new Random();
    	bs.clear();
    	System.out.println("Largo del bitset: " + tamMaximo);
    	for(int i=0;i<tamMaximo;i++) 
    		if (rnd.nextInt(2) == 1)
    			bs.set(i);
    	return bs;
    }
	public static void main(String[] args) {
		SingletonMutador m = SingletonMutador.getInstance();
		BitSet b = new BitSet(3);
		GrayCode gc = new GrayCode(b);
		b = (BitSet) m.mutarBitSet(new BitSet(3), 3);
		System.out.println(gc.getNumber(b));
	}
    
}
