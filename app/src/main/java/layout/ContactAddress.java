package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
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
public class ContactAddress extends Fragment {

    public static boolean completeAddress = false;
    private CheckBox checkBox;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Spinner spinner;
    private Spinner spinner1;
    private Spinner spinner2;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Personal Details" ;
    private Context context;

    public ContactAddress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_address, container, false);
        context = getContext();
        checkBox = view.findViewById(R.id.checkAddress);
        editText = view.findViewById(R.id.conemail);
        editText1 = view.findViewById(R.id.phone);
        editText2 = view.findViewById(R.id.conAddress);
        editText3 = view.findViewById(R.id.city);
        spinner = view.findViewById(R.id.spinner_residence);
        spinner1 = view.findViewById(R.id.spinner_origin);
        spinner2 = view.findViewById(R.id.spinner_nation);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()){
                    String residence = spinner.getSelectedItem().toString();
                    String origin = spinner1.getSelectedItem().toString();
                    String nation = spinner2.getSelectedItem().toString();
                    String email = editText.getText().toString();
                    String phone = editText1.getText().toString();
                    String address = editText2.getText().toString();
                    String city = editText3.getText().toString();

                    if (residence.trim().isEmpty() || residence.equals("-- select a state --")){
                        checkBox.setChecked(false);
                        TextView errorText = (TextView)spinner.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("select a state");//changes the selected item text to this
                    }
                    if (origin.trim().isEmpty() || origin.equals("-- select a state --")){
                        checkBox.setChecked(false);
                        TextView errorText = (TextView)spinner1.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("select a state");//changes the selected item text to this
                    }
                    if (nation.trim().isEmpty() || nation.equals("-- select a country --")){
                        checkBox.setChecked(false);
                        TextView errorText = (TextView)spinner2.getSelectedView();
                        errorText.setError("");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("select a state");//changes the selected item text to this
                    }

                    boolean correct = isValidEmail(email);
                    if(!correct){
                        checkBox.setChecked(false);
                        editText.setError("Enter a valid email address");
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

                    if (phone.length() != 11){
                        checkBox.setChecked(false);
                        editText1.setError("Phone number must have seven digits");
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
                    if(address.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText2.setError("Enter home address");
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
                    if(city.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText3.setError("Enter name of city");
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
                    else {
                        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("residence", residence);
                        editor.putString("origin", origin);
                        editor.putString("nation", nation);
                        editor.putString("email", email);
                        editor.putString("number", phone);
                        editor.putString("address_line", address.trim());
                        editor.putString("city", city);
                        editor.putBoolean("address", true);
                        editor.commit();
                    }
                }
            }
        });
        return view;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
