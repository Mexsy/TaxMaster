package synergy.eclectic.com.taxmaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Emeka Chukumah on 08/02/2016.
 */
public class Login extends Activity {
    DataStorage storage = new DataStorage(this);
    private String Email;
    SharedPreferences currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        }

        ImageButton imageButton = (ImageButton) findViewById(R.id.goOnlineButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://166.78.174.228/lirs/"));
                startActivity(browserIntent);
            }
        });
        TextView textView = (TextView) findViewById(R.id.goOnline);
        TextView textViewForgot = (TextView) findViewById(R.id.act);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://166.78.174.228/lirs/"));
                startActivity(browserIntent);
            }
        });

        Button signIN = (Button) findViewById(R.id.email_sign_in_button);
        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText a = (EditText) findViewById(R.id.email);
                Email = a.getText().toString();

                EditText b = (EditText) findViewById(R.id.password);
                String pass = b.getText().toString();

                String key = "THISisMYKey0090$"; // 128 bit key
                // Create key and cipher
                Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                //KeyGenerator generator = KeyGenerator.getInstance("AES");
                String decrypted = "";

                Cipher cipher = null;
                try {
                    cipher = Cipher.getInstance("AES");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                }

                /*
                // decrypt the text
                try {
                    cipher.init(Cipher.DECRYPT_MODE, aesKey);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
                try {
                     decrypted = new String(cipher.doFinal(password));
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
                */

                if (Email.equals("") || pass.equals("") && Email.isEmpty() || pass.isEmpty()) {
                    Toast nullChecker = Toast.makeText(Login.this, "Please enter password and username", Toast.LENGTH_SHORT);
                    nullChecker.show();
                }
                else if (Email.contains(" ") && pass.contains(" ")) {
                        a.setError("No Spaces Allowed");
                        Toast.makeText(Login.this, "No Spaces Allowed", Toast.LENGTH_SHORT).show();
                }
                    else{
                        String password = storage.searchPass(Email);
                        if (pass.equals(password)) {
                            System.out.println("Login successful");
                            Intent i = new Intent(Login.this, MainActivity.class);
                            i.putExtra("Username", Email);
                            String EmailToPass = Email;
                            currentUser = getSharedPreferences("USER", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = currentUser.edit();
                            editor.putString("currentEmail", EmailToPass);
                            editor.apply();
                            startActivity(i);
                        } else {
                            Toast passwordChecker = Toast.makeText(Login.this, "Failed to login passcode or username incorrect", Toast.LENGTH_SHORT);
                            passwordChecker.show();
                            System.out.println("password" + password + "value" + pass);
                        }
                    }

                }
            //finish();
    });

    /**if(v.getId() == R.id.sign_up_button){
     Intent i = new Intent(this, SignUp.class);
     startActivity (i);
     }

     if(v.getId() == R.id.act){
     Intent i = new Intent(this, SignUp.class);
     startActivity (i);
     }*/
}

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //new AttemptLogin().execute();
        } else {
            //checkInternet();
        }
        return false;
    }
}
