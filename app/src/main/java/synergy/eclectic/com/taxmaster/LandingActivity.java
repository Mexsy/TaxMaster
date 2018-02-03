package synergy.eclectic.com.taxmaster;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class LandingActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private ImageButton imageButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        imageButton = (ImageButton) findViewById(R.id.mobile);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LandingActivity.this, Login.class);
                startActivity (i);
            }
        });
        imageButton1 = (ImageButton) findViewById(R.id.web_choice);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://166.78.174.228/lirs/"));
                startActivity(browserIntent);
            }
        });
    }
}
