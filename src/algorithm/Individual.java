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

    public Individual(String genes){
        SIZE=genes.length();
        this.genes=new BitSet(SIZE);
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
        Individual ind = new Individual("11100010001001100110110101100001000111000111010100101000101001001000101110101000110111011010010001100110001100100101110001011010110100110000110110011110100101100011100011101010100000001001001001010110100110100010011100100001111100100001011001000011001111001001111101011001100011011");
        ind.evaluate();
        System.out.println("Ind1: "+ind);

        Individual ind1 = new Individual("01100010011001000110110101100001000111000111010100101000101001001000101110101000110111011010010001100110001100100101110001011010110100110000110110011110100101100011100011101010100000001001001001010110100110100010011100100001111100100001011001000011001111001001111101011001100011011");
        ind1.evaluate();
        System.out.println("Ind1: "+ind1);
    }
}
