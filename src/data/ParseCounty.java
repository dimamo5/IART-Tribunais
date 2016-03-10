package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by diogo on 10/03/2016.
 */
public class ParseCounty {
    private FileReader file;

    public ParseCounty(String file) {
        try {
            this.file = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<County> getCounties() throws IOException, ParseException {
        ArrayList<County> listCounties = new ArrayList<County>();

        JSONParser parser = new JSONParser();

        System.out.print("Started parsing counties info ");

        Object obj = parser.parse(this.file);

        JSONObject jsonObject = (JSONObject) obj;

        JSONArray counties = (JSONArray) jsonObject.get("counties");

        Iterator<JSONObject> iterator = counties.iterator();

        while (iterator.hasNext()) {
            JSONObject countyJSON = iterator.next();

            Integer pos = ((Long) countyJSON.get("position")).intValue();
            String name = (String) countyJSON.get("name");
            Integer populacao = ((Long) countyJSON.get("population")).intValue();
            Double percTotal = (Double) countyJSON.get("percTotal");
            Double latitude = (Double) countyJSON.get("latitude");
            Double longitude = (Double) countyJSON.get("longitude");


            County c = new County(pos, name, populacao, percTotal, latitude, longitude);

            listCounties.add(c);
            System.out.print('.');

        }
        return listCounties;
    }


    /**
     * Gets the latitude and longitude of a specific County Name
     *
     * @param countyName County Name
     * @return Json file with all the geografic info
     * @throws IOException
     */
    public static String getCoords(String countyName) throws IOException {
        String googlekey = "AIzaSyAuUKUyv4JqgLzbTBtiic3XGFXSNvvqiRI";

        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(countyName, "utf-8") + ",Portugal&key=" + googlekey);
        InputStream is = (InputStream) url.getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null)
            sb.append(line);
        return sb.toString();

    }

    /**
     * Transforms the old json file with only the County Population info and adds the location.
     * Creates a new json file with all the info compiled
     */
    private void jsonCountyPlusLocation() {
        JSONParser parser = new JSONParser();

        ArrayList<County> counties = new ArrayList<County>();

        try {

            Object obj = parser.parse(new FileReader(
                    "resources/countydata.json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray concelhos = (JSONArray) jsonObject.get("concelhos");


            // loop array
            Iterator<JSONObject> iterator = concelhos.iterator();
            while (iterator.hasNext()) {
                JSONObject countyObj = iterator.next();

                Integer pos = Integer.valueOf((String) countyObj.get("Posicao"));
                String name = (String) countyObj.get("Concelho");
                name = name.trim();
                Integer populacao = Integer.valueOf((String) countyObj.get("População residente"));
                Double percTotal = (NumberFormat.getNumberInstance(Locale.FRANCE).parse((String) countyObj.get("% do Total"))).doubleValue();

                String jsonCounty = getCoords(name);

                JSONObject county = (JSONObject) parser.parse(jsonCounty);
                JSONArray results = (JSONArray) county.get("results");
                JSONObject geometry = (JSONObject) results.get(0);
                JSONObject geometry1 = (JSONObject) geometry.get("geometry");
                JSONObject location = (JSONObject) geometry1.get("location");
                Double lat = (Double) location.get("lat");
                Double lng = (Double) location.get("lng");

                County c = new County(pos, name, populacao, percTotal, lat, lng);

                counties.add(c);

            }

            JSONArray countiesArray = new JSONArray();

            for (County p : counties) {
                countiesArray.add(p.getJson());
                System.out.println(p.toString());
            }
            JSONObject county = new JSONObject();
            county.put("counties", countiesArray);
            //System.out.println(counties.toString());


            try (FileWriter file = new FileWriter("resources/countyInfo.json")) {
                file.write(county.toJSONString());
                System.out.println("Successfully Copied JSON Object to File...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

