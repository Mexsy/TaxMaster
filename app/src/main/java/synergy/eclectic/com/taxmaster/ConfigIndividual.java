package synergy.eclectic.com.taxmaster;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import synergy.eclectic.com.taxmaster.DBinserter.SingleUser;
import synergy.eclectic.com.taxmaster.DBinserter.Tax;
import synergy.eclectic.com.taxmaster.DBinserter.TaxType;


public class ConfigIndividual extends AppCompatActivity implements View.OnClickListener{
    private boolean loggin_in = false;
    public static Handler UIHandler;
    RetrieveFeedTask rtask;
    private SharedPreferences sharedpreferences;
    ProgressDialog bar;

    DataStorage storage = new DataStorage(this);
    TextView tv1;
    private String nameOfUser;
    private String emailOfUser;
    private String taxOffice;
    private final static String MyPREFERENCES = "TaxOffice";
    private int userID;
    private String userSurname;
    private String userFirstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_individual);

        tv1= findViewById(R.id.act);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.otf");
        tv1.setTypeface(face);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.con_button){

            bar = new ProgressDialog(this);//creates a new progress dialog.
            bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//sets what direction the bar runs in.
            bar.setMessage("Verifying Account");//shows a message.
            bar.show();//shows the progress bar.

            EditText username = findViewById(R.id.config_email);
            String string_name = username.getText().toString();

            EditText b = findViewById(R.id.config_password);
            String pass = b.getText().toString();

            if (string_name.isEmpty() || pass.isEmpty()) {
                Toast nullChecker = Toast.makeText(ConfigIndividual.this, "Please enter password and username", Toast.LENGTH_SHORT);
                nullChecker.show();
            }
            else {
                rtask = new RetrieveFeedTask(string_name,pass);
                verify_credentials(string_name,pass);
            }
        }
    }

    /**
     * This method is called by the onClick method when the button is clicked.
     * It takes in two parameters
     * @param name Parameter 1.
     * @param pass Parameter 2.
     * @return nothing as it is a void method.
     */
    public void verify_credentials(String name, String pass){
        //checks if the parameters recieved are empty and handles it
        if((!name.isEmpty() && !pass.isEmpty())) {
            System.out.println("Not Empty");
            loggin_in = rtask.doLogs(name, pass);
                if (loggin_in = true){

                }
            }
        else {
            loggin_in = rtask.doLogs(name, pass);
        }
    }

    public String openPage(boolean success){
        String opened,closed;
        if (success){
            Intent i = new Intent(this, CreatePassCode.class);
            i.putExtra("firstToPass",userFirstname);
            i.putExtra("lastToPass",userSurname);
            i.putExtra("idToPass",userID);
            i.putExtra("taxOfficeToPass",taxOffice);
            i.putExtra("emailToPass",emailOfUser);

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("officeToShow", taxOffice);
            editor.putString("nameToShow", nameOfUser);
            editor.putString("emailToShow", emailOfUser);
            editor.commit();

            startActivity (i);
            opened = "The page opened";
            this.finish();
            return opened;
        }
        else{
            closed = "closed";
            return closed;
        }
    }

    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }
    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }



    /*************************************************** Beginning of inner class ***********************************************************************************************
     * Created by Emeka Chukumah on 23/02/2017.
     * This inner class was created to handle the communication between the mobile application and the API endpoint for SoftTax Mobile.
     * This is because in recent android APIs, it is forbidden to handle URL communication on the UI thread.
     */

    public class RetrieveFeedTask extends Thread {

        private Exception exception;
        private final String u;
        private final String p;
        private RetrieveFeedTask instance;
        private int fail_response_code = 200; // for wrong username or password.
        private int success_response_code = 100; // for correct username and password.


        //Application Base URL
        String baseURL = "http://166.78.174.228/lirs/";

        String validate_user = baseURL + "mobile/ValidateTaxOfficer";
        String get_single_user = baseURL +  "mobile/GetSingleUser";
        String get_tax_type = baseURL +  "mobile/GetTaxType";
        private String response_code = "ResponseCode";
        private String response_body = "ResponseBody";

        ArrayList<Tax> taxlist = new ArrayList<Tax>();

        private boolean open;

        public boolean doLogs(String user,String pass){
            if(instance != null)
                if(instance.isAlive())
                    return true;

            instance = new RetrieveFeedTask(user,pass);
            instance.start();
            return true;
        }

        public RetrieveFeedTask(String u, String p){
            this.u = u;
            this.p = p;
        }

        public String streamToString(InputStream toread) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(toread));
            StringBuilder out = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String toreturn = out.toString();
            if(toread == null)
                toreturn = "null string";
            return toreturn;
        }

        private void validate_user(String name, String pass) throws IOException, JSONException {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(validate_user)
                    .get()
                    .addHeader("username", name)
                    .addHeader("password", pass)
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String jsonData = response.body().string();
                JSONObject j_object = new JSONObject(jsonData);
                System.out.println(jsonData);
                j_object.getString(response_code);
                int r_code = j_object.getInt(response_code);

                if(r_code == success_response_code){
                    System.out.println("Success");
                    ConfigIndividual.runOnUI(new Runnable() {
                        public void run() {
                            Toast confirm = Toast.makeText(ConfigIndividual.this, "Getting your data!!!", Toast.LENGTH_SHORT);
                            confirm.show();
                        }
                    });
                    getUser(name,pass);
                    getTaxItem();
                    GetTaxType();
                }
                else if (r_code == fail_response_code){
                    System.out.println("Failed");
                    ConfigIndividual.runOnUI(new Runnable() {
                        public void run() {
                            Toast confirm = Toast.makeText(ConfigIndividual.this, "username or password incorrect!!!", Toast.LENGTH_SHORT);
                            confirm.show();
                            bar.dismiss();
                        }
                    });
                }
            }
            else {
                System.out.println("Failed to get response from server");
            }
            response.close();
        }

        private void getTaxItem() throws IOException, JSONException {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://166.78.174.228/lirs/Mobile/GetTaxItem")
                    .get()
                    .build();

            //how to add a token .addHeader("postman-token", "537aab2f-c10b-06dc-e51f-f4f3f3c96290")
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String jsonData = response.body().string();
                JSONObject j_object = new JSONObject(jsonData);
                JSONArray jsonArray = j_object.getJSONArray(response_body);
               // JSONObject j_obj = jsonArray.getJSONObject(0);
                int length = jsonArray.length();
                System.out.println("length of array is: " + length + jsonArray.get(0));
                j_object.getString(response_code);
                int r_code = j_object.getInt(response_code);

                if(r_code == success_response_code){
                    String percentagevalue;
                    String percentagedisplay;
                    String create_date;
                    String user_create;
                    String erpitemcode;
                    String fixedamount;
                    String user_lastupdate;
                    String computationtype;
                    System.out.println("Success on tax");System.out.println(j_object.getString(response_body));
                    if(jsonArray!=null && jsonArray.length()>0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String myJsonData =  jsonArray.get(i).toString();
                            JSONObject JSONTaxType =  new JSONObject(myJsonData);


                            int taxitem_id = JSONTaxType.getInt("TAXITEMID");
                            String itemname = JSONTaxType.getString("ITEMNAME");
                            int taxtypeID = JSONTaxType.getInt("TAXTYPEID");


                            if(JSONTaxType.getString("COMPUTATIONTYPE") == null){
                                computationtype = "null";
                            }
                            else{
                                computationtype = JSONTaxType.getString("COMPUTATIONTYPE");
                            }

                            if(JSONTaxType.getString("PERCENTAGEVALUE") == null){
                                 percentagevalue = "null";
                            }
                            else{
                                 percentagevalue = JSONTaxType.getString("PERCENTAGEVALUE");
                            }

                            if(JSONTaxType.getString("FIXEDAMOUNT") == null){
                                fixedamount = null;
                            }
                            else{
                                fixedamount = JSONTaxType.getString("FIXEDAMOUNT");
                            }


                            if(JSONTaxType.getString("PERCENTAGEDISPLAY") == null){
                                percentagedisplay = null;
                            }
                            else{
                                 percentagedisplay = JSONTaxType.getString("PERCENTAGEDISPLAY");
                            }


                            String activeflag;
                            if(JSONTaxType.getString("ACTIVEFLAG") == null){
                                activeflag = null;
                            }
                            else{
                                activeflag = JSONTaxType.getString("ACTIVEFLAG");
                            }


                            if(JSONTaxType.getString("USER_CREATE") == null){
                                user_create = null;
                            }
                            else{
                                user_create = JSONTaxType.getString("USER_CREATE");
                            }
                            if(JSONTaxType.getString("CREATE_DATE") == null){
                                create_date = null;
                            }
                            else{
                                create_date = JSONTaxType.getString("CREATE_DATE");
                            }

                            if(JSONTaxType.getString("USER_LASTUPDATE") == null){
                                user_lastupdate = null;
                            }
                            else{
                                user_lastupdate = JSONTaxType.getString("USER_LASTUPDATE");
                            }


                            String hasseparateflow;
                            if(JSONTaxType.getString("HASSEPARATEFLOW") == null){
                                hasseparateflow = null;
                            }
                            else{
                                hasseparateflow = JSONTaxType.getString("HASSEPARATEFLOW");
                            }

                            String appliestocategory;
                            if(JSONTaxType.getString("APPLIESTOCATEGORY") == null){
                                appliestocategory = null;
                            }
                            else{
                                appliestocategory = JSONTaxType.getString("APPLIESTOCATEGORY");
                            }

                            String last_updatedate = JSONTaxType.getString("LAST_UPDATEDATE");


                            String taxItemUserCategory = JSONTaxType.getString("TaxItemUserCategory");
                            boolean isactiveflag = JSONTaxType.getBoolean("ISACTIVEFLAG");
                            String typename = JSONTaxType.getString("TYPENAME");
                            if(JSONTaxType.getString("ERPITEMCODE") == null){
                                erpitemcode = null;
                            }
                            else{
                                erpitemcode = JSONTaxType.getString("ERPITEMCODE");
                            }

                            String billprefix = JSONTaxType.getString("BILLPREFIX");
                            String typecode = JSONTaxType.getString("TYPECODE");

                            System.out.println("Item I retrieved is " + itemname);

                            String activeFlag;
                            if(isactiveflag == true){
                                activeFlag = "true";
                            }
                            else{
                                activeFlag = "false";
                            }

                            //int existingUserID = storage.searchAPP_ID(user_id);

                            //create a new single user with details recieved from api.
                            Tax tax = new Tax();
                            tax.setTaxItemID(taxitem_id);
                            tax.setItemName(itemname);
                            tax.setTaxTypeID(taxtypeID);
                            tax.setCom_type(computationtype);
                            tax.setPercentage(percentagevalue);
                            tax.setFixed(fixedamount);
                            tax.setPercentageDis(percentagedisplay);
                            tax.setActive_flag(activeflag);
                            tax.setUser_create(user_create);
                            tax.setCreate_date(create_date);
                            tax.setLastUpdate(last_updatedate);
                            tax.setUser_last(user_lastupdate);
                            tax.setFlow(hasseparateflow);
                            tax.setApptoCat(appliestocategory);
                            tax.settIUCat(taxItemUserCategory);
                            tax.setIs_active(activeFlag);
                            tax.setTypeName(typename);
                            tax.setErp(erpitemcode);
                            tax.setBill_prefix(billprefix);
                            tax.setType_code(typecode);
                            taxlist.add(tax);
                            taxlist.toArray();

                            System.out.println(taxlist.toArray().toString());
                            System.out.println(taxlist.toArray().toString());

                            storage.insertTaxItem(tax);
                        }
                    }
                }

                else if (r_code == fail_response_code){
                    System.out.println("Failed");
                }
            }
            else {
                System.out.println("Failed to get response from server");
            }
            response.close();


        }


        private void GetTaxType() throws IOException, JSONException {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://166.78.174.228/lirs/Mobile/GetTaxType")
                    .get()
                    .build();

            //how to add a token .addHeader("postman-token", "537aab2f-c10b-06dc-e51f-f4f3f3c96290")
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String jsonData = response.body().string();
                JSONObject j_object = new JSONObject(jsonData);
                JSONArray jsonArray = j_object.getJSONArray(response_body);
                j_object.getString(response_code);


                int r_code = j_object.getInt(response_code);

                if(r_code == success_response_code){
                    System.out.println("Success on tax type");System.out.println(j_object.getString(response_body));
                    if(jsonArray!=null && jsonArray.length()>0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String myJsonData =  jsonArray.get(i).toString();
                            JSONObject JSONTaxType =  new JSONObject(myJsonData);

                            String taxtypeid = JSONTaxType.getString("TAXTYPEID");
                            String itemname = JSONTaxType.getString("TYPENAME");
                            String organisationid = JSONTaxType.getString("ORGANISATIONID");
                            String isincometax = JSONTaxType.getString("ISINCOMETAX");
                            String activeflag = JSONTaxType.getString("ACTIVEFLAG");
                            String user_create;

                            if(JSONTaxType.getString("USER_CREATE") == null){
                                user_create = "null";
                            }
                            else{
                                user_create = JSONTaxType.getString("USER_CREATE");
                            }
                            String myReturnID,create_date2;
                            if(JSONTaxType.getString("CREATE_DATE") == null){
                                create_date2 = "null";
                            }
                            else{
                                create_date2 = JSONTaxType.getString("CREATE_DATE");
                            }

                            String user_lastupdate1 = JSONTaxType.getString("USER_LASTUPDATE");
                            String last_updatedate = JSONTaxType.getString("LAST_UPDATEDATE");
                            if(JSONTaxType.getString("myReturnID") == null){
                                myReturnID = "null";
                            }
                            else{
                                myReturnID = JSONTaxType.getString("myReturnID");
                            }
                            String isincometaxflag = JSONTaxType.getString("ISINCOMETAXFLAG");
                            String isactiveflag = JSONTaxType.getString("ISACTIVEFLAG");


                            //create a new single user with details recieved from api.
                            TaxType taxType = new TaxType();
                            taxType.setTax_type_id(taxtypeid);
                            taxType.setTypename(itemname);
                            taxType.setOrg_id(organisationid);
                            taxType.setIsIncomeTax(isincometax);
                            taxType.setActive(activeflag);
                            taxType.setUser_create(user_create);
                            taxType.setCreateDate(create_date2);
                            taxType.setUserLastUpdate(user_lastupdate1);
                            taxType.setUpdatedate(last_updatedate);
                            taxType.setMyRID(myReturnID);
                            taxType.setIsFlag(isincometaxflag);
                            taxType.setIsAct(isactiveflag);


                            storage.insertTaxType(taxType);
                        }
                    }
                }

                else if (r_code == fail_response_code){
                    System.out.println("Failed");
                }
            }
            else {
                System.out.println("Failed to get response from server");
            }
            response.close();

        }

        private void getUser(String username, String password) throws IOException, JSONException {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(get_single_user)
                    .get()
                    .addHeader("username", username)
                    .addHeader("password", password)
                    .build();

            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                String jsonData = response.body().string();
                JSONObject j_object = new JSONObject(jsonData);
                System.out.println(jsonData);
                j_object.getString(response_code);
                int r_code = j_object.getInt(response_code);

                if(r_code == success_response_code){
                    System.out.println("Success");System.out.println(j_object.getString(response_body));
                    int user_id = j_object.getJSONObject(response_body).getInt("APP_USERID");
                    userID = user_id;
                    String s_name = j_object.getJSONObject(response_body).getString("SURNAME");
                    userSurname = s_name;
                    String f_name = j_object.getJSONObject(response_body).getString("FIRSTNAME");
                    userFirstname = f_name;
                    nameOfUser = s_name + " " + f_name;
                    String o_name = j_object.getJSONObject(response_body).getString("OTHERNAME");
                    String dob = j_object.getJSONObject(response_body).getString("DATEOFBIRTH");
                    String email = j_object.getJSONObject(response_body).getString("EMAILADDRESS");
                    emailOfUser = email;
                    String u_name = j_object.getJSONObject(response_body).getString("USERNAME");
                    String pass = j_object.getJSONObject(response_body).getString("PASSWORD");
                    int org_id = j_object.getJSONObject(response_body).getInt("ORGANISATIONID");
                    int is_glo_d = j_object.getJSONObject(response_body).getInt("IS_GLOBALADMIN");
                    int is_orga = j_object.getJSONObject(response_body).getInt("IS_ORGADMIN");
                    int is_tax = j_object.getJSONObject(response_body).getInt("IS_TAXOFFICER");
                    int man_to = j_object.getJSONObject(response_body).getInt("MANAGES_TAXOFFICE");
                    String act = j_object.getJSONObject(response_body).getString("ACTIVE");
                    int u_type = j_object.getJSONObject(response_body).getInt("UserType");
                    String temp_dob = j_object.getJSONObject(response_body).getString("tempDOB");
                    String taxpayer_id = j_object.getJSONObject(response_body).getString("TAXPAYERID");
                    String corp_id = j_object.getJSONObject(response_body).getString("CORPORATEID");
                    String user_man_tax_office = j_object.getJSONObject(response_body).getString("UserManagesTaxOffice");
                    taxOffice = user_man_tax_office;
                    String userROLE = j_object.getJSONObject(response_body).getString("USERROLE");
                    String CORP = j_object.getJSONObject(response_body).getString("CORPORATEs");
                    String coma = j_object.getJSONObject(response_body).getString("comaSeparatedRoles");
                    String taxPayers = j_object.getJSONObject(response_body).getString("TAXPAYERs");
                    String UserRoles = j_object.getJSONObject(response_body).getString("userRoles");
                    //System.out.println("User ID is :" + user_id);

                    int existingUserID = storage.searchAPP_ID(user_id);

                    if (existingUserID != user_id){
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
                    }
                    if (s_name != null && !s_name.isEmpty()) {
                        ConfigIndividual.runOnUI(new Runnable() {
                            public void run() {
                                Toast very = Toast.makeText(ConfigIndividual.this, "Account Verified!!!", Toast.LENGTH_SHORT);
                                very.show();
                                bar.dismiss();
                            }
                        });
                        openPage(true);
                    }
                    else {
                        System.out.println("did not read code");
                    }
                }

                else if (r_code == fail_response_code){
                    System.out.println("Failed");
                }
            }
            else {
                System.out.println("Failed to get response from server");
            }
            response.close();
        }



        @Override
        public void run() {
            super.run();
            try {
                validate_user(u,p);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
