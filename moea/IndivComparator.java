package moea;

import java.util.Comparator;

public class IndivComparator extends Individuo implements Comparator {
	public int compare(Object o1, Object o2) {
		Individuo i1 = (Individuo) o1;
		Individuo i2 = (Individuo) o2;
		if (i1.fitness<i2.fitness) return -1;
		else if (i2.fitness<i1.fitness) {
			return 1;
		}
		else {
			return 0;
		}
	}
	public boolean equals(Object o1, Object o2) {
		return compare(o1, o2)==0;
	}
}