package ic.practica2.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ic.practica2.filereader.FileReader;

public class Populacion {

    public List<Individuo> individuals = new ArrayList<Individuo>();
    public List<Integer> costs  = new ArrayList<Integer>();

    public Populacion(int size) {
    	for (int i = 0; i < size; i++) {
    		individuals.add(new Individuo());
    		costs.add(0);
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
    	return cost;
    }
    
    public int minCost() {
    	int minCost = costs.get(0);
    	for (int i=1; i<costs.size(); i++)
    		if(costs.get(i) < minCost)
    			minCost = costs.get(i);
    	return minCost;
    }
    
    public Individuo bestIndividual() {
    	for (int i=0; i<individuals.size(); i++)
    		if(costs.get(i) == minCost())
    			return individuals.get(i);
    	return individuals.get(0);
    }
}
