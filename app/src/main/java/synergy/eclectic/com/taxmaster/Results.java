package synergy.eclectic.com.taxmaster;

/**
 * Created by Emeka Chukumah on 05/04/2017.
 */

public class Results {
    public String getSurname() {
        return surname;
    }

    public String getStatus() {
        return status;
    }

    private String surname;
    private String status;

    public Results(String sur, String stat){
        this.surname = sur;
        this.status = stat;
    }
}