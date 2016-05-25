package algorithm;

import java.util.Random;

/**
 * Created by diogo on 20/05/2016.
 */
public class GeneticAlgorithm {
    public static int MAX_ITER = 500;             // max number of iterations
    public static double MUTATION_RATE = 0.1;     // probability of mutation
    public static double CROSSOVER_RATE = 0.8;     // probability of crossover

    private Population pop = new Population();
    private Individual[] newPop = new Individual[Population.POP_SIZE];
    private Individual[] indiv = new Individual[2];
    private Random rand = new Random();
    private Individual bestIndiv;

    public static void main(String[] args) {
        new GeneticAlgorithm().start();
    }

    public Individual getBestIndiv() {
        return bestIndiv;
    }

    // TODO: 25/05/2016 melhorar isto
    public void startIte(int ite) {
        // main loop
        int count;
        if (ite < MAX_ITER) {
            count = 0;

            // Elitism
            for (int i = 0; i < Population.ELITISM_K; ++i) {
                newPop[count] = pop.findBestIndividual();
                count++;
            }

            // build new Population
            while (count < Population.POP_SIZE) {
                // Selection
                //System.out.println("selectionAntes"+indiv[0].getGenes().length()+indiv[1].getGenes().length());
                indiv[0] = pop.rouletteWheelSelection();
                indiv[1] = pop.rouletteWheelSelection();

                // Crossover
                if (rand.nextDouble() < CROSSOVER_RATE) {
                    indiv = pop.crossover(indiv[0], indiv[1]);
                }

                // Mutation
                if (rand.nextDouble() < MUTATION_RATE) {
                    indiv[0].mutate();
                }
                if (rand.nextDouble() < MUTATION_RATE) {
                    indiv[1].mutate();
                }

                // add to new population
                newPop[count] = indiv[0];
                newPop[count + 1] = indiv[1];
                count += 2;
            }
            pop.setPopulation(newPop);

            // reevaluate current population
            pop.evaluate();
            System.out.println(pop.toString());
        }

        // best indiv
        this.bestIndiv = pop.findBestIndividual();
    }

    public void start() {
        // main loop
        int count;
        for (int iter = 0; iter < MAX_ITER; iter++) {
            count = 0;

            // Elitism
            for (int i = 0; i < Population.ELITISM_K; ++i) {
                newPop[count] = pop.findBestIndividual();
                count++;
            }

            // build new Population
            while (count < Population.POP_SIZE) {
                // Selection
                //System.out.println("selectionAntes"+indiv[0].getGenes().length()+indiv[1].getGenes().length());
                indiv[0] = pop.rouletteWheelSelection();
                indiv[1] = pop.rouletteWheelSelection();

                // Crossover
                if (rand.nextDouble() < CROSSOVER_RATE) {
                    indiv = pop.crossover(indiv[0], indiv[1]);
                }

                // Mutation
                if (rand.nextDouble() < MUTATION_RATE) {
                    indiv[0].mutate();
                }
                if (rand.nextDouble() < MUTATION_RATE) {
                    indiv[1].mutate();
                }

                // add to new population
                newPop[count] = indiv[0];
                newPop[count + 1] = indiv[1];
                count += 2;
            }
            pop.setPopulation(newPop);

            // reevaluate current population
            pop.evaluate();
            System.out.println(pop.toString());
        }

        // best indiv
        this.bestIndiv = pop.findBestIndividual();
    }
}
