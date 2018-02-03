package synergy.eclectic.com.taxmaster.Dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import synergy.eclectic.com.taxmaster.Login;
import synergy.eclectic.com.taxmaster.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogOutDialog extends DialogFragment {
    private Context t_context;
    private TextView tv1;


    public LogOutDialog() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle mArgs = getArguments();

        //exsiting_members = mArgs.getInt("number");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        t_context = getContext();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setCancelable(false);
        builder.setView(inflater.inflate(R.layout.fragment_logout, null))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Dialog f = (Dialog) dialog;
                        Intent i = new Intent(t_context, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity (i);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
