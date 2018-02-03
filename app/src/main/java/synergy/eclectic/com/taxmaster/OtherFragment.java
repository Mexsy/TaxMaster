package synergy.eclectic.com.taxmaster;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import layout.TaxItemFragment;
import synergy.eclectic.com.taxmaster.Adapters.NothingSelectedSpinnerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // Spinner element
    private Spinner spinner;
    private Handler mHandler;
    private Context otherFragContext;
    DataStorage db;
    ProgressDialog bar;
    public static final String TAG_WITHHOLDING_TAX = "Withholding Tax";
    public static final String TAG_OTHER_TAX = "Other Taxes";
    public static String TAG_CURRENT = TAG_OTHER_TAX;

    private static final String TAG_SIGNBOARD = "Signboard and Advertisement permit fees";


    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        otherFragContext = getContext();
        mHandler = new Handler();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        // Spinner element
        spinner = view.findViewById(R.id.spinner_taxType);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Loading spinner data from database
        loadSpinnerData();
        TextView textView = view.findViewById(R.id.pageInfo1);
        Typeface face= Typeface.createFromAsset(otherFragContext.getAssets(), "fonts/quicksandbold.otf");
        textView.setTypeface(face);
        return view;
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
        db = new DataStorage(otherFragContext);

        // Spinner Drop down elements
        List<String> lables = db.getAllTaxTypes();


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(otherFragContext,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter,
                        R.layout.tax_type_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        otherFragContext));
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
                TaxItemFragment taxItem = new TaxItemFragment();
                Fragment fragment = taxItem;
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame3, fragment, "Spinner");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String itemSelected = "";
        if(spinner != null && spinner.getSelectedItem() != null){
            itemSelected = spinner.getSelectedItem().toString();
        }

        if(itemSelected.equals(TAG_OTHER_TAX)){
            TAG_CURRENT = TAG_OTHER_TAX;

        }
        if(itemSelected.equals(TAG_WITHHOLDING_TAX)){
            TAG_CURRENT = TAG_WITHHOLDING_TAX;
        }
        loadHomeFragment();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
