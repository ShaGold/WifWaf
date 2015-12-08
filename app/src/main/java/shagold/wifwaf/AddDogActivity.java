package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;

public class AddDogActivity extends AppCompatActivity {

    private Socket mSocket;
    private User mUser;
    private Button confirmAddDog;
    private LinearLayout actlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Gestion socket
        mSocket = SocketManager.getMySocket();
        mUser = SocketManager.getMyUser();
        mSocket.on("RGetAllBehaviours", onRGetAllBehaviours);
        mSocket.emit("getAllBehaviours");

        // Gestion vue + gestion activity's state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        // Gestion click bouton de confirmation
        this.confirmAddDog = (Button) findViewById(R.id.confirmAddDogButton);
        this.confirmAddDog.setBackgroundColor(WifWafColor.BROWN_DARK);
        /*final Intent getUserDog = new Intent(getApplicationContext(), UserDogsActivity.class);
        confirmAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getUserDog);
            }
        });*/
    }

    public void tryAddDog(View view) throws JSONException {
        System.out.println("Je passe ici");
        //Récupération des valeurs
        EditText ETname = (EditText) findViewById(R.id.name);
        String Sname = ETname.getText().toString();
        EditText ETage = (EditText) findViewById(R.id.age);
        int age = Integer.parseInt(ETage.getText().toString());
        EditText ETbreed = (EditText) findViewById(R.id.breed);
        String Sbreed = ETbreed.getText().toString();
        EditText ETsize = (EditText) findViewById(R.id.size);
        int size = Integer.parseInt(ETsize.getText().toString());
        EditText ETGetalongwithMales = (EditText) findViewById(R.id.getAlongWithMales);
        String Sgetalongwithmales = ETGetalongwithMales.getText().toString();
        EditText ETGetalongwithFemales = (EditText) findViewById(R.id.getAlongWithFemales);
        String Sgetalongwithfemales = ETGetalongwithFemales.getText().toString();
        EditText ETGetalongwithKids = (EditText) findViewById(R.id.getAlongWithKids);
        String Sgetalongwithkids = ETGetalongwithKids.getText().toString();
        EditText ETGetalongwithHumans = (EditText) findViewById(R.id.getAlongWithHumans);
        String Sgetalongwithhumans = ETGetalongwithHumans.getText().toString();
        EditText ETDescription = (EditText) findViewById(R.id.description);
        String Sdescription = ETDescription.getText().toString();

        //Vérification champs
        if (Sname.length() < 2){
            Toast.makeText(AddDogActivity.this, "Le nom du chien est trop court", Toast.LENGTH_LONG).show();
        }

        //Test ajout d'un chien
        Dog dog = new Dog(mUser.getIdUser(), Sname, age, Sbreed, size, Sgetalongwithmales, Sgetalongwithfemales, Sgetalongwithkids, Sgetalongwithhumans, Sdescription);
        JSONObject jsonDog = dog.toJson();
        System.out.println("TryAddDog" + jsonDog);
        mSocket.emit("TryAddDog", jsonDog);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.defaultMenu(this, item) || super.onOptionsItemSelected(item);
    }

    private Emitter.Listener onRGetAllBehaviours = new Emitter.Listener() {
        @Override
        public void call(final Object... args)  {
            AddDogActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray listBehaviour =  (JSONArray) args[0];
                    System.out.println("je recois l'objet" + listBehaviour.toString());
                    for (int i = 0; i < listBehaviour.length(); i++) {
                        JSONObject currentObj = null;
                        try {
                            currentObj = listBehaviour.getJSONObject(i);
                            int idBehaviour = currentObj.getInt("idBehaviour");
                            String description = currentObj.getString("description");
                            CheckBox cb = new CheckBox(getApplicationContext());
                            cb.setText(description);
                            cb.setId(idBehaviour);
                            //TODO gestion correcte des couleurs via les ressources
                            cb.setTextColor(0xAA000000);
                            actlayout = (LinearLayout) findViewById(R.id.layout);
                            actlayout.addView(cb, 9);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    };
}
