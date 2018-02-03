package synergy.eclectic.com.taxmaster;

/**
 * Created by Emeka Chukumah on 15/02/2017.
 */

public class Invoice {


    private String Tin, Name, Invoice_amt, amt_paid, amt_owed;

    String[] tins = {"0011","0012","0013"};
    String[] names = {"Ayo","Alalade","Doyin"};
    String[] invoice_a = {"1000","1200","1500"};
    String[] atm_p = {"200","1200","1400"};
    String[] atm_0 = {"800","0","100"};


    public String getTin() {
        return Tin;
    }

    public void setTin(String tin) {
        tin = tins[0];
        Tin = tin;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        name = names[0];
        Name = name;
    }

    public String getInvoice_amt() {
        return Invoice_amt;
    }

    public void setInvoice_amt(String invoice_amt) {
        invoice_amt = invoice_a[0];
        Invoice_amt = invoice_amt;
    }

    public String getAmt_paid() {
        return amt_paid;
    }

    public void setAmt_paid(String amt_paid) {
        amt_paid = atm_p[0];
        this.amt_paid = amt_paid;
    }

    public String getAmt_owed() {
        return amt_owed;
    }

    public void setAmt_owed(String amt_owed) {
        amt_owed = atm_0[0];
        this.amt_owed = amt_owed;
    }

}
