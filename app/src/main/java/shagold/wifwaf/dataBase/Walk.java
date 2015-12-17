package shagold.wifwaf.dataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
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
    private ArrayList<Dog> dogs = new ArrayList<Dog>();

    public Walk(int id, int idDog, int idUser, String wN, String description, String city, String dep, ArrayList<Dog> dogs) {
        this.idWalk = id;
        this.idDog = idDog;
        this.idUser = idUser;
        this.walkName = wN;
        this.description = description;
        this.city = city;
        this.departure = dep;
        this.dogs = dogs;
    }

    public Walk(int idDog, int idUser, String wN, String description, String city, String dep, ArrayList<Dog> dogs) {
        this.idDog = idDog;
        this.idUser = idUser;
        this.walkName = wN;
        this.description = description;
        this.city = city;
        this.departure = dep;
        this.dogs = dogs;
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
        JSONArray dogs= (JSONArray) jsonWalk.get("dogs");
        if (dogs != null){
            for (int i = 0; i < dogs.length(); i++) {
                JSONObject currentDog = null;
                try{
                    currentDog = dogs.getJSONObject(i);
                    Dog newDog = new Dog(currentDog);
                    this.dogs.add(newDog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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
        JSONArray mydogs = new JSONArray();
        for (Dog d : dogs){
            mydogs.put(d.toJson());
        }
        walkJson.put("dogs", mydogs);
        System.out.println("Résultat" + walkJson);
        return walkJson;
    }

    public int getIdWalk() {
        return idWalk;
    }

    public int getIdUser() {
        return idUser;
    }

    public ArrayList<Dog> getDogs(){ return this.dogs; }

    public String getTitle() {
        return walkName;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    };

    public void setDogs(ArrayList<Dog> dogs){
        this.dogs = dogs;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ArrayList<Location> getPath() {
        return path;
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
