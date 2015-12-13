package shagold.wifwaf.dataBase;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimmy on 22/11/15.
 */
public class Walk implements Serializable {

    private int id;
    private int idCreator;
    private String title;
    private String description;
    private String city;
    private double length;

    public Walk(int id, int idCreator, String title, String description, String city, double length) {
        this.id = id;
        this.idCreator = idCreator;
        this.title = title;
        this.description = description;
        this.city = city;
        this.length = length;
    }

    public Walk(JSONObject json) throws JSONException {
        //TODO check with server
    }

    public int getId() {
        return id;
    }

    public int getIdCreator() {
        return idCreator;
    }

    public boolean isCreator(int idUser) {
        return idCreator == idUser;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public double getLength() {
        return length;
    }
}
