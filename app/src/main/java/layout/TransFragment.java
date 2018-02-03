package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import synergy.eclectic.com.taxmaster.DataStorage;
import synergy.eclectic.com.taxmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransFragment extends Fragment {


    private Context context;

    public TransFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        DataStorage storage = new DataStorage(context);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trans, container, false);
        // Find ListView to populate
        final ListView lvItems = view.findViewById(R.id.listView);

        final List<String> l = storage.getAllInvoices();

        ArrayAdapter myAdapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_list_item_1, l);
        myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        lvItems.setAdapter(myAdapter);
        lvItems.setEmptyView(view.findViewById( R.id.empty_list_view1 ));
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }

}
