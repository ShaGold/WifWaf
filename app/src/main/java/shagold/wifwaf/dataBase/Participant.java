package shagold.wifwaf.dataBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Participant {
    private int idDog;
    private int idWalk;
    private int idUser;
    private int valid;

    public Participant(int idDog, int idWalk, int idUser, int valid){
        this.idDog = idDog;
        this.idWalk = idWalk;
        this.idUser = idUser;
        this.valid = valid; // valid permet de déterminer la couleur de la participation
    }

    public Participant(JSONObject participantJson) throws JSONException {
        this.idDog = participantJson.getInt("idDog");
        this.idUser = participantJson.getInt("idUser");
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
