/**
 * Created by diogo on 02/03/2016.
 */

import data.County;
import data.ParseCounty;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<County> counties = new ArrayList<>();
        try {
            counties = new ParseCounty("resources/countyInfo.json").getCounties();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (County p : counties) {
            System.out.println(p.toString());
        }

    }

}
