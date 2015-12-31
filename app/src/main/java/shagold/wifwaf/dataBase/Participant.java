package shagold.wifwaf.dataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Participant {
    private Dog dog;
    private int idWalk;
    private User user;
    private int valid;

    public String getDogName() {
        return dog.getName();
    }

    public int getIdWalk() {
        return idWalk;
    }

    public int getValid() {
        return valid;
    }

    public Participant(Dog idDog, int idWalk, User idUser, int valid){
        this.dog = idDog;
        this.idWalk = idWalk;
        this.user = idUser;
        this.valid = valid; // valid permet de déterminer la couleur de la participation
    }

    public Participant(JSONObject participantJson) throws JSONException {
        JSONObject objDog = participantJson.getJSONObject("dog");
        this.dog = new Dog(objDog);
        JSONObject objUser = participantJson.getJSONObject("user");
        this.user = new User(objUser);
        this.idWalk = participantJson.getInt("idWalk");
        this.valid = participantJson.getInt("valid");
    }

    public static List<Participant> generateParticipantsFromJson(JSONArray participantsJson) {
        List<Participant> participants = new ArrayList<Participant>();
        if(participantsJson != null) {
            for (int i = 0; i < participantsJson.length(); i++) {
                JSONObject currentP = null;
                try {
                    currentP = participantsJson.getJSONObject(i);
                    Participant newParticipant = new Participant(currentP);
                    participants.add(newParticipant);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return participants;
    }

}
