package synergy.eclectic.com.taxmaster.DBinserter;

/**
 * Created by Emeka Chukumah on 06/03/2017.
 */
public class FilingHistory {
    public String getFilingNO() {
        return filingNO;
    }

    public void setFilingNO(String filingNO) {
        this.filingNO = filingNO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFiling_date() {
        return filing_date;
    }

    public void setFiling_date(String filing_date) {
        this.filing_date = filing_date;
    }

    String filingNO;
    String type;
    String filing_date;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    String amount;
}
