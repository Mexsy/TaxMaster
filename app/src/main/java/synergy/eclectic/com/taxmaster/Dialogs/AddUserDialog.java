package synergy.eclectic.com.taxmaster.Dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import synergy.eclectic.com.taxmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserDialog extends DialogFragment {
    private Context t_context;
    private int exsiting_members;
    private TextView tv1;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;
    private Handler mResponseHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        Activity a;
        if (context instanceof Activity){
            a = (Activity) context;

            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (NoticeDialogListener) a;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(a.toString()
                        + " must implement NoticeDialogListener");
            }
        }

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
        builder.setView(inflater.inflate(R.layout.fragment_add_user_dialog, null))
                .setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Dialog f = (Dialog) dialog;
                        tv1=(TextView)f.findViewById(R.id.addmember);
                        Typeface face= Typeface.createFromAsset(t_context.getAssets(), "fonts/quicksandbold.otf");
                        tv1.setTypeface(face);
                        EditText inputTemp = (EditText) f.findViewById(R.id.username);
                        String emailstr = inputTemp.getText().toString();
                        System.out.println(exsiting_members);
                        boolean correct = isValidEmail(emailstr);

                        if(correct){
                            // Send the positive button event back to the host activity
                            mListener.onDialogPositiveClick(AddUserDialog.this);
                        }
                        else{
                            Toast nullChecker = Toast.makeText(t_context,"Please enter a valid email address!!",Toast.LENGTH_LONG);
                            nullChecker.show();
                        }

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

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


}
