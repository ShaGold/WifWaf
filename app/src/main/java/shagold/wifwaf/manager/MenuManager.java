package shagold.wifwaf.manager;

import android.app.Activity;

import shagold.wifwaf.R;

/**
 * Created by jimmy on 07/11/15.
 */
public class MenuManager {

    public static boolean emptyMenu(Activity a, int id) {
        switch (id) {
            case R.id.action_settings :
                return true;
            default:
                return false;
        }
    }

    public static boolean defaultMenu(Activity a, int id) {
        switch (id) {
            case R.id.action_settings :
                return true;
            case R.id.action_home :
                if(ActivityManager.isNotHome(a))
                    a.startActivity(ActivityManager.getHome(a));
                return true;
            case R.id.action_profile :
                if(ActivityManager.isNotUserProfile(a))
                    a.startActivity(ActivityManager.getUserProfile(a));
                return true;
            case R.id.action_dogs :
                if(ActivityManager.isNotUserDogs(a))
                    a.startActivity(ActivityManager.getUserDogs(a));
            default:
                return false;
        }
    }

}
