package com.example.tugaspmob_ayu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends Activity {
    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    EditText first_name,last_name,email,username,password;
    Intent a;
    private static String url = "http://192.168.122.1/login/register.php";
    Button register;
    TextView verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        register = (Button)findViewById(R.id.btn_register);
        verify = (TextView)findViewById(R.id.verify);
        first_name = (EditText)findViewById(R.id.fld_first);
        last_name = (EditText)findViewById(R.id.fld_last);
        email = (EditText)findViewById(R.id.fld_email);
        username = (EditText)findViewById(R.id.fld_username);
        password = (EditText)findViewById(R.id.fld_pwd);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new InputData().execute();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = new Intent(Register.this, Login.class);
                startActivity(a);
            }
        });
    }
    public class InputData extends AsyncTask<String, String, String>{
        String success;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Registering Account...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String strfirst_name = first_name.getText().toString();
            String strlast_name = last_name.getText().toString();
            String stremail = email.getText().toString();
            String strusername = username.getText().toString();
            String strpassword = password.getText().toString();

            List <NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("first_name",strfirst_name));
            params.add(new BasicNameValuePair("last_name",strlast_name));
            params.add(new BasicNameValuePair("email",stremail));
            params.add(new BasicNameValuePair("username",strusername));
            params.add(new BasicNameValuePair("password",strpassword));

            JSONObject json =
                    jsonParser.makeHttpRequest(url, "POST", params);
            try {
                success = json.getString("success");
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (success.equals("1")) {
                Toast.makeText(getApplicationContext(),"Registration Succesfully",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(),Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        finish();
    }
}
