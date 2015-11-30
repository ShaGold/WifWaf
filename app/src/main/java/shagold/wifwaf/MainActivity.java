package shagold.wifwaf;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import shagold.wifwaf.manager.ActivityManager;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafActivity;
import shagold.wifwaf.tool.WifWafColor;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends WifWafActivity {

    private Socket mSocket;
    private Button signUpButton;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackground();
        initSimpleToolBar(R.id.toolbarMain);

        initSignInButton();
        initSignUpButton();
        try {
            SocketManager.setMySocket(IO.socket("http://51.254.124.136:8000"));
        } catch (URISyntaxException e) {}
        mSocket = SocketManager.getMySocket();
        mSocket.connect();
        //Pour tous les events qu'on veut écouter il faudra faire: mSocket.on()...
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

    private void initSignUpButton() {
        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setBackgroundColor(WifWafColor.BROWN_DARK);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ActivityManager.getHome(getSelf()));
            }
        });
    }

    private void initSignInButton() {
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setBackgroundColor(WifWafColor.BROWN_DARK);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ActivityManager.getSignUp(getSelf()));
            }
        });
    }
}
