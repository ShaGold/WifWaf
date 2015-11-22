package shagold.wifwaf.list;

import android.widget.ImageView;
import android.widget.TextView;

import shagold.wifwaf.R;

/**
 * Created by jimmy on 22/11/15.
 */
public class DogViewHolder {

    private TextView name;
    private TextView description;
    private ImageView avatar;

    public DogViewHolder() {
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public int getDefaultAvatar() {
        return R.drawable.dogi2;
    }
}
