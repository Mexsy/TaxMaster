package synergy.eclectic.com.taxmaster;

/**
 * Created by Emeka Chukumah on 08/03/2017.
 */

public class PaymentInfo {
    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getRefID() {
        return RefID;
    }

    public void setRefID(String refID) {
        RefID = refID;
    }

    String customer_id,invoice_number,amount,transaction_date,RefID;
}
