/**
 * Created by diogo on 10/03/2016.
 */

package algorithm;

import data.Database;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

public class Individual {

    public static int SIZE;
    private BitSet genes;
    private int fitness;

    private Database counties = Database.getInstance();

    public Individual() {
        SIZE = counties.getSize();
        this.genes = new BitSet(SIZE);
        this.fitness = 0;
    }

    public Individual(Individual ind) {
        this.genes = (BitSet) ind.getGenes().clone();
        this.fitness = ind.getFitnessValue();
    }

    public void addFitness(int fit) {
        fitness += fit;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "fitness=" + fitness +
                ", genes=" + genes.toString() +
                '}';
    }

    public int getFitnessValue() {
        return fitness;
    }

    public boolean getGene(int index) {
        return genes.get(index);
    }

    public BitSet getGenes() {
        return genes;
    }

    public void setGene(int index, boolean gene) {
        this.genes.set(index,gene);
    }

    public void randGenes() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; ++i) {
            this.setGene(i, rand.nextBoolean());
        }
    }
    public int countGenePositive(){
        int count=0;
        for ( int i=0;i< genes.length();i++){
            if(genes.get(i)){
                count++;
            }
        }
        return  count;
    }


    public void mutate() {
        Random rand = new Random();
        int index = rand.nextInt(SIZE);
        this.genes.set(index, !this.genes.get(index));    // flip
    }

    public int evaluate() {
        int fitness = 0;
        int count = 0;
        /*for (int i = 0; i < SIZE; ++i) {
            if (this.genes[i] == 1)
                fitness += this.counties.getCounty(i).getPopulation();
        }*/

        this.fitness = fitness;

        return fitness;
    }


}
