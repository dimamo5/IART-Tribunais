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

    public Individual(Boolean[] genes){
        SIZE=genes.length;
        this.genes=new BitSet(SIZE);
        this.fitness=0;
        for (int i = 0; i < genes.length; ++i) {
            this.setGene(i, genes[i]);
        }
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
        String s="";
        for(int i =0;i< genes.length();i++){
            if(genes.get(i)){
                s+="1";
            }else{
                s+="0";
            }
        }
        return "Individual{" +
                "fitness=" + fitness +
                ", genes=" + s +
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

    /**
     * Calculates the fitness value of the individual
     * NOTE: Avoid using this since!
     * @return
     */
    public int evaluate() {
       new Evaluate(this).run();
        return fitness;
    }

    public static void main(String[] args){
        Individual ind = new Individual();
        ind.randGenes();
        System.out.println("Ind: "+ind);
    }
}
