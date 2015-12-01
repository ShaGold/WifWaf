package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private Button signUpButton;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        return MenuManager.emptyMenu(item) || super.onOptionsItemSelected(item);
    }

    private void initSignUpButton() {
        signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setBackgroundColor(WifWafColor.BROWN_DARK);
        final Intent home = new Intent(getApplicationContext(), HomeActivity.class);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home);
            }
        });
    }

    private void initSignInButton() {
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setBackgroundColor(WifWafColor.BROWN_DARK);
        final Intent signUp = new Intent(getApplicationContext(), SignUpActivity.class);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUp);
            }
        });
    }
}
