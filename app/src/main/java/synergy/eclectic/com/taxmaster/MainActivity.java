package synergy.eclectic.com.taxmaster;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    // index to identify current nav menu item
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "DashBoard";
    private static final String TAG_CORP = "Corporation";
    private static final String TAG_INDIE = "Individual";
    private static final String TAG_CUSTOMER = "Customer";
    private static final String TAG_TAX = "TaxDetails";
    private static final String TAG_OUT = "LogOut";
    public static String CURRENT_TAG = TAG_HOME;
    DataStorage storage = new DataStorage(this);

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    private NavigationView navigationView;
    private Handler mHandler;
    private View navHeader;
    private TextView txtName, txtEmail;
    private boolean shouldLoadHomeFragOnBackPress = true;
    Context context;
    String username;
    String email;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "TaxOffice" ;
    public static final String MySECONDPREFERENCES = "Warning" ;
    String office;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        //username = getIntent().getStringExtra("nameToShow");
        //email = getIntent().getStringExtra("emailToShow");

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        username = sharedpreferences.getString("nameToShow","");
        email = sharedpreferences.getString("emailToShow","");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title_app_name);
        Typeface face= Typeface.createFromAsset(this.getAssets(), "fonts/quicksandbold.otf");
        mTitle.setTypeface(face);

        mHandler = new Handler();
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.usernameTV);
        txtEmail = (TextView) navHeader.findViewById(R.id.emailTV);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RaiseAssessment.class);
                startActivity(intent);
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // initializing navigation menu
        setUpNavigationView();

        //storage.insertInvoice();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            navigationView.getMenu().getItem(navItemIndex).setActionView(R.layout.menu_dot);
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setDot(navItemIndex);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    public void onClickSync(View view) {
        if (view.getId() == R.id.backbutton){
            Snackbar.make(view, "Device synced", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText(username);
        txtEmail.setText(email);

        /* loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);*/

        /* Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);*/

    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        //  setToolbarTitle();


        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                DashBoard dashFragment = new DashBoard();
                return dashFragment;
            case 1:
                // photos
                ReviewCorporate rev_corpFragment = new ReviewCorporate();
                return rev_corpFragment;
            case 2:
                // movies fragment
                ReviewIndieFragment rev_indieFragment = new ReviewIndieFragment();
                return rev_indieFragment;
            case 3:
                // notifications fragment
                RegisterCustomer reg_CusFragment = new RegisterCustomer();
                return reg_CusFragment;
            case 4:
                // notifications fragment

            default:
                return new DashBoard();
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_corp:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CORP;
                        break;
                    case R.id.nav_rip:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_INDIE;
                        break;
                    case R.id.nav_customer:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CUSTOMER;
                        break;
                    case R.id.nav_log_out:
                        navItemIndex = 4;
                        Intent i = new Intent(context, Login.class);
                        startActivity (i);
                        finish();
                        setDot(navItemIndex);
                        break;
                    case R.id.nav_reconfig:
                        navItemIndex = 5;
                        setDot(navItemIndex);
                        break;
                    /**
                     case R.id.nav_privacy_policy:
                     // launch new intent instead of loading fragment
                     startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                     drawer.closeDrawers();
                     return true;*/
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void setDot(int index){
        if (index == 0){
            navigationView.getMenu().getItem(index).setActionView(R.layout.menu_dot);
            navigationView.getMenu().getItem(1).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(2).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(3).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(4).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(5).setActionView(R.layout.no_dot);
        }
        if (index == 1){
            navigationView.getMenu().getItem(index).setActionView(R.layout.menu_dot);
            navigationView.getMenu().getItem(0).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(2).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(3).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(4).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(5).setActionView(R.layout.no_dot);
        }
        if (index == 2){
            navigationView.getMenu().getItem(index).setActionView(R.layout.menu_dot);
            navigationView.getMenu().getItem(0).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(1).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(3).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(4).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(5).setActionView(R.layout.no_dot);
        }
        if (index == 3){
            navigationView.getMenu().getItem(index).setActionView(R.layout.menu_dot);
            navigationView.getMenu().getItem(0).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(1).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(2).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(4).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(5).setActionView(R.layout.no_dot);
        }
        if (index == 4){
            navigationView.getMenu().getItem(index).setActionView(R.layout.menu_dot);
            navigationView.getMenu().getItem(0).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(1).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(2).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(3).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(5).setActionView(R.layout.no_dot);
        }
        if (index == 5){
            navigationView.getMenu().getItem(index).setActionView(R.layout.menu_dot);
            navigationView.getMenu().getItem(0).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(1).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(2).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(3).setActionView(R.layout.no_dot);
            navigationView.getMenu().getItem(4).setActionView(R.layout.no_dot);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Check to see which item was being clicked and perform appropriate action
        switch (id) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_dashboard:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                setDot(navItemIndex);
                //navigationView.getMenu().getItem(navItemIndex).setActionView(R.layout.menu_dot);
                break;
            case R.id.nav_corp:
                navItemIndex = 1;
                CURRENT_TAG = TAG_CORP;
                setDot(navItemIndex);
                break;
            case R.id.nav_rip:
                navItemIndex = 2;
                CURRENT_TAG = TAG_INDIE;
                setDot(navItemIndex);
                break;
            case R.id.nav_customer:
                navItemIndex = 3;
                CURRENT_TAG = TAG_CUSTOMER;
                setDot(navItemIndex);
                //Intent intent3 = new Intent(MainActivity.this, RegCustomer.class);
                //startActivity (intent3);
                break;
            case R.id.nav_assessments:
                navItemIndex = 4;
                //CURRENT_TAG = TAG_TAX;
                Intent intent2 = new Intent(MainActivity.this, RaiseAssessment.class);
                startActivity (intent2);
                setDot(navItemIndex);
                break;
            case R.id.nav_log_out:
                navItemIndex = 5;
                CURRENT_TAG = TAG_OUT;
                Intent i = new Intent(MainActivity.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("EXIT", true);
                startActivity (i);
                this.finish();
                //LogOutDialog logOutDialog = new LogOutDialog();
                //logOutDialog.show(getSupportFragmentManager(), "log_out");
                setDot(navItemIndex);
                break;
            case R.id.nav_reconfig:
                Intent intent = new Intent(context, ConfigurationActivity.class);
                startActivity (intent);
                SharedPreferences settings = getSharedPreferences(MySECONDPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("warn", true);
                editor.apply();
                break;
            case R.id.nav_pass_change:
                Intent open = new Intent(context, ChangePasscode.class);
                startActivity (open);
                break;

            /**
             case R.id.nav_privacy_policy:
             // launch new intent instead of loading fragment
             startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
             drawer.closeDrawers();
             return true;*/
            default:
                navItemIndex = 0;
                setDot(navItemIndex);
        }

        //Checking if the item is in checked state or not, if not make it in checked state
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();

        // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

