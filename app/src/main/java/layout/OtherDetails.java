package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import synergy.eclectic.com.taxmaster.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OtherDetails extends Fragment {
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    public static boolean completeOther = false;
    private Context context;
    private Spinner spinner;
    private CheckBox checkBox;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Personal Details" ;


    public OtherDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_details, container, false);
        context = getContext();
        editText = view.findViewById(R.id.bvn);
        editText1 = view.findViewById(R.id.occ);
        editText2 = view.findViewById(R.id.prof);
        editText3 = view.findViewById(R.id.tin);
        editText4 = view.findViewById(R.id.nin);
        spinner = view.findViewById(R.id.spinner_pto);
        checkBox = view.findViewById(R.id.checkFurther);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()){
                    String tax_office = spinner.getSelectedItem().toString();
                    String BVN = editText.getText().toString();
                    String occupation = editText1.getText().toString();
                    String pro = editText2.getText().toString();
                    String TIN = editText3.getText().toString();
                    String NIN = editText4.getText().toString();

                    if(tax_office.trim().isEmpty() || tax_office.equals("-- select preferred office --")){
                        checkBox.setChecked(false);
                        TextView errorText = (TextView)spinner .getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("select a tax office");//changes the selected item text to this
                    }
                    if(BVN.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText.setError("Enter bank verification number");
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editText.setError(null);
                            }
                        });
                    }
                    if(occupation.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText1.setError("Enter an occupation");
                        editText1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editText1.setError(null);
                            }
                        });
                    }
                    if(pro.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText2.setError("Enter bank verification number");
                        editText2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editText2.setError(null);
                            }
                        });
                    }
                    if(TIN.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText3.setError("Enter TIN");
                        editText3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editText3.setError(null);
                            }
                        });
                    }
                    if(NIN.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText4.setError("Enter nNational identification number");
                        editText4.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editText4.setError(null);
                            }
                        });
                    }

                    else{
                        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("office", tax_office);
                        editor.putString("bvn", BVN);
                        editor.putString("occup", occupation);
                        editor.putString("tin", TIN);
                        editor.putString("nin", NIN);
                        editor.putBoolean("other", true);
                        editor.commit();
                    }
                }
            }
        });
        return view;
    }

}
