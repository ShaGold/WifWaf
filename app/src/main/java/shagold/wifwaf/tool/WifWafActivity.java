package shagold.wifwaf.tool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import java.util.Objects;

import shagold.wifwaf.AddDogActivity;
import shagold.wifwaf.DogProfileActivity;
import shagold.wifwaf.HomeActivity;
import shagold.wifwaf.R;
import shagold.wifwaf.UserDogsActivity;
import shagold.wifwaf.UserProfileActivity;
import shagold.wifwaf.WalkActivity;
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

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        Class[] homeActivityOther = {WalkActivity.class};
        final WifWafImageButton home =
                WifWafImageButtonFactory.createImageButton(this, HomeActivity.class, R.drawable.home, lp, homeActivityOther);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityManager.isNotSameActivity(self, home.getActivity()))
                    startActivity(ActivityManager.getHome(getSelf()));
            }
        });
        toolbar.addView(home);

        final WifWafImageButton profile =
                WifWafImageButtonFactory.createImageButton(this, UserProfileActivity.class, R.drawable.user_icon, lp, null);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityManager.isNotSameActivity(self, profile.getActivity()))
                    startActivity(ActivityManager.getUserProfile(getSelf()));
            }
        });
        toolbar.addView(profile);

        Class[] dogsActivityOther = {AddDogActivity.class, DogProfileActivity.class};
        final WifWafImageButton dogs =
            WifWafImageButtonFactory.createImageButton(this, UserDogsActivity.class, R.drawable.dogi2, lp, dogsActivityOther);
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

        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        final WifWafImageButton home =
                WifWafImageButtonFactory.createImageButton(this, HomeActivity.class, R.drawable.home, lp, null);
        home.setBackground(home.getOriginalBackground());
        toolbar.addView(home);

    }
}
