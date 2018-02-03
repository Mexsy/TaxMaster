package synergy.eclectic.com.taxmaster.DBinserter;

/**
 * Created by Emeka Chukumah on 08/02/2016.
 */
public class ContactDetails {
    public String getUsermanages() {
        return usermanages;
    }

    public void setUsermanages(String usermanages) {
        this.usermanages = usermanages;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }



    private String usermanages;
    private String surname;
    private String firstname;

    public byte[] getPass() {
        return pass;
    }

    public void setPass(byte[] pass) {
        this.pass = pass;
    }

    private byte[] pass;
    private String email;

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    private String passcode;
    private int userid;

}
