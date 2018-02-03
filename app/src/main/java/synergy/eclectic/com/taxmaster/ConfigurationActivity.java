package synergy.eclectic.com.taxmaster;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import synergy.eclectic.com.taxmaster.Dialogs.WarningDialog;

public class ConfigurationActivity extends AppCompatActivity  {
    public static final String MyPREFERENCES = "Warning" ;
    private boolean warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        SharedPreferences settings = getSharedPreferences(MyPREFERENCES,  Context.MODE_PRIVATE);
        warn = settings.getBoolean("warn", false);

        if(warn){
            DialogFragment newFragment = new WarningDialog();
            // newFragment.setArguments(args);
            newFragment.show(getFragmentManager(), "add_a_member");
            Toast confirm = Toast.makeText(ConfigurationActivity.this, "You have been warned!!!", Toast.LENGTH_SHORT);
            confirm.show();
        }
        else {

        }
    }

    public void onImageClick(View v){
        if (v.getId() == R.id.single_user){
            System.out.println("Single");
            Intent i = new Intent(this, ConfigIndividual.class);
            startActivity (i);
        }
        if (v.getId() == R.id.multi_user){
            System.out.println("Multi");
            Intent i = new Intent(this, ConfigureTeam.class);
            startActivity (i);
        }
    }
}
