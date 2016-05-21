package algorithm;

import data.Database;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by diogo on 12/03/2016.
 */
public class Evaluate implements Runnable {
    private static int CONST_ACCESS_TRIBUNAL = 4;
    private static int CONST_CONSTRUCT_TRIBUNAL = 10;
    private static int MAX_DIST=50000; //50000m em linha recta de distancia maxima
    private Database db = Database.getInstance();
    private Individual individual;


    public Evaluate(Individual ind) {
        this.individual = ind;
    }

    @Override
    public void run() {
        for (int i = 0; i < Individual.SIZE; i++) {
            if (individual.getGene(i)) {
                individual.addFitness((int) (db.getCounty(i).getPopulation() * CONST_ACCESS_TRIBUNAL - db.getCounty(i).getConstrution_cost() * CONST_CONSTRUCT_TRIBUNAL));
            }else if(individual.getGene(i) && getMin(i)){
                //TODO preencher os outros dois casos
            }else{

            }
        }
    }

    public int getMin(int countyPos){
        long[] row= db.getDistMatrix()[countyPos];
        long min = Long.MAX_VALUE;
        int pos=0;
        for ( int i =0; i< row.length;i++){
            if(min> row[i] && row[i]>=7){   //GPS Coord has a tolerance of 7 meters
                min= row[i];
                pos=i;
            }
        }
        return pos;

    }

}
