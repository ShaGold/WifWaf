package shagold.wifwaf.dataBase;

import org.json.JSONException;
import org.json.JSONObject;

public class Dog {
    private int idDog;
    private String dogName;
    private int age;
    private String breed;
    private int size;
    private String getAlongWithMales;
    private String getAlongWithFemales;
    private String getAlongWithKids;
    private String getAlongWithHumans;
    private String description;

    public Dog(){}

    public Dog(int idDog, String dogName, int age, String breed, int size, String getAlongWithMales, String getAlongWithFemales, String getAlongWithKids, String getAlongWithHumans, String description){
        this.idDog = idDog;
        this.dogName = dogName;
        this.age = age;
        this.breed = breed;
        this.size = size;
        this.getAlongWithMales = getAlongWithMales;
        this.getAlongWithFemales = getAlongWithFemales;
        this.getAlongWithKids = getAlongWithKids;
        this.getAlongWithHumans = getAlongWithHumans;
        this.description = description;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject dogJson = new JSONObject();
        dogJson.put("dogName", this.dogName);
        dogJson.put("age", this.age);
        dogJson.put("breed", this.breed);
        dogJson.put("size", this.size);
        dogJson.put("getAlongWithMales", this.getAlongWithMales);
        dogJson.put("getAlongWithFemales", this.getAlongWithFemales);
        dogJson.put("getAlongWithKids", this.getAlongWithKids);
        dogJson.put("getAlongWithHumans", this.getAlongWithHumans);
        dogJson.put("description", this.description);
        return dogJson;
    }

}