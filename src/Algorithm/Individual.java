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

    //TODO Criar uma classe para as funcoes de fitness
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    //para testes
    public static void main(String[] args) {
        System.out.println("Lisboa->Porto:" + Individual.distFrom(38.7222524, -9.1393366, 41.1579438, -8.629105299999999));
    }

}
