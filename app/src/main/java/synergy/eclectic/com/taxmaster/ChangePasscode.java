package synergy.eclectic.com.taxmaster;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ChangePasscode extends AppCompatActivity implements View.OnClickListener {
    private DataStorage ds = new DataStorage(this);
    private String key = "THISisMYKey0090$"; // 128 bit key
    Context context;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passcode);
        textView = (TextView) findViewById(R.id.newPassChange);
        Typeface face= Typeface.createFromAsset(getAssets(), "fonts/quicksandbold.otf");
        textView.setTypeface(face);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save_pass_button){
            EditText retrivedText1 = (EditText) findViewById(R.id.old_passcode);
            EditText retrivedText2 = (EditText) findViewById(R.id.new_passcode);
            EditText retrivedText3 = (EditText) findViewById(R.id.connew_pass);

            String old = retrivedText1.getText().toString();
            String new_pass = retrivedText2.getText().toString();
            String con_new = retrivedText3.getText().toString();

            context = this;

            SharedPreferences sharedpreferences = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
            String userEmail = sharedpreferences.getString("currentEmail","");

            if (userEmail.isEmpty()){
                System.out.println("it didnt work oh");
            }

            String password = ds.searchPass(userEmail);

            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            //KeyGenerator generator = KeyGenerator.getInstance("AES");

            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            // encrypt the text
            try {
                cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            /*
            String decrypted = "";
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


            if(password.equals(old)){
                if (new_pass.length()<5){
                    Toast nameCheck = Toast.makeText(ChangePasscode.this, "Passcode must contain at least 5 digits", Toast.LENGTH_SHORT);
                    nameCheck.show();
                }
                else if (!new_pass.equals(con_new)) {
                    //creates pop up message
                    Toast passwordCheck = Toast.makeText(ChangePasscode.this, "Failed to sign up passcodes do not match", Toast.LENGTH_SHORT);
                    passwordCheck.show();
                }
                else {
                    //encrypt passcode here
                    byte[] new_encrypted = doEncrypty(new_pass);
                    System.err.println("This is the old encrypted passcode: " + new String(password));
                    System.out.println("This is the new encrypted passcode: " +new String(new_encrypted));
                    // details will be inserted into database.
                    ds.changePasscode(userEmail,new_encrypted,new_pass);//put new encrypted passcode in database

                    String newpassword = ds.searchPass(userEmail);
                    // decrypt the text
                    //String decrypteded = decryptPasscode(newpassword);
                    System.out.println("This is the new encrypted password, obtained from database: " + new String(newpassword));
                    //System.out.println("new password is: " + decrypteded);
                    Toast passwordCorrectCheck = Toast.makeText(ChangePasscode.this, newpassword, Toast.LENGTH_SHORT);
                    passwordCorrectCheck.show();
                    Intent i = new Intent(ChangePasscode.this, MainActivity.class);
                    i.putExtra("Username", userEmail);
                    startActivity(i);
                }
            }
            else{
                Toast passwordCorrectCheck = Toast.makeText(ChangePasscode.this, "Your current passcode is incorrect", Toast.LENGTH_SHORT);
                passwordCorrectCheck.show();
            }
        }
    }

    private byte[] doEncrypty(String new_pass){
        byte[] new_encrypted = new byte[0];
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            new_encrypted = cipher.doFinal(new_pass.getBytes());


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new_encrypted;
    }

    private String decryptPasscode(byte[] password){
        // Create key and cipher
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String decrypted = "";
        try {
             decrypted = new String(cipher.doFinal(password));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return decrypted;
    }
}
