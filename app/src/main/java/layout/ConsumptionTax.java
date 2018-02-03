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
public class ConsumptionTax extends Fragment implements AdapterView.OnItemSelectedListener{
    private View view;
    private Spinner spinner;
    private Context context;
    private static final String TAG_SIGNBOARD = "Signboard and Advertisement permit fees";
    private static final String TAG_CONSUMPTION_TAX = "Consumption Tax";
    private static final String TAG_CAPITAL_GAIN_TAX = "Capital Gains Tax";
    public static int navItemIndex = 0;
    private DataStorage db;
    private Handler mHandler;
    private Button button;
    private EditText editText;


    public ConsumptionTax() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mHandler = new Handler();
        view = inflater.inflate(R.layout.fragment_consumption_tax, container, false);
        context = getContext();
        editText = (EditText) view.findViewById(R.id.TotalEarnings);
        button = (Button) view.findViewById(R.id.calcConsumptionTax);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.calcConsumptionTax:
                        String value = editText.getText().toString();
                        double totalE = Double.parseDouble(value) * 0.05;
                        loadHomeFragment(totalE);
                        break;
            }}
        });
        spinner = (Spinner) view.findViewById(R.id.spinner_taxItemConsumption);
        spinner.setOnItemSelectedListener(this);
        String tag = OtherFragment.TAG_CURRENT;
        loadSpinnerData(tag);
        spinner.setSelection(6);
        return view;
    }



    private void loadHomeFragment(double totalE) {
        final Bundle bundle = new Bundle();
        bundle.putDouble("value", totalE);
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
                Fragment fragment = consumptionBreakdownFragment;
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame5, fragment, "Calculate");
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
