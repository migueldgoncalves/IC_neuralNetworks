package ic.practica2.base;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IndividuoTest {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void individuoConstructor() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		for(int i=0; i<Main.fileSize; i++)
			array.add(i);
		Individuo individuo = new Individuo();
		Assert.assertFalse(array.equals(individuo.genes));
		Assert.assertTrue(Algoritmo.allElementsUnique(array));
	}
	
	@After
	public void tearDown() {
		
	}

}
