package shagold.wifwaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;

public class UserProfileActivity extends AppCompatActivity {

    private User mUser;
    private Socket mSocket;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUser = SocketManager.getMyUser();

        mSocket = SocketManager.getMySocket();
        mSocket.on("RUpdateUser", onRUpdateUser);

        Log.d("CREATE", "USER PROFILE ACTIVITY");

        EditText userProfileName = (EditText) findViewById(R.id.userProfileName);
        userProfileName.setText(mUser.getNickname());

        EditText userProfileMail = (EditText) findViewById(R.id.userProfileMail);
        userProfileMail.setText(mUser.getEmail());

        EditText userProfileBirthday = (EditText) findViewById(R.id.userProfileBirthday);
        userProfileBirthday.setText(mUser.getBirthday());

        EditText userProfileDescription = (EditText) findViewById(R.id.userProfileDescription);
        userProfileDescription.setText(mUser.getDescription());

        EditText userProfilePhoneNumber = (EditText) findViewById(R.id.userProfilePhoneNumber);
        userProfilePhoneNumber.setText(Integer.toString(mUser.getPhoneNumber()));

        // TODO défault
        ImageView creatorWalk = (ImageView) findViewById(R.id.avatarUserProfile);
        creatorWalk.setImageResource(R.drawable.user);

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

    public void saveProfileUser(View view) {
        EditText userProfileName = (EditText) findViewById(R.id.userProfileName);
        String nameU = userProfileName.getText().toString();

        EditText userProfileMail = (EditText) findViewById(R.id.userProfileMail);
        String mailU = userProfileMail.getText().toString();

        EditText userProfileBirthday = (EditText) findViewById(R.id.userProfileBirthday);
        String birthday = userProfileBirthday.getText().toString();

        EditText userProfileDescription = (EditText) findViewById(R.id.userProfileDescription);
        String descriptionU = userProfileDescription.getText().toString();

        EditText userProfilePhoneNumber = (EditText) findViewById(R.id.userProfilePhoneNumber);
        userProfilePhoneNumber.setText(Integer.toString(mUser.getPhoneNumber()));
        int phoneU = Integer.parseInt(userProfilePhoneNumber.getText().toString());

        User u = new User(mUser.getIdUser(), mailU, nameU, mUser.getPassword(), birthday, phoneU, descriptionU, "");
        SocketManager.setMyUser(u);

        try {
            mSocket.emit("updateUser", u.toJsonWithId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Emitter.Listener onRUpdateUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            UserProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent result = new Intent(UserProfileActivity.this, HomeActivity.class);
                    startActivity(result);
                }
            });
        }

    };
}
