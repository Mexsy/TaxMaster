package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import synergy.eclectic.com.taxmaster.DBinserter.Customer;
import synergy.eclectic.com.taxmaster.DBinserter.CustomerPersonal;
import synergy.eclectic.com.taxmaster.DataStorage;
import synergy.eclectic.com.taxmaster.Dialogs.DatePickerFragment;
import synergy.eclectic.com.taxmaster.Dialogs.SaveDialog;
import synergy.eclectic.com.taxmaster.R;
import synergy.eclectic.com.taxmaster.RegCustomer;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalDetails extends Fragment  {
    private DataStorage dataStorage;
    private View view;
    private Context context;
    private String date = "";
    String isComplete;
    public static EditText editText;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Spinner spinner;
    public boolean status;
    public static boolean completePersonal = false;
    private SharedPreferences sharedpreferences;
    Spinner spinner2;
    //private CheckBox checkBox;
    private Button save_button;
    private String gender;
    private String birthday;
    private boolean readyToSave = false;
    public static final String MyPREFERENCES = "Personal Details" ;
    public static final String PERPREFERENCES = "Continue" ;


    public PersonalDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        dataStorage = new DataStorage(context);
        view = inflater.inflate(R.layout.fragment_personal_details, container, false);





        Button button = view.findViewById(R.id.chooseDate);
        save_button = view.findViewById(R.id.saveButton);

        //checkBox = (CheckBox) view.findViewById(R.id.checkPersonal);
        editText = view.findViewById(R.id.dob);
        editText1 = view.findViewById(R.id.et_surname);
        editText2 = view.findViewById(R.id.et_firstname);
        editText3 = view.findViewById(R.id.otherName);
        editText4 = view.findViewById(R.id.maidName);

        spinner = view.findViewById(R.id.spinnerTitle);
        final RadioButton male = view.findViewById(R.id.male);
        final RadioButton female = view.findViewById(R.id.female);
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender = female.getText().toString();
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender = male.getText().toString();
            }
        });
        male.setChecked(true);
        spinner2 = view.findViewById(R.id.spinnerMarriage);

        //Handles continue registration.
        CustomerPersonal customerPersonal = contReg();
        if(!customerPersonal.getSurname().equals("null")){
            editText1.setText(customerPersonal.getSurname());
            editText2.setText(customerPersonal.getFirst());
            editText3.setText(customerPersonal.getOther());
            editText4.setText(customerPersonal.getMaid());
            editText.setText(customerPersonal.getBirth());
            if(customerPersonal.getSex().equals("Male")){
                male.setChecked(true);
            }
            else if(customerPersonal.getSex().equals("Female")){
                female.setChecked(true);
            }
            if(customerPersonal.getTitle().equals("Dr")){
                spinner.setSelection(1);
            }
            else if(customerPersonal.getTitle().equals("Mr")){
                spinner.setSelection(2);
            }
            else if(customerPersonal.getTitle().equals("Mrs")){
                spinner.setSelection(3);
            }
            else if(customerPersonal.getTitle().equals("Miss")){
                spinner.setSelection(4);
            }
            else if(customerPersonal.getTitle().equals("Chief")){
                spinner.setSelection(5);
            }

            if(customerPersonal.getMarital().equals("Married")){
                spinner2.setSelection(1);
            }
            else if(customerPersonal.getMarital().equals("Single")){
                spinner2.setSelection(2);
            }

        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = spinner.getSelectedItem().toString();
                birthday = editText.getText().toString();
                String surname = editText1.getText().toString();
                String firstName = editText2.getText().toString();
                String otherName = editText3.getText().toString();
                String maidName = editText4.getText().toString();
                String maritalStat = spinner2.getSelectedItem().toString();

                if(title.trim().isEmpty() || title.equals("-- title --")){
                    TextView errorText = (TextView)spinner .getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("select a title");//changes the selected item text to this
                    Snackbar.make(view, "Select a title", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                if(maritalStat.trim().isEmpty() || maritalStat.equals("-- select marital status --")){
                    TextView errorText = (TextView)spinner2 .getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("select status");//changes the selected item text to this
                    Snackbar.make(view, "Select status", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
                if(birthday.trim().isEmpty()){
                    editText.setError("Please use the button below to select a date of birth");
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
                if(surname.trim().isEmpty()){
                    editText1.setError("Surname is missing");
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
                if(firstName.trim().isEmpty()){
                    editText2.setError("First name is missing");
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
                if(otherName.trim().isEmpty()){
                    editText3.setError("Other given name is missing");
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
                if(maidName.trim().isEmpty()){
                    editText4.setError("Maiden name is missing");
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
                else {
                    sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Title", title);
                    editor.putString("birth", birthday);
                    editor.putString("Surname", surname);
                    editor.putString("first", firstName);
                    editor.putString("other", otherName);
                    editor.putString("maid", maidName);
                    editor.putString("marital", maritalStat);
                    editor.putString("sex", gender);
                    editor.putBoolean("personal", true);
                    editor.commit();

                    Customer customer = new Customer();
                    customer.setTitle(title);
                    customer.setBirth(birthday);
                    customer.setSurname(surname);
                    customer.setFirst(firstName);
                    customer.setOther(otherName);
                    customer.setMaid(maidName);
                    customer.setMarital(maritalStat);
                    customer.setSex(gender);
                    readyToSave = true;
                    status = RegCustomer.getStatus();
                    if (status){
                        isComplete = "complete";
                    }
                    else {
                        isComplete = "incomplete";
                    }
                    customer.setStatus(isComplete);
                    dataStorage.insertCustomerDetails(customer);
                }
                if(readyToSave){
                    DialogFragment newFragment = new SaveDialog();
                    newFragment.show(getChildFragmentManager(), "save");
                }
            }
        });

        return view;
    }

    //Handles continue
    private CustomerPersonal contReg(){
        CustomerPersonal customer;
        SharedPreferences editContinue = context.getSharedPreferences(PERPREFERENCES, Context.MODE_PRIVATE);
        String cont_surname = editContinue.getString("Surname","null");
        String cont_first_name = editContinue.getString("First_name","null");
        String cont_other_name = editContinue.getString("otherName","null");
        String cont_maid = editContinue.getString("Maiden","null");
        String cont_marital = editContinue.getString("Marriage","null");
        String cont_gender = editContinue.getString("sex","null");
        String cont_title = editContinue.getString("Title","null");
        String cont_date = editContinue.getString("DOB","null");

        Toast nullChecker = Toast.makeText(context, cont_surname + " " + cont_first_name, Toast.LENGTH_SHORT);
        nullChecker.show();
        customer = new CustomerPersonal(cont_surname,cont_first_name,cont_other_name,cont_maid,cont_marital,cont_gender,cont_title,cont_date);
        return customer;
    }



    public static void setDate(String date){
        editText.setText(date);
    }

}
