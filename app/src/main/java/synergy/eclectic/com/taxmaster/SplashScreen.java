package synergy.eclectic.com.taxmaster;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity implements Runnable {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        tv1= findViewById(R.id.splashtitle);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.otf");
        tv1.setTypeface(face);

        Handler splashHandler = new Handler();
        splashHandler.postDelayed(this,4000);
        ProgressThread thread = new ProgressThread();// creates a new thread
        thread.start();//starts the thread.
    }

    @Override
    public void run() {
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
    }


    private class ProgressThread extends Thread{
        public void run(){
            for(int count = 0;count<=100;count++){
                try{
                    Thread.sleep(35);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }

    }
}
