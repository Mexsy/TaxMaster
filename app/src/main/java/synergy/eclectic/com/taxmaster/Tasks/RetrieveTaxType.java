package synergy.eclectic.com.taxmaster.Tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import synergy.eclectic.com.taxmaster.MyThreads;

/**
 * Created by Emeka Chukumah on 24/02/2017.
 */

public class RetrieveTaxType extends Thread {
    private static RetrieveTaxType instance;
    //Application Base URL
    String baseURL = "http://166.78.174.228/lirs/";
    String get_tax_type = baseURL +  "mobile/GetTaxType";


    public String streamToString(InputStream toread) throws IOException {
        return null;
    }

    public void getData() throws IOException {
        InputStream inputStream = null;
        URL url = new URL(get_tax_type);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("GET");
        connection.setChunkedStreamingMode(0);
        connection.setDoInput(true);


        String resp = streamToString(connection.getInputStream());
        System.out.println("Validation Response: " + resp);
    }
}
