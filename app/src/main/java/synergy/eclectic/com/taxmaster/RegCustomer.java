package synergy.eclectic.com.taxmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import layout.ContactAddress;
import layout.OtherDetails;
import layout.PersonalDetails;
import layout.SecurityDetails;
import synergy.eclectic.com.taxmaster.DBinserter.Customer;

public class RegCustomer extends AppCompatActivity {
    private final static String TAG_CONTACT_ADDRESS = "Address";
    private final static String TAG_PERSONAL_DETAILS = "Personal";
    private final static String TAG_OTHER_DETAILS = "Other";
    private final static String TAG_SECURITY_DETAILS = "Security";
    public static final String MyPREFERENCES = "Personal Details" ;
    private static boolean status;
    private DataStorage dataStorage;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_customer);
        dataStorage = new DataStorage(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        Typeface face = Typeface.createFromAsset(this.getAssets(), "fonts/quicksandbold.otf");
        mTitle.setTypeface(face);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                /*Get values from shared preferences*/

                //Values from the personal details fragment
                String title = editor.getString("Title", "null");
                String birthday = editor.getString("birth", "null");
                String surname = editor.getString("Surname", "null");
                String firstName = editor.getString("first", "null");
                String otherName = editor.getString("other", "null");
                String maidName = editor.getString("maid", "null");
                String maritalStat = editor.getString("marital", "null");
                String gender = editor.getString("sex", "null");
                boolean personalCompleted = editor.getBoolean("personal", false);

                //Values from the address fragment
                String residence = editor.getString("residence", "null");
                String origin = editor.getString("origin", "null");
                String nation = editor.getString("nation", "null");
                String email = editor.getString("email", "null");
                String number = editor.getString("number", "null");
                String address = editor.getString("address_line", "null");
                String city = editor.getString("city", "null");
                boolean addressCompleted = editor.getBoolean("address", false);

                String tax_office = editor.getString("office", "null");
                String BVN = editor.getString("bvn", "null");
                String occupation = editor.getString("occup", "null");
                String TIN = editor.getString("tin", "null");
                String NIN = editor.getString("nin", "null");
                boolean otherCompleted = editor.getBoolean("other", false);

                String password = editor.getString("pass", "null");
                String security = editor.getString("question", "null");
                String answer = editor.getString("answer", "null");
                boolean securityCompleted = editor.getBoolean("security", false);


                if(personalCompleted && addressCompleted && otherCompleted && securityCompleted){
                    Customer customer = new Customer();
                    customer.setTitle(title);
                    customer.setBirth(birthday);
                    customer.setSurname(surname);
                    customer.setFirst(firstName);
                    customer.setOther(otherName);
                    customer.setMaid(maidName);
                    customer.setMarital(maritalStat);
                    customer.setSex(gender);
                    customer.setPass(password);
                    customer.setResidence(residence);
                    customer.setOrign(origin);
                    customer.setNation(nation);
                    customer.setEmail(email);
                    customer.setNumber(number);
                    customer.setAddress(address);
                    customer.setCity(city);
                    customer.setOffice(tax_office);
                    customer.setBvn(BVN);
                    customer.setOccupation(occupation);
                    customer.setTin(TIN);
                    customer.setNin(NIN);
                    customer.setPass(password);
                    customer.setQuestion(security);
                    customer.setAns(answer);
                    dataStorage.insertCustomer(customer);

                    Snackbar.make(view, "Customer has been registered", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RegCustomer.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).show();
                }
                else {
                    Snackbar.make(view, "Please complete all fields", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();

                }

            }
        });

    }

    public static boolean getStatus(){
        if (PersonalDetails.completePersonal && OtherDetails.completeOther && ContactAddress.completeAddress && SecurityDetails.completeSec){
            status = true;
        }
        else {
            status = false;
        }
        return status;
    }

    public void onClickBack(View view) {
        if (view.getId() == R.id.backbutton){
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg_customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int navPositionIndex = 0;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            navPositionIndex = position;
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (navPositionIndex) {
                case 0:
                    // home
                    PersonalDetails personalFragment = new PersonalDetails();
                    return personalFragment;
                case 1:
                    // photos
                    ContactAddress contactAddress = new ContactAddress();
                    return contactAddress;
                case 2:
                    // movies fragment
                    OtherDetails otherDetails = new OtherDetails();
                    return otherDetails;
                case 3:
                    SecurityDetails securityDetails = new SecurityDetails();
                    return securityDetails;

                default:
                    return new PersonalDetails();
            }
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    navPositionIndex = 0;
                    return TAG_PERSONAL_DETAILS;
                case 1:
                    navPositionIndex = 1;
                    return TAG_CONTACT_ADDRESS;
                case 2:
                    navPositionIndex = 2;
                    return TAG_OTHER_DETAILS;
                case 3:
                    navPositionIndex = 3;
                    return TAG_SECURITY_DETAILS;
            }
            return null;
        }
    }
}
