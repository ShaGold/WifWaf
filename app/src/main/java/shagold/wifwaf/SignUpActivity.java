package shagold.wifwaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import shagold.wifwaf.dataBase.User;

public class SignUpActivity extends AppCompatActivity {

    /*private Socket socket;
    {
        try {
            socket = IO.socket("http://notre.adresse.ici");
        } catch (URISyntaxException e) {}
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        /*socket.connect();
        socket.on("onTest", onTest);
        socket.on("RTrySignUp", onRTrySignUp);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*socket.disconnect();
        // TODO faire socket.off pour chaque event écouté
        socket.off("RTrySignUp", onRTrySignUp);*/
    }

    public void trySignUp(View view) throws JSONException {
        //Récupération des valeurs
        EditText ETnickname = (EditText) findViewById(R.id.Nickname);
        String Snickname = ETnickname.getText().toString();
        EditText ETpassword = (EditText) findViewById(R.id.Password);
        String Spassword = ETpassword.getText().toString();
        EditText ETemail = (EditText) findViewById(R.id.Email);
        String Semail = ETemail.getText().toString();
        EditText ETPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        int SphoneNumber = Integer.parseInt(ETPhoneNumber.getText().toString());
        EditText ETBirthday = (EditText) findViewById(R.id.Birthday);
        String Sbirthday = ETBirthday.getText().toString();
        EditText ETDescription = (EditText) findViewById(R.id.Description);
        String Sdescription = ETDescription.getText().toString();

        //Test inscription
        User user = new User(Semail,Snickname,Spassword,Sbirthday,SphoneNumber,Sdescription,"");
        JSONObject jsonUser = user.toJson();
        System.out.println(jsonUser);
        //socket.emit("TrySignUp", jsonUser);
    }

  /*  private Emitter.Listener onTest = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                    System.out.println("je recois onTest");
                }
            });
        }
    };

    private Emitter.Listener onRTrySignUp = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            SignUpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonuser = (JSONObject) args[0];
                    if (jsonuser == null){
                        Toast.makeText(SignUpActivity.this,"Inscription impossible", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent resultat = new Intent(SignUpActivity.this, ResultSignUpActivity.class);
                        String nickname = null;
                        try {
                            nickname = jsonuser.getString("nickname");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("[Réussite inscription]");
                        resultat.putExtra("Nickname", nickname); // pour pouvoir afficher bienvenue ..
                        startActivity(resultat);
                    }
                    System.out.println("[Demande inscription]");
                }
            });
        }
    };*/
}
