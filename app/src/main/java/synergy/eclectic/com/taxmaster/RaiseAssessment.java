package synergy.eclectic.com.taxmaster;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class RaiseAssessment extends AppCompatActivity {

    private Toolbar toolbar;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "Income Tax";
    private static final String TAG_Other_Tax = "Other Tax";
    public static String CURRENT_TAG = TAG_HOME;
    private Handler mHandler;
    Typeface faceOfTax;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_assessment);

        mHandler = new Handler();
        faceOfTax = Typeface.createFromAsset(this.getAssets(), "fonts/quicksandbold.otf");
        textView = findViewById(R.id.typeOfTax);
        textView.setText(TAG_HOME);
        textView.setTypeface(faceOfTax);

        RadioButton income = findViewById(R.id.incomeRadio);
        income.setChecked(true);
        toolbar = findViewById(R.id.toolbar_back); // Attaching the layout to the toolbar object
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        Typeface face = Typeface.createFromAsset(this.getAssets(), "fonts/quicksandbold.otf");
        mTitle.setTypeface(face);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            loadHomeFragment();
        }

    }

    private void loadHomeFragment() {
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame2, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                return new IncomeFragment();
            case 1:
                // photos
                return new OtherFragment();

            default:
                return new IncomeFragment();
        }
    }

    public void onClickBack(View view) {
        if (view.getId() == R.id.backbutton){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.incomeRadio:
                if (checked)
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                    textView.setText(TAG_HOME);
                    textView.setTypeface(faceOfTax);
                    loadHomeFragment();
                    break;
            case R.id.otherRadio:
                if (checked)
                    navItemIndex = 1;
                    CURRENT_TAG = TAG_Other_Tax;
                    textView.setText(TAG_Other_Tax);
                    textView.setTypeface(faceOfTax);
                    loadHomeFragment();
                    break;
            //default:

        }
    }
}
