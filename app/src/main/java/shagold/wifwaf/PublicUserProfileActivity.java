package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.list.DogAdapter;
import shagold.wifwaf.manager.SocketManager;

public class PublicUserProfileActivity extends AppCompatActivity {

    private User mUser;
    private User defUser =  new User("adresse@gmail.com", "A", "B", "2001-10-12", 123, "J'aime beaucoup les animaux!", null);
    private Socket mSocket;
    private DogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user_profile);

        mSocket = SocketManager.getMySocket();
        mUser = SocketManager.getMyUser();
        mSocket.on("RGetAllMyDogs", onRGetAllMyDogs);

        //TODO recup vrai user id, need text
        int idUser = (int) getIntent().getIntExtra("USER", -1);

        // TODO besoin de l'user que lon veut
        //mSocket.emit("getAllMyDogs", defUser.getIdUser());
        mSocket.emit("getAllMyDogs", mUser.getIdUser());

        TextView userProfileName = (TextView) findViewById(R.id.userProfileName);
        userProfileName.setText(defUser.getNickname());

        TextView userProfileMail = (TextView) findViewById(R.id.userProfileMail);
        userProfileMail.setText(Html.fromHtml("<a href=mailto:" + defUser.getEmail() + ">" + defUser.getEmail() + "</a>"));

        TextView userProfileBirthday = (TextView) findViewById(R.id.userProfileBirthday);
        userProfileBirthday.setText(defUser.getBirthday());

        TextView userProfileDescription = (TextView) findViewById(R.id.userProfileDescription);
        userProfileDescription.setText(defUser.getDescription());

        TextView userProfilePhoneNumber = (TextView) findViewById(R.id.userProfilePhoneNumber);
        userProfilePhoneNumber.setText(Html.fromHtml("<a href=tel:" + Integer.toString(defUser.getPhoneNumber()) + ">" + Integer.toString(defUser.getPhoneNumber()) + "</a>"));

        // TODO default image
        ImageView userAvatar = (ImageView) findViewById(R.id.avatarPublicUserProfile);
        userAvatar.setImageResource(R.drawable.user);

        // TODO send to public dog profile

        userProfileMail.setMovementMethod(LinkMovementMethod.getInstance());
        userProfilePhoneNumber.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
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

    private List<Dog> generateDogsFromJson(JSONArray dogsJSON) {

        List<Dog> dogs = new ArrayList<Dog>();

        if(dogsJSON != null) {
            for (int i = 0; i < dogsJSON.length(); i++) {
                JSONObject currentObj = null;
                try {
                    currentObj = dogsJSON.getJSONObject(i);
                    Dog newDog = new Dog(currentObj);
                    dogs.add(newDog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return dogs;
    }

    public void userDogs(View view) {
        AlertDialog.Builder userDogsDialog = new AlertDialog.Builder(PublicUserProfileActivity.this);

        userDogsDialog.setTitle("User Dogs");

        ListView modeList = new ListView(PublicUserProfileActivity.this);
        modeList.setAdapter(adapter);

        userDogsDialog.setView(modeList);

        AlertDialog alertDogs = userDogsDialog.create();
        alertDogs.show();
    }

    private Emitter.Listener onRGetAllMyDogs = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            PublicUserProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Dog> dogs = generateDogsFromJson((JSONArray) args[0]);
                    System.out.println("DOGS PUBLIC " + dogs);
                    adapter = new DogAdapter(PublicUserProfileActivity.this, dogs);
                }

            });
        }

    };
}
