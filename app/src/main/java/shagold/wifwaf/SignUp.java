package shagold.wifwaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.dataBase.UserOperations;

public class SignUp extends AppCompatActivity {

    private UserOperations userdbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

    public void trySignUp(View view){
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
        String SBirthday = ETBirthday.getText().toString();
        EditText ETDescription = (EditText) findViewById(R.id.Description);
        String SDescription = ETDescription.getText().toString();

        //Test inscription
        //User user = userdbOperations.addUser(Semail,Snickname,Spassword,SBirthday,SphoneNumber,SDescription,"");

        /*if (user == null){
            Toast.makeText(SignUp.this,"Inscription impossible", Toast.LENGTH_LONG).show();
        }
        else {
            Intent resultat = new Intent(SignUp.this, ResultSignUp.class);
            resultat.putExtra("Nickname", Snickname);
            resultat.putExtra("Password", Spassword);
            resultat.putExtra("Email", Semail);
            resultat.putExtra("Phone number", SphoneNumber);
            resultat.putExtra("Birthday", SBirthday);
            resultat.putExtra("Description", SDescription);
            startActivity(resultat);
        }*/
    }
}
