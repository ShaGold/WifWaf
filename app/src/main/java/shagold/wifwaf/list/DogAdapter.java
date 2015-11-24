package shagold.wifwaf.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shagold.wifwaf.R;
import shagold.wifwaf.dataBase.Dog;

/**
 * Created by jimmy on 22/11/15.
 */
public class DogAdapter extends ArrayAdapter<Dog> {

    public DogAdapter(Context context, List<Dog> dogs) {
        super(context, 0, dogs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_dog,parent, false);

        DogViewHolder viewHolder = (DogViewHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new DogViewHolder();
            viewHolder.setName((TextView) convertView.findViewById(R.id.nameRowDog));
            viewHolder.setDescription((TextView) convertView.findViewById(R.id.descriptionRowDog));
            viewHolder.setAvatar((ImageView) convertView.findViewById(R.id.avatarRowDog));
            convertView.setTag(viewHolder);
        }

        Dog dog = getItem(position);
        viewHolder.getName().setText(dog.getName());
        viewHolder.getDescription().setText(dog.getDescription());
        viewHolder.getAvatar().setImageResource(viewHolder.getDefaultAvatar());

        return convertView;
    }

}
