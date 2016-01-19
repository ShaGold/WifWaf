package shagold.wifwaf;

import android.app.ActivityOptions;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import android.content.Intent;
import java.util.List;

import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.adapter.WalkAdapter;
import shagold.wifwaf.manager.SocketManager;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private Socket mSocket;

    //Gestion shake
    private long lasttime = 0;
    private boolean activateShake = true;

    private SensorManager mySensorManager;

    //Valeurs actuelles et précédentes de l'accélération
    private float xAccel;
    private float yAccel;
    private float zAccel;
    private float xPreviousAccel;
    private float yPreviousAccel;
    private float zPreviousAccel;
    private boolean firstUpdate = true;

    //Valeur plus petite -> détection plus rapide du shake
    private final float shakeThreshold = 8f;
    private boolean shakeInitiated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSocket = SocketManager.getMySocket();
        //gestion shake
        mySensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mySensorManager.registerListener(mySensorEventListener, mySensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mSocket.on("RrandomWalk", onRrandomWalk);

        //Gestion push notifs/vibreur
        WifWafPreferences.isLaunched = true;

        mSocket.on("RGetAllWalks", onRGetAllWalks);
        mSocket.emit("getAllWalks");
        initListView();

        activateShake = true;
    }

    @Override
    public void onResume(){
        super.onResume();
        activateShake = true;
    }

    @Override
    public void onPause(){
        super.onPause();
        activateShake = false;
    }

    private final SensorEventListener mySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent se) {
            updateAccelParameters(se.values[0], se.values[1], se.values[2]);
            if ((!shakeInitiated) && isAccelerationChanged()) {
                shakeInitiated = true;
            } else if ((shakeInitiated) && isAccelerationChanged()) {
                //un shake toutes les deux secondes, les autres sont ignorés
                if((lasttime == 0 || lasttime + 2000 < System.currentTimeMillis()) && activateShake) {
                    executeShakeAction();
                    lasttime = System.currentTimeMillis();
                }
            } else if ((shakeInitiated) && (!isAccelerationChanged())) {
                shakeInitiated = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void executeShakeAction() {
        mSocket.emit("randomWalk");
    }

    //détection shake
    private boolean isAccelerationChanged() {
        float deltaX = Math.abs(xPreviousAccel - xAccel);
        float deltaY = Math.abs(yPreviousAccel - yAccel);
        float deltaZ = Math.abs(zPreviousAccel - zAccel);
        return (deltaX > shakeThreshold && deltaY > shakeThreshold)
                || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                || (deltaY > shakeThreshold && deltaZ > shakeThreshold);
    }

    private void updateAccelParameters(float xNewAccel, float yNewAccel,
                                       float zNewAccel) {
        if (firstUpdate) {
            xPreviousAccel = xNewAccel;
            yPreviousAccel = yNewAccel;
            zPreviousAccel = zNewAccel;
            firstUpdate = false;
        } else {
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;
        }
        xAccel = xNewAccel;
        yAccel = yNewAccel;
        zAccel = zNewAccel;
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

    private void initListView() {
        mListView = (ListView) findViewById(R.id.walkListView);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Walk walk = (Walk) mListView.getItemAtPosition(position);
                Intent clickedWalkProfile = new Intent(getApplicationContext(), PublicWalkProfileActivity.class);
                ActivityOptionsCompat options =  ActivityOptionsCompat.makeSceneTransitionAnimation(HomeActivity.this, view, getString(R.string.transition_walk));

                clickedWalkProfile.putExtra("WALK", walk);
                ActivityCompat.startActivity(HomeActivity.this, clickedWalkProfile, options.toBundle());
            }
        });
    }

    private Emitter.Listener onRGetAllWalks = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Walk> walks = Walk.generateWalksFromJSON((JSONArray) args[0]);
                    WalkAdapter adapter = new WalkAdapter(HomeActivity.this, walks);
                    mListView.setAdapter(adapter);
                }

            });
        }

    };

    private Emitter.Listener onRrandomWalk = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //J'ai récupéré ma balade au hasard, je vais donc l'envoyer à la page PublicWalkProfile
                    JSONObject Jsonwalk = (JSONObject) args[0];
                    System.out.println("Je recois " + Jsonwalk);
                    Walk walk = null;
                    try {
                        walk = new Walk(Jsonwalk);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent walkProfile = new Intent(getApplicationContext(), PublicWalkProfileActivity.class);
                    walkProfile.putExtra("WALK", walk);
                    System.out.println("id walk" + walk.getIdWalk());
                    startActivity(walkProfile);
                }
            });
        }

    };

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MainActivity", "destroy");
        WifWafPreferences.isLaunched = false;
    }

}
