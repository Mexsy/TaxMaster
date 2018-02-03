package synergy.eclectic.com.taxmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DashBoard extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context t_context;
    private String office;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "TaxOffice" ;
    private TextView taxOffice;
    private Handler mHandler;


    public DashBoard() {
        // Required empty public constructor
    }

    public static DashBoard newInstance(String param1, String param2) {
        DashBoard fragment = new DashBoard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        t_context = getContext();
        mHandler = new Handler();

        sharedpreferences = t_context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        office = sharedpreferences.getString("officeToShow","");


        taxOffice = (TextView)view.findViewById(R.id.taxOffice);
        Typeface face= Typeface.createFromAsset(t_context.getAssets(), "fonts/quicksandbold.otf");
        taxOffice.setTypeface(face);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAssessments);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(t_context,RaiseAssessment.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fabSync = (FloatingActionButton) view.findViewById(R.id.fabSync);
        fabSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your changes are being synced online", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FloatingActionButton fabHis = (FloatingActionButton) view.findViewById(R.id.fabHistory);
        fabHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No tax history available", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (!office.isEmpty()){
            taxOffice.setText(office);
        }
        else {
            taxOffice.setText("Oh boi office no de");
        }
        System.out.println("from dashboard "+ office);


        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = new TranActivityTabs();
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frameTransAct, fragment, "To Show");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        return view;
    }

}