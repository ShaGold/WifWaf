package shagold.wifwaf;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.adapter.DogPublicAdapter;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.tool.WifWafColor;
import shagold.wifwaf.tool.WifWafUserBirthday;

public class PublicUserProfileActivity extends AppCompatActivity {

    private User user;
    private Socket mSocket;
    private DogPublicAdapter adapter;
    private TextView tvPhone;
    private TextView tvMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_user_profile);

        mSocket = SocketManager.getMySocket();
        mSocket.on("RGetAllMyDogs", onRGetAllMyDogs);

        int idUser = getIntent().getIntExtra("USER", -1);
        System.out.println("ID U : " + idUser);
        mSocket.emit("getUserById", idUser);
        mSocket.on("RGetUser", onRGetUser);
        mSocket.emit("getAllMyDogs", idUser);

        //Mise en page formulaire
        tvMail = (TextView) findViewById(R.id.userPublicProfileMail_tv);
        tvPhone = (TextView) findViewById(R.id.userPublicProfilePhoneNumber_tv);
        style();
    }

    public void style(){
        //Récupération des textView
        //Récupération typeface
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/coolvetica rg.ttf");

        //Récupération texview
        TextView tvName = (TextView) findViewById(R.id.userPublicProfileName_tv);
        TextView tvBirth = (TextView) findViewById(R.id.userPublicProfileBirthday_tv);
        TextView tvDesc = (TextView) findViewById(R.id.userPublicProfileDescription_tv);

        //On ajoute le style à tous les textview
        tvName.setTypeface(tf);
        tvName.setTextSize(24.0f);
        tvName.setTextColor(WifWafColor.BROWN);

        tvMail.setTypeface(tf);
        tvMail.setTextSize(24.0f);
        tvMail.setTextColor(WifWafColor.BROWN);

        tvBirth.setTypeface(tf);
        tvBirth.setTextSize(24.0f);
        tvBirth.setTextColor(WifWafColor.BROWN);

        tvPhone.setTypeface(tf);
        tvPhone.setTextSize(20.0f);
        tvPhone.setTextColor(WifWafColor.BROWN);

        tvDesc.setTypeface(tf);
        tvDesc.setTextSize(20.0f);
        tvDesc.setTextColor(WifWafColor.BROWN);
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

    public void userDogs(View view) {
        AlertDialog.Builder userDogsDialog = new AlertDialog.Builder(PublicUserProfileActivity.this);

        userDogsDialog.setTitle("User Dogs");

        final ListView modeList = new ListView(PublicUserProfileActivity.this);
        modeList.setAdapter(adapter);
        modeList.setDividerHeight(modeList.getDividerHeight()*3);

        userDogsDialog.setView(modeList);

        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Dog dog = (Dog) modeList.getItemAtPosition(position);
                Intent clickedWalkProfile = new Intent(getApplicationContext(), PublicDogProfileActivity.class);
                clickedWalkProfile.putExtra("DOG", dog);
                startActivity(clickedWalkProfile);
            }
        });

        AlertDialog alertDogs = userDogsDialog.create();
        alertDogs.show();
    }

    private Emitter.Listener onRGetAllMyDogs = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            PublicUserProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Dog> dogs = Dog.generateDogsFromJson((JSONArray) args[0]);
                    adapter = new DogPublicAdapter(PublicUserProfileActivity.this, dogs);
                }

            });
        }

    };

    private Emitter.Listener onRGetUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            PublicUserProfileActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("USER P - " + args[0]);
                    try {
                        user = new User((JSONObject) args[0]);
                        System.out.println(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    System.out.println("USER - " + user);

                    TextView userProfileName = (TextView) findViewById(R.id.userProfileName);
                    userProfileName.setText(user.getNickname());

                    TextView userProfileMail = (TextView) findViewById(R.id.userProfileMail);
                    userProfileMail.setText(Html.fromHtml("<a href=mailto:" + user.getEmail() + ">" + user.getEmail() + "</a>"));

                    TextView userProfileBirthday = (TextView) findViewById(R.id.userProfileBirthday);
                    WifWafUserBirthday birthday = new WifWafUserBirthday(user.getBirthday());
                    userProfileBirthday.setText(birthday.getDate());

                    TextView userProfileDescription = (TextView) findViewById(R.id.userProfileDescription);
                    userProfileDescription.setText(user.getDescription());

                    TextView userProfilePhoneNumber = (TextView) findViewById(R.id.userProfilePhoneNumber);
                    userProfilePhoneNumber.setText(Html.fromHtml("<a href=tel:" + user.getPhoneNumber() + ">" + user.getPhoneNumber() + "</a>"));
                    System.out.println("Val" + user.getPhoneNumber());
                    if (user.getPhoneNumber().isEmpty()){
                        userProfilePhoneNumber.setVisibility(View.GONE);
                        tvPhone.setVisibility(View.GONE);
                    }

                    ImageView userAvatar = (ImageView) findViewById(R.id.avatarPublicUserProfile);
                    userAvatar.setImageBitmap(user.getPhotoBitmap());

                    userProfileMail.setMovementMethod(LinkMovementMethod.getInstance());
                    userProfilePhoneNumber.setMovementMethod(LinkMovementMethod.getInstance());
                }

            });
        }

    };
}
