package shagold.wifwaf.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;


import shagold.wifwaf.R;
import shagold.wifwaf.UseWalkActivity;
import shagold.wifwaf.WalkProfileActivity;
import shagold.wifwaf.dataBase.Walk;

public class WifWafWalkChangeFragment extends DialogFragment {

    private Walk walk;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.useWalk)
                .setMessage(R.string.careful_modified_walk)

                .setNegativeButton(R.string.continue_without_saving, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent result = new Intent(getContext(), UseWalkActivity.class);
                        result.putExtra("WALK", walk);
                        startActivity(result);
                    }
                })

                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    public void setWalk(Walk walk) {
        this.walk = walk;
    }
}
