/**
 * Created by diogo on 02/03/2016.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(
                    "resources/cocelhosDataJson.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray concelhos = (JSONArray) jsonObject.get("concelhos");

            // loop array
            Iterator<JSONObject> iterator = concelhos.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
