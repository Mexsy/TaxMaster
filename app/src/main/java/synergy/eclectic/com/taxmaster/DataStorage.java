package synergy.eclectic.com.taxmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import synergy.eclectic.com.taxmaster.DBinserter.ContactDetails;
import synergy.eclectic.com.taxmaster.DBinserter.CorpInfo;
import synergy.eclectic.com.taxmaster.DBinserter.Customer;
import synergy.eclectic.com.taxmaster.DBinserter.CustomerPersonal;
import synergy.eclectic.com.taxmaster.DBinserter.SingleUser;
import synergy.eclectic.com.taxmaster.DBinserter.Tax;
import synergy.eclectic.com.taxmaster.DBinserter.TaxType;

/**
 * Created by Emeka Chukumah on 04/02/2016.
 */
public class DataStorage extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSCODE = "passcode";
    private static final String COLUMN_STRING_PASSCODE = "passtextcode";


    private static final String COLUMN_PASSWORD = "password";

    private static final String TABLE_NAME_2 = "invoice";
    private static final String COLUMN_ID_INVOICE = "id_invoice";
    private static final String COLUMN_TIN= "tin";
    private static final String COLUMN_INVOICE_AMOUNT = "invoice_amount";
    private static final String COLUMN_AMOUNT_PAID = "amount_paid";
    private static final String COLUMN_AMOUNT_OUTSTANDING = "amount_outstanding";

    private static final String TABLE_NAME_3 = "userdetails";
    private static final String COLUMN_APP_USERID = "userid";
    private static final String COLUMN_SURNAME= "surname";
    private static final String COLUMN_FIRSTNAME_TWO = "firstname";
    private static final String COLUMN_OTHERNAME = "othername";
    private static final String COLUMN_DATEOFBIRTH = "dateofbirth";
    private static final String COLUMN_EMAILADDRESS = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD_TWO = "password";
    private static final String COLUMN_ORGANISATIONID = "orgid";
    private static final String COLUMN_IS_GLOBALADMIN = "globadmin";
    private static final String COLUMN_IS_ORGADMIN= "orgadmin";
    private static final String COLUMN_IS_TAXOFFICER = "isataxofficer";
    private static final String COLUMN_MANAGES_TAXOFFICE = "managesto";
    private static final String COLUMN_ACTIVE = "active";
    private static final String COLUMN_UserType = "usertype";
    private static final String COLUMN_tempDOB = "tempdob";
    private static final String COLUMN_TAXPAYERID = "taxpayerid";
    private static final String COLUMN_CORPORATEID = "corporateid";
    private static final String COLUMN_UserManagesTaxOffice = "usermanages";
    private static final String COLUMN_comaSeparatedRoles = "comaseparatedroles";
    private static final String COLUMN_USERROLE = "userrole";
    private static final String COLUMN_CORPORATEs = "corporates";
    private static final String COLUMN_TAXPAYERs = "taxpayers";
    private static final String COLUMN_userRoles = "userroles";

    private static final String TABLE_NAME_4 = "taxitem";
    private static final String COLUMN_TAXITEMID = "taxitemid";
    private static final String COLUMN_ITEMNAME = "itemname";
    private static final String COLUMN_TAXTYPEID = "taxtypeid";
    private static final String COLUMN_COMPUTATIONTYPE = "computationtype";
    private static final String COLUMN_PERCENTAGEVALUE = "percentagevalue";
    private static final String COLUMN_FIXEDAMOUNT = "fixedamount";
    private static final String COLUMN_PERCENTAGEDISPLAY = "percentagedisplay";
    private static final String COLUMN_ACTIVEFLAG = "activeflag";
    private static final String COLUMN_USER_CREATE = "usercreate";
    private static final String COLUMN_CREATE_DATE = "createdate";
    private static final String COLUMN_USER_LASTUPDATE = "lastupdate";
    private static final String COLUMN_LAST_UPDATEDATE = "lastupdatedate";
    private static final String COLUMN_HASSEPARATEFLOW = "hass";
    private static final String COLUMN_APPLIESTOCATEGORY = "atc";
    private static final String COLUMN_TaxItemUserCategory = "tiucat";
    private static final String COLUMN_ISACTIVEFLAG = "isactive";
    private static final String COLUMN_TYPENAME = "typename";
    private static final String COLUMN_ERPITEMCODE= "erp";
    private static final String COLUMN_BILLPREFIX = "bp";
    private static final String COLUMN_TYPECODE = "typecode";


    private static final String TABLE_NAME_6 = "taxtype";
    private static final String COLUMN_ISINCOMETAX = "isincome";
    private static final String COLUMN_myReturnID = "myreturn";
    private static final String COLUMN_ISINCOMETAXFLAG = "isincome";

    private static final String CREATE_TABLE_6 = "create table taxtype (taxtypeid integer primary key not null , "+
            "typename text, orgid text, usercreate text, isincome text not null, activeflag text, isactive text, lastupdate text, lastupdatedate text, myreturn text, createdate text);";


    private static final String TABLE_NAME_5 = "CorporateInfo";
    private static final String COLUMN_CORPORATEID_INFO = "corporate_id";
    private static final String COLUMN_CORPORATENAME = "corporate_name";
    private static final String COLUMN_OFFICEADDRESS = "office_ad";
    private static final String COLUMN_BUSINESSTYPE = "business_type";
    private static final String COLUMN_INCORPORATIONDATE = "incorp";
    private static final String COLUMN_RCNUMBER = "rc_number";
    private static final String COLUMN_CUSTOMERID = "customer_id";
    private static final String COLUMN_PAYEMONTHOUTSTANDING = "business_tyoe";

    //private static final String TABLE_NAME_8 = "CustomerAddress";


   // private static final String CREATE_TABLE_8 = "create table CustomerAddress (id integer primary key not null , "+
    //        "customerRef integer not null, surname text not null, FOREIGN KEY(customerRef) REFERENCES CustomerPersonalDetails(customerID));";


    private static final String TABLE_NAME_7 = "CustomerPersonalDetails";
    private static final String COLUMN_CUSTOMER_ID = "customerID";
    private static final String COLUMN_OTHER_NAMES = "otherNames";
    private static final String COLUMN_MAIDEN_NAME = "maidenName";
    private static final String COLUMN_MARITAL_STATUS = "marital";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_TITLE = "title";

    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_STATE_OF_ORIGIN = "origin";
    private static final String COLUMN_STATE_OF_RESIDENCE= "residence";
    private static final String COLUMN_NATION= "nation";
    private static final String COLUMN_PREF_OFFICE= "PreferredOffice";
    private static final String COLUMN_PHONE_NUMBER = "phone";

    private static final String COLUMN_NIN = "nin";
    private static final String COLUMN_OCCUPATION = "occupation";
    private static final String COLUMN_BVN = "bvn";
    private static final String COLUMN_QUESTION = "securityquestion";
    private static final String COLUMN_ANSWER = "answer";

    private static final String TABLE_NAME_8 = "CustomerDetails";
    private static final String COLUMN_STAT = "status";

    private static final String TABLE_NAME_9 = "CustomerTaxDetails";




    private static final String CREATE_TABLE = "create table users (id integer primary key not null , "+
            "userid integer not null, surname text not null, firstname text not null, email text not null, usermanages text not null, passcode text not null, passtextcode text not null);";

    private static final String CREATE_TABLE_2 = "create table invoice (id_invoice integer primary key not null , "+
            "tin text not null, lastname text not null, invoice_amount text not null, amount_paid text not null, amount_outstanding text not null);";

    private static final String CREATE_TABLE_3 = "create table userdetails (userid integer primary key not null , "+
            "surname text not null, firstname text not null, othername text not null, dateofbirth text , email text not null, username text not null, password text not null, orgid integer not null, globadmin integer not null, orgadmin integer not null, isataxofficer integer not null, managesto integer not null, active text, usertype integer not null, tempdob text, taxpayerid text, corporateid text, usermanages text not null, comaseparatedroles text, userrole text, corporates text, taxpayers text, userroles text);";

    private static final String CREATE_TABLE_4 = "create table taxitem (taxitemid integer primary key not null , "+
            "itemname text not null, taxtypeid integer not null, computationtype text, percentagevalue text, fixedamount text, percentagedisplay text, activeflag integer, usercreate text, createdate text, lastupdate text, lastupdatedate text, hass text, atc text, tiucat text, isactive text, typename text not null, erp text, bp text, typecode text);";


    private static final String CREATE_TABLE_5 = "create table CorporateInfo (corporate_id integer primary key not null , "+
            "corporate_name text, office_ad text, usercreate text, email text not null, business_type text, incorp text, rc_number text, tin text, customer_id text, userid integer, createdate text);";

    private static final String CREATE_TABLE_7 = "create table CustomerPersonalDetails (customerID INTEGER PRIMARY KEY NOT NULL , "+
            "surname text not null, firstname text not null, otherNames text not null, maidenName text, marital text not null, gender text not null, title text not null, dateofbirth text , email text not null, password text, securityquestion text, answer text, PreferredOffice text, address text, " +
            "phone text, city text, origin text, residence text, nation text, tin text not null, nin not null, occupation not null, bvn not null);";

    private static final String CREATE_TABLE_8 = "create table CustomerDetails (customerID INTEGER PRIMARY KEY NOT NULL , "+
            "surname text not null, firstname text not null, otherNames text not null, maidenName text, marital text not null, gender text not null, title text not null, dateofbirth text, status text not null );";



    List<String> editList = new ArrayList<>();

    SQLiteDatabase db;


    //FOREIGN KEY(user_id) REFERENCES userdetails(userid)

    public DataStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_2);
        sqLiteDatabase.execSQL(CREATE_TABLE_3);
        sqLiteDatabase.execSQL(CREATE_TABLE_4);
        sqLiteDatabase.execSQL(CREATE_TABLE_5);
        sqLiteDatabase.execSQL(CREATE_TABLE_6);
        sqLiteDatabase.execSQL(CREATE_TABLE_7);
        sqLiteDatabase.execSQL(CREATE_TABLE_8);
        this.db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS" + TABLE_NAME_2;
        String query3 = "DROP TABLE IF EXISTS" + TABLE_NAME_3;
        String query4 = "DROP TABLE IF EXISTS" + TABLE_NAME_4;
        String query5 = "DROP TABLE IF EXISTS" + TABLE_NAME_5;
        String query6 = "DROP TABLE IF EXISTS" + TABLE_NAME_6;
        String query7 = "DROP TABLE IF EXISTS" + TABLE_NAME_7;
        String query8 = "DROP TABLE IF EXISTS" + TABLE_NAME_8;
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);
        sqLiteDatabase.execSQL(query5);
        sqLiteDatabase.execSQL(query6);
        sqLiteDatabase.execSQL(query7);
        sqLiteDatabase.execSQL(query8);
        this.onCreate(sqLiteDatabase);

    }


    public void insertInvoice() {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        String query = "select * from invoice";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID_INVOICE, count);
        values.put(COLUMN_TIN , "0011");
        values.put(COLUMN_LASTNAME , "Chukumah");
        values.put(COLUMN_INVOICE_AMOUNT , "1500");
        values.put(COLUMN_AMOUNT_PAID , "1450");
        values.put(COLUMN_AMOUNT_OUTSTANDING , "50");

        db.insert(TABLE_NAME_2, null, values);
        db.close();

    }

    public ArrayList<String> getUserInfo(){
        ArrayList<String> pList = new ArrayList<>();
        db = getReadableDatabase();
        String query = "select * from users";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                int ID = cursor.getInt(0);
                int userID = cursor.getInt(1);
                String surnsme = cursor.getString(2);
                String firstname = cursor.getString(3);
                String email = cursor.getString(4);
                String usermanages = cursor.getString(5);
                pList.add(String.valueOf(ID));
                pList.add(String.valueOf(userID));
                pList.add(surnsme);
                pList.add(firstname);
                pList.add(email);
                pList.add(usermanages);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pList;
    }


    public void changePasscode(String email, byte[] newPass, String newPasscode){
        db = this.getWritableDatabase();
        //int i = getID(email);
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSCODE , newPass);
        values.put(COLUMN_STRING_PASSCODE , newPasscode);
        String query = "UPDATE  "+TABLE_NAME+ " " + " set "+COLUMN_STRING_PASSCODE+ "="+ "'" + newPasscode +"'" + " " + "where " + COLUMN_EMAIL + "=" + "'" + email +"'";
        db.execSQL(query);
        //db.update(TABLE_NAME,values, "id= " + i,null);
        db.close();
    }

    public int getID(String email){
        db = getReadableDatabase();
        String query = "select email,id from "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        int ID = 0;
        String a;
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0);
                if (a.equals(email)) { //checks if string a is equal to the name entered
                    ID = Integer.parseInt(cursor.getString(1));
                    System.out.println("This is the id: " + ID);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        System.out.println(ID);
        cursor.close();
        db.close();
        return ID;
    }

    public List<String> getAllInvoices(){
        List<String> pList = new ArrayList<>();
        db = getReadableDatabase();
        String query = "select tin from "+TABLE_NAME_2;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(0);
                pList.add(title);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return pList;
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllTaxTypes(){
        List<String> labels = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT typename FROM " + TABLE_NAME_6;

        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public List<String> searchTax(String typename) {
        List<String> labels = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "select itemname from " + TABLE_NAME_4 + " " + "WHERE typename = " + "'" + typename +"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        return labels;
    }

    /*public List<String> searchTax() {
        List<String> labels = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = "select itemname from " + TABLE_NAME_4;
        Cursor cursor = db.rawQuery(query, null);
        int a = 0;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getInt(0);
                if (a == (app_id)) { //checks if string a is equal to the name entered
                    System.out.println("ids are equal");
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return a;
    }*/

    public void insertCustomerDetails(Customer c) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();


        String query = "select * from CustomerDetails";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_CUSTOMER_ID, count);
        values.put(COLUMN_SURNAME, c.getSurname());
        values.put(COLUMN_FIRSTNAME, c.getFirst());
        values.put(COLUMN_OTHER_NAMES, c.getOther());
        values.put(COLUMN_MARITAL_STATUS, c.getMarital());
        values.put(COLUMN_TITLE, c.getTitle());
        values.put(COLUMN_DATEOFBIRTH, c.getBirth());
        values.put(COLUMN_GENDER, c.getSex());
        values.put(COLUMN_MAIDEN_NAME, c.getMaid());
        values.put(COLUMN_STAT, c.getStatus());

        db.insert(TABLE_NAME_8, null, values);
        db.close();

    }

    public List<Results> retreiveSaved () {
        db = this.getReadableDatabase();
        List<Results> customer = new ArrayList<>();
        String query = "SELECT surname,status FROM "+TABLE_NAME_8+" ORDER BY customerID DESC";
        Cursor cursor = db.rawQuery(query, null);
        String a;
        String b;
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0); //sets string a to username at cursor
                b = cursor.getString(1); //sets string a to status at cursor
                Results results = new Results(a,b);
                customer.add(results);
            }
            while(cursor.moveToNext());
        }
        return customer;
    }

    public CustomerPersonal getFilledDetails(int ID) {
        db = this.getReadableDatabase();
        String query = "SELECT surname,firstname,otherNames,maidenName,marital,gender,title,dateofbirth from "+TABLE_NAME_8+" WHERE customerID = " + ID;
        Cursor cursor = db.rawQuery(query, null);
        String Surname;
        String First;
        String other;
        String maid;
        String marital;
        String gender;
        String title;
        String dateof;
        CustomerPersonal customer = null;
        if(cursor.moveToFirst()){
            do {
                Surname = cursor.getString(0); //sets string a to username at cursor
                First = cursor.getString(1); //sets string a to firstname at cursor
                other = cursor.getString(2);
                maid = cursor.getString(3);
                marital = cursor.getString(4);
                gender = cursor.getString(5);
                title = cursor.getString(6);
                dateof = cursor.getString(7);
                customer = new CustomerPersonal(Surname,First,other,maid,marital,gender,title,dateof);
            }
            while(cursor.moveToNext());
        }
        return customer;
    }

    public void insertCustomer(Customer c) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();


        String query = "select * from CustomerPersonalDetails";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_CUSTOMER_ID, count);
        values.put(COLUMN_SURNAME, c.getSurname());
        values.put(COLUMN_FIRSTNAME, c.getFirst());
        values.put(COLUMN_OTHER_NAMES, c.getOther());
        values.put(COLUMN_TIN, c.getTin());
        values.put(COLUMN_PHONE_NUMBER, c.getNumber());
        values.put(COLUMN_MARITAL_STATUS, c.getMarital());
        values.put(COLUMN_PASSWORD, c.getPass());
        values.put(COLUMN_CITY, c.getCity());
        values.put(COLUMN_NATION, c.getNation());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_ADDRESS, c.getAddress());
        values.put(COLUMN_NIN, c.getNin());
        values.put(COLUMN_OCCUPATION, c.getOccupation());
        values.put(COLUMN_BVN, c.getBvn());
        values.put(COLUMN_QUESTION, c.getQuestion());
        values.put(COLUMN_ANSWER, c.getAns());
        values.put(COLUMN_TITLE, c.getTitle());
        values.put(COLUMN_DATEOFBIRTH, c.getBirth());
        values.put(COLUMN_PREF_OFFICE, c.getOffice());
        values.put(COLUMN_GENDER, c.getSex());
        values.put(COLUMN_MAIDEN_NAME, c.getMaid());
        values.put(COLUMN_STATE_OF_ORIGIN, c.getOrign());
        values.put(COLUMN_STATE_OF_RESIDENCE, c.getResidence());

        db.insert(TABLE_NAME_7, null, values);
        db.close();

    }

    public void insertContactDetails(ContactDetails c) {
        db = getWritableDatabase();
        ContentValues values = new ContentValues();


        String query = "select * from users";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_APP_USERID, c.getUserid());
        values.put(COLUMN_FIRSTNAME , c.getFirstname());
        values.put(COLUMN_SURNAME , c.getSurname());
        values.put(COLUMN_EMAIL , c.getEmail());
        values.put(COLUMN_UserManagesTaxOffice , c.getUsermanages());
        values.put(COLUMN_PASSCODE , c.getPass());
        values.put(COLUMN_STRING_PASSCODE , c.getPasscode());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public String searchEmail(String email) {
        db = this.getReadableDatabase();
        String query = "select email from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a = "";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(email)) { //checks if string a is equal to the name entered
                    //b = cursor.getString(1); // sets string b to password at cursor
                    System.out.println("names are equal");
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return a;
    }

    public String searchPass(String email) {
        db = this.getReadableDatabase();
        String query = "select email, passtextcode from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a;
        String b = "";
        //byte[] b = new byte[0];
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0); //sets string a to username at cursor
                if (a.equals(email)){ //checks if string a is equal to the name entered
                    //b = cursor.getBlob(1); // sets string b to password at cursor
                    b = cursor.getString(1); // sets string b to password at cursor
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        return b;
    }

    public int searchAPP_ID(int app_id) {
        db = this.getReadableDatabase();
        String query = "select userid from " + TABLE_NAME_3;
        Cursor cursor = db.rawQuery(query, null);
        int a = 0;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getInt(0);
                if (a == (app_id)) { //checks if string a is equal to the name entered
                    System.out.println("ids are equal");
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return a;
    }

    public void insertCorporateInfo(CorpInfo corpInfo) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CORPORATEID_INFO, corpInfo.getCorpIDInfo());
        values.put(COLUMN_CORPORATENAME, corpInfo.getCorpName());
        values.put(COLUMN_OFFICEADDRESS, corpInfo.getOfficeAddress());
        values.put(COLUMN_BUSINESSTYPE, corpInfo.getBusiness_type());
        values.put(COLUMN_INCORPORATIONDATE, corpInfo.getIncorp_date());
        values.put(COLUMN_RCNUMBER, corpInfo.getRc_number());
        values.put(COLUMN_CUSTOMERID, corpInfo.getCustomer_id());
        values.put(COLUMN_TIN, corpInfo.getTin());
        values.put(COLUMN_APP_USERID, corpInfo.getUserid());
        values.put(COLUMN_EMAIL, corpInfo.getEmail());
        values.put(COLUMN_USER_CREATE, corpInfo.getUsercreate());
        values.put(COLUMN_CREATE_DATE, corpInfo.getCreate_date());

        db.insert(TABLE_NAME_5, null, values);
        db.close();

    }

    public void insertTaxType(TaxType t) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TAXTYPEID , t.getTax_type_id());
        values.put(COLUMN_TYPENAME , t.getTypename());
        values.put(COLUMN_ORGANISATIONID , t.getOrg_id());
        values.put(COLUMN_ISINCOMETAX , t.getIsIncomeTax());
        values.put(COLUMN_ACTIVEFLAG, t.getActive());
        values.put(COLUMN_USER_CREATE , t.getUser_create());
        values.put(COLUMN_CREATE_DATE , t.getCreateDate());
        values.put(COLUMN_USER_LASTUPDATE , t.getUserLastUpdate());
        values.put(COLUMN_LAST_UPDATEDATE , t.getUpdatedate());
        values.put(COLUMN_myReturnID, t.getMyRID());
        values.put(COLUMN_ISINCOMETAXFLAG, t.getIsFlag());
        values.put(COLUMN_ISACTIVEFLAG , t.getIsAct());


        db.insert(TABLE_NAME_6, null, values);
        db.close();

    }

    public void insertTaxItem(Tax t) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TAXITEMID, t.getTaxItemID());
        values.put(COLUMN_ITEMNAME , t.getItemName());
        values.put(COLUMN_TAXTYPEID , t.getTaxTypeID());
        values.put(COLUMN_COMPUTATIONTYPE , t.getCom_type());
        values.put(COLUMN_PERCENTAGEVALUE , t.getPercentage());
        values.put(COLUMN_FIXEDAMOUNT , t.getFixed());
        values.put(COLUMN_PERCENTAGEDISPLAY , t.getPercentageDis());
        values.put(COLUMN_ACTIVEFLAG , t.getActive_flag());
        values.put(COLUMN_USER_CREATE , t.getUser_create());
        values.put(COLUMN_CREATE_DATE , t.getCreate_date());
        values.put(COLUMN_USER_LASTUPDATE , t.getUser_last());
        values.put(COLUMN_LAST_UPDATEDATE , t.getLastUpdate());
        values.put(COLUMN_HASSEPARATEFLOW , t.getFlow());
        values.put(COLUMN_APPLIESTOCATEGORY , t.getApptoCat());
        values.put(COLUMN_TaxItemUserCategory , t.gettIUCat());
        values.put(COLUMN_ISACTIVEFLAG , t.getIs_active());
        values.put(COLUMN_TYPENAME , t.getTypeName());
        values.put(COLUMN_ERPITEMCODE , t.getErp());
        values.put(COLUMN_BILLPREFIX , t.getBill_prefix());
        values.put(COLUMN_TYPECODE , t.getType_code());

        db.insert(TABLE_NAME_4, null, values);
        db.close();

    }

    public void insertSingleUser(SingleUser su) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        String query = "select * from userdetails";
        Cursor cursor = db.rawQuery(query, null);

        values.put(COLUMN_APP_USERID, su.getUserID());
        values.put(COLUMN_SURNAME , su.getSurname());
        values.put(COLUMN_FIRSTNAME_TWO , su.getFirstname());
        values.put(COLUMN_EMAILADDRESS , su.getEmailaddress());
        values.put(COLUMN_OTHERNAME , su.getOthername());
        values.put(COLUMN_DATEOFBIRTH , su.getDateofbirth());
        values.put(COLUMN_USERNAME , su.getUsername());
        values.put(COLUMN_PASSWORD_TWO , su.getPassword());
        values.put(COLUMN_ORGANISATIONID , su.getOrganisationID());
        values.put(COLUMN_IS_GLOBALADMIN , su.getIs_globaladmin());
        values.put(COLUMN_IS_ORGADMIN, su.getIs_organadmiin());
        values.put(COLUMN_IS_TAXOFFICER , su.getIs_taxofficer());
        values.put(COLUMN_MANAGES_TAXOFFICE , su.getManages_tax_office());
        values.put(COLUMN_ACTIVE , su.getActive());
        values.put(COLUMN_UserType , su.getUserType());
        values.put(COLUMN_tempDOB , su.getTemp());
        values.put(COLUMN_TAXPAYERID, su.getTax_payer_ID());
        values.put(COLUMN_CORPORATEID , su.getCoporates());
        values.put(COLUMN_UserManagesTaxOffice, su.getUserManagesTO());
        values.put(COLUMN_comaSeparatedRoles , su.getCsr());
        values.put(COLUMN_USERROLE , su.getUser_role());
        values.put(COLUMN_CORPORATEs , su.getCoporates());
        values.put(COLUMN_TAXPAYERs , su.getTaxpayers());
        values.put(COLUMN_userRoles , su.getUserroles());


        db.insert(TABLE_NAME_3, null, values);
        db.close();

    }

}
