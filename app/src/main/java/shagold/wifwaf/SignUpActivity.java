package shagold.wifwaf;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafDatePickerFragment;

public class SignUpActivity extends AppCompatActivity {

    private Socket mSocket;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSocket = SocketManager.getMySocket();
        mSocket.on("onTestString", onTestString);
        mSocket.on("onTestJson", onTestJson);
        mSocket.on("onTestJsonArray", onTestJsonArray);
        mSocket.on("RTrySignUp", onRTrySignUp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.emptyMenu(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO faire socket.off pour chaque event écouté
        mSocket.off("RTrySignUp", onRTrySignUp);
    }

    public void showDatePickerDialog(View v) {
        WifWafDatePickerFragment newFragment = new WifWafDatePickerFragment();
        EditText ETBirthday = (EditText) findViewById(R.id.Birthday);
        newFragment.setDateText(ETBirthday);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void trySignUp(View view) throws JSONException {
        // Récupération des valeurs
        EditText ETnickname = (EditText) findViewById(R.id.Nickname);
        String Snickname = ETnickname.getText().toString();
        EditText ETpassword = (EditText) findViewById(R.id.Password);
        String Spassword = ETpassword.getText().toString();
        EditText ETemail = (EditText) findViewById(R.id.Email);
        String Semail = ETemail.getText().toString();
        EditText ETPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        //Vérification que le numéro de téléphone est bien un int
        int SphoneNumber;
        try{
            SphoneNumber = Integer.parseInt(ETPhoneNumber.getText().toString());
        }
        catch(Exception e){
            System.out.println("Num tel:" + ETPhoneNumber.getText().toString());
            System.out.print("Ici" + e.getMessage());
            Toast.makeText(SignUpActivity.this, "Le numéro de téléphone doit être un nombre", Toast.LENGTH_LONG).show();
            return;
        }
        EditText ETBirthday = (EditText) findViewById(R.id.Birthday);
        ETBirthday.setFocusable(false);
        String Sbirthday = ETBirthday.getText().toString();
        EditText ETDescription = (EditText) findViewById(R.id.Description);
        String Sdescription = ETDescription.getText().toString();

        // Vérification champs
        if (Spassword.length() < 6){
            Toast.makeText(SignUpActivity.this, "Mot de passe trop court", Toast.LENGTH_LONG).show();
            return;
        }
        if (Snickname.length() < 3){
            Toast.makeText(SignUpActivity.this, "Pseudo trop court", Toast.LENGTH_LONG).show();
            return;
        }
        if (ETPhoneNumber.getText().toString().length() < 10){
            Toast.makeText(SignUpActivity.this, "Numéro de téléphone trop court", Toast.LENGTH_LONG).show();
            return;
        }

        //Encryptage mdp
        Spassword = User.encryptPassword(Spassword);

        //Test inscription
        User user = new User(Semail,Snickname,Spassword,Sbirthday,SphoneNumber,Sdescription,"");
        JSONObject jsonUser = user.toJson();
        mSocket.emit("TrySignUp", jsonUser);
    }

    private Emitter.Listener onTestString = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String a = (String) args[0];
                    System.out.println("je recois onTest" + a);
                    mSocket.emit("onTestSendString", "unMot");
                }
            });
        }
    };

    private Emitter.Listener onTestJson = new Emitter.Listener() {
        @Override
        public void call(final Object... args)  {
            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject a =  (JSONObject) args[0];
                    System.out.println("je recois l'objet" + a.toString());
                    //préparer l'objet et faire l'envoi ici
                    JSONObject JsonTest = new JSONObject();
                    try {
                        JsonTest.put("email", "adr@mail.fr");
                        JsonTest.put("name", "thisismyname");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("JsonObject préparé: " + JsonTest.toString());
                    mSocket.emit("onTestSendJson", JsonTest);
                }
            });
        }
    };

    private Emitter.Listener onTestJsonArray = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray a = (JSONArray) args[0];
                    System.out.println("je recois l'objet" + a.toString());
                    //préparer l'objet pour l'envoi*
                    JSONArray JsonArrayTest = null;
                    try {
                        JsonArrayTest = new JSONArray()
                                .put(new JSONObject()
                                        .put("prenom", "jimmy")
                                        .put("nom", "lopez"))
                                .put(new JSONObject()
                                        .put("prenom", "marlene")
                                        .put("nom", "gui"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.print("JsonArray préparé: " + JsonArrayTest.toString());
                    mSocket.emit("onTestSendJsonArray", JsonArrayTest);
                }
            });
        }
    };

    private Emitter.Listener onRTrySignUp = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject param = (JSONObject) args[0];
                    try {
                        if((int)param.get("id") < 0) { //TODO faire un traitement spécifique pour chaque erreur?
                           AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                            alertDialog.setTitle("Erreur");
                            alertDialog.setMessage("Cette adresse mail est déjà utilisée"); //TODO internationalisation
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        else{
                            User mUser = new User(param);
                            Intent resultat = new Intent(SignUpActivity.this, HomeActivity.class);
                            System.out.println("[Réussite inscription]"+param);
                            SocketManager.setMyUser(mUser);

                            startActivity(resultat);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
