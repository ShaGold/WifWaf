package shagold.wifwaf.dataBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Walk implements Serializable {

    private int idWalk;
    private int idDog;
    private int idUser;
    private String walkName;
    private String description;
    private String city;
    private String departure; //TODO Timestamp?
    private ArrayList<Location> parcours = new ArrayList<Location>();

    public Walk(int id, int idDog, int idUser, String wN, String description, String city, String desc) {
        this.idWalk = id;
        this.idDog = idDog;
        this.idUser = idUser;
        this.walkName = wN;
        this.description = description;
        this.city = city;
        this.departure = desc;
    }

    public void addLocationToWalk(double latitude, double longitude){
        int order = parcours.size() + 1;
        Location newLoc = new Location(latitude, longitude, order);
        parcours.add(newLoc);
    }

    public Walk(JSONObject jsonWalk) throws JSONException {
        //TODO check with server
    }

    public int getIdWalk() {
        return idWalk;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getTitle() {
        return walkName;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    };
}
