package algorithm;

import java.util.Random;

/**
 * Created by diogo on 10/03/2016.
 */

public class Population {
    public static int ELITISM_K = 5;
    public static int POP_SIZE = 150 + ELITISM_K;  // population size

    private static Random m_rand = new Random();  // random-number generator
    private Individual[] m_population;
    private double totalFitness;

    public Population() {
        m_population = new Individual[POP_SIZE];

        // init population
        for (int i = 0; i < POP_SIZE; i++) {
            m_population[i] = new Individual();
            //Inicializa genes com valor random
            m_population[i].randGenes();
        }

        // evaluate current population
        this.evaluate();
    }

    public Individual[] getPopulation() {
        return this.m_population;
    }

    public void setPopulation(Individual[] newPop) {
        // this.m_population = newPop;
        System.arraycopy(newPop, 0, this.m_population, 0, POP_SIZE);
    }

    public double evaluate() {
        this.totalFitness = 0.0;
        for (int i = 0; i < POP_SIZE; i++) {
            this.totalFitness += m_population[i].evaluate();
        }
        return this.totalFitness;
    }

    public Individual rouletteWheelSelection() {
        double randNum = m_rand.nextDouble() * this.totalFitness;
        int idx;
        for (idx = 0; idx < POP_SIZE && randNum > 0; ++idx) {
            randNum -= m_population[idx].getFitnessValue();
        }
        return m_population[idx - 1];
    }

    public Individual findBestIndividual() {
        int idxMax = 0, idxMin = 0;
        double currentMax = 0.0;
        double currentMin = 1.0;
        double currentVal;

        for (int idx = 0; idx < POP_SIZE; ++idx) {
            currentVal = m_population[idx].getFitnessValue();
            if (currentMax < currentMin) {
                currentMax = currentMin = currentVal;
                idxMax = idxMin = idx;
            }
            if (currentVal > currentMax) {
                currentMax = currentVal;
                idxMax = idx;
            }
            if (currentVal < currentMin) {
                currentMin = currentVal;
                idxMin = idx;
            }
        }

        //return m_population[idxMin];      // minimization
        return m_population[idxMax];        // maximization
    }

    public Individual[] crossover(Individual indiv1, Individual indiv2) {
        Individual[] newIndiv = new Individual[2];
        newIndiv[0] = new Individual();
        newIndiv[1] = new Individual();


        int randPoint = m_rand.nextInt(Individual.SIZE);
        int i;
        for (i = 0; i < randPoint; ++i) {
            newIndiv[0].setGene(i, indiv1.getGene(i));
            newIndiv[1].setGene(i, indiv2.getGene(i));
        }
        for (; i < Individual.SIZE; ++i) {
            newIndiv[0].setGene(i, indiv2.getGene(i));
            newIndiv[1].setGene(i, indiv1.getGene(i));
        }

        return newIndiv;
    }

    @Override
    public String toString() {
        return "Population{" +
                "bestIndividual=" + this.findBestIndividual() +
                '}';
    }
}
