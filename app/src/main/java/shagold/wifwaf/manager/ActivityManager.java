package shagold.wifwaf.manager;

import android.app.Activity;
import android.content.Intent;

import shagold.wifwaf.AddDogActivity;
import shagold.wifwaf.HomeActivity;
import shagold.wifwaf.SignUpActivity;
import shagold.wifwaf.UserDogsActivity;
import shagold.wifwaf.UserProfileActivity;

/**
 * Created by jimmy on 07/11/15.
 */
public class ActivityManager {

    public static Intent getClass(Activity a, Class cl) {
        return new Intent(a.getApplicationContext(), cl);
    }

    public static Intent getHome(Activity a) {
        return getClass(a, HomeActivity.class);
    }

    public static Intent getUserProfile(Activity a) {
        return getClass(a, UserProfileActivity.class);
    }

    public static Intent getUserDogs(Activity a) {
        return getClass(a, UserDogsActivity.class);
    }

    public static Intent getAddDog(Activity a) {
        return getClass(a, AddDogActivity.class);
    }

    public static Intent getSignUp(Activity a) {
        return getClass(a, SignUpActivity.class);
    }

    public static boolean isHome(Activity a) {
        return a.getClass() == HomeActivity.class;
    }

    public static boolean isUserProfile(Activity a) {
        return a.getClass() == UserProfileActivity.class;
    }

    public static boolean isUserDogs(Activity a) {
        return a.getClass() == UserDogsActivity.class;
    }

    public static boolean isNotHome(Activity a) {
        return a.getClass() != HomeActivity.class;
    }

    public static boolean isNotUserProfile(Activity a) {
        return a.getClass() != UserProfileActivity.class;
    }

    public static boolean isNotUserDogs(Activity a) {
        return a.getClass() != UserDogsActivity.class;
    }


}
