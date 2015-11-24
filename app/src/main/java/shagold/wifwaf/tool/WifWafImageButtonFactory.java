package shagold.wifwaf.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

import shagold.wifwaf.AddDogActivity;
import shagold.wifwaf.HomeActivity;
import shagold.wifwaf.R;
import shagold.wifwaf.manager.ActivityManager;

/**
 * Created by jimmy on 19/11/15.
 */
public class WifWafImageButtonFactory {

    public static WifWafImageButton createImageButton(Context context, Class activity, int id, LayoutParams lp, Class[] otherActivity) {
        WifWafImageButton button = new WifWafImageButton(context, activity);
        button.setImageResource(id);
        button.setLayoutParams(lp);
        button.initBackground();
        if(ActivityManager.isNotSameActivity((Activity) context, button.getActivity()))
            button.setBackgroundColor(Color.TRANSPARENT);
        if(otherActivity != null)
            for(Class c : otherActivity)
                if(ActivityManager.isSameActivity((Activity) context, c))
                    button.setBackground(button.getOriginalBackground());
        button.setTag(View.NO_ID);
        button.setId(View.NO_ID);
        return button;
    }
}
