/**
 * Created by diogo on 08/03/2016.
 */
package data;
import org.json.simple.JSONObject;

public class County {
    private int position;
    private String name;
    private int population;

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public double getPercTotal() {
        return percTotal;
    }

    private double percTotal;
    private double latitude;
    private double longitude;
    private double constrution_cost;

    County(int pos, String name, int population, double percTotal, double latitude, double longitude, double constrution_cost) {
        this.position = pos;
        this.name = name;
        this.population = population;
        this.percTotal = percTotal;
        this.latitude = latitude;
        this.longitude = longitude;
        this.constrution_cost = constrution_cost;
    }

    @Override
    public String toString() {
        return "County{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", percTotal=" + percTotal +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", constrution_cost=" + constrution_cost +
                '}';
    }

    public JSONObject getJson() {
        JSONObject obj = new JSONObject();

        obj.put("position", position);
        obj.put("name", name);
        obj.put("population", population);
        obj.put("percTotal", percTotal);
        obj.put("latitude", latitude);
        obj.put("longitude", longitude);
        obj.put("construction cost", constrution_cost);

        return obj;
    }

    public double getConstrution_cost() {
        return constrution_cost;
    }

    public void setConstrution_cost(double constrution_cost) {
        this.constrution_cost = constrution_cost;
    }
}
