package synergy.eclectic.com.taxmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import synergy.eclectic.com.taxmaster.Adapters.CustomerMultipleUser;
import synergy.eclectic.com.taxmaster.DBinserter.ContactDetails;
import synergy.eclectic.com.taxmaster.DBinserter.SingleUser;
import synergy.eclectic.com.taxmaster.Dialogs.AddUserDialog;

public class ConfigureTeam extends FragmentActivity implements AddUserDialog.NoticeDialogListener {
    String member1 = "", member2 = "", member3 = "", member4 = "", member5 = "", member6 = "", member7 = "", member8 = "", member9 = "", member10 = "";
    //String[] values = new String[] { "Android", "iPhone", "WindowsMobile","Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X","Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux","OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2","Android", "iPhone", "WindowsMobile" };
    private boolean loggin_in = false;
    private ArrayList<String> Teamlist = new ArrayList<>();
    RetrieveMultiUsersTask rtask;
    DataStorage storage = new DataStorage(this);
    CustomerMultipleUser customerMultipleUser;
    ProgressDialog bar;
    private String PASSWORD = "Stepup333";
    Context context;
    public static Handler UIHandler;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_team);
        context = getBaseContext();

        tv1=(TextView)findViewById(R.id.createTeam);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.otf");
        tv1.setTypeface(face);

        bar = new ProgressDialog(this);//creates a new progress dialog.
        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//sets what direction the bar runs in.
        bar.setMessage("Verifying Account");//shows a message.

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int NUM = numberOFMEMBERS();
                if (NUM == 10){
                    Toast nullChecker = Toast.makeText(ConfigureTeam.this,"You have already reached the max number of members", Toast.LENGTH_LONG);
                    nullChecker.show();
                }
                else{
                    DialogFragment newFragment = new AddUserDialog();
                    // Bundle args = new Bundle();
                    //args.putInt("number", NUM);
                     // newFragment.setArguments(args);
                    newFragment.show(getSupportFragmentManager(), "add_a_member");
                }
            }
        });


        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            Teamlist = savedInstanceState.getStringArrayList("arraylist");
        } else {
            // Do nothing
        }

        // Find ListView to populate
        final ListView lvItems = (ListView)findViewById(R.id.listViewTeam);

        customerMultipleUser = new CustomerMultipleUser(this, Teamlist);
        lvItems.setAdapter(customerMultipleUser);
        lvItems.setEmptyView(findViewById( R.id.empty_list_view2));
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putStringArrayList("arraylist",Teamlist);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void verify_credentials(ArrayList<String> tlist){
        //checks if the parameters recieved are empty and handles it
        if((!tlist.isEmpty())) {
            System.out.println("Not Empty");
            loggin_in = rtask.doLogs(tlist);
            if (loggin_in = true){

            }
        }
        else {
            loggin_in = rtask.doLogs(tlist);
        }
    }

    public int numberOFMEMBERS(){
        return Teamlist.size();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
           EditText a = (EditText) dialog.getDialog().findViewById(R.id.username);
           String str = a.getText().toString();
           System.out.println(str);

            if(member1 == null || member1.isEmpty() || member1.equals("")){
                member1 = str;
                if(member1 != null && !member1.isEmpty()){
                    System.out.println("Before" +Teamlist);
                    Teamlist.add(member1);
                    customerMultipleUser.notifyDataSetChanged();
                    System.out.println("After" + Teamlist);
                }
            }
            else if(member2 == null || member2.isEmpty() || member2.equals("")){
                member2 = str;
                if(member2 != null && !member2.isEmpty()){
                    System.out.println("Before" +Teamlist);
                    Teamlist.add(member2);
                    customerMultipleUser.notifyDataSetChanged();
                    System.out.println("After" + Teamlist);
                }
            }
            else if(member3 == null || member3.isEmpty() || member3.equals("")) {
                member3 = str;
                if (member3 != null && !member3.isEmpty()) {
                    System.out.println("Before" + Teamlist);
                    Teamlist.add(member3);
                    customerMultipleUser.notifyDataSetChanged();
                    System.out.println("After" + Teamlist);
                }
            }
            else if(member4 == null || member4.isEmpty() || member4.equals("")){
                    member4 = str;
                    if(member4 != null && !member4.isEmpty()){
                        System.out.println("Before" +Teamlist);
                        Teamlist.add(member4);
                        customerMultipleUser.notifyDataSetChanged();
                        System.out.println("After" + Teamlist);
            }
        }
            else if(member5 == null || member5.isEmpty() || member5.equals("")){
                    member5 = str;
                    if(member5 != null && !member5.isEmpty()){
                        System.out.println("Before" +Teamlist);
                        Teamlist.add(member5);
                        customerMultipleUser.notifyDataSetChanged();
                        System.out.println("After" + Teamlist);
                    }
        }
            else if(member6 == null || member6.isEmpty() || member6.equals("")){
                member6 = str;
                if(member6 != null && !member6.isEmpty()){
                    System.out.println("Before" +Teamlist);
                    Teamlist.add(member6);
                    customerMultipleUser.notifyDataSetChanged();
                    System.out.println("After" + Teamlist);
                }
        }
            else if(member7 == null || member7.isEmpty() || member7.equals("")){
                member7 = str;
                if(member7 != null && !member7.isEmpty()){
                System.out.println("Before" +Teamlist);
                Teamlist.add(member7);
                    customerMultipleUser.notifyDataSetChanged();
                System.out.println("After" + Teamlist);
                }
        }
            else if(member8 == null || member8.isEmpty() || member8.equals("")){
            member8 = str;
                 if(member8 != null && !member8.isEmpty()){
                    System.out.println("Before" +Teamlist);
                     Teamlist.add(member8);
                     customerMultipleUser.notifyDataSetChanged();
                    System.out.println("After" + Teamlist);
                 }
        }
            else if(member9 == null || member9.isEmpty() || member9.equals("")){
                member9 = str;
            if(member9 != null && !member9.isEmpty()){
                System.out.println("Before" +Teamlist);
                Teamlist.add(member9);
                customerMultipleUser.notifyDataSetChanged();
                System.out.println("After" + Teamlist);
            }
        }
            else if(member10 == null || member10.isEmpty() || member10.equals("")){
                member10 = str;
                if(member10 != null && !member10.isEmpty()){
                    Teamlist.add(member10);
                    customerMultipleUser.notifyDataSetChanged();
                }
        }
        else {
                System.out.println("Member is not empty" + member1);
            }
    }


        @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void onButtonClick(View v) {
        //bar.show();//shows the progress bar.
        if (v.getId() == R.id.reg_team) {
            rtask = new RetrieveMultiUsersTask(Teamlist);
            verify_credentials(Teamlist);
        }
    }

    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    class RetrieveMultiUsersTask extends Thread {
        //private final String username_toCheck;
        private final ArrayList<String> teamlist;
        private RetrieveMultiUsersTask instance;
        String baseURL = "http://166.78.174.228/lirs/";
        String get_multi_user = baseURL +  "mobile/GetMultipleUsers";
        private int fail_response_code = 200; // for wrong username or password.
        private int success_response_code = 100; // for correct username and password.
        private String response_code = "ResponseCode";
        private String response_body = "ResponseBody";

        public boolean doLogs(ArrayList<String> tlist){
            if(instance != null)
                if(instance.isAlive())
                    return true;

            instance = new RetrieveMultiUsersTask(tlist);
            instance.start();
            return true;
        }

        public RetrieveMultiUsersTask(ArrayList<String> tlist){
            this.teamlist = tlist;
        }

        private void getMutliUser(ArrayList<String> usernames) throws IOException, JSONException {
            String name1 = "",name2= "",name3 = "",name4 = "",name5 = "",name6 = "",name7 = "",name8 = "",name9 = "",name10 = "";


          /*  for(int i = 0; i < usernames.size(); i++){
                name1
            }*/

            //To be handled with for loop
            if(usernames.size() == 1){
                name1 = usernames.get(0);
            }
            if(usernames.size() == 2){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
            }
            if(usernames.size() == 3){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
            }
            if(usernames.size() == 4){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
            }
            if(usernames.size() == 5){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
                name5 = usernames.get(4);
            }
            if(usernames.size() == 6){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
                name5 = usernames.get(4);
                name6 = usernames.get(5);
            }
            if(usernames.size() == 7){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
                name5 = usernames.get(4);
                name6 = usernames.get(5);
                name7 = usernames.get(6);
            }
            if(usernames.size() == 8){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
                name5 = usernames.get(4);
                name6 = usernames.get(5);
                name7 = usernames.get(6);
                name8 = usernames.get(7);
            }
            if(usernames.size() == 9){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
                name5 = usernames.get(4);
                name6 = usernames.get(5);
                name7 = usernames.get(6);
                name8 = usernames.get(7);
                name9 = usernames.get(8);
            }
            else if(usernames.size() == 10){
                name1 = usernames.get(0);
                name2 = usernames.get(1);
                name3 = usernames.get(2);
                name4 = usernames.get(3);
                name5 = usernames.get(4);
                name6 = usernames.get(5);
                name7 = usernames.get(6);
                name8 = usernames.get(7);
                name9 = usernames.get(8);
                name10 = usernames.get(9);
            }
            String namesToCheck  = "";

            if (!name1.isEmpty()){
                namesToCheck = name1;
            }
            if (!name2.isEmpty()){
                namesToCheck = name1 + "," +name2;
            }
            if (!name3.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3;
            }
            if (!name4.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4;
            }
            if (!name5.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4+ "," +name5;
            }
            if (!name6.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4+ "," +name5 + "," + name6;
            }
            if (!name7.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4+ "," +name5 + "," + name6+ "," + name7;
            }
            if (!name8.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4+ "," +name5 + "," + name6+ "," + name7 + "," + name8;
            }
            if (!name9.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4+ "," +name5 + "," + name6+ "," + name7 + "," + name8 + "," + name9;
            }
            if (!name10.isEmpty()){
                namesToCheck = name1 + "," +name2 + "," +name3 + "," +name4+ "," +name5 + "," + name6+ "," + name7 + "," + name8 + "," + name9 + "," + name10;
            }

            System.out.println("header: " + namesToCheck);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(get_multi_user)
                    .get()
                    .addHeader("usernames", namesToCheck)
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String jsonData = response.body().string();
                JSONObject j_object = new JSONObject(jsonData);
                JSONArray jsonArray = j_object.getJSONArray(response_body);
                System.out.println(jsonData);
                j_object.getString(response_code);
                int r_code = j_object.getInt(response_code);

                if(jsonArray!=null && jsonArray.length()>0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String myJsonData = jsonArray.get(i).toString();
                        JSONObject JSONUser = new JSONObject(myJsonData);
                        int user_id = JSONUser.getInt("APP_USERID");
                        String s_name = JSONUser.getString("SURNAME");
                        String f_name = JSONUser.getString("FIRSTNAME");
                        String o_name = JSONUser.getString("OTHERNAME");
                        String dob = JSONUser.getString("DATEOFBIRTH");
                        String email = JSONUser.getString("EMAILADDRESS");
                        String u_name = JSONUser.getString("USERNAME");
                        String pass = JSONUser.getString("PASSWORD");
                        int org_id = JSONUser.getInt("ORGANISATIONID");
                        int is_glo_d = JSONUser.getInt("IS_GLOBALADMIN");
                        int is_orga = JSONUser.getInt("IS_ORGADMIN");
                        int is_tax = JSONUser.getInt("IS_TAXOFFICER");
                        int man_to = JSONUser.getInt("MANAGES_TAXOFFICE");
                        String act = JSONUser.getString("ACTIVE");
                        int u_type = JSONUser.getInt("UserType");
                        String temp_dob = JSONUser.getString("tempDOB");
                        String taxpayer_id = JSONUser.getString("TAXPAYERID");
                        String corp_id = JSONUser.getString("CORPORATEID");
                        String user_man_tax_office = JSONUser.getString("UserManagesTaxOffice");
                        String userROLE = JSONUser.getString("USERROLE");
                        String CORP = JSONUser.getString("CORPORATEs");
                        String coma = JSONUser.getString("comaSeparatedRoles");
                        String taxPayers = JSONUser.getString("TAXPAYERs");
                        String UserRoles = JSONUser.getString("userRoles");

                        //create a new single user with details recieved from api.
                        int existingUserID = storage.searchAPP_ID(user_id);

                        /*if (existingUserID == user_id){
                            ConfigureTeam.runOnUI(new Runnable() {
                                public void run() {
                                    Toast very = Toast.makeText(ConfigureTeam.this, "Account Verified!!!", Toast.LENGTH_SHORT);
                                    very.show();
                                }
                            });
                        }*/

                        if (existingUserID != user_id) {
                            //create a new single user with details recieved from api.
                            SingleUser SU = new SingleUser();
                            SU.setUserID(user_id);
                            SU.setActive(act);
                            SU.setSurname(s_name);
                            SU.setFirstname(f_name);
                            SU.setOthername(o_name);
                            SU.setDateofbirth(dob);
                            SU.setEmailaddress(email);
                            SU.setUsername(u_name);
                            SU.setPassword(pass);
                            SU.setOrganisationID(org_id);
                            SU.setIs_globaladmin(is_glo_d);
                            SU.setIs_organadmiin(is_orga);
                            SU.setIs_taxofficer(is_tax);
                            SU.setManages_tax_office(man_to);
                            SU.setUserType(u_type);
                            SU.setTemp(temp_dob);
                            SU.setTax_payer_ID(taxpayer_id);
                            SU.setCorpID(corp_id);
                            SU.setUserManagesTO(user_man_tax_office);
                            SU.setCsr(coma);
                            SU.setUser_role(userROLE);
                            SU.setCoporates(CORP);
                            SU.setTaxpayers(taxPayers);
                            SU.setUserroles(UserRoles);
                            storage.insertSingleUser(SU);

                            int max = 1000000000;
                            int min = 10000;
                            Random rand = new Random();
                            int value = rand.nextInt((max - min) + 1) + min;

                            Toast nameCheck = Toast.makeText(ConfigureTeam.this, String.valueOf(value), Toast.LENGTH_LONG);
                            nameCheck.show();

                            try {
                                GMailSender sender = new GMailSender("echukumah@softalliance.com", PASSWORD);
                                sender.sendMail("Hi there, welcome to softTax mobile",
                                        "Your pass code for login is: " + value + " " + "Please remember to change your passcode after login, under the change passcode in the navigation menu",
                                        "echukumah@softalliance.com",
                                        "emexsy@gmail.com");
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }

                            //encrypt passcode here
                            byte[] passcode = encryptPasscode(String.valueOf(value));

                            ContactDetails c = new ContactDetails();
                            c.setFirstname(f_name);
                            c.setSurname(s_name);
                            c.setUserid(user_id);
                            c.setUsermanages(user_man_tax_office);
                            c.setEmail(email);
                            c.setPasscode(String.valueOf(value));
                            c.setPass(passcode);
                            storage.insertContactDetails(c);

                        }
                    }
                    bar.dismiss();
                    Intent intent = new Intent(context, Login.class);
                    startActivity (intent);
                }
                else if (r_code == fail_response_code){
                    System.out.println("Failed");
                    bar.dismiss();
                }
            }

            else {
                bar.dismiss();
                System.out.println("Failed to get response from server");
            }
            response.close();
        }

        private byte[] encryptPasscode(String passcode){
            byte[] encrypted = new byte[0];
            try {
                //String text = "Hello World";
                String key = "THISisMYKey0090$"; // 128 bit key
                // Create key and cipher
                Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                //KeyGenerator generator = KeyGenerator.getInstance("AES");

                Cipher cipher = Cipher.getInstance("AES");
                // encrypt the text
                cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                encrypted = cipher.doFinal(passcode.getBytes());
                System.err.println(new String(encrypted));
                System.out.println(new String(encrypted));
                // decrypt the text
                cipher.init(Cipher.DECRYPT_MODE, aesKey);
                String decrypted = new String(cipher.doFinal(encrypted));
                System.err.println(decrypted);
                System.out.println(decrypted);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            return encrypted;
        }

        @Override
        public void run() {
            super.run();
            try {
                getMutliUser(teamlist);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}




