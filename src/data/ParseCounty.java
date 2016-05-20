package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.*;

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
            Double construction_const = (Double) countyJSON.get("construction cost");

            County c = new County(pos, name, populacao, percTotal, latitude, longitude, construction_const);

            listCounties.add(c);
            System.out.print('.');
        }

        System.out.println("");
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
       // ArrayList<String> counties_name = new ArrayList<String>();

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

                County c = new County(pos, name, populacao, percTotal, lat, lng, 0);

                counties.add(c);
                //counties_name.add(name);
            }

            /*
            //adcicionar cenas
            String s = "Alijó=600, Vila Flor=600, Grândola=2.915, Vila do Porto=600, Meda=600, Moura=656, Covilhã=608, Marvão=600, Gouveia=600, Mesão Frio=600, Nelas=438, Calheta (Madeira)=600, Avis=600, Vila do Conde=600, Sernancelhe=600, Mondim de Basto=600, Benavente=1.109, Marinha Grande=600, Lourinhã=956, Caldas da Rainha=600, Alvito=600, Loures=1.350, Aveiro=919, Torres Novas=600, Penamacor=600, Tomar=869, Barrancos=600, Sabugal=600, Miranda do Douro=600, Portalegre=600, Ourém=705, Tabuaço=600, Tarouca=600, Crato=600, Ribeira Grande=600, Vimioso=600, Fronteira=600, Almeida=600, Vila Nova de Paiva=600, Peso da Régua=600, Idanha-a-Nova=600, Cantanhede=860, Condeixa-a-Nova=815, Alfandega da Fé=600, Ponte da Barca=600, Vila Pouca de Aguiar=600, Santa Cruz da Graciosa=600, Coruche=858, Marco de Canaveses=600, Tondela=537, Ovar=963, Viseu=770, Arronches=600, Paredes=842, Aljustrel=600, Entroncamento=883, Leiria=846, Fornos de Algodres=600, Seixal=1.292, Cuba=687, Sintra=1.733, Amarante=786, Montalegre=686, Faro=2.017, Matosinhos=1.259, Moimenta da Beira=600, Paços de Ferreira=600, Vila Viçosa=600, Velas=705, Ponte de Sor=600, Monchique=600, Lagos=2.558, Mafra=1.287, Angra do Heroísmo=600, Valongo=917, Rio Maior=600, Castelo de Vide=600, Castanheira de Pêra=600, Baião=829, Montemor-o-Novo=1.659, Peniche=1.093, Ribeira Brava=600, Vinhais=600, Oeiras=2.178, Valpaços=600, Mealhada=785, Pedrógão Grande=600, Odemira=1.435, Batalha=870, Lamego=600, Cadaval=722, Silves=1.596, Armamar=600, Mértola=788, Mação=600, Machico=597, Celorico de Basto=600, Montemor-o-Velho=761, Viana do Castelo=600, Almeirim=804, Santa Marta de Penaguião=600, Penedono=600, Vila de Rei=600, São Brás de Alportel=600, Ílhavo=995, Beja=765, Coimbra=1.068, Almodôvar=600, Tavira=1.873, Santarém=839, Loulé=2.591, Oliveira de Frades=600, Santa Maria da Feira=600, São Roque do Pico=600, Mira=913, Santana=941, Freixo de Espada à Cinta=600, Vila Nova de Gaia=600, Vila Verde=600, Lisboa=3.266, Almada=1.537, Aljezur=1.392, Alcanena=520, Ferreira do Alentejo=600, Salvaterra de Magos=600, Belmonte=600, Miranda do Corvo=600, Reguengos de Monsaraz=600, Nazaré=1.423, Felgueiras=1.036, Vieira do Minho=600, Trofa=892, Porto Moniz=600, Castro Marim=600, Carregal do Sal=600, Povoa De Varzim=600, Águeda=645, Alcoutim=730, Golegã=631, Praia da Vitória=600, Barcelos=1.098, Serpa=648, Santa Cruz das Flores=600, Penacova=656, Amadora=1.410, Vendas Novas=600, Viana do Alentejo=600, Caminha=1.413, Fafe=719, Manteigas=600, Gondomar=940, Cabeceiras de Basto=600, Cinfães=748, Elvas=1.033, Pinhel=600, Barreiro=1.015, Vagos=840, Campo Maior=600, Óbidos=1.419, Alcochete=1.364, Monção=600, Azambuja=829, Lousada=694, Sardoal=600, Oliveira de Azeméis=600, Figueira da Foz=600, São João da Pesqueira=600, Aguiar da Beira=600, Mirandela=600, Ferreira do Zêzere=600, Pampilhosa da Serra=600, Ponte de Lima=600, Seia=600, Alcácer do Sal=600, Bragança=579, Calheta (São Jorge)=600, Vila Nova de Foz Côa=600, Vila Nova de Cerveira=600, São João da Madeira=600, Ponta do Sol=600, Sabrosa=600, Figueiró dos Vinhos=600, Santa Comba Dão=600, Tábua=625, Abrantes=745, Penela=776, Sátão=600, Nisa=600, Vila Nova de Famalicão=600, Porto de Mós=600, Mora=600, Redondo=471, Lagoa (Algarve)=600, Câmara de Lobos=600, Castelo de Paiva=600, Vila Real de Santo António=600, Nordeste=705, Albergaria-a-Velha=705, Vila do Bispo=600, Braga=932, Sever do Vouga=600, Penafiel=812, Vila Franca de Xira=600, Chamusca=600, Lajes das Flores=600, Figueira de Castelo Rodrigo=600, Anadia=666, Guimarães=834, Gavião=592, Borba=600, Porto=1.728, Porto Santo=600, Cascais=2.631, Póvoa de Lanhoso=600, Macedo de Cavaleiros=600, Torres Vedras=600, Corvo=645, Lajes do Pico=600, Mogadouro=600, Portimão=1.601, Sobral de Monte Agraço=600, Celorico da Beira=600, Arraiolos=675, Fundão=478, Montijo=980, Arganil=468, Carrazeda de Ansiães=600, Chaves=734, Castelo Branco=600, Góis=600, Palmela=1.141, Penalva do Castelo=600, Lagoa (São Miguel)=600, Monforte=600, Cartaxo=730, Castro Daire=600, Ansião=667, Ribeira de Pena=600, Mangualde=535, Constância=600, Soure=643, Boticas=600, Estarreja=695, Alpiarça=877, Mourão=600, Oliveira do Bairro=600, Évora=1.104, Trancoso=600, Madalena=705, Alvaiázere=636, Arcos de Valdevez=600, Guarda=678, Valença=828, Olhão=1.573, Arruda dos Vinhos=600, Odivelas=1.332, Santa Cruz=600, Torre de Moncorvo=600, Vila Real=600, Setúbal=1.271, Sesimbra=1.318, Ponta Delgada=600, Castro Verde=600, Oleiros=600, Pombal=717, Melgaço=600, São Pedro do Sul=600, Ourique=600, Murtosa=732, Alter do Chão=600, Alandroal=712, Alcobaça=946, Mortágua=600, Estremoz=600, Vidigueira=600, Portel=564, Santo Tirso=600, Bombarral=737, Terras de Bouro=600, Lousã=697, Moita=1.179, Espinho=1.064, Maia=1.015, Paredes de Coura=600, Sertã=600, Albufeira=2.094, Alenquer=815, Arouca=597, Murça=600, Esposende=1.196, Vizela=828, Proença-a-Nova=600, Horta=645, Amares=724, Povoação=597, Oliveira do Hospital=600, Resende=600, Vouzela=607, Santiago do Cacém=600, Funchal=666";
            HashMap<String,String> notfound = new HashMap<>();

            int index = 0;

            for(String c : s.split("(, )" )){
                if( (index = counties_name.indexOf(c.split("=")[0])) != -1){
                    counties.get(index).setConstrution_cost(Double.parseDouble(c.split("=")[1]));
                }
                else {
                    notfound.put(c.split("=")[0], c.split("=")[1]);
                }
            }

            //print not found keys
            for(HashMap.Entry<String,String> entry : notfound.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
            } */

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

    void write_counties(ArrayList<County> c, String file_path){

        JSONArray countiesArray = new JSONArray();

        for (County p : c) {
            countiesArray.add(p.getJson());
            //System.out.println(p.toString());
        }

        JSONObject county = new JSONObject();
        county.put("counties", countiesArray);

        try (FileWriter file = new FileWriter(file_path)) {
            file.write(county.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]){

        ParseCounty p = new ParseCounty("resources/countyInfo.json");
       /* ArrayList<County> c = null;

        try {
           c =  p.getCounties();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] splitted;
        String cost, new_cost = null;
        for(County county : c ){

            cost = Double.toString(county.getConstrution_cost());
            splitted = cost.split("\\.");

            if(splitted[0].length() == 1){
                new_cost = cost.replaceAll("\\.","");
            }
            else new_cost = cost;

          //  System.out.print("Previous: " + county.getConstrution_cost() + " After: " );

            county.setConstrution_cost(Double.parseDouble(new_cost));

         //   System.out.println(county.getConstrution_cost());
        }

        p.write_counties(c, "resources/counties.json");*/

    }


}

