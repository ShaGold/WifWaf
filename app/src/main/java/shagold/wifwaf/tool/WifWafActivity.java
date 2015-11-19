package shagold.wifwaf.tool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import shagold.wifwaf.AddDogActivity;
import shagold.wifwaf.HomeActivity;
import shagold.wifwaf.R;
import shagold.wifwaf.UserDogsActivity;
import shagold.wifwaf.UserProfileActivity;
import shagold.wifwaf.manager.ActivityManager;

/**
 * Created by jimmy on 19/11/15.
 */
public class WifWafActivity extends AppCompatActivity {

    private Activity self;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.self = this;
    }

    public Activity getSelf() {
        return self;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void initBackground() {
        getWindow().getDecorView().setBackgroundColor(WifWafColor.BROWN_LIGHT);
    }

    public void initToolBar(int id) {
        toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundColor(WifWafColor.BROWN);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final WifWafImageButton home = new WifWafImageButton(this, HomeActivity.class);
        home.setImageResource(R.drawable.home);
        home.setLayoutParams(lp);
        home.initBackground();
        if(ActivityManager.isNotSameActivity(this, home.getActivity()))
            home.setBackgroundColor(Color.TRANSPARENT);
        home.setTag(View.NO_ID);
        home.setId(View.NO_ID);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityManager.isNotSameActivity(self, home.getActivity()))
                    startActivity(ActivityManager.getHome(getSelf()));
            }
        });
        toolbar.addView(home);

        final WifWafImageButton profile = new WifWafImageButton(this, UserProfileActivity.class);
        profile.setImageResource(R.drawable.user_icon);
        profile.setLayoutParams(lp);
        profile.initBackground();
        if(ActivityManager.isNotSameActivity(this, profile.getActivity()))
            profile.setBackgroundColor(Color.TRANSPARENT);
        profile.setTag(View.NO_ID);
        profile.setId(View.NO_ID);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityManager.isNotSameActivity(self, profile.getActivity()))
                    startActivity(ActivityManager.getUserProfile(getSelf()));
            }
        });
        toolbar.addView(profile);

        final WifWafImageButton dogs = new WifWafImageButton(this, UserDogsActivity.class);
        dogs.setImageResource(R.drawable.dogi2);
        dogs.setLayoutParams(lp);
        dogs.initBackground();
        if(ActivityManager.isNotSameActivity(this, dogs.getActivity()))
            dogs.setBackgroundColor(Color.TRANSPARENT);
        if(ActivityManager.isSameActivity(this, AddDogActivity.class))
            dogs.setBackground(dogs.getOriginalBackground());
        dogs.setTag(View.NO_ID);
        dogs.setId(View.NO_ID);
        dogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityManager.isNotSameActivity(self, dogs.getActivity()))
                    startActivity(ActivityManager.getUserDogs(getSelf()));
            }
        });
        toolbar.addView(dogs);
    }

    public void initSimpleToolBar(int id) {
        toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundColor(WifWafColor.BROWN);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final WifWafImageButton home = new WifWafImageButton(this, HomeActivity.class);
        home.setImageResource(R.drawable.home);
        home.setLayoutParams(lp);
        home.initBackground();
        home.setTag(View.NO_ID);
        home.setId(View.NO_ID);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nothing
            }
        });
        toolbar.addView(home);

    }
}
