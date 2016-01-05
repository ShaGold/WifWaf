package shagold.wifwaf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONException;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import shagold.wifwaf.dataBase.Dog;
import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.manager.MenuManager;
import shagold.wifwaf.manager.SocketManager;
import shagold.wifwaf.fragment.WifWafDatePickerFragment;
import shagold.wifwaf.tool.WifWafColor;
import shagold.wifwaf.tool.WifWafUserBirthday;
import shagold.wifwaf.view.ErrorMessage;
import shagold.wifwaf.view.TextValidator;
import shagold.wifwaf.view.ValidateMessage;
import shagold.wifwaf.view.filter.text.EditTextFilter;
import shagold.wifwaf.view.filter.text.EmailFilter;
import shagold.wifwaf.view.filter.text.NumberFilter;
import shagold.wifwaf.view.filter.text.SizeFilter;
import shagold.wifwaf.view.filter.textview.PersonalizedBlankFilter;
import shagold.wifwaf.view.filter.textview.TextViewFilter;

public class UserProfileActivity extends AppCompatActivity {

    private User mUser;
    private Socket mSocket;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int ACTION_SELECT_PICTURE = 2;

    ImageView mImageView;

    //Edit text
    EditText userProfileName;
    EditText userProfileMail;
    TextView userProfileBirthday;
    EditText userProfileDescription;
    EditText userProfilePhoneNumber;
    ImageView creatorWalk;

    Bitmap imageBitmap;
    String bitmapImagedata = "not changed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Gestion socket
        mUser = SocketManager.getMyUser();
        mSocket = SocketManager.getMySocket();
        mSocket.on("RUpdateUser", onRUpdateUser);

        initFields();
    }

    public void style(){
        //Récupération typeface
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/coolvetica rg.ttf");

        //Récupération texview
        TextView tvName = (TextView) findViewById(R.id.userProfileName_tv);
        TextView tvMail = (TextView) findViewById(R.id.userProfileMail_tv);
        TextView tvBirth = (TextView) findViewById(R.id.userProfileBirthday_tv);
        TextView tvPhone = (TextView) findViewById(R.id.userProfilePhoneNumber_tv);
        TextView tvDesc = (TextView) findViewById(R.id.userProfileDescription_tv);

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

    public void initFields(){
        //Récupération values
        mImageView = (ImageView) findViewById(R.id.avatarUserProfile);

        userProfileName = (EditText) findViewById(R.id.userProfileName);
        userProfileName.setText(mUser.getNickname());

        userProfileMail = (EditText) findViewById(R.id.userProfileMail);
        userProfileMail.setText(mUser.getEmail());

        userProfileBirthday = (TextView) findViewById(R.id.userProfileBirthday);
        WifWafUserBirthday birthday = new WifWafUserBirthday(mUser.getBirthday());
        userProfileBirthday.setText(birthday.getDate());

        userProfileDescription = (EditText) findViewById(R.id.userProfileDescription);
        userProfileDescription.setText(mUser.getDescription());

        userProfilePhoneNumber = (EditText) findViewById(R.id.userProfilePhoneNumber);
        userProfilePhoneNumber.setText(mUser.getPhoneNumber());

        Bitmap myphoto = mUser.getPhotoBitmap();
        mImageView.setImageBitmap(myphoto);
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

    private boolean filter() {
        //Définition des filtres
        EditTextFilter[] filterNumber = {new SizeFilter(0,9), new NumberFilter()}; //pour le champ numéro de téléphone
        EditTextFilter[] filterSize = {new SizeFilter()}; // pour les champs texte classiques
        EditTextFilter[] filterEmail = {new SizeFilter(), new EmailFilter()};
        TextViewFilter filterDate = new PersonalizedBlankFilter(ErrorMessage.DATE);

        //Test validité des champs
        TextValidator textValidator = new TextValidator();
        boolean valid = true;

        //Nickname
        ValidateMessage vmNickname = textValidator.validate(userProfileName, filterSize);
        if(!vmNickname.getValue()) {
            valid = false;
            int min = ((SizeFilter) filterSize[0]).getMin();
            int max = ((SizeFilter) filterSize[0]).getMax();
            userProfileName.setError(vmNickname.getError().toString() + " min: " + min + " max: " + max);
        }

        //Adresse mail
        ValidateMessage vmMail = textValidator.validate(userProfileMail, filterEmail);
        if(!vmMail.getValue()) {
            valid = false;
            if (vmMail.getError().equals(ErrorMessage.SIZE)){
                int min = ((SizeFilter) filterSize[0]).getMin();
                int max = ((SizeFilter) filterSize[0]).getMax();
                userProfileMail.setError(vmMail.getError().toString() + " min: " + min + " max: " + max);
            }
            else{
                userProfileMail.setError(vmMail.getError().toString());
            }
        }

        //Numéro de téléphone
        /*ValidateMessage vmNumTel = textValidator.validate(userProfilePhoneNumber, filterNumber);
        if(!vmNumTel.getValue()) {
            valid = false;
            if (vmNumTel.getError().equals(ErrorMessage.SIZE)){
                int min = ((SizeFilter) filterSize[0]).getMin();
                int max = ((SizeFilter) filterSize[0]).getMax();
                userProfilePhoneNumber.setError(vmNumTel.getError().toString() + " min: " + min + " max: " + max);
            }
            else{
                userProfilePhoneNumber.setError(vmNumTel.getError().toString());
            }
        }*/

        //Description
        ValidateMessage vmDescrip = textValidator.validate(userProfileDescription, filterSize);
        if(!vmDescrip.getValue()) {
            valid = false;
            int min = ((SizeFilter) filterSize[0]).getMin();
            int max = ((SizeFilter) filterSize[0]).getMax();
            userProfileDescription.setError(vmDescrip.getError().toString() + " min: " + min + " max: " + max);
        }

        //date de naissance
       /* ValidateMessage vmBirthday = textValidator.validate(userProfileBirthday, filterDate);
        TextView birthday = (TextView) findViewById(R.id.BirthdayMaster);
        if(!vmBirthday.getValue()) {
            valid = false;
            birthday.setError(vmBirthday.getError().toString());
        }
        else {
            birthday.setError(null);
        }*/

        return valid;
    }

    public void saveProfileUser(View view) {
        //Vérification saisie
        if(!filter())
            return;

        //Récupération valeurs des champs
        String nameU = userProfileName.getText().toString();
        String mailU = userProfileMail.getText().toString();
        String birthday = userProfileBirthday.getText().toString();
        String descriptionU = userProfileDescription.getText().toString();
        String phoneU = userProfilePhoneNumber.getText().toString();

        if(bitmapImagedata == "not changed"){
            bitmapImagedata = mUser.getPhoto();
        }

        User u = new User(mUser.getIdUser(), mailU, nameU, mUser.getPassword(), birthday, phoneU, descriptionU, bitmapImagedata);

        try {
            mSocket.emit("updateUser", u.toJsonWithId());
            if(bitmapImagedata == "not changed") {
                SocketManager.setMyUserWithoutPic(u);
            }
            else{
                SocketManager.setMyUser(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void takePic(View view){
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            preparePhoto();
        }
        if (requestCode == ACTION_SELECT_PICTURE && resultCode == RESULT_OK) {
            getFileFromPath(data);
        }

    }

    public void selectPic(View view){
        //Préparation du bouton d'exploration de fichiers
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        String title = getString(R.string.select_picture_from_explorer);
        startActivityForResult(Intent.createChooser(intent,
                title), ACTION_SELECT_PICTURE);
    }

    public void getFileFromPath(final Intent data) {
        runOnUiThread(new Runnable() {
            public void run() {
                Uri selectedImageUri = data.getData();
                BitmapFactory.Options bfOptions = new BitmapFactory.Options();

                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap myImage = BitmapFactory.decodeStream(stream, null, bfOptions);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                myImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                myImage = Bitmap.createScaledBitmap(myImage, 204, 153, false);
                bitmapImagedata = Dog.encodeTobase64(myImage);

                mImageView.setImageBitmap(myImage);
            }
        });
    }

    public void preparePhoto(){
        BitmapFactory.Options bfOptions = new BitmapFactory.Options();
        bfOptions.inTempStorage = new byte[32 * 1024];

        // On convertit l'image en tableau de BYTE
        bitmapImagedata = User.encodeTobase64(imageBitmap);
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

    public void showDatePickerBirthdayDialog(View view) {
        WifWafDatePickerFragment newFragment = new WifWafDatePickerFragment();
        TextView ETBirthday = (TextView) findViewById(R.id.userProfileBirthday);
        newFragment.setDateText(ETBirthday);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
