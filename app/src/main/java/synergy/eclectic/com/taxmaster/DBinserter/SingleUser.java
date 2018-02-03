package synergy.eclectic.com.taxmaster.DBinserter;

/**
 * Created by Emeka Chukumah on 26/02/2017.
 */

public class SingleUser {
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getUserManagesTO() {
        return userManagesTO;
    }

    public void setUserManagesTO(String userManagesTO) {
        this.userManagesTO = userManagesTO;
    }

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getCoporates() {
        return coporates;
    }

    public void setCoporates(String coporates) {
        this.coporates = coporates;
    }

    public String getTaxpayers() {
        return taxpayers;
    }

    public void setTaxpayers(String taxpayers) {
        this.taxpayers = taxpayers;
    }

    public String getUserroles() {
        return userroles;
    }

    public void setUserroles(String userroles) {
        this.userroles = userroles;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOrganisationID() {
        return organisationID;
    }

    public void setOrganisationID(int organisationID) {
        this.organisationID = organisationID;
    }

    public int getIs_globaladmin() {
        return is_globaladmin;
    }

    public void setIs_globaladmin(int is_globaladmin) {
        this.is_globaladmin = is_globaladmin;
    }

    public int getIs_organadmiin() {
        return is_organadmiin;
    }

    public void setIs_organadmiin(int is_organadmiin) {
        this.is_organadmiin = is_organadmiin;
    }

    public int getIs_taxofficer() {
        return is_taxofficer;
    }

    public void setIs_taxofficer(int is_taxofficer) {
        this.is_taxofficer = is_taxofficer;
    }

    public int getManages_tax_office() {
        return manages_tax_office;
    }

    public void setManages_tax_office(int manages_tax_office) {
        this.manages_tax_office = manages_tax_office;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getTax_payer_ID() {
        return tax_payer_ID;
    }

    public void setTax_payer_ID(String tax_payer_ID) {
        this.tax_payer_ID = tax_payer_ID;
    }

    public String getCorpID() {
        return corpID;
    }

    public void setCorpID(String corpID) {
        this.corpID = corpID;
    }

    private String surname,firstname,othername,dateofbirth,emailaddress,username,password,active,temp,userManagesTO,csr,user_role,coporates,taxpayers,userroles,tax_payer_ID,corpID;
    private int userID,organisationID,is_globaladmin,is_organadmiin,is_taxofficer,manages_tax_office,userType;
}
