package shagold.wifwaf.manager;

import android.app.Activity;
import android.content.Intent;

import shagold.wifwaf.AddDogActivity;
import shagold.wifwaf.DogProfileActivity;
import shagold.wifwaf.HomeActivity;
import shagold.wifwaf.SignUpActivity;
import shagold.wifwaf.UserDogsActivity;
import shagold.wifwaf.UserProfileActivity;
import shagold.wifwaf.WalkProfileActivity;

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

    public static Intent getWalk(Activity a) {
        return getClass(a, WalkProfileActivity.class);
    }

    public static Intent getDogProfile(Activity a) {
        return getClass(a, DogProfileActivity.class);
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

    public static boolean isWalk(Activity a) {
        return a.getClass() == WalkProfileActivity.class;
    }

    public static boolean isDogProfile(Activity a) {
        return a.getClass() == DogProfileActivity.class;
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

    public static boolean isSameActivity(Activity a, Class c) {
        return a.getClass() == c;
    }

    public static boolean isNotSameActivity(Activity a, Class c) {
        return a.getClass() != c;
    }

    public static boolean isNotWalk(Activity a) {
        return a.getClass() != WalkProfileActivity.class;
    }

    public static boolean isNotDogProfile(Activity a) {
        return a.getClass() != DogProfileActivity.class;
    }
}
