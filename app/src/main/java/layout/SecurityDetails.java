package layout;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import synergy.eclectic.com.taxmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecurityDetails extends Fragment {
    private CheckBox checkBox;
    private EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Context context;
    public static boolean completeSec = false;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Personal Details" ;


    public SecurityDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_security_details, container, false);
        context = getContext();
        checkBox = view.findViewById(R.id.checkSecurity);
        editText = view.findViewById(R.id.sign_password1);
        editText1 = view.findViewById(R.id.confirm_password1);
        editText2 = view.findViewById(R.id.security_question);
        editText3 = view.findViewById(R.id.answer1);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox.isChecked()){
                    String password = editText.getText().toString();
                    String password1 = editText1.getText().toString();
                    String security = editText2.getText().toString();
                    String answer = editText3.getText().toString();

                    if(password.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText.setError("Enter a password");
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

                    if(password1.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText1.setError("Enter password again");
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

                    else if (!password.equals(password1)) {
                        //creates pop up message
                        Toast passwordCheck = Toast.makeText(context, "passwords do not match", Toast.LENGTH_SHORT);
                        passwordCheck.show();
                    }

                    if(security.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText2.setError("Enter password again");
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

                    if(answer.trim().isEmpty()){
                        checkBox.setChecked(false);
                        editText3.setError("Enter password again");
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
                        editor.putString("pass", password);
                        editor.putString("question", security);
                        editor.putString("answer", answer);
                        editor.putBoolean("security", true);
                        editor.commit();
                    }
                }
            }
        });
        return view;
    }

}
