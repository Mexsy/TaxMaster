package synergy.eclectic.com.taxmaster;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Emeka Chukumah on 24/02/2017.
 */

public interface MyThreads {
     public String streamToString(InputStream toread) throws IOException;
     void getData() throws IOException;
}
