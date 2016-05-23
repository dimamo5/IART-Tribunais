package algorithm;

import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;

import static java.lang.Math.exp;

public class SimAnnealing {

    private /*final*/ double STARTING_TEMP = 5000;
    private double STOP_CONDITION = 0.01;

    private Individual individual;
    private double coolingRate = 0.01;

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
        double temp = STARTING_TEMP;

        int best = 0;
        FileWriter file = new FileWriter("test.txt",true);
        //file.write("Teste\n\n");

        double stop_cond =  STARTING_TEMP*STOP_CONDITION;

        int total=0, bad = 0;

        while (temp > stop_cond) {
            total++;
            for(int i = 0; i < 140; i++) {

                double f = this.individual.evaluate();
                Individual ind = new Individual(this.individual);
                ind.mutate();
                double newF = ind.evaluate();
                if (acceptanceProbability(f, newF, temp) > Math.random()) {
                    bad++;
                    this.individual = ind;
                }
                /* testing related */
                if (this.individual.getFitnessValue() > best)
                    best = this.individual.getFitnessValue();
            }
            // Cool system
            temp *= 1 - coolingRate;
        }

        System.out.println("Total:"+ total+" " + "bad:" + bad );

        String s = String.format("%15s\n",NumberFormat.getNumberInstance(Locale.GERMANY).format(best));
        String s2 = String.format("%15s\n",NumberFormat.getNumberInstance(Locale.GERMANY).format(this.individual.getFitnessValue()));

        file.write(s);
        file.write(s2);
        file.write("\n\n");
        file.flush();
        file.close();

        System.out.printf(s);
        System.out.printf(s2);
    }

    // TODO: 22/05/2016 Possible optimization: Keep the best individual saved, at the end compare the final result and return to the best state if it's better than the achieved one
    //maximum possible retries(go back to the best state and startover) to be defined



    // Calculate the acceptance probability
    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better (>), accept it
        if (newEnergy > energy) {
            return 1.0;
        } else {  // If the new solution is worse, calculate an acceptance probability
            /* Energy = valor retornado da funçao de fitness
            A probabilidade de aceitar vai ser tanto maior quanto menor a diferença entre newEnergy e ActualEnergy
            ou seja, ex: A: NewEnergy=10, Energy=11  , B: NewEnergy= 2 , Energy=11
            acceptanceProbability(A) > acceptanceProbability(B)   */

            return exp((newEnergy - energy) / temperature);
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("===============TESTE 0.001================\n\n");

       for(int i = 0; i < 1 ; i++) {
            SimAnnealing sm1 = new SimAnnealing();
            sm1.setSTOP_CONDITION(0.1);
           // sm1.setSTARTING_TEMP(100*exp(i));
           // sm1.setCoolingRate(0.01);
            sm1.run();
       }
    }


}
