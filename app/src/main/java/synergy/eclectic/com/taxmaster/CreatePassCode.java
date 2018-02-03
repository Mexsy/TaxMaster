package synergy.eclectic.com.taxmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import synergy.eclectic.com.taxmaster.DBinserter.ContactDetails;


public class CreatePassCode extends AppCompatActivity implements View.OnClickListener {
    private DataStorage ds = new DataStorage(this);
    private String firstname;
    private String email;
    private String surname;
    private int userid;
    private String taxO;
    private int UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pass_code);
        firstname = getIntent().getStringExtra("firstToPass");
        surname = getIntent().getStringExtra("lastToPass");
        userid = getIntent().getIntExtra("idToPass", 0);
        taxO = getIntent().getStringExtra("taxOfficeToPass");
        email = getIntent().getStringExtra("emailToPass");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.done_pass_button){
            System.out.println("This is the user id : " + UserID);
            EditText retrivedText1 = (EditText) findViewById(R.id.config_passcode);
            EditText retrivedText2 = (EditText) findViewById(R.id.config_pass);

            String pass1str = retrivedText1.getText().toString();
            String pass2str = retrivedText2.getText().toString();

            boolean success = false;

            if (pass1str.contains(" ")) {
                retrivedText1.setError("No Spaces Allowed");
                Toast.makeText(CreatePassCode.this, "No Spaces Allowed", Toast.LENGTH_SHORT).show();
            }

            if (pass2str.contains(" ")) {
                retrivedText2.setError("No Spaces Allowed");
                Toast.makeText(CreatePassCode.this, "No Spaces Allowed", Toast.LENGTH_SHORT).show();
            }

            if (pass1str.length()<5){
                Toast nameCheck = Toast.makeText(CreatePassCode.this, "Passcode must contain at least 5 digits", Toast.LENGTH_SHORT);
                nameCheck.show();
            }

            else if (!pass1str.equals(pass2str)) {
                //creates pop up message
                Toast passwordCheck = Toast.makeText(CreatePassCode.this, "Failed to sign up passcodes do not match", Toast.LENGTH_SHORT);
                passwordCheck.show();
            }
            else {
                //encrypt passcode here
                byte[] passcode = encryptPasscode(pass1str);
                // details will be inserted into database.
                ContactDetails c = new ContactDetails();
                c.setFirstname(firstname);
                c.setSurname(surname);
                c.setUserid(UserID);
                c.setUsermanages(taxO);
                c.setEmail(email);
                c.setPasscode(pass1str);
                c.setPass(passcode);

                SharedPreferences settings = getSharedPreferences("prefs", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("firstRun", false);
                editor.apply();

                ds.insertContactDetails(c);
                openPage();
            }
        }
        }

    private byte[] encryptPasscode(String passcode){
        byte[] encrypted = new byte[0];
        try {
            //String text = "Hello World";
            String key = "THISisMYKey0090$"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            //KeyGenerator generator = KeyGenerator.getInstance("AES");

            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            encrypted = cipher.doFinal(passcode.getBytes());
            System.err.println(new String(encrypted));
            System.out.println(new String(encrypted));
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(encrypted));
            System.err.println(decrypted);
            System.out.println(decrypted);
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
        return encrypted;
    }

    private void openPage() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}

