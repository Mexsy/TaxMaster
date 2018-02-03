package synergy.eclectic.com.taxmaster;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import synergy.eclectic.com.taxmaster.Adapters.MyCustomAdapterTwo;
import synergy.eclectic.com.taxmaster.Adapters.MyCustomBaseAdapter;
import synergy.eclectic.com.taxmaster.DBinserter.CorpInfo;
import synergy.eclectic.com.taxmaster.DBinserter.FilingHistory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewCorporate extends Fragment {
    private boolean loggin_in = false;
    List<String> text_to_show = new ArrayList<String>();
    List<String> info_button_list = new ArrayList<String>();
    List<FilingHistory> fileHISTORY = new ArrayList<>();
    List<PaymentInfo> paymentInfoArrayList = new ArrayList<>();
    String string_name;
    private ProgressDialog bar;
    View view;


    public ReviewCorporate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review_corporate, container, false);

        final Button payments_button = view.findViewById(R.id.view_payments);
        payments_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.view_payments:
                        System.out.println("Payments");

                        final ListView lvItems = view.findViewById(R.id.listViewRC);

                        final List<String> l = text_to_show;

                        //ArrayAdapter myAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, l);
                        //myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

                        lvItems.setAdapter(new MyCustomAdapterTwo(getContext(), paymentInfoArrayList));
                        lvItems.setEmptyView(view.findViewById( R.id.empty_list_view1 ));
                        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        });

                        break;
                }
            }
        });






        final Button history_button = view.findViewById(R.id.view_filling_history);
        history_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               switch (v.getId()) {
                                                   case R.id.view_filling_history:
                                                       System.out.println("Filing");

                                                       final ListView lvItems = view.findViewById(R.id.listViewRC);

                                                       lvItems.setAdapter(new MyCustomBaseAdapter(getContext(), fileHISTORY));
                                                       lvItems.setEmptyView(view.findViewById( R.id.empty_list_view1 ));
                                                       lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                                           @Override
                                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                           }
                                                       });

                                                       break;
                                               }
                                               }
                                           });



        final ImageButton go_button = view.findViewById(R.id.search_buttonT);
        go_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.search_buttonT:
                        //offline implementation by searching database first to check for string_name


                        //online
                        bar = new ProgressDialog(getContext());//creates a new progress dialog.
                        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);//sets what direction the bar runs in.
                        bar.setMessage("loading");//shows a message.
                        bar.show();//shows the progress bar.

                        EditText a = getActivity().findViewById(R.id.search_view);
                        string_name = a.getText().toString().toUpperCase();
                        new GetCorporate().execute(string_name);
                        //gtask = new GetCorporate(string_name);

                        //textView = (TextView) getActivity().findViewById(R.id.CorporateInfo);
                       // textView.setText(gtask.getId_to_show());
                        break;
                }
            }
        });

        return view;
    }



    /*************************************************** Beginning of inner class ***********************************************************************************************
     * Created by Emeka Chukumah on 02/03/2017.
     * This inner class was created to handle the communication between the mobile application and the API endpoint for SoftTax Mobile.
     * This is because in recent android APIs, it is forbidden to handle URL communication on the UI thread.
     */


    public class GetCorporate extends AsyncTask<String,Void,List<String>> {
        private int fail_response_code = 200; // for wrong username or password.
        private int success_response_code = 100; // for correct username and password.


        //Application Base URL
        String baseURL = "http://166.78.174.228/lirs/";
        String get_corporate_info = baseURL + "Mobile/GetCorporateInfo";
        private String response_code = "ResponseCode";
        private String response_body = "ResponseBody";



        @Override
        protected List<String> doInBackground(String... params) {
            String value = params[0];
            List<String> pList = new ArrayList<>();

            List<String> filing = new ArrayList<>();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(get_corporate_info)
                    .get()
                    .addHeader("TIN", value)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    JSONObject j_object = new JSONObject(jsonData);
                    JSONObject response_b = j_object.getJSONObject(response_body);

                    //Corporate information.
                    JSONObject corporate_info = response_b.getJSONObject("CorporateInfo");

                    //Corporate filing information.
                    JSONArray jsonArray = response_b.getJSONArray("CorporateFilingInfo");

                    //Corporate payment information.
                    JSONArray jsonArrayPaymentInfo = response_b.getJSONArray("CorporatePaymentInfo");

                    System.out.println(jsonData);
                    System.out.println(jsonArray);
                    System.out.println(jsonArrayPaymentInfo);
                    j_object.getString(response_code);
                    int r_code = j_object.getInt(response_code);

                    if (r_code == success_response_code) {
                        //this block of code gets the corporate information.
                        //for saving to database
                        String corp_id;
                        String c_name;
                        String officeaddress;
                        String corp_email;
                        String buss_type;
                        String incorp_date;
                        String rcnumber;
                        String tin;
                        String customerid;
                        String app_userid;
                        String user_create;
                        String create_date;
                        corp_id = corporate_info.getString("CORPORATEID");
                        pList.add("CORPORATEID : "+" " +corp_id);// add string to the list
                        c_name = corporate_info.getString("CORPORATENAME");
                        pList.add("CORPORATENAME : "+" " +c_name);
                        officeaddress = corporate_info.getString("OFFICEADDRESS");
                        pList.add("CORPORATENAME : "+" " +officeaddress);
                        corp_email = corporate_info.getString("EMAILADDRESS");
                        pList.add("EMAILADDRESS : "+" " +corp_email);
                        String pcorp_id = corporate_info.getString("PARENTCORPORATEID");
                        pList.add("PARENTCORPORATEID : "+" " +pcorp_id);
                        buss_type = corporate_info.getString("BUSINESSTYPE");
                        pList.add("BUSINESSTYPE : "+" " +buss_type);
                        incorp_date = corporate_info.getString("INCORPORATIONDATE");
                        pList.add("INCORPORATIONDATE : "+" " +incorp_date);
                        rcnumber = corporate_info.getString("RCNUMBER");
                        pList.add("RCNUMBER : "+" " +rcnumber);
                        tin = corporate_info.getString("TIN");
                        pList.add("TIN : "+" " +tin);
                        customerid =corporate_info.getString("CUSTOMERID");
                        pList.add("CUSTOMERID : "+" " +customerid);
                        String payemonthoutstanding =corporate_info.getString("PAYEMONTHOUTSTANDING");
                        pList.add("PAYEMONTHOUTSTANDING : "+" " +payemonthoutstanding);
                        String activeflag =corporate_info.getString("ACTIVEFLAG");
                        pList.add("ACTIVEFLAG : "+" " +activeflag);
                        app_userid =corporate_info.getString("APP_USERID");
                        pList.add("APP_USERID : "+" " +app_userid);
                        user_create = corporate_info.getString("USER_CREATE");
                        pList.add("USER_CREATE : "+" " +user_create);
                        create_date =corporate_info.getString("CREATE_DATE");
                        pList.add("CREATE_DATE : "+" " +create_date);
                        String user_lastupdate =corporate_info.getString("USER_LASTUPDATE");
                        pList.add("USER_LASTUPDATE : "+" " +user_lastupdate);
                        String last_updatedate =corporate_info.getString("LAST_UPDATEDATE");
                        pList.add("LAST_UPDATEDATE : "+" " +last_updatedate);
                        String tempDate =corporate_info.getString("tempDate");
                        pList.add("tempDate : "+" " +tempDate);
                        String temp_taxofficeid =corporate_info.getString("TEMP_TAXOFFICEID");
                        pList.add("TEMP_TAXOFFICEID : "+" " +temp_taxofficeid);
                        String corporatestafFs =corporate_info.getString("CORPORATESTAFFs");
                        pList.add("CORPORATESTAFFs : "+" " +corporatestafFs);
                        String corporatetaxofficEs =corporate_info.getString("CORPORATETAXOFFICEs");
                        pList.add("CORPORATETAXOFFICEs : "+" " +corporatetaxofficEs);
                        String sector =corporate_info.getString("SECTOR");
                        pList.add("SECTOR : "+" " +sector);


                        DataStorage dataStorage = new DataStorage(getContext());
                        //create a new corpInfo object to set values.
                        CorpInfo corpInfo = new CorpInfo();
                        corpInfo.setCorpIDInfo(corp_id);
                        corpInfo.setCorpName(c_name);
                        corpInfo.setBusiness_type(buss_type);
                        corpInfo.setEmail(corp_email);
                        corpInfo.setIncorp_date(incorp_date);
                        corpInfo.setUserid(app_userid);
                        corpInfo.setOfficeAddress(officeaddress);
                        corpInfo.setRc_number(rcnumber);
                        corpInfo.setTin(tin);
                        corpInfo.setUsercreate(user_create);
                        corpInfo.setCorpName(create_date);
                        corpInfo.setCustomer_id(customerid);

                        dataStorage.insertCorporateInfo(corpInfo);

                        //this block of code gets the filing history.
                        if(jsonArray!=null && jsonArray.length()>0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String myJsonData =  jsonArray.get(i).toString();
                                JSONObject JSONHistory =  new JSONObject(myJsonData);


                                String filingno = JSONHistory.getString("FILINGNO");
                                String filing_type = JSONHistory.getString("ITEMNAME");
                                String filingdate = JSONHistory.getString("FILINGDATE");
                                String taxamount = JSONHistory.getString("TAXAMOUNT");

                                FilingHistory filingHistory = new FilingHistory();

                                filingHistory.setFilingNO(filingno);
                                filingHistory.setType(filing_type);
                                filingHistory.setFiling_date(filingdate);
                                filingHistory.setAmount(taxamount);

                                fileHISTORY.add(filingHistory);
                                text_to_show.add(filingno);
                                System.out.println(filingno);
                            }
                        }

                        //this block is for the CorporatePaymentInfo
                        if(jsonArrayPaymentInfo!=null && jsonArrayPaymentInfo.length()>0) {
                            for (int i = 0; i < jsonArrayPaymentInfo.length(); i++) {
                                String myJsonData = jsonArrayPaymentInfo.get(i).toString();
                                JSONObject JSONPayment = new JSONObject(myJsonData);


                                String customerId = JSONPayment.getString("customer_id");
                                String invoiceNumber = JSONPayment.getString("invoice_number");
                                String amount = JSONPayment.getString("amount");
                                String transactionDate = JSONPayment.getString("transaction_date");
                                String refID = JSONPayment.getString("RefID");

                                PaymentInfo paymentInfo = new PaymentInfo();

                                paymentInfo.setAmount(amount);
                                paymentInfo.setCustomer_id(customerId);
                                paymentInfo.setInvoice_number(invoiceNumber);
                                paymentInfo.setRefID(refID);
                                paymentInfo.setTransaction_date(transactionDate);

                                paymentInfoArrayList.add(paymentInfo);
                            }
                        }

                    } else if (r_code == fail_response_code) {
                        System.out.println("Failed");
                    }
                } else {
                    System.out.println("Failed to get response from server");
                }
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            publishProgress();
            return pList;
        }

        protected void onPostExecute(List<String> result) {
            // Find ListView to populate
            final ListView lvItems = view.findViewById(R.id.listViewRC);

            final List<String> l = result;
            info_button_list = l;



            ArrayAdapter myAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, l);
            myAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            lvItems.setAdapter(myAdapter);
            lvItems.setEmptyView(view.findViewById( R.id.empty_list_view1 ));
            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });
        }

        protected void onProgressUpdate(Void... values) {
            bar.dismiss();
        }

    }

}
