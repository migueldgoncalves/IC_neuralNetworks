package ic.practica2.base;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ic.practica2.filereader.FileReader;

public class AlgoritmoTest {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void numberInArrayTest() {
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(0);
		array.add(1);
		array.add(2);
		Assert.assertFalse(Algoritmo.numberInArray(array, -1));
		Assert.assertTrue(Algoritmo.numberInArray(array, 0));
		Assert.assertTrue(Algoritmo.numberInArray(array, 1));
		Assert.assertTrue(Algoritmo.numberInArray(array, 2));
		Assert.assertFalse(Algoritmo.numberInArray(array, 3));
	}
	
	@Test
	public void allElementsUniqueTest() {
		ArrayList<Integer> unique = new ArrayList<Integer>(4);
		unique.add(0);
		unique.add(1);
		unique.add(2);
		unique.add(3);
		
		ArrayList<Integer> notUnique = new ArrayList<Integer>(4);
		notUnique.add(0);
		notUnique.add(1);
		notUnique.add(1);
		notUnique.add(3);
		
		ArrayList<Integer> invalid = new ArrayList<Integer>(4);
		invalid.add(-1);
		invalid.add(0);
		invalid.add(1);
		invalid.add(2);
		
		ArrayList<Integer> largeUnique = new ArrayList<Integer>(256);
		for(int i=0; i<256; i++)
			largeUnique.add(i);
		
		Assert.assertTrue(Algoritmo.allElementsUnique(unique));
		Assert.assertFalse(Algoritmo.allElementsUnique(notUnique));
		Assert.assertFalse(Algoritmo.allElementsUnique(invalid));
		Assert.assertTrue(Algoritmo.allElementsUnique(largeUnique));
		
		largeUnique.set(0, 1);
		
		Assert.assertFalse(Algoritmo.allElementsUnique(largeUnique));
		
	}
	
	@Test
	public void getSecondHalfTest() {
		ArrayList<Integer> first = new ArrayList<Integer>();
		ArrayList<Integer> parent = new ArrayList<Integer>();
		first.add(1);
		first.add(2);
		parent.add(4);
		parent.add(3);
		parent.add(2);
		parent.add(1);
		
		ArrayList<Integer> second = Algoritmo.getSecondHalf(first, parent);
		Assert.assertTrue(second.get(0)==4);
		Assert.assertTrue(second.get(1)==3);
	}
	
	@After
	public void tearDown() {
		
	}
	
}
