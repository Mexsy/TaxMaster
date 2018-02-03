package synergy.eclectic.com.taxmaster.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import synergy.eclectic.com.taxmaster.MainActivity;
import synergy.eclectic.com.taxmaster.R;


public class SaveDialog extends DialogFragment {


    private Context context;

    public SaveDialog() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_dialog, container, false);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getContext();
        //exsiting_members = mArgs.getInt("number");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setCancelable(false);
        builder.setView(inflater.inflate(R.layout.fragment_save_dialog, null))
                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Dialog f = (Dialog) dialog;
                        dialog.cancel();


                    }
                })
                .setNegativeButton(R.string.Later, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Intent intent = new Intent(context,MainActivity.class);
                        startActivity(intent);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
