package shagold.wifwaf;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.tool.WifWafActivity;

/**
 * Created by jimmy on 07/11/15.
 */
public class UserProfileActivity extends WifWafActivity {

    private User defaultUser = new User("toto@gmail.com", "marlene", "toto", "12 Nov", 674560934, "une codeuse", null);

    private EditText userProfileName;
    private EditText userProfileMail;
    private EditText userProfileBirthday;
    private EditText userProfilePhoneNumber;
    private EditText userProfileDescription;

    private Button applyChangeUserProfile;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initBackground();

        initToolBar(R.id.toolbarUserProfile);

        Log.d("CREATE", "USER PROFILE ACTIVITY");

        userProfileName = (EditText) findViewById(R.id.userProfileName);
        userProfileName.setText(defaultUser.getNickname());

        userProfileMail = (EditText) findViewById(R.id.userProfileMail);
        userProfileMail.setText(defaultUser.getEmail());

        userProfileBirthday = (EditText) findViewById(R.id.userProfileBirthday);
        userProfileBirthday.setText(defaultUser.getBirthday());

        userProfileDescription = (EditText) findViewById(R.id.userProfileDescription);
        userProfileDescription.setText(defaultUser.getDescription());

        userProfilePhoneNumber = (EditText) findViewById(R.id.userProfilePhoneNumber);
        userProfilePhoneNumber.setText(Integer.toString(defaultUser.getPhoneNumber()));

        applyChangeUserProfile = (Button) findViewById(R.id.applyChangeUserProfile);
        applyChangeUserProfile.setEnabled(false);


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
}
