package Algorithm;

public class SimAnnealing {

    private final double STARTING_TEMP = 1000;
    private Individual individual;
    private double coolingRate = 0.01;

    public SimAnnealing(Individual ind) {
        this.individual = ind;
    }

    public SimAnnealing() {
        this.individual = new Individual();
        this.individual.randGenes();
    }

    public void run() {
        double temp = STARTING_TEMP;

        while (temp > 1) {
            double f = this.individual.evaluate();
            Individual ind = new Individual(this.individual);
            ind.mutate();
            double newF = ind.evaluate();
            if (acceptanceProbability(f, newF, temp) > Math.random()) {
                this.individual = ind;
            }
            // Cool system
            temp *= 1 - coolingRate;
            System.out.println(this.individual.toString());
        }
    }

    // Calculate the acceptance probability
    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }

    public static void main(String[] args) {
        new SimAnnealing().run();
    }


}
