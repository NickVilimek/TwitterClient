package mobilecomputing.twitterclient;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

public class AddGroupFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Test commit

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Test")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Fill
                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();
    }
}
