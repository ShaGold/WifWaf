package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import shagold.wifwaf.view.ErrorMessage;
import shagold.wifwaf.view.TextValidator;
import shagold.wifwaf.view.ValidateMessage;
import shagold.wifwaf.view.filter.text.EditTextFilter;
import shagold.wifwaf.view.filter.text.EmailFilter;
import shagold.wifwaf.view.filter.text.SizeFilter;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;
    private Button signUpButton;
    private Button signInButton;
    private Button connexionDebug;
    private EditText Etemail;
    private EditText ETPassword;
    private String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SocketManager.setMySocket(IO.socket("http://51.254.124.136:8000"));
        } catch (URISyntaxException e) {}
        mSocket = SocketManager.getMySocket();
        mSocket.connect();
        mSocket.on("RTrySignIn", onRTrySignIn);

        initSignUpButton();
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

    public void trySignInDebug(View view) throws JSONException {
        connexionDebug = (Button) findViewById(R.id.connexionDebug);
        User user = new User("test@test.fr", "test");
        JSONObject jsonUser = user.toJson();
        System.out.println("TrySignIn" + jsonUser);
        mSocket.emit("TrySignIn", jsonUser);
        pass = "test";
    }

    public void trySignIn(View view) throws JSONException {
        //préparation filtres
        EditTextFilter[] filterSize = {new SizeFilter()};
        EditTextFilter[] filterEmail = {new SizeFilter(), new EmailFilter()};

        Etemail = (EditText) findViewById(R.id.Email);
        ETPassword = (EditText) findViewById(R.id.Password);

        //Vérification champs
        TextValidator textValidator = new TextValidator();
        boolean valid = true;
        //E-mail
        ValidateMessage vmMail = textValidator.validate(Etemail, filterEmail);
        if(!vmMail.getValue()) {
            valid = false;
            if (vmMail.getError().equals(ErrorMessage.SIZE)){
                int min = ((SizeFilter) filterSize[0]).getMin();
                int max = ((SizeFilter) filterSize[0]).getMax();
                Etemail.setError(vmMail.getError().toString() + " min: " + min + " max: " + max);
            }
            else{
                Etemail.setError(vmMail.getError().toString());
            }
        }

        //Mot de passe
        ValidateMessage vmPass = textValidator.validate(ETPassword, filterSize);
        if(!vmPass.getValue()) {
            valid = false;
            int min = ((SizeFilter) filterSize[0]).getMin();
            int max = ((SizeFilter) filterSize[0]).getMax();
            ETPassword.setError(vmPass.getError().toString() + " min: " + min + " max: " + max);
        }

        if (!valid){
            return;
        }

        String email = Etemail.getText().toString();
        pass = ETPassword.getText().toString();
        //Test de connexion
        User user = new User(email, pass);
        JSONObject jsonUser = user.toJson();
        System.out.println("TrySignIn" + jsonUser);
        mSocket.emit("TrySignIn", jsonUser);

    }

    private void initSignUpButton() {
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

    private Emitter.Listener onRTrySignIn = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject param = (JSONObject) args[0];
                    System.out.println(param);
                    try {
                        String realpass = (String) param.get("password");
                        String typedpassencrypt = User.encryptPassword(pass);
                        int id = Integer.parseInt(param.get("id").toString());
                        if(realpass.equals(typedpassencrypt) && id != -1){
                            User newUser = new User(param);
                            SocketManager.setMyUser(newUser);
                            Intent resultat = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(resultat);
                        }
                        else{
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Erreur");
                            alertDialog.setMessage("Mauvais identifiants"); //TODO internationalisation
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
