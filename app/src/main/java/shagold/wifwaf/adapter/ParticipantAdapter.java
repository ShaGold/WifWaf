package shagold.wifwaf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.Socket;

import java.util.List;

import shagold.wifwaf.R;
import shagold.wifwaf.dataBase.Participant;
import shagold.wifwaf.manager.SocketManager;

public class ParticipantAdapter extends ArrayAdapter<Participant> {

    private Context c;
    private Socket mSocket;

    public ParticipantAdapter(Context context, List<Participant> participantList) {
        super(context, 0, participantList);
        c = context;
        mSocket = SocketManager.getMySocket();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_participant, parent, false);

        ParticipantViewHolder viewHolder = (ParticipantViewHolder) convertView.getTag();

        if(viewHolder == null){
            viewHolder = new ParticipantViewHolder();
            viewHolder.setDogName((TextView) convertView.findViewById(R.id.nameRowUser));
            viewHolder.setUserName((TextView) convertView.findViewById(R.id.nameRowDog));
            convertView.setTag(viewHolder);
        }

        final Participant participant = getItem(position);
        viewHolder.getDogName().setText(Participant.getUserName());
        viewHolder.getUserName().setText(dog.getPhotoBitmap());

        viewHolder.getButton().setFocusable(false);
        viewHolder.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(c);
                dialog.setTitle("Delete Dog");
                dialog.setMessage("Are you sure to delete the dog : \n\n\t" + dog.getName());
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = dog.getIdDog();
                        mSocket.emit("deleteDog", id);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDeleteDogs = dialog.create();
                alertDeleteDogs.show();
            }
        });
        return convertView;
    }

}
