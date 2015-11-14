package shagold.wifwaf;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;

/**
 * Created by jimmy on 07/11/15.
 */
public class UserDogsActivity extends AppCompatActivity {

    private Activity self;
    private Button addDog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dogs);
        self = this;

        addDog = (Button) findViewById(R.id.addDogButton);
        addDog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(ActivityManager.getAddDog(self));
            }
        });
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
