package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private Button signUpButton;
    private Button signInButton;
    private EditText ETNickname;
    private EditText ETPassword;

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
        //mSocket.on("RTrySignIn", onRTrySignIn);
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

    private void trySignIn(View view) throws JSONException {
        ETNickname = (EditText) findViewById(R.id.Nickname);
        String nickname = ETNickname.getText().toString();
        ETPassword = (EditText) findViewById(R.id.Password);
        String pass = ETPassword.getText().toString();

        if (nickname.length() < 3){
            Toast.makeText(MainActivity.this, "Pseudo trop court", Toast.LENGTH_LONG).show();
        }
        if (pass.length() < 6){
            Toast.makeText(MainActivity.this, "Le mot de passe est trop court", Toast.LENGTH_LONG).show();
        }

        //Test de connexion
        User user = new User(nickname, pass);
        JSONObject jsonUser = user.toJson();
        System.out.println("TrySignIn" + jsonUser);
        mSocket.emit("TrySignIn", jsonUser);

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
