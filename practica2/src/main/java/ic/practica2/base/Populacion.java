package ic.practica2.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ic.practica2.filereader.FileReader;

public class Populacion {

    public List<Individuo> individuals = new ArrayList<Individuo>();
    public List<Integer> costs  = new ArrayList<Integer>();
    public List<Integer> costsBaldwinianos = new ArrayList<Integer>();

    public Populacion(int size) {
    	for (int i = 0; i < size; i++) {
    		individuals.add(new Individuo());
    		costs.add(0);
    		costsBaldwinianos.add(100000000);
    	}
    	allCosts();
    }
    
    public void allCosts() {
    	for (int i=0; i<costs.size(); i++)
    		cost(i);
    }
    
    public int cost(int index) {
    	Individuo individuo = individuals.get(index);
    	int cost = 0;
    	for(int i=0; i<Main.fileSize; i++) {
    		for(int j=0; j<Main.fileSize; j++) {
    			cost += Main.distanceMatrix[i][j] * Main.weightMatrix[individuo.genes.get(i)][individuo.genes.get(j)];
    		}
    	}
    	costs.set(index, cost);
    	costsBaldwinianos.set(index, 100000000); //100 millones es un valor muy arriba del coste minimo del tai256c
    	return cost;
    }
    
    public static int costIndividuo(Individuo individuo) {
    	int cost = 0;
    	for(int i=0; i<Main.fileSize; i++) {
    		for(int j=0; j<Main.fileSize; j++) {
    			cost += Main.distanceMatrix[i][j] * Main.weightMatrix[individuo.genes.get(i)][individuo.genes.get(j)];
    		}
    	}
    	return cost;
    }
    
    public int minCost() {
    	int minCost = costs.get(0);
    	for (int i=1; i<costs.size(); i++)
    		if(costs.get(i) < minCost)
    			minCost = costs.get(i);
    	for (int i=0; i<costsBaldwinianos.size(); i++)
    		if(costsBaldwinianos.get(i) < minCost)
    			minCost = costsBaldwinianos.get(i);
    	return minCost;
    }
    
    public void resetCostsBaldwinianos() {
    	for (int i = 0; i < costsBaldwinianos.size(); i++)
    		costsBaldwinianos.set(i, 100000000);
    }
    
    public Individuo bestIndividual() {
    	for (int i=0; i<individuals.size(); i++)
    		if(costs.get(i) == minCost())
    			return individuals.get(i);
    	return individuals.get(0);
    }
}
