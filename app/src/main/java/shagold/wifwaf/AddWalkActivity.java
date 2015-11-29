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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.tool.WifWafColor;
import shagold.wifwaf.view.ErrorMessage;
import shagold.wifwaf.view.TextValidator;
import shagold.wifwaf.view.ValidateMessage;
import shagold.wifwaf.view.filter.text.BlankFiler;
import shagold.wifwaf.view.filter.text.EditTextFilter;
import shagold.wifwaf.view.filter.text.NumberFilter;

/**
 * Created by jimmy on 22/11/15.
 */
public class AddWalkActivity extends WifWafActivity {

    private AlertDialog alertSelectDogs;
    private Spinner dogSpinner;
    private int dogsSelectedNumber = 0;
    private List<Dog> dogChoise = new ArrayList<Dog>();
    private List<Dog> userDogs = new ArrayList<Dog>();
    private EditText nameWalk;
    private EditText descriptionWalk;
    private TextValidator textValidator = new TextValidator();
    private EditTextFilter[] filters = {new BlankFiler(), new NumberFilter()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_walk);
        initBackground();
        initToolBar(R.id.toolbarAddWalk);

        initEditText();
        initAlertDialog();
        initConfirmButton();
    }

    private void initEditText() {
        nameWalk = (EditText) findViewById(R.id.nameWalk);
        descriptionWalk = (EditText) findViewById(R.id.descriptionWalk);

    }

    private void initConfirmButton(){
        Button confirmNewWalk = (Button) findViewById(R.id.addWalkButton);
        confirmNewWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long tsLong = System.currentTimeMillis() / 1000;
                String ts = tsLong.toString();
                Log.d("Ts", ts);

                for (Dog d : dogChoise)
                    Log.d("dog", d.getName());

                boolean validText = true;

                ValidateMessage vm = textValidator.validate(nameWalk, filters);
                if(!vm.getValue()) {
                    validText = vm.getValue();
                    nameWalk.setError(vm.getError().toString());
                }

                vm = textValidator.validate(descriptionWalk, filters);
                if(!vm.getValue()) {
                    validText = vm.getValue();
                    descriptionWalk.setError(vm.getError().toString());
                }

                if (dogsSelectedNumber > 0) {
                    if (validText) {
                        startActivity(ActivityManager.getGPSWalk(getSelf()));
                    }
                }
                else {
                    ((TextView)dogSpinner.getSelectedView()).setError(ErrorMessage.BLANK.toString());
                }

            }
        });
    }

    private void initAlertDialog() {
        dogSpinner = (Spinner) findViewById(R.id.spinner1);
        Button selectDogsForWalk = (Button) findViewById(R.id.selectDogsForWalk);
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
