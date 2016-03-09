import org.json.simple.JSONObject;

/**
 * Created by diogo on 08/03/2016.
 */
public class County {
    private int position;
    private String name;
    private int population;
    private double percTotal;
    private double latitude;
    private double longitude;

    County(int pos,String name,int population,double percTotal,double latitude,double longitude){
        this.position=pos;
        this.name=name;
        this.population=population;
        this.percTotal=percTotal;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    @Override
    public String toString() {
        return "Posição: "+position+" Nome:"+name + " População: " + population +" Percentagem do Total: "+percTotal+ " Coordenadas: (" + latitude + "," + longitude + ")";
    }

    public JSONObject getJson(){
        JSONObject obj = new JSONObject();

        obj.put("position", this.position);
        obj.put("name", this.name);
        obj.put("population", population);
        obj.put("percTotal", percTotal);
        obj.put("latitude",latitude);
        obj.put("longitude",longitude);

        return obj;
    }

}
