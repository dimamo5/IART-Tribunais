package data;

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
}
