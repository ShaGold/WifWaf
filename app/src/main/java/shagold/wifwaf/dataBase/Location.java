package shagold.wifwaf.dataBase;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
    private int idLocation;
    private double lattitude;
    private double longitude;
    private int ordering;

    public Location(int idLocation, double latt, double longitude, int o){
        this.idLocation = idLocation;
        this.lattitude = latt;
        this.longitude = longitude;
        this.ordering = o;
    }

    public Location(double latt, double longitude, int o){
        this.lattitude = latt;
        this.longitude = longitude;
        this.ordering = o;
    }

    public Location(JSONObject jsonLocation) throws JSONException {
        this.idLocation = (int) jsonLocation.get("idLocation");
        this.lattitude = (double) jsonLocation.get("lattitude");
        this.longitude = (double) jsonLocation.get("longitude");
        this.ordering = (int) jsonLocation.get("ordering");
    }

    public JSONObject toJson() throws JSONException {
        JSONObject locationJson = new JSONObject();
        locationJson.put("latitude", this.lattitude);
        locationJson.put("longitude", this.longitude);
        locationJson.put("ordering", this.ordering);
        return locationJson;
    }

}
