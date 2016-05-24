package algorithm;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;

import static java.lang.Math.exp;


public class SimAnnealing {

    private /*final*/ double STARTING_TEMP = 5000;
    private double STOP_CONDITION = 0.01;

    private Individual individual;
    private double coolingRate = 0.001;

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public void setSTARTING_TEMP(double STARTING_TEMP) {
        this.STARTING_TEMP = STARTING_TEMP;
    }


    public void setSTOP_CONDITION(double STOP_CONDITION) {
        this.STOP_CONDITION = STOP_CONDITION;
    }

    public SimAnnealing(Individual ind) {
        this.individual = ind;
    }

    public SimAnnealing() {
        this.individual = new Individual();
        this.individual.randGenes();
    }

    public void run() throws IOException {
        double temperature = STARTING_TEMP;

        //FileWriter file = new FileWriter("test.txt",true);

        double stop_cond = STARTING_TEMP * STOP_CONDITION;


        int total = 0, bad = 0, best = 0, good = 0, fitness_diff = 0, total_changes = 0,genes_iguais=0,changes=0;

        double f, newF;

        while (temperature > stop_cond) {
            for (int i = 0; i < 140; i++) {
                Individual newInd = new Individual(this.individual);  //novo individuo
                newInd.mutate();

                newF = newInd.evaluate(); //newInd Rating
                f = this.individual.evaluate(); //actualInd Rating

                if(!this.individual.getGenes().equals(newInd.getGenes())) {
                    if (this.individual.getFitnessValue() != newInd.getFitnessValue())
                        fitness_diff++;
                }else
                    genes_iguais++;

                total++;
                if (acceptanceProbability(f, newF, temperature)) {
                    if(!this.individual.getGenes().equals(newInd.getGenes())
                            && this.individual.getFitnessValue() != newInd.getFitnessValue()){
                        changes++;
                        this.individual = newInd;
                    }

                    if(!this.individual.getGenes().equals(newInd.getGenes())
                            && this.individual.getFitnessValue() != newInd.getFitnessValue()
                            && this.individual.getFitnessValue() > newInd.getFitnessValue())
                        bad++;

                    //total_changes++;
                }

                if (this.individual.getFitnessValue() > best)   /* testing related */
                    best = this.individual.getFitnessValue();
            }

            temperature *= 1 - coolingRate; // Cool system
        }


        System.out.println("\nGenes Iguais: " + genes_iguais
                +"\nFitness Diferente: " + fitness_diff
                +"\nTroca p/ Individuo != actual: " + changes
                +"\nDowngrade(descida na colina):" + bad
              //  +"\nTotal trocas: " + total_changes
                + "\nTotal Iterações: " + total );

        String s = String.format("\n%15s\n", NumberFormat.getNumberInstance(Locale.GERMANY).format(best));
        String s2 = String.format("%15s\n", NumberFormat.getNumberInstance(Locale.GERMANY).format(this.individual.getFitnessValue()));
        System.out.printf(s);
        System.out.printf(s2 + "\n");
    }


    // TODO: 22/05/2016 Possible optimization: Keep the best individual saved, at the end compare the final result and return to the best state if it's better than the achieved one
    //maximum possible retries(go back to the best state and startover) to be defined

    // Calculate the acceptance probability
    public static boolean acceptanceProbability(double E, double newE, double temperature) {
        double delta_E = newE - E;

        if(delta_E < 0)
            System.out.println("e^("+delta_E+"/"+temperature+") = " + exp(delta_E / temperature) );

        return delta_E >= 0 || exp(delta_E / temperature) > Math.random();
    }



    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 1; i++) {
            SimAnnealing sm1 = new SimAnnealing();
            sm1.setSTOP_CONDITION(0.01);
            // sm1.setSTARTING_TEMP(100*exp(i));
            // sm1.setCoolingRate(0.01);
            sm1.run();
        }
    }


}
