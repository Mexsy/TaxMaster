package synergy.eclectic.com.taxmaster.DBinserter;

/**
 * Created by Emeka Chukumah on 05/04/2017.
 */

public class CustomerPersonal {
    private String status;
    private String surname;
    private String marital;
    private String first;
    private String title;
    private String other;
    private String birth;
    private String sex;

    public CustomerPersonal(String sur,String first,String other,String maid,String marital,String sex,String title,String birth){
        this.surname = sur;
        this.first = first;
        this.other = other;
        this.maid = maid;
        this.marital = marital;
        this.sex = sex;
        this.title = title;
        this.birth = birth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    private String maid;
}
