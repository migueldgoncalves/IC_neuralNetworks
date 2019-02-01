package ic.practica2.base;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PopulacionTest {
	
	int MIN_COST = 44095032;
	
	Populacion populacion = null;
	
	@Before
	public void setUp() {
		populacion = new Populacion(100);
	}
	
	@Test
	public void populationConstructor() {
		Assert.assertTrue(populacion.individuals.size()==100);
		Assert.assertTrue(populacion.costs.size()==100);
		
		for(int i=0; i<populacion.individuals.size(); i++)
			Assert.assertNotNull(populacion.individuals.get(i));
		for(int i=0; i<populacion.costs.size(); i++)
			Assert.assertTrue(populacion.costs.get(i)>=MIN_COST);
	}
	
	@Test
	public void costTest() {
		int[] elements = {2, 0, 1};
		int[][] distanceMatrix = {{0, 2, 9}, {1, 0, 8}, {4, 5, 0}};
		int[][] weightMatrix = {{0, 1, 8}, {4, 0, 2}, {7, 9, 0}};
    	int cost = 0;
    	for(int i=0; i<3; i++) {
    		for(int j=0; j<3; j++) {
    			cost += distanceMatrix[i][j] * weightMatrix[elements[i]][elements[j]];
    		}
    	}
    	Assert.assertEquals(0*0+2*7+9*9+0*0+1*8+8*1+0*0+4*2+5*4, cost);
    	Assert.assertEquals(139, cost);
	}
	
	@Test
	public void minCostTest() {
		List<Integer> costs  = new ArrayList<Integer>();
		costs.add(2);
		costs.add(4);
		costs.add(1);
    	int minCost = costs.get(0);
    	for (int i=1; i<costs.size(); i++)
    		if(costs.get(i) < minCost)
    			minCost = costs.get(i);
    	Assert.assertEquals(1, minCost);
	}
	
	@Test
	public void bestIndividualTest() {
		populacion.individuals = new ArrayList<Individuo>();
		populacion.individuals.add(new Individuo());
		populacion.individuals.add(new Individuo());
		populacion.individuals.add(new Individuo());
		populacion.individuals.get(0).genes.set(0, 10000);
		populacion.individuals.get(1).genes.set(0, 20000);
		populacion.individuals.get(2).genes.set(0, 30000);
		
		populacion.costs = new ArrayList<Integer>();
		populacion.costs.add(2);
		populacion.costs.add(4);
		populacion.costs.add(1);
		
		Individuo best = new Individuo();
    	for (int i=0; i<populacion.individuals.size(); i++)
    		if(populacion.costs.get(i) == populacion.minCost())
    			best = populacion.individuals.get(i);
    	Assert.assertEquals(30000, (int) best.genes.get(0));
	}
	
	@After
	public void tearDown() {
		
	}

}
