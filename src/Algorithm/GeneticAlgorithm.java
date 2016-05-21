package algorithm;

import java.util.Random;

/**
 * Created by diogo on 20/05/2016.
 */
public class GeneticAlgorithm {
    public final static int MAX_ITER = 100;             // max number of iterations
    public final static double MUTATION_RATE = 0.1;     // probability of mutation
    public final static double CROSSOVER_RATE = 0.75;     // probability of crossover


    private Population pop = new Population();
    private Individual[] newPop = new Individual[Population.POP_SIZE];
    private Individual[] indiv = new Individual[2];
    private Random rand = new Random();

    public static void main(String[] args) {
        new GeneticAlgorithm().start();
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
        Individual bestIndiv = pop.findBestIndividual();
    }
}
