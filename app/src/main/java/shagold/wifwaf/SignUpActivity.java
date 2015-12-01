package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class SignUpActivity extends AppCompatActivity {

    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mSocket = SocketManager.getMySocket();
        mSocket.on("onTestString", onTestString);
        mSocket.on("onTestJson", onTestJson);
        mSocket.on("onTestJsonArray", onTestJsonArray);
        mSocket.on("RTrySignUp", onRTrySignUp);
        mSocket.on("RGetAllMyDogs", onRGetAllMyDogs);
        mSocket.emit("getAllMyDogs", 1);

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
        //socket.off("RTrySignUp", onRTrySignUp);
    }

    public void trySignUp(View view) throws JSONException {
        System.out.print("test");

        //Récupération des valeurs
        EditText ETnickname = (EditText) findViewById(R.id.Nickname);
        String Snickname = ETnickname.getText().toString();
        EditText ETpassword = (EditText) findViewById(R.id.Password);
        String Spassword = ETpassword.getText().toString();
        EditText ETemail = (EditText) findViewById(R.id.Email);
        String Semail = ETemail.getText().toString();
        EditText ETPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        int SphoneNumber = Integer.parseInt(ETPhoneNumber.getText().toString());
        EditText ETBirthday = (EditText) findViewById(R.id.Birthday);
        String Sbirthday = ETBirthday.getText().toString();
        EditText ETDescription = (EditText) findViewById(R.id.Description);
        String Sdescription = ETDescription.getText().toString();

        //Vérification champs
        if (Spassword.length() < 6){
            Toast.makeText(SignUpActivity.this, "Mot de passe trop court", Toast.LENGTH_LONG).show();
        }
        if (Snickname.length() < 3){
            Toast.makeText(SignUpActivity.this, "Pseudo trop court", Toast.LENGTH_LONG).show();
        }
        if (ETPhoneNumber.getText().toString().length() < 10){
            Toast.makeText(SignUpActivity.this, "Numéro de téléphone trop court", Toast.LENGTH_LONG).show();
        }

        //Test inscription
        User user = new User(Semail,Snickname,Spassword,Sbirthday,SphoneNumber,Sdescription,"");
        JSONObject jsonUser = user.toJson();
        System.out.println(jsonUser);
        mSocket.emit("TrySignUp", jsonUser);
    }

    //Test RéceptiononGetAllMyDogs
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

    private Emitter.Listener onRGetAllMyDogs = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    System.out.println(args[0]);

                    //Log.d("LOG", args[0].toString());

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
                    Integer err = (Integer) args[0];
                    if (err != 0) {
                       AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).create();
                        alertDialog.setTitle("Erreur");
                        alertDialog.setMessage("Erreur " + err );
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                    else{
                        Intent resultat = new Intent(SignUpActivity.this, ResultSignUpActivity.class);
                        System.out.println("[Réussite inscription]");
                       // resultat.putExtra("Nickname", nickname); // pour pouvoir afficher bienvenue ..
                        startActivity(resultat);
                    }
                }
            });
        }
    };
}
