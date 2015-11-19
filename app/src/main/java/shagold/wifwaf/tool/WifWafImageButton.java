package shagold.wifwaf.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

/**
 * Created by jimmy on 19/11/15.
 */
public class WifWafImageButton extends ImageButton {

    private Drawable originalBackground;

    private Class activity;

    public WifWafImageButton(Context context, Class activity) {
        super(context);
        this.activity = activity;
    }

    public void initBackground() {
        originalBackground = getBackground();
    }

    public Drawable getOriginalBackground() {
        return originalBackground;
    }

    public Class getActivity() {
        return activity;
    }
}
