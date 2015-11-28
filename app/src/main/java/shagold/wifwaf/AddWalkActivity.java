package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.tool.WifWafColor;

/**
 * Created by jimmy on 22/11/15.
 */
public class AddWalkActivity extends WifWafActivity {

    private Button selectDogsForWalk;
    private Button confirmNewWalk;
    private AlertDialog alertSelectDogs;
    private Spinner dogSpinner;
    private int dogsSelectedNumber = 0;
    private List<Dog> dogChoise = new ArrayList<Dog>();
    private List<Dog> userDogs = new ArrayList<Dog>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_walk);
        initBackground();
        initToolBar(R.id.toolbarAddWalk);

        dogSpinner = (Spinner) findViewById(R.id.spinner1);

        selectDogsForWalk = (Button) findViewById(R.id.selectDogsForWalk);
        selectDogsForWalk.setBackgroundColor(WifWafColor.BROWN_DARK);
        selectDogsForWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDogs = generateDogs();
                List<String> dogsName = new ArrayList<String>();

                for (Dog dog : userDogs)
                    dogsName.add(dog.getName());

                final CharSequence[] dogsList = dogsName.toArray(new CharSequence[dogsName.size()]);

                AlertDialog.Builder multyChoiceDialog = new AlertDialog.Builder(getSelf());

                multyChoiceDialog.setTitle("Choice Dogs");

                boolean[] _selections = new boolean[dogsList.length];

                multyChoiceDialog.setMultiChoiceItems(dogsList, _selections, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                    }
                });

                multyChoiceDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView list = ((AlertDialog) dialog).getListView();
                        List<String> dogsSelected = new ArrayList<String>();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);
                            if (checked)
                                dogsSelected.add(list.getItemAtPosition(i).toString());
                        }
                        updateItemSpinner(dogsSelected);
                    }
                });

                multyChoiceDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alertSelectDogs = multyChoiceDialog.create();
                alertSelectDogs.show();
            }
        });

        confirmNewWalk = (Button) findViewById(R.id.addWalkButton);
        confirmNewWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                Log.d("Ts", ts);

                for(Dog d : dogChoise)
                    Log.d("dog", d.getName());

                if(dogsSelectedNumber > 0)
                    startActivity(ActivityManager.getGPSWalk(getSelf()));

            }
        });

    }

    private void initAlertDialog() {

    }

    private List<Dog> generateDogs() {
        List<Dog> dogs = new ArrayList<Dog>();

        dogs.add(new Dog(1, "Gold1", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(2, "Shana2", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(3, "Gold3", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(4, "Shana4", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(5, "Gold5", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(6, "Shana6", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(7, "Gold7", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(8, "Shana8", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));

        return dogs;
    }

    private void updateItemSpinner(List<String> list) {

        if(list.size() == 0) {
            list.add("No dogs");
            setDogNumber(0);
        }
        else {
            setDogNumber(list.size());
            dogChoise.clear();
            for(Dog dog : userDogs) {
                if(list.contains(dog.getName())) {
                    dogChoise.add(dog);
                }
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogSpinner.setAdapter(dataAdapter);
    }

    private void setDogNumber(int n) {
        dogsSelectedNumber = n;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.emptyMenu(this, item.getItemId()) || super.onOptionsItemSelected(item);
    }
}
