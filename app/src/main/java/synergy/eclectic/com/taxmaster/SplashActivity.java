package synergy.eclectic.com.taxmaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity implements Runnable {
    private ProgressDialog bar;
    //private MediaPlayer SplashSound;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Handler splashHandler = new Handler();
        splashHandler.postDelayed(this,4000);

        //bar = new ProgressDialog(this);//creates a new progress dialog.
       // bar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//sets what direction the bar runs in.
       // bar.setMessage("loading");//shows a message.
       // bar.show();//shows the progress bar.

        ProgressThread thread = new ProgressThread();// creates a new thread
        thread.start();//starts the thread.

    }


    @Override
    public void run()
    {

        SharedPreferences settings = getSharedPreferences("prefs", 0);
        boolean firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            // here run your first-time instructions.
            Intent intent = new Intent(this, ConfigurationActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    private class ProgressThread extends Thread{
        public void run(){
            for(int count = 0;count<=100;count++){
                try{
                    Thread.sleep(35);

                    if(count == 100){
                       // bar.dismiss();
                    }
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }

    }
}
