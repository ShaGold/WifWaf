package shagold.wifwaf;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.dataBase.WalkDifficulty;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.view.WalkAdapter;

/**
 * Created by jimmy on 07/11/15.
 */
public class HomeActivity extends WifWafActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initBackground();
        initToolBar(R.id.toolbarHome);
        initListView();

        List<Walk> walks = generateWalks();
        WalkAdapter adapter = new WalkAdapter(this, walks);
        mListView.setAdapter(adapter);
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

    private List<Walk> generateWalks() {
        List<Walk> walks = new ArrayList<Walk>();

        walks.add(new Walk("Balade 1", "Ma premiere balade !", WalkDifficulty.VERY_EASY));
        walks.add(new Walk("Balade 2", "Ma premiere balade !", WalkDifficulty.EASY));
        walks.add(new Walk("Balade 3", "Ma premiere balade !", WalkDifficulty.MEDIUM));
        walks.add(new Walk("Balade 4", "Ma premiere balade !", WalkDifficulty.HARD));
        walks.add(new Walk("Balade 5", "Ma premiere balade !", WalkDifficulty.EASY));
        walks.add(new Walk("Balade 6", "Ma premiere balade !", WalkDifficulty.MEDIUM));
        walks.add(new Walk("Balade 1", "Ma premiere balade !", WalkDifficulty.HARD));
        walks.add(new Walk("Balade 2", "Ma premiere balade !", WalkDifficulty.EASY));
        walks.add(new Walk("Balade 3", "Ma premiere balade !", WalkDifficulty.MEDIUM));
        walks.add(new Walk("Balade 4", "Ma premiere balade !", WalkDifficulty.HARD));
        walks.add(new Walk("Balade 5", "Ma premiere balade !", WalkDifficulty.EASY));
        walks.add(new Walk("Balade 6", "Ma premiere balade !", WalkDifficulty.VERY_HARD));
        return walks;
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.listView);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Walk walk = (Walk) mListView.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), walk.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
