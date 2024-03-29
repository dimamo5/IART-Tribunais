package algorithm;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;


public class SimAnnealing {

    public static double STOP_CONDITION = 0.01;
    public static double coolingRate = 0.01;
    private final double STARTING_TEMP = 1.0;
    public double temperature;
    public double stop_cond;
    private Individual individual;

    public SimAnnealing(Individual ind) {
        this.individual = new Individual(ind);
        this.temperature = STARTING_TEMP;
        this.stop_cond = STARTING_TEMP * STOP_CONDITION;
    }

    public SimAnnealing() {
        this.individual = new Individual();
        this.individual.randGenes();
        this.temperature = STARTING_TEMP;
        this.stop_cond = STARTING_TEMP * STOP_CONDITION;
    }

    // Calculate the acceptance probability
    public static boolean acceptanceProbability(double E, double newE, double temperature) {

        /*if (newE == 0.0)
            return false;*/

        double delta_E = newE - E;

        double k = Math.exp(-(((((delta_E) / E) * 10) / temperature) + 1)); //percentage
        //double k = exp(delta_E / temperature);
        return k <= 1 && (delta_E > 0 || k > Math.random());
    }

    public static void main(String[] args) throws IOException {

        // (int i = 0; i < 10; i++) {
        SimAnnealing sm1 = new SimAnnealing(new Individual("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111011111111101111111010111111011111011110101100111110111000100000101000001001100001000000000000000000100000000000000000100000000000000000000000000000000000000000000000000000000000000000"));
        //sm1.run();
        SimAnnealing sm = new SimAnnealing();
        sm.setCoolingRate(0.1);
        long start = System.currentTimeMillis();
        sm.run();
        System.out.println(System.currentTimeMillis()-start);

        start =System.currentTimeMillis();
        sm = new SimAnnealing();
        sm.setCoolingRate(0.01);
        sm.run();
        System.out.println(System.currentTimeMillis()-start);

        start =System.currentTimeMillis();
        sm = new SimAnnealing();
        sm.setCoolingRate(0.001);
        sm.run();
        System.out.println(System.currentTimeMillis()-start);

        //}
    }

    public void setCoolingRate(double coolingRate) {
        this.coolingRate = coolingRate;
    }

    public void setSTOP_CONDITION(double STOP_CONDITION) {
        this.STOP_CONDITION = STOP_CONDITION;
    }

    public Individual getBestIndiv() {
        return individual;
    }

    public void runIte() {
        int total = 0, bad = 0, best = 0, good = 0, fitness_diff = 0, no_changes = 0, genes_iguais = 0, changes = 0;

        double f, newF;
        if (temperature > stop_cond) {
            for (int i = 0; i < 140; i++) {
                Individual newInd = new Individual(this.individual);  //novo individuo
                newInd.mutate();

                newF = newInd.evaluate(); //newInd Rating
                f = this.individual.evaluate(); //actualInd Rating


                if (!Arrays.equals(this.individual.getGenes(), newInd.getGenes())) {
                    if (this.individual.getFitnessValue() != newInd.getFitnessValue())
                        fitness_diff++;
                    else {/*fitness_eq++*/; this.individual = newInd;}
                } else
                    genes_iguais++;

                total++;

                if (acceptanceProbability(f, newF, temperature)) {
                    if (!Arrays.equals(this.individual.getGenes(), newInd.getGenes())) {
                        this.individual = newInd;
                        changes++;
                    }else
                        no_changes++;

                    if ((newF - f) <= 0)
                        bad++;
                    else
                        good++;
                } else
                    no_changes++;

                if (this.individual.getFitnessValue() > best)   /* testing related */
                    best = this.individual.getFitnessValue();
            }

            temperature *= 1 - coolingRate; // Cool system
        }

        /*System.out.println("\nGenes Iguais: " + genes_iguais
                + "\nFitness Diferente: " + fitness_diff
                + "\nTroca p/ Individuo != actual: " + changes
                + "\nUpgrade: " + good
                + "\nDowngrade(descida na colina):" + bad
                //  +"\nTotal trocas: " + total_changes
                + "\nTotal Iterações: " + total);

        String s = String.format("\n%15s\n", NumberFormat.getNumberInstance(Locale.GERMANY).format(best));
        String s2 = String.format("%15s\n", NumberFormat.getNumberInstance(Locale.GERMANY).format(this.individual.getFitnessValue()));
        System.out.printf(s);
        System.out.printf(s2 + "\n");*/
    }

    public void run() throws IOException {
        int total = 0, bad = 0, best = 0, good = 0, fitness_diff = 0, no_changes = 0, genes_iguais = 0, changes = 0, fitness_eq = 0;

        double f, newF;
        while (temperature > stop_cond) {
            for (int i = 0; i < 140; i++) {
                Individual newInd = new Individual(this.individual);  //novo individuo
                newInd.mutate();

                newF = newInd.evaluate(); //newInd Rating
                f = this.individual.evaluate(); //actualInd Rating


                if (!Arrays.equals(this.individual.getGenes(), newInd.getGenes())) {
                    if (this.individual.getFitnessValue() != newInd.getFitnessValue())
                        fitness_diff++;
                    else {fitness_eq++; this.individual = newInd;}
                } else
                    genes_iguais++;

                total++;

                if (acceptanceProbability(f, newF, temperature)) {
                    if (!Arrays.equals(this.individual.getGenes(), newInd.getGenes())) {
                        this.individual = newInd;
                        changes++;
                    }else
                        no_changes++;

                    if ((newF - f) <= 0)
                        bad++;
                    else
                        good++;
                } else
                    no_changes++;

                if (this.individual.getFitnessValue() > best)   /* testing related */
                    best = this.individual.getFitnessValue();
            }

            temperature *= 1 - coolingRate; // Cool system
        }

        System.out.println("\nGenes Iguais: " + genes_iguais
                + "\nFitness Diferente/Igual: " + fitness_diff + " " + fitness_eq
                + "\nTroca p/ Individuo != actual: " + changes
                + "\n Sem trocas :" + no_changes
                + "\nUpgrade: " + good
                + "\nDowngrade(descida na colina):" + bad
                //  +"\nTotal trocas: " + total_changes
                + "\nTotal Iterações: " + total);

        String s = String.format("\n%15s\n", NumberFormat.getNumberInstance(Locale.GERMANY).format(best));
        String s2 = String.format("%15s\n", NumberFormat.getNumberInstance(Locale.GERMANY).format(this.individual.getFitnessValue()));
        System.out.printf(s);
        System.out.printf(s2 + "\n");
    }
}
