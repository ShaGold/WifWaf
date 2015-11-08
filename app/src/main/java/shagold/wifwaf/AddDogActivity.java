package shagold.wifwaf;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;

public class AddDogActivity extends AppCompatActivity {

    private Activity self;
    private Button confirmAddDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        self = this;

        confirmAddDog = (Button) findViewById(R.id.confirmAddDogButton);
        confirmAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ActivityManager.getUserDogs(self));

            }
        });

        EditText e = (EditText) findViewById(R.id.Name);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuManager.defaultMenu(this, item.getItemId()) || super.onOptionsItemSelected(item);
    }
}
