package ic.practica2.base;

import java.util.ArrayList;
import java.util.List;

public class Populacion {

    private List<Individuo> individuals;

    public Populacion(int size, boolean createNew) {
        individuals = new ArrayList<Individuo>();
        if (createNew) {
            createNewPopulation(size);
        }
    }

    protected Individuo getIndividual(int index) {
        return individuals.get(index);
    }

    protected Individuo getFittest() {
        Individuo fittest = individuals.get(0);
        for (int i = 0; i < individuals.size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    private void createNewPopulation(int size) {
        for (int i = 0; i < size; i++) {
            Individuo newIndividual = new Individuo();
            individuals.add(i, newIndividual);
        }
    }
    
    protected List<Individuo> getIndividuos() {
    	return this.individuals;
    }
}
