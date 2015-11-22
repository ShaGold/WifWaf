package shagold.wifwaf;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;

/**
 * Created by jimmy on 22/11/15.
 */
public class AddWalkActivity extends WifWafActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_walk);
        initBackground();
        initToolBar(R.id.toolbarAddWalk);


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
