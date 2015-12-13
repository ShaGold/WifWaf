package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.github.nkzawa.socketio.client.Socket;

import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;

/**
 * Created by jimmy on 13/12/15.
 */
public class UserWalksActivity extends AppCompatActivity {

    private Socket mSocket;
    private User mUser;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_walks);

        mSocket = SocketManager.getMySocket();
        mUser = SocketManager.getMyUser();

        final Intent activityAddWalk = new Intent(getApplicationContext(), AddWalkActivity.class);

        Button addNewWalk = (Button) findViewById(R.id.addNewWalkButton);
        addNewWalk.setBackgroundColor(WifWafColor.BROWN_DARK);
        addNewWalk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(activityAddWalk);
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
        return MenuManager.defaultMenu(this, item) || super.onOptionsItemSelected(item);
    }


}
