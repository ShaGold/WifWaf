package shagold.wifwaf.dataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Walk implements Serializable {

    private int idWalk;
    private int idDog;
    private int idUser;
    private String walkName;
    private String description;
    private String city;
    private String departure; //TODO Timestamp?
    private ArrayList<Location> path = new ArrayList<Location>();

    public Walk(int id, int idDog, int idUser, String wN, String description, String city, String dep) {
        this.idWalk = id;
        this.idDog = idDog;
        this.idUser = idUser;
        this.walkName = wN;
        this.description = description;
        this.city = city;
        this.departure = dep;
    }

    public Walk(int idDog, int idUser, String wN, String description, String city, String dep) {
        this.idDog = idDog;
        this.idUser = idUser;
        this.walkName = wN;
        this.description = description;
        this.city = city;
        this.departure = dep;
    }

    public Walk(JSONObject jsonWalk) throws JSONException{
        this.idWalk = (int) jsonWalk.get("idWalk");
        this.idDog = (int) jsonWalk.get("idDog");
        this.idUser = (int) jsonWalk.get("idUser");
        this.walkName = (String) jsonWalk.get("walkName");
        this.description = (String) jsonWalk.get("description");
        this.city = (String) jsonWalk.get("city");
        this.departure = (String) jsonWalk.get("departure");
        JSONArray trace= (JSONArray) jsonWalk.get("path");
        if (trace != null){
            for (int i = 0; i < trace.length(); i++) {
                JSONObject currentLoc = null;
                try{
                    currentLoc = trace.getJSONObject(i);
                    Location newLoc = new Location(currentLoc);
                    this.path.add(newLoc);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        /*if(dogsJSON != null) {
            for (int i = 0; i < dogsJSON.length(); i++) {
                JSONObject currentObj = null;
                try {
                    currentObj = dogsJSON.getJSONObject(i);
                    Dog newDog = new Dog(currentObj);
                    dogs.add(newDog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }


    public void addLocationToWalk(double latitude, double longitude){
        int order = path.size() + 1;
        Location newLoc = new Location(latitude, longitude, order);
        path.add(newLoc);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject walkJson = new JSONObject();
        walkJson.put("idDog", this.idDog);
        walkJson.put("idUser", this.idUser);
        walkJson.put("walkName", this.walkName);
        walkJson.put("description", this.description);
        walkJson.put("city", this.city);
        walkJson.put("departure", this.departure);
        JSONArray mylocations = new JSONArray();
        for (Location l : path){
            mylocations.put(l.toJson());
        }
        walkJson.put("location", mylocations);
        System.out.println("Résultat" + walkJson);
        return walkJson;
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

    public void setCity(String city) {
        this.city = city;
    }

    public static List<Walk> generateWalksFromJSON(JSONArray json) {

        List<Walk> walks = new ArrayList<Walk>();
        System.out.println("Mes balades" + json);

        if(json != null) {
            for (int i = 0; i < json.length(); i++) {
                JSONObject currentObj = null;
                try {
                    currentObj = json.getJSONObject(i);
                    Walk newWalk = new Walk(currentObj);
                    walks.add(newWalk);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return walks;
    }

}
