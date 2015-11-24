package shagold.wifwaf;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.tool.WifWafColor;

/**
 * Created by jimmy on 22/11/15.
 */
public class WalkProfileActivity extends WifWafActivity {

    private Walk walk;
    private Button useWalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_profile);
        initBackground();
        initToolBar(R.id.toolbarWalkProfile);

        useWalk = (Button) findViewById(R.id.useWalkButton);
        useWalk.setBackgroundColor(WifWafColor.BROWN_DARK);
        useWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ActivityManager.getGPSWalk(getSelf()));
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
