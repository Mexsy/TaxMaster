package layout;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.List;

import synergy.eclectic.com.taxmaster.Adapters.CustomerAdapter;
import synergy.eclectic.com.taxmaster.DBinserter.CustomerPersonal;
import synergy.eclectic.com.taxmaster.DataStorage;
import synergy.eclectic.com.taxmaster.R;
import synergy.eclectic.com.taxmaster.RegCustomer;
import synergy.eclectic.com.taxmaster.Results;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {
    private CustomerAdapter customerAdapter;
    private ArrayList<Results> Actlist = new ArrayList<>();
    private DataStorage storage;
    private Context context;
    private SharedPreferences sharedpreferences;
    public static final String PERPREFERENCES = "Continue" ;

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        storage = new DataStorage(context);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        // Find ListView to populate
        final ListView lvItems = view.findViewById(R.id.listViewActivity);
        final List<Results> l = storage.retreiveSaved();
        ArrayList<Results> tmp = new ArrayList<>(l);
        Actlist = tmp;

        customerAdapter = new CustomerAdapter(context, Actlist);
        lvItems.setAdapter(customerAdapter);
        lvItems.setItemsCanFocus(true);
        lvItems.setEmptyView(view.findViewById( R.id.empty_list_view_activity));
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "ID is : " + position, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                CustomerPersonal customerPersonal = storage.getFilledDetails(position);
                String surname = customerPersonal.getSurname();
                String firstname = customerPersonal.getFirst();
                String other_name = customerPersonal.getOther();
                String maiden = customerPersonal.getMaid();
                String marital = customerPersonal.getMarital();
                String gender = customerPersonal.getSex();
                String title = customerPersonal.getTitle();
                String dateOf = customerPersonal.getBirth();

                sharedpreferences = context.getSharedPreferences(PERPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editContinue = sharedpreferences.edit();
                editContinue.putString("Surname",surname);
                editContinue.putString("First_name",firstname);
                editContinue.putString("otherName",other_name);
                editContinue.putString("Maiden",maiden);
                editContinue.putString("Marriage",marital);
                editContinue.putString("sex",gender);
                editContinue.putString("Title",title);
                editContinue.putString("DOB",dateOf);
                editContinue.apply();

                Intent i = new Intent(context, RegCustomer.class);
                startActivity(i);
            }
        });
        return view;
    }


}
