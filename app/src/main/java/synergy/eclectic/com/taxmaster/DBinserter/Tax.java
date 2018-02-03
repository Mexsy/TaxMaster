package synergy.eclectic.com.taxmaster.DBinserter;

/**
 * Created by Emeka Chukumah on 28/02/2017.
 */

public class Tax {
    private int taxItemID,taxTypeID;
    private String tIUCat;

    public int getTaxItemID() {
        return taxItemID;
    }

    public void setTaxItemID(int taxItemID) {
        this.taxItemID = taxItemID;
    }

    public int getTaxTypeID() {
        return taxTypeID;
    }

    public void setTaxTypeID(int taxTypeID) {
        this.taxTypeID = taxTypeID;
    }

    public String getCom_type() {
        return com_type;
    }

    public void setCom_type(String com_type) {
        this.com_type = com_type;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getPercentageDis() {
        return percentageDis;
    }

    public void setPercentageDis(String percentageDis) {
        this.percentageDis = percentageDis;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public String getUser_create() {
        return user_create;
    }

    public void setUser_create(String user_create) {
        this.user_create = user_create;
    }

    public String getUser_last() {
        return user_last;
    }

    public void setUser_last(String user_last) {
        this.user_last = user_last;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getApptoCat() {
        return apptoCat;
    }

    public void setApptoCat(String apptoCat) {
        this.apptoCat = apptoCat;
    }

    public String gettIUCat() {
        return tIUCat;
    }

    public void settIUCat(String tIUCat) {
        this.tIUCat = tIUCat;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }

    public String getBill_prefix() {
        return bill_prefix;
    }

    public void setBill_prefix(String bill_prefix) {
        this.bill_prefix = bill_prefix;
    }

    private String itemName,percentage, percentageDis,user_create;
    private String create_date,fixed,user_last;
    private String lastUpdate,active_flag, flow, apptoCat;
    private String typeName;
    private String erp;
    private String bill_prefix,com_type;

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    private String type_code;

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    private String is_active;
}
