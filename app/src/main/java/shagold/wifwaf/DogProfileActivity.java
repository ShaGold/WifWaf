package shagold.wifwaf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.manager.MenuManager;

public class DogProfileActivity extends AppCompatActivity {

    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_profile);

        dog = (Dog) getIntent().getSerializableExtra("DOG");

        EditText dogName = (EditText) findViewById(R.id.dogProfileName);
        dogName.setText(dog.getName());

        // TODO default image

        ImageView dogProfile = (ImageView) findViewById(R.id.avatarDog);
        dogProfile.setImageResource(R.drawable.dogi2);

        EditText dogDescription = (EditText) findViewById(R.id.dogProfileDescription);
        dogDescription.setText(dog.getDescription());

        EditText dogAge = (EditText) findViewById(R.id.dogProfileAge);
        dogAge.setText(Integer.toString(dog.getAge()));

        EditText dogBreed = (EditText) findViewById(R.id.dogProfilebreed);
        dogBreed.setText(dog.getBreed());

        EditText dogSize = (EditText) findViewById(R.id.dogProfileSize);
        dogSize.setText(Integer.toString(dog.getSize()));

        EditText dogGAWM = (EditText) findViewById(R.id.dogProfilegetAlongWithMales);
        dogGAWM.setText(dog.getGetAlongWithMales());

        EditText dogGAWF = (EditText) findViewById(R.id.dogProfilegetAlongWithFemales);
        dogGAWF.setText(dog.getGetAlongWithFemales());

        EditText dogGAWH = (EditText) findViewById(R.id.dogProfilegetAlongWithHumans);
        dogGAWH.setText(dog.getGetAlongWithHumans());

        EditText dogGAWK = (EditText) findViewById(R.id.dogProfilegetAlongWithKids);
        dogGAWK.setText(dog.getGetAlongWithKids());
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

    public void saveChange(View view){

    }
}
