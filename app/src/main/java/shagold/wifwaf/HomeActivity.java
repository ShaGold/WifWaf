package shagold.wifwaf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import android.content.Intent;
import java.util.ArrayList;
import java.util.List;
import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.list.WalkAdapter;
import shagold.wifwaf.manager.SocketManager;

import com.github.nkzawa.socketio.client.Socket;

/**
 * Created by jimmy on 07/11/15.
 */
public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSocket = SocketManager.getMySocket();
        mSocket.emit("getAllWalks");
        initListView();

        List<Walk> walks = generateWalks();
        WalkAdapter adapter = new WalkAdapter(this, walks);
        mListView.setAdapter(adapter);
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

    private List<Walk> generateWalks() {
        List<Walk> walks = new ArrayList<Walk>();

        walks.add(new Walk("Balade 1", "Ma premiere balade !"));
        walks.add(new Walk("Balade 2", "Ma premiere balade !"));
        walks.add(new Walk("Balade 3", "Ma premiere balade !"));
        walks.add(new Walk("Balade 4", "Ma premiere balade !"));
        walks.add(new Walk("Balade 5", "Ma premiere balade !"));
        walks.add(new Walk("Balade 6", "Ma premiere balade !"));
        walks.add(new Walk("Balade 1", "Ma premiere balade !"));
        walks.add(new Walk("Balade 2", "Ma premiere balade !"));
        walks.add(new Walk("Balade 3", "Ma premiere balade !"));
        walks.add(new Walk("Balade 4", "Ma premiere balade !"));
        walks.add(new Walk("Balade 5", "Ma premiere balade !"));
        walks.add(new Walk("Balade 6", "Ma premiere balade !"));
        return walks;
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.walkListView);

        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Walk walk = (Walk) mListView.getItemAtPosition(position);
                //Toast.makeText(getBaseContext(), walk.getTitle(), Toast.LENGTH_SHORT).show();
                final Intent getWalk = new Intent(getApplicationContext(), WalkProfileActivity.class);
                startActivity(getWalk);
            }
        });
    }

}
