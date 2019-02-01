package ic.practica2.base;

import java.util.Collections;
import java.util.ArrayList;

public class Individuo {
	
	public ArrayList<Integer> genes = new ArrayList<Integer>();
	public int cost = 0;

	public Individuo() {
		for (int i=0; i<Main.fileSize; i++) {
			genes.add(i);
		}
		Collections.shuffle(genes);
	}
}
