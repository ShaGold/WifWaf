package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafColor;

public class AddDogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);

        Button confirmAddDog = (Button) findViewById(R.id.confirmAddDogButton);
        confirmAddDog.setBackgroundColor(WifWafColor.BROWN_DARK);
        final Intent getUserDog = new Intent(getApplicationContext(), UserDogsActivity.class);
        confirmAddDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getUserDog);

            }
        });

        EditText e = (EditText) findViewById(R.id.name);

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
}
