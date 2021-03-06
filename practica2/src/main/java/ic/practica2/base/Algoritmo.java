package ic.practica2.base;

import java.util.ArrayList;

public class Algoritmo {
	
	public static final int POPULATION_SIZE = 100;
	public static final int GENERATION_NUMBER = 100;
	public static final double CROSSOVER_PROBABILITY = 0.5;
	public static final double MUTATION_PROBABILITY = 0.025;
	public static final int TOURNAMENT_SIZE = 5;
	
	public static final int SALTO_BALDWINIANO = 100;
	public static final int SALTO_LAMARCKIANO = 40;
	
	public static final boolean greedyBaldwiniano = false;
	public static final boolean greedyLamarckiano = true;
    
    private Populacion populacion = new Populacion(POPULATION_SIZE);

    public Algoritmo() {
    	
    }
    
    public void execute() {

        int generation = 1;
        while (generation <= GENERATION_NUMBER) {
        	
        	populacion.resetCostsBaldwinianos();
        	populacion.allCosts(); //Actualizar los costes base
            System.out.println("Generacion actual: " + generation);
            System.out.println("Coste minimo encontrado: " + populacion.minCost());
            if(generation==GENERATION_NUMBER) {
            	String best = "";
            	for(int i=0; i<populacion.bestIndividual().genes.size(); i++) {
            		System.out.print(populacion.bestIndividual().genes.get(i));
            		System.out.print(" ");
            	}
            	System.out.println(best);
            }
            populacion = evolvePopulacion(populacion);
            generation++;
        }
        
        System.out.println("FIN DE LA EJECUCION");
        
    }

    public Populacion evolvePopulacion(Populacion population) {
        Populacion newPopulation = new Populacion(POPULATION_SIZE);
        newPopulation.individuals = new ArrayList<Individuo>();
        newPopulation.individuals.add(population.bestIndividual());

        for (int i=1; i<POPULATION_SIZE; i++) {
            Individuo individuo1 = tournamentSelection();
            Individuo individuo2 = tournamentSelection();
            Individuo newIndividuo = crossover(individuo1, individuo2);
            newPopulation.individuals.add(i, newIndividuo);
        }

        for (int i=1; i<POPULATION_SIZE; i++) {
            mutate(newPopulation.individuals.get(i));
        }
        
        if(greedyBaldwiniano)
        	for(int i=1; i<POPULATION_SIZE; i++) {
        		int bestCost = greedyBaldwiniano(newPopulation.individuals.get(i));
        		newPopulation.costsBaldwinianos.set(i, bestCost);
        	}
        
        if(greedyLamarckiano)
        	for(int i=1; i<POPULATION_SIZE; i++) {
        		greedyLamarckiano(newPopulation.individuals.get(i));
        	}
        return newPopulation;
    }
    
    public Individuo tournamentSelection() {
        Populacion torneo = new Populacion(TOURNAMENT_SIZE);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int random = (int) (Math.random() * POPULATION_SIZE-1);
            torneo.individuals.set(i, populacion.individuals.get(random));
        }
        torneo.allCosts();
        return torneo.bestIndividual();
    }

    public Individuo crossover(Individuo individuo1, Individuo individuo2) {
        Individuo nuevo = new Individuo();
        if (Math.random() <= CROSSOVER_PROBABILITY) {
        	nuevo.genes = new ArrayList<Integer>();
        	for(int i=0; i<Main.fileSize/2; i++) //La primera mitad del nuevo individuo viene del primero padre
        		nuevo.genes.add(individuo1.genes.get(i));
        	ArrayList<Integer> secondHalf = Algoritmo.getSecondHalf(nuevo.genes, individuo2.genes); //La segunda mitad del nuevo individuo viene del segundo padre
        	for(int i=Main.fileSize/2; i<Main.fileSize; i++)
        		nuevo.genes.add(secondHalf.get(i-Main.fileSize/2));
        }
        return nuevo;
    }

    public void mutate(Individuo individuo) {
    	if(Math.random() <= MUTATION_PROBABILITY) {
    		int gene1Posicion = (int) (Math.random() * Main.fileSize);
    		int gene2Posicion = (int) (Math.random() * Main.fileSize);
    		int gene1 = individuo.genes.get(gene1Posicion);
    		int gene2 = individuo.genes.get(gene2Posicion);
    		individuo.genes.set(gene1Posicion, gene2);
    		individuo.genes.set(gene2Posicion, gene1);
    	}
    }
    
    public int greedyBaldwiniano(Individuo individuo) {
    	int cost = individuo.cost;
    	Individuo localBest = individuo;
    	for(int i=0; i<localBest.genes.size(); i+=SALTO_BALDWINIANO) {
    		for(int j=0; j<localBest.genes.size(); j+=SALTO_BALDWINIANO) {
        		int gene1 = localBest.genes.get(i);
        		int gene2 = localBest.genes.get(j);
        		localBest.genes.set(i, gene2);
        		localBest.genes.set(j, gene1);
        		int newCost = Populacion.costIndividuo(localBest);
        		if(newCost<cost) {
        			cost = newCost;
        		}
        		localBest = individuo;
    		}
    	}
    	return cost;
    }
    
    public void greedyLamarckiano(Individuo individuo) {
    	Individuo localBest = individuo;
    	for(int i=0; i<localBest.genes.size(); i+=SALTO_LAMARCKIANO) {
    		for(int j=0; j<localBest.genes.size(); j+=SALTO_LAMARCKIANO) {
        		int gene1 = localBest.genes.get(i);
        		int gene2 = localBest.genes.get(j);
        		localBest.genes.set(i, gene2);
        		localBest.genes.set(j, gene1);
        		if(Populacion.costIndividuo(localBest)<individuo.cost) {
        			individuo = localBest;
        		}
        		localBest = individuo;
    		}
    	}
    }
    
    public static boolean numberInArray(ArrayList<Integer> array, int number) {
    	for(int i=0; i<array.size(); i++)
    		if(array.get(i)==number)
    			return true;
    	return false;
    }
    
    public static ArrayList<Integer> getSecondHalf(ArrayList<Integer> first, ArrayList<Integer> parent) {
    	ArrayList<Integer> second = new ArrayList<Integer>();
    	int genesRellenados = 0;
    	int iterador = first.size();
    	while (genesRellenados != first.size()) {
    		if(!first.contains(parent.get(iterador))) {
    			second.add(parent.get(iterador));
    			genesRellenados++;
    		}
    		iterador++;
    		if(iterador==parent.size())
    			iterador=0;
    	}
    	return second;
    }
    
    public static boolean allElementsUnique(ArrayList<Integer> array) {
    	boolean numbersInArray = true;
    	for (int i=0; i<array.size(); i++) {
    		if(!array.contains(i)) {
    			numbersInArray = false;
    		}
    	}
    	return numbersInArray;
    }

}