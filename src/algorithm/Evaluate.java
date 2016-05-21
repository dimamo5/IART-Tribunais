package algorithm;

import data.Database;

/**
 * Created by diogo on 12/03/2016.
 */
public class Evaluate implements Runnable {
    private static int CONST_ACCESS_TRIBUNAL = 4;
    private static int CONST_CONSTRUCT_TRIBUNAL = 10;
    private static int MAX_DIST=50; //50km em linha recta de distancia maxima
    Database db = Database.getInstance();
    private Individual individual;


    public Evaluate(Individual ind) {
        this.individual = ind;
    }

    @Override
    public void run() {
        for (int i = 0; i < Individual.SIZE; i++) {
            if (individual.getGene(i)) {
                individual.addFitness((int) (db.getCounty(i).getPopulation() * CONST_ACCESS_TRIBUNAL - db.getCounty(i).getConstrution_cost() * CONST_CONSTRUCT_TRIBUNAL));
            }else if(true){
                //TODO preencher os outros dois casos
            }else{

            }
        }
    }
}
