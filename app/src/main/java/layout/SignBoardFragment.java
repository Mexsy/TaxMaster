package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class SignBoardFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private View view;
    private Spinner spinner;
    private Context context;
    private DataStorage db;
    private String sb = "Signboard and Advertisement permit fees";
    private int sign_board;


    public SignBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_board, container, false);
        context = getContext();
        spinner = (Spinner) view.findViewById(R.id.spinner_taxItemSignBoard);
        spinner.setOnItemSelectedListener(this);
        String tag = OtherFragment.TAG_CURRENT;
        loadSpinnerData(tag);
        spinner.setSelection(1);
        return view;
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
        //repeat same method from tax item fragment
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
