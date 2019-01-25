package ic.practica2.base;

public class Algoritmo {
	
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.025;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static byte[] solution = new byte[64];

    public boolean runAlgorithm(int populationSize, String solution) {
        if (solution.length() != Algoritmo.solution.length) {
            throw new RuntimeException("The solution needs to have " + Algoritmo.solution.length + " bytes");
        }
        setSolution(solution);
        Populacion myPop = new Populacion(populationSize, true);

        int generationCount = 1;
        while (myPop.getFittest().getFitness() < getMaxFitness()) {
            System.out.println("Generation: " + generationCount + " Correct genes found: " + myPop.getFittest().getFitness());
            myPop = evolvePopulacion(myPop);
            generationCount++;
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(myPop.getFittest());
        return true;
    }

    public Populacion evolvePopulacion(Populacion pop) {
        int elitismOffset;
        Populacion newPopulation = new Populacion(pop.getIndividuos().size(), false);

        if (elitism) {
            newPopulation.getIndividuos().add(0, pop.getFittest());
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        for (int i = elitismOffset; i < pop.getIndividuos().size(); i++) {
            Individuo indiv1 = tournamentSelection(pop);
            Individuo indiv2 = tournamentSelection(pop);
            Individuo newIndiv = crossover(indiv1, indiv2);
            newPopulation.getIndividuos().add(i, newIndiv);
        }

        for (int i = elitismOffset; i < newPopulation.getIndividuos().size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    private Individuo crossover(Individuo indiv1, Individuo indiv2) {
        Individuo newSol = new Individuo();
        for (int i = 0; i < newSol.getDefaultGeneLength(); i++) {
            if (Math.random() <= uniformRate) {
                newSol.setSingleGene(i, indiv1.getSingleGene(i));
            } else {
                newSol.setSingleGene(i, indiv2.getSingleGene(i));
            }
        }
        return newSol;
    }

    private void mutate(Individuo indiv) {
        for (int i = 0; i < indiv.getDefaultGeneLength(); i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setSingleGene(i, gene);
            }
        }
    }

    private Individuo tournamentSelection(Populacion pop) {
        Populacion tournament = new Populacion(tournamentSize, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getIndividuos().size());
            tournament.getIndividuos().add(i, pop.getIndividual(randomId));
        }
        Individuo fittest = tournament.getFittest();
        return fittest;
    }

    protected static int getFitness(Individuo individual) {
        int fitness = 0;
        for (int i = 0; i < individual.getDefaultGeneLength() && i < solution.length; i++) {
            if (individual.getSingleGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }

    protected int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }

    protected void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

}
