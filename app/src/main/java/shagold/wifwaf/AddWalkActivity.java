package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.tool.WifWafColor;

/**
 * Created by jimmy on 22/11/15.
 */
public class AddWalkActivity extends WifWafActivity {

    private Button selectDogsForWalk;
    private AlertDialog alertSelectDogs;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_walk);
        initBackground();
        initToolBar(R.id.toolbarAddWalk);

        selectDogsForWalk = (Button) findViewById(R.id.selectDogsForWalk);
        selectDogsForWalk.setBackgroundColor(WifWafColor.BROWN_DARK);
        selectDogsForWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Dog> dogs = generateDogs();
                List<String> dogsName = new ArrayList<String>();

                for(Dog dog : dogs)
                    dogsName.add(dog.getName());

                final CharSequence[] dilogList = dogsName.toArray(new CharSequence[dogsName.size()]);

                AlertDialog.Builder multChoiceDialog = new AlertDialog.Builder(getSelf());

                multChoiceDialog.setTitle("Choice Dogs");

                boolean[] _selections = new boolean[dilogList.length];

                multChoiceDialog.setMultiChoiceItems(dilogList, _selections, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                    }
                });

                // add positive button here
                multChoiceDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // getting listview from alert box
                        ListView list = ((AlertDialog) dialog).getListView();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < list.getCount(); i++) {
                            boolean checked = list.isItemChecked(i);
                            // get checked list value
                            if (checked) {
                                if (sb.length() > 0)
                                    sb.append(",");
                                sb.append(list.getItemAtPosition(i));
                            }
                        }

                        //Toast.makeText(getApplicationContext(), "Selected digit:" + sb.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                // add negative button
                multChoiceDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // cancel code here
                            }
                        });
                alertSelectDogs = multChoiceDialog.create();
                alertSelectDogs.show();
            }
        });

    }

    private void initAlertDialog() {

    }

    private List<Dog> generateDogs() {
        List<Dog> dogs = new ArrayList<Dog>();

        dogs.add(new Dog(1, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(2, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(3, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(4, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(5, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(6, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));
        dogs.add(new Dog(7, "Gold", 23, "Golden", 23, "null", "null", "null", "null", "un chien jaune"));
        dogs.add(new Dog(8, "Shana", 23, "Epagnol", 23, "null", "null", "null", "null", "un chien passif"));

        return dogs;
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
