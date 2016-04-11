/**
 * Created by diogo on 10/03/2016.
 */

package Algorithm;

import data.Database;

import java.util.Arrays;
import java.util.Random;

public class Individual {

    public static int SIZE;
    private int[] genes;
    private int fitness;

    Database counties = Database.getInstance();

    public Individual() {
        this.SIZE = counties.getSize();
        this.genes = new int[SIZE];

    }

    public Individual(Individual ind) {
        this.genes = Arrays.copyOf(ind.getGenes(), ind.getGenes().length);
        this.fitness = ind.getFitnessValue();

    }

    @Override
    public String toString() {
        return "Individual{" +
                "genes=" + Arrays.toString(genes) +
                ", fitness=" + fitness +
                '}';
    }

    public int getFitnessValue() {
        return fitness;
    }

    public int getGene(int index) {
        return genes[index];
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGene(int index, int gene) {
        this.genes[index] = gene;
    }

    public void randGenes() {
        Random rand = new Random();
        for (int i = 0; i < this.SIZE; ++i) {
            this.setGene(i, rand.nextInt(2));
        }
    }

    public void mutate() {
        Random rand = new Random();
        int index = rand.nextInt(this.SIZE);
        this.setGene(index, 1 - this.getGene(index));    // flip
    }

    public int evaluate() {
        int fitness = 0;
        int count = 0;
        for (int i = 0; i < this.SIZE; ++i) {
            if (this.genes[i] == 1)
                fitness += this.counties.getCounty(i).getPopulation();
        }

        this.fitness = fitness;

        return fitness;
    }

}