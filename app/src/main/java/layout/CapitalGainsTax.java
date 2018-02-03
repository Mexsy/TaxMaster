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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import synergy.eclectic.com.taxmaster.Adapters.NothingSelectedSpinnerAdapter;
import synergy.eclectic.com.taxmaster.DataStorage;
import synergy.eclectic.com.taxmaster.OtherFragment;
import synergy.eclectic.com.taxmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapitalGainsTax extends Fragment implements AdapterView.OnItemSelectedListener{
    private View view;
    private Spinner spinner;
    private Context context;
    private DataStorage db;
    private Handler mHandler;
    private Button button;
    private EditText editTextSales;
    private EditText editTextAllowable;
    private EditText editTextCost;


    public CapitalGainsTax() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mHandler = new Handler();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_capital_gains_tax, container, false);
        context = getContext();
        editTextSales = view.findViewById(R.id.salesProceeds);
        editTextAllowable = view.findViewById(R.id.allowableExpenses);
        editTextCost = view.findViewById(R.id.costOfAcquisition);
        button = view.findViewById(R.id.calcCapitalGains);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.calcCapitalGains:
                        String sales = editTextSales.getText().toString();
                        String allowable = editTextAllowable.getText().toString();
                        String cost = editTextCost.getText().toString();
                        double salesFigure = Double.parseDouble(sales);
                        double allowableFigure = Double.parseDouble(allowable);
                        double costFigure = Double.parseDouble(cost);
                        double val = Algo(salesFigure,allowableFigure,costFigure);
                        loadHomeFragment(val);
                        break;
                }}
        });
        spinner = view.findViewById(R.id.spinner_taxItemCapital);
        spinner.setOnItemSelectedListener(this);
        String tag = OtherFragment.TAG_CURRENT;
        loadSpinnerData(tag);
        spinner.setSelection(3);
        return view;
    }

    private double Algo(double sales, double allowable, double cost){
        double tenPercent = 0.10;

        double resultOfAddition = allowable + cost;
        double resultOfSub = sales - resultOfAddition;
        double valueToPass = resultOfSub * tenPercent;

        return valueToPass;
    }

    private void loadHomeFragment(double val) {
        final Bundle bundle = new Bundle();
        bundle.putDouble("value", val);
        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                ConsumptionBreakdownFragment consumptionBreakdownFragment = new ConsumptionBreakdownFragment();
                consumptionBreakdownFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame6, consumptionBreakdownFragment, "Calculate Capital");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

    }

    private void loadSpinnerData(String tag) {
        // database handler
        db = new DataStorage(context);

        // Spinner Drop down elements
        List<String> lables = db.searchTax(tag);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, lables);
        //spinner.setSelection(0);
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(dataAdapter, R.layout.contact_spinner_row_nothing_selected,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                context));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
