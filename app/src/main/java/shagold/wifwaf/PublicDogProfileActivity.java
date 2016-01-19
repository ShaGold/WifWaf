package shagold.wifwaf;

import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import shagold.wifwaf.dataBase.Behaviour;
import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;

public class PublicDogProfileActivity extends AppCompatActivity {

    private Dog dog;

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
        setContentView(R.layout.activity_public_dog_profile);

        //socket
        mSocket = SocketManager.getMySocket();

        //gestion shake
        mySensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mySensorManager.registerListener(mySensorEventListener, mySensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mSocket.on("RrandomDog", onRrandomDog);

        dog = (Dog) getIntent().getSerializableExtra("DOG");

        initFields();
        style();

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
        System.out.println("Shake");
        mSocket.emit("randomDog");
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

    public void initFields(){
        TextView nameDog = (TextView) findViewById(R.id.namePublicDog);
        nameDog.setText(dog.getName());

        TextView ageDog = (TextView) findViewById(R.id.dogPublicProfileAge);
        String age = String.valueOf(Double.parseDouble(dog.getAge()));
        ageDog.setText(age);

        TextView sizeDog = (TextView) findViewById(R.id.dogPublicProfileSize);
        sizeDog.setText(Integer.toString(dog.getSize()));

        TextView breedDog = (TextView) findViewById(R.id.dogPublicProfileBreed);
        breedDog.setText(dog.getBreed());

        TextView description = (TextView) findViewById(R.id.dogPublicProfileDescription);
        description.setText(dog.getDescription());

        ImageView avatar = (ImageView) findViewById(R.id.avatarPublicDog);
        avatar.setImageBitmap(dog.getPhotoBitmap());

        TextView gender = (TextView) findViewById(R.id.genderDogPublicProfile);
        gender.setText(dog.getSex());

        TextView GetAlongWithMales = (TextView) findViewById(R.id.dogPublicProfileGetAlongWithMales);
        GetAlongWithMales.setText(dog.getGetAlongWithMales());

        TextView GetAlongWithFemales = (TextView) findViewById(R.id.dogPublicProfileGetAlongWithFemales);
        GetAlongWithFemales.setText(dog.getGetAlongWithFemales());

        TextView GetAlongWithKids = (TextView) findViewById(R.id.dogPublicProfileGetAlongWithKids);
        GetAlongWithKids.setText(dog.getGetAlongWithKids());

        TextView GetAlongWithHumans = (TextView) findViewById(R.id.dogPublicProfileGetAlongWithHumans);
        GetAlongWithHumans.setText(dog.getGetAlongWithHumans());

        TextView Behaviours = (TextView) findViewById(R.id.behaviour);
        String listB = "";
        for (Behaviour b : dog.getBehaviours()) {
            listB += " " + b.getDescription();
        }
        Behaviours.setText(listB);
    }

    public void style(){
        //Récupération des textView
        TextView tvName = (TextView) findViewById(R.id.namePublicDogProfile_tv);
        TextView tvAge = (TextView) findViewById(R.id.dogPublicProfileAge_tv);
        TextView tvDesc = (TextView) findViewById(R.id.dogPublicProfileDesc_tv);
        TextView tvGender = (TextView) findViewById(R.id.dogPublicProfileGender_tv);
        TextView tvBreed = (TextView) findViewById(R.id.dogPublicProfileBreed_tv);
        TextView tvSize = (TextView) findViewById(R.id.dogPublicProfileSize_tv);
        TextView tvGAWM = (TextView) findViewById(R.id.dogPublicProfileGAWM_tv);
        TextView tvGAWF = (TextView) findViewById(R.id.dogPublicProfileGAWF_tv);
        TextView tvGAWK = (TextView) findViewById(R.id.dogPublicProfileGAWK_tv);
        TextView tvGAWH = (TextView) findViewById(R.id.dogPublicProfileGAWH_tv);
        TextView tvBehaviour = (TextView) findViewById(R.id.dogPublicProfileBehaviour_tv);

        //Récupération police
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/coolvetica rg.ttf");

        //On ajoute le style à tous les textview
        tvName.setTypeface(tf);
        tvName.setTextSize(24.0f);
        tvName.setTextColor(WifWafColor.BROWN);

        tvAge.setTypeface(tf);
        tvAge.setTextSize(24.0f);
        tvAge.setTextColor(WifWafColor.BROWN);

        tvBehaviour.setTypeface(tf);
        tvBehaviour.setTextSize(24.0f);
        tvBehaviour.setTextColor(WifWafColor.BROWN);

        tvBreed.setTypeface(tf);
        tvBreed.setTextSize(20.0f);
        tvBreed.setTextColor(WifWafColor.BROWN);

        tvDesc.setTypeface(tf);
        tvDesc.setTextSize(20.0f);
        tvDesc.setTextColor(WifWafColor.BROWN);

        tvGAWF.setTypeface(tf);
        tvGAWF.setTextSize(20.0f);
        tvGAWF.setTextColor(WifWafColor.BROWN);

        tvGAWM.setTypeface(tf);
        tvGAWM.setTextSize(20.0f);
        tvGAWM.setTextColor(WifWafColor.BROWN);

        tvGAWK.setTypeface(tf);
        tvGAWK.setTextSize(20.0f);
        tvGAWK.setTextColor(WifWafColor.BROWN);

        tvGAWH.setTypeface(tf);
        tvGAWH.setTextSize(20.0f);
        tvGAWH.setTextColor(WifWafColor.BROWN);

        tvSize.setTypeface(tf);
        tvSize.setTextSize(20.0f);
        tvSize.setTextColor(WifWafColor.BROWN);

        tvGender.setTypeface(tf);
        tvGender.setTextSize(20.0f);
        tvGender.setTextColor(WifWafColor.BROWN);
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

    private Emitter.Listener onRrandomDog = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            PublicDogProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //J'ai récupéré mon chien au hasard, je vais donc l'envoyer à la page PublicDogProfile
                    JSONArray jsonArrayDog = (JSONArray) args[0];
                    JSONObject jsonDog = null;
                    try {
                        jsonDog = jsonArrayDog.getJSONObject(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Je recois " + jsonDog);
                    Dog dog = null;
                    try {
                        dog = new Dog(jsonDog);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent dogProfile = new Intent(getApplicationContext(), PublicDogProfileActivity.class);
                    dogProfile.putExtra("DOG", dog);
                    startActivity(dogProfile);
                }
            });
        }

    };
}
