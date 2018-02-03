package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import synergy.eclectic.com.taxmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumptionBreakdownFragment extends Fragment {


    public ConsumptionBreakdownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consumption_breakdown, container, false);
        Double strtext =getArguments().getDouble("value");
        String show = strtext.toString();
        EditText editText = view.findViewById(R.id.consResult);
        editText.setText(show);
        return view;
    }

}
