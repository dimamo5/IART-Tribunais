package data;

import algorithm.Utils;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by diogo on 12/03/2016.
 */
public class Database {
    private ArrayList<County> counties = new ArrayList<County>();
    private int size;
    private static Database instance = null;
    private long[][] distMatrix;

    private Database() {
        ParseCounty parser = new ParseCounty("resources/countyInfo.json");
        try {
            counties = parser.getCounties();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.size = counties.size();
        distMatrix=new long[size][size];
        calcDistMatrix();

    }

    public long[][] getDistMatrix() {
        return distMatrix;
    }

    public County getCounty(int i) {
        return counties.get(i);
    }

    public int getSize() {
        return size;
    }

    public ArrayList<County> getCounties() {
        return counties;
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Pre process the distances between all counties and puts them into a matrix
     */
    public void calcDistMatrix(){
        for(int i =1;i<size;i++){
            long[] row= distMatrix[i-1];
            for(int m=1;m<size;m++){
                distMatrix[i-1][m-1]= (long) Utils.distFrom(this.getCounty(i).getLatitude(),this.getCounty(i).getLongitude(),this.getCounty(m).getLatitude(),this.getCounty(m).getLongitude());
            }
        }
        // TODO: 21/05/2016 verificar se info na matrix está correcta já que existem muitos valores a 0 no final
        //Printing func
        /*for (int i = 0; i < distMatrix.length; i++) {
            for (int j = 0; j < distMatrix[0].length; j++) {
                System.out.print(distMatrix[i][j] + " ");
            }
            System.out.print("\n");
        }*/
    }

    public static void main(String[] args){
        Database.getInstance();
    }

}
