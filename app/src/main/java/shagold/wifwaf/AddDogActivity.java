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

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.LineNumberReader;

import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;

public class AddDogActivity extends AppCompatActivity {

    private Socket mSocket;
    private Button confirmAddDog;
    private LinearLayout actlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSocket = SocketManager.getMySocket();
        mSocket.on("RGetAllBehaviours", onRGetAllBehaviours);
        mSocket.emit("getAllBehaviours");

        System.out.println("je passe là");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        this.confirmAddDog = (Button) findViewById(R.id.confirmAddDogButton);
        this.confirmAddDog.setBackgroundColor(WifWafColor.BROWN_DARK);
        final Intent getUserDog = new Intent(getApplicationContext(), UserDogsActivity.class);
        confirmAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getUserDog);
            }
        });

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
