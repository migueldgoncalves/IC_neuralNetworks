package ic.practica2.base;

import java.util.ArrayList;

import ic.practica2.filereader.FileReader;

public class Algoritmo {
	
	public static final int POPULATION_SIZE = 100;
	public static final int GENERATION_NUMBER = 100;
	public static final double CROSSOVER_PROBABILITY = 1;
	public static final double MUTATION_PROBABILITY = 1;
	private static final int TOURNAMENT_SIZE = 5;
	
    private static final boolean elitism = true;
    
    private Populacion populacion = new Populacion(POPULATION_SIZE);

    public Algoritmo() {
    	
    }
    
    public void execute() {

        int generation = 1;
        while (generation <= GENERATION_NUMBER) {
        	
            System.out.println("Generacion actual: " + generation);
            System.out.println("Coste minimo encontrado: " + populacion.minCost());
            populacion = evolvePopulacion(populacion);
            generation++;
        }
        
        System.out.println("FIN DE LA EJECUCION");
        System.out.println("Coste minimo encontrado: " + populacion.minCost());
        System.out.println(populacion.bestIndividual().genes.toString());
        
    }

    public Populacion evolvePopulacion(Populacion population) {
        int elitismOffset;
        Populacion newPopulation = new Populacion(Main.fileSize);

        if (elitism) {
            newPopulation.individuals.set(0, population.bestIndividual());
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        for (int i=elitismOffset; i<Main.fileSize; i++) {
        	FileReader.log.info(String.valueOf(i));
            Individuo individuo1 = tournamentSelection();
            Individuo individuo2 = tournamentSelection();  
            Individuo newIndividuo = crossover(individuo1, individuo2);
            newPopulation.individuals.add(i, newIndividuo);
        }

        for (int i=elitismOffset; i<Main.fileSize; i++) {
            mutate(newPopulation.individuals.get(i));
        }
        return newPopulation;
    }
    
    public Individuo tournamentSelection() {
        Populacion torneo = new Populacion(TOURNAMENT_SIZE);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int random = (int) (Math.random() * POPULATION_SIZE-1);
            torneo.individuals.set(i, populacion.individuals.get(random));
        }
        return torneo.bestIndividual();
    }

    public Individuo crossover(Individuo individuo1, Individuo individuo2) {
        Individuo nuevo = new Individuo();
        if (Math.random() <= CROSSOVER_PROBABILITY) {
            for(int i=0; i<Main.fileSize; i++)
            	nuevo.genes.set(i, 0);
        	for(int i=0; i<Main.fileSize/2; i++) //La primera mitad del nuevo individuo viene del primero padre
        		nuevo.genes.set(i, individuo1.genes.get(i));
        	int genesRellenados = Main.fileSize/2;
        	int iterador = Main.fileSize/2;
        	while (genesRellenados != Main.fileSize) { //Rellena los restantes genes del nuevo individuo
        		if(!nuevo.genes.contains((int)individuo2.genes.get(iterador))) {
        			nuevo.genes.set(genesRellenados, individuo2.genes.get(iterador));
        			genesRellenados++;
        		}
        		iterador++;
        		/*FileReader.log.info("Iterador: " + String.valueOf(iterador));
        		FileReader.log.info("Genes rellenados: " + String.valueOf(genesRellenados));*/
        		if(iterador==Main.fileSize)
        			iterador=0;
        	}
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
    
    public static boolean numberInArray(ArrayList<Integer> array, int number) {
    	for(int i=0; i<array.size(); i++)
    		if(array.get(i)==number)
    			return true;
    	return false;
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