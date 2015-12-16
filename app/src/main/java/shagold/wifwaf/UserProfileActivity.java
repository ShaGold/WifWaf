package shagold.wifwaf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;

/**
 * Created by jimmy on 07/11/15.
 */
public class UserProfileActivity extends AppCompatActivity {

    private User mUser;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mUser = SocketManager.getMyUser();

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

        Button applyChangeUserProfile = (Button) findViewById(R.id.applyChangeUserProfile);
        applyChangeUserProfile.setEnabled(false);


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
