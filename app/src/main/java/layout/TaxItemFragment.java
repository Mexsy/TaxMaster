package layout;


import android.content.Context;
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

import java.util.List;

import synergy.eclectic.com.taxmaster.Adapters.NothingSelectedSpinnerAdapter;
import synergy.eclectic.com.taxmaster.DataStorage;
import synergy.eclectic.com.taxmaster.OtherFragment;
import synergy.eclectic.com.taxmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaxItemFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private Spinner spinner1;
    private View view;
    private static final String TAG_SIGNBOARD = "Signboard and Advertisement permit fees";
    private static final String TAG_CONSUMPTION_TAX = "Consumption Tax";
    private static final String TAG_CAPITAL_GAIN_TAX = "Capital Gains Tax";
    public static int navItemIndex = 0;
    DataStorage db;
    private Handler mHandler;
    Context TaxItemFragContext;



    public TaxItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mHandler = new Handler();
        view = inflater.inflate(R.layout.fragment_tax_item, container, false);
        TaxItemFragContext = getContext();
        spinner1 = (Spinner) view.findViewById(R.id.spinner_taxItem);
        spinner1.setOnItemSelectedListener(this);
        String tag = OtherFragment.TAG_CURRENT;
        loadSpinnerData(tag);
        return view;
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData(String tag) {
        // database handler
        db = new DataStorage(TaxItemFragContext);

        // Spinner Drop down elements
        List<String> lables = db.searchTax(tag);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(TaxItemFragContext,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        dataAdapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        TaxItemFragContext));
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
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame4, fragment, "To Show");
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
                SignBoardFragment signBoardFragment = new SignBoardFragment();
                return signBoardFragment;
            case 1:
                return new ConsumptionTax();
            case 2:
                return new CapitalGainsTax();

            default:
                return new SignBoardFragment();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String seconditemSelected = "";
        if(spinner1 != null && spinner1.getSelectedItem() != null){
            seconditemSelected = spinner1.getSelectedItem().toString();
        }
        if(seconditemSelected.equals(TAG_SIGNBOARD)){
            navItemIndex = 0;
            loadHomeFragment();
            System.out.println(TAG_SIGNBOARD);
        }
        if(seconditemSelected.equals(TAG_CONSUMPTION_TAX)){
            navItemIndex = 1;
            loadHomeFragment();
            System.out.println(TAG_CONSUMPTION_TAX);
        }
        if(seconditemSelected.equals(TAG_CAPITAL_GAIN_TAX)){
            navItemIndex = 2;
            loadHomeFragment();
            System.out.println(TAG_CAPITAL_GAIN_TAX);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
