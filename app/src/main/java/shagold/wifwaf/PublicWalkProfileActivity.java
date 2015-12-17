package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.list.DogPublicAdapter;

public class PublicWalkProfileActivity extends AppCompatActivity {

    private Walk walk;
    private DogPublicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_walk_profile);

        walk = (Walk) getIntent().getSerializableExtra("WALK");

        TextView titleWalk = (TextView) findViewById(R.id.walkPublicTitle);
        titleWalk.setText(walk.getTitle());

        // TODO défault
        ImageView creatorWalk = (ImageView) findViewById(R.id.avatarCreatorPublicWalk);
        creatorWalk.setImageResource(R.drawable.user);

        TextView cityWalk = (TextView) findViewById(R.id.walkPublicCity);
        cityWalk.setText(walk.getCity());

        TextView descriptionWalk = (TextView) findViewById(R.id.walkPublicDescription);
        descriptionWalk.setText(walk.getDescription());

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

        List<Dog> dogs = new ArrayList<Dog>(walk.getDogs());
        adapter = new DogPublicAdapter(PublicWalkProfileActivity.this, dogs);

        ListView modeList = new ListView(PublicWalkProfileActivity.this);
        modeList.setAdapter(adapter);

        userDogsDialog.setView(modeList);

        AlertDialog alertDogs = userDogsDialog.create();
        alertDogs.show();

    }
}
