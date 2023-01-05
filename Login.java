package com.example.tugaspmob_ayu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends Activity {
    Button login;
    Intent a;
    EditText username, password;
    TextView verify;
    String url, success;
    SessionManager session;
    AlertDialogManager alert = new AlertDialogManager();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.login);
        session = new SessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(),"user Login Status:" +
                session.isLoggedIn(), Toast.LENGTH_LONG).show();
        login = (Button)findViewById(R.id.btn_login);
        username = (EditText)findViewById(R.id.fld_username);
        password = (EditText)findViewById(R.id.fld_pwd);
        verify = (TextView)findViewById(R.id.verify);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url="http://192.168.122.1/login/login.php?" +
                        "username=" + username.getText().toString() +
                        "&password=" + password.getText().toString();
                if(username.getText().toString().trim().length()>0
                        && password.getText().toString().trim().length()>0){
                    new AmbilData().execute();
                }else{
                    alert.showAlertDialog(Login.this,"Login Failed...!",
                            "Silahkan isi username dan password",false);
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = new Intent(Login.this, Register.class);
                startActivity(a);
            }
        });
    }
    public class AmbilData extends AsyncTask<String,String,String> {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<
                HashMap<String, String>>();
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Loading Data...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            try {
                success = json.getString("success");
                Log.e("error", "nilai sukses=" + success);
                JSONArray hasil = json.getJSONArray("login");
                if (success.equals("1")) {
                    for (int i = 0; i < hasil.length(); i++) {
                        JSONObject c = hasil.getJSONObject(i);
                        //Storing each json item in variable
                        String username = c.getString("username").trim();
                        String email = c.getString("email").trim();
                        session.createLoginSession(username, email);
                        Log.e("ok", "ambil data");
                    }
                } else {
                    Log.e("Error", "tidak bisa ambil data 0");
                }
            } catch (Exception e) {
                Log.e("Error", "Tidak bisa ambil data 1");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(success.equals("1")){
                a = new Intent(Login.this, Akunku.class);
                startActivity(a);
                finish();
            }else{
                Toast.makeText(getBaseContext(), "Username/password incorrect!!", Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(Login.this, "Login Failed..",
                        "Username/Password is incorrect",false);
            }
        }
    }
}
