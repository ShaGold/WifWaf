package shagold.wifwaf.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.Socket;

import java.util.List;

import shagold.wifwaf.R;
import shagold.wifwaf.dataBase.User;
import shagold.wifwaf.dataBase.Walk;
import shagold.wifwaf.manager.SocketManager;

public class WalkAdapter extends ArrayAdapter<Walk> {

    private Context c;
    private Socket mSocket;
    private User mUser;

    public WalkAdapter(Context context, List<Walk> walks) {
        super(context, 0, walks);
        c = context;
        mSocket = SocketManager.getMySocket();
        mUser = SocketManager.getMyUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_walk,parent, false);

        WalkViewHolder viewHolder = (WalkViewHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new WalkViewHolder();
            viewHolder.setTitle((TextView) convertView.findViewById(R.id.titleRowWalk));
            viewHolder.setDescription((TextView) convertView.findViewById(R.id.descriptionRowWalk));
            viewHolder.setAvatar((ImageView) convertView.findViewById(R.id.avatarRowWalk));
            //viewHolder.setButton((ImageButton) convertView.findViewById(R.id.deleteWalkButton));
            viewHolder.setCity((TextView) convertView.findViewById(R.id.cityRowWalk));
            convertView.setTag(viewHolder);
        }

        final Walk walk = getItem(position);
        viewHolder.getTitle().setText(walk.getTitle());
        viewHolder.getDescription().setText(walk.getDescription());
        //TODO default value
        viewHolder.getAvatar().setImageResource(R.drawable.user);

        viewHolder.getCity().setText("City " + " : " + walk.getCity());

        return convertView;
    }
}
