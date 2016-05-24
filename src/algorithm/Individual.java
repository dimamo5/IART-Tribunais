/**
 * Created by diogo on 10/03/2016.
 */

package algorithm;

import data.Database;

import java.text.NumberFormat;
import java.util.BitSet;
import java.util.Random;

public class Individual {

    public static int SIZE;
    private boolean[] genes;
    private int fitness;

    private Database counties = Database.getInstance();

    public Individual() {
        SIZE = counties.getSize();
        this.genes = new boolean[SIZE];
        this.fitness = 0;
    }

    public Individual(String genes){
        SIZE=genes.length();
        this.genes = new boolean[SIZE];
        this.fitness=0;
        for (int i = 0; i < genes.length(); ++i) {
            if(genes.charAt(i)=='0'){
                this.setGene(i,false);
            }else{
                this.setGene(i,true);
            }
        }
    }

    public Individual(Individual ind) {
        System.arraycopy(this.genes, 0, ind.getGenes(), 0, ind.getGenes().length);
        this.fitness = ind.getFitnessValue();
    }

    public void addFitness(int fit) {
        if (fitness + fit > 0)
            fitness += fit;
    }

    public void resetFitness() {
        this.fitness = 0;
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i < genes.length; i++) {
            if (genes[i]) {
                s+="1";
            }else{
                s+="0";
            }
        }
        return "Individual{" + "Count: " + this.countGenePositive() +
                " ,fitness=" + String.format("%12s", NumberFormat.getNumberInstance().format(fitness)) +
                ", genes=" + s +
                '}';
    }

    public int getFitnessValue() {
        return fitness;
    }

    public boolean getGene(int index) {
        return genes[index];
    }

    public boolean[] getGenes() {
        return genes;
    }

    public void setGene(int index, boolean gene) {
        this.genes[index] = gene;
    }

    public void randGenes() {
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            this.setGene(i, rand.nextBoolean());
        }
    }
    public int countGenePositive(){
        int count=0;
        for (int i = 0; i < genes.length; i++) {
            if (getGene(i)) {
                count++;
            }
        }
        return  count;
    }


    public void mutate() {
        Random rand = new Random();
        int index = rand.nextInt(SIZE);
        this.genes[index] = !this.genes[index];
    }

    /**
     * Calculates the fitness value of the individual
     * NOTE: Avoid using this since evalute implements Threads and function better with them!
     * @return
     */
    public double evaluate() {
       new Evaluate(this).run();
        return fitness;
    }


}
