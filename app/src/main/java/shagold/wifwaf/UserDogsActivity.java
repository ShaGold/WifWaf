package shagold.wifwaf;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.tool.WifWafColor;

/**
 * Created by jimmy on 07/11/15.
 */
public class UserDogsActivity extends WifWafActivity {

    private Button addDog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dogs);
        initBackground();
        initToolBar(R.id.toolbarUserDogs);

        addDog = (Button) findViewById(R.id.addDogButton);
        addDog.setBackgroundColor(WifWafColor.BROWN_DARK);
        addDog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(ActivityManager.getAddDog(getSelf()));
            }
        });
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
