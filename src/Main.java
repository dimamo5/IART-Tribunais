/**
 * Created by diogo on 02/03/2016.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        ArrayList<County>counties=new ArrayList<County>();

        try {

            Object obj = parser.parse(new FileReader(
                    "resources/countydata.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray concelhos = (JSONArray) jsonObject.get("concelhos");



            // loop array
            Iterator<JSONObject> iterator = concelhos.iterator();
            while (iterator.hasNext()) {
                JSONObject countyObj=iterator.next();

                Integer pos=Integer.valueOf((String)countyObj.get("Posicao"));
                String name=(String)countyObj.get("Concelho");
                name=name.trim();
                Integer populacao=Integer.valueOf((String) countyObj.get("População residente"));
                Double percTotal= (NumberFormat.getNumberInstance(Locale.FRANCE).parse((String)countyObj.get("% do Total"))).doubleValue();

                String jsonCounty=getCoords(name);

                JSONObject county=(JSONObject) parser.parse(jsonCounty);
                JSONArray results=(JSONArray)county.get("results");
                JSONObject geometry=(JSONObject) results.get(0);
                JSONObject geometry1=(JSONObject) geometry.get("geometry");
                JSONObject location=(JSONObject) geometry1.get("location") ;
                Double lat=(Double) location.get("lat");
                Double lng=(Double) location.get("lng");

                County c=new County(pos,name,populacao,percTotal,lat,lng);

                counties.add(c);

            }

            JSONArray countiesArray=new JSONArray();

            for (County p : counties){
                countiesArray.add(p.getJson());
                System.out.println(p.toString());
            }
            JSONObject county = new JSONObject();
            county.put("counties",countiesArray);
            //System.out.println(counties.toString());


            try (FileWriter file = new FileWriter("resources/countyTest.json")) {
                file.write(county.toJSONString());
                System.out.println("Successfully Copied JSON Object to File...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static String getCoords(String countyName) throws IOException {
        String googlekey="AIzaSyAuUKUyv4JqgLzbTBtiic3XGFXSNvvqiRI";

        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(countyName,"utf-8") +",Portugal&key=" + googlekey);
        InputStream is = (InputStream)url.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null)
                sb.append(line);
        return sb.toString();

    }

}
