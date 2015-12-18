package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.list.DogPublicAdapter;
import shagold.wifwaf.manager.SocketManager;

public class PublicWalkProfileActivity extends AppCompatActivity {

    private Walk walk;
    private ArrayList<Dog> dogWalk = new ArrayList<Dog>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_walk_profile);

        walk = (Walk) getIntent().getSerializableExtra("WALK");

        Socket mSocket = SocketManager.getMySocket();

        for(Dog d : walk.getDogs()) {
            mSocket = SocketManager.getMySocket();
            System.out.println("ID-DOG- " + d.getIdDog());
            mSocket.emit("getDogById", d.getIdDog());
        }

        mSocket.on("RGetDogById", onRGetDogById);

        TextView titleWalk = (TextView) findViewById(R.id.walkPublicTitle);
        titleWalk.setText(walk.getTitle());

        // TODO défault
        ImageView creatorWalk = (ImageView) findViewById(R.id.avatarCreatorPublicWalk);
        creatorWalk.setImageResource(R.drawable.user);

        TextView cityWalk = (TextView) findViewById(R.id.walkPublicCity);
        cityWalk.setText(walk.getCity());

        TextView descriptionWalk = (TextView) findViewById(R.id.walkPublicDescription);
        descriptionWalk.setText(walk.getDescription());

        TextView departureWalk = (TextView) findViewById(R.id.walkPublicDeparture);
        departureWalk.setText(walk.getDeparture());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void redirectUserProfile(View view){
        Intent resultat = new Intent(PublicWalkProfileActivity.this, PublicUserProfileActivity.class);
        resultat.putExtra("USER", walk.getIdUser());
        startActivity(resultat);
    }

    public void viewPath(View view) {
        Intent resultat = new Intent(PublicWalkProfileActivity.this, PublicPathProfileActivity.class);
        resultat.putExtra("WALK", walk);
        startActivity(resultat);
    }

    public void useWalk(View view) {

        Intent resultat = new Intent(PublicWalkProfileActivity.this, UseWalkActivity.class);
        resultat.putExtra("WALK", walk);
        startActivity(resultat);
    }

    public void walkDogs(View view) {

        AlertDialog.Builder userDogsDialog = new AlertDialog.Builder(PublicWalkProfileActivity.this);

        userDogsDialog.setTitle("Walk Dogs");

        List<Dog> dogs = new ArrayList<Dog>(dogWalk);
        DogPublicAdapter adapter = new DogPublicAdapter(PublicWalkProfileActivity.this, dogs);

        final ListView modeList = new ListView(PublicWalkProfileActivity.this);
        modeList.setAdapter(adapter);

        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Dog dog = (Dog) modeList.getItemAtPosition(position);
                Intent clickedWalkProfile = new Intent(getApplicationContext(), PublicDogProfileActivity.class);
                clickedWalkProfile.putExtra("DOG", dog);
                startActivity(clickedWalkProfile);
            }
        });

        userDogsDialog.setView(modeList);

        AlertDialog alertDogs = userDogsDialog.create();
        alertDogs.show();

    }

    private Emitter.Listener onRGetDogById = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            PublicWalkProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray param = (JSONArray) args[0];
                        Dog dog = new Dog(param.getJSONObject(0));
                        dogWalk.add(dog);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    };
}
