package synergy.eclectic.com.taxmaster.DBinserter;

/**
 * Created by Emeka Chukumah on 09/03/2017.
 */

public class TaxType {
    String tax_type_id;

    public String getTax_type_id() {
        return tax_type_id;
    }

    public void setTax_type_id(String tax_type_id) {
        this.tax_type_id = tax_type_id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getIsIncomeTax() {
        return isIncomeTax;
    }

    public void setIsIncomeTax(String isIncomeTax) {
        this.isIncomeTax = isIncomeTax;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUser_create() {
        return user_create;
    }

    public void setUser_create(String user_create) {
        this.user_create = user_create;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserLastUpdate() {
        return lastUpdate;
    }

    public void setUserLastUpdate(String userlastUpdate) {
        this.lastUpdate = userlastUpdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getMyRID() {
        return myRID;
    }

    public void setMyRID(String myRID) {
        this.myRID = myRID;
    }

    public String getIsFlag() {
        return IsFlag;
    }

    public void setIsFlag(String isFlag) {
        IsFlag = isFlag;
    }

    public String getIsAct() {
        return isAct;
    }

    public void setIsAct(String isAct) {
        this.isAct = isAct;
    }

    String typename;
    String org_id;
    String isIncomeTax;
    String active;
    String user_create;
    String createDate;
    String lastUpdate;
    String updatedate;
    String myRID;
    String IsFlag;
    String isAct;
}
