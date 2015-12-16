package shagold.wifwaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import shagold.wifwaf.dataBase.Walk;

public class PublicWalkProfileActivity extends AppCompatActivity {

    private Walk walk;

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
        System.out.println("Je passe ici");
        Intent resultat = new Intent(PublicWalkProfileActivity.this, PublicUserProfileActivity.class);
        resultat.putExtra("USER", walk.getIdUser());
        startActivity(resultat);
    }

    public void useWalk(View view) {


        Intent resultat = new Intent(PublicWalkProfileActivity.this, AddWalkActivity.class);
        resultat.putExtra("WALK", walk);
        startActivity(resultat);

    }

    public void walkDogs(View view) {

    }
}
