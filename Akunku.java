package com.example.tugaspmob_ayu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import java.util.HashMap;

public class Akunku extends Activity {
    Button logout;
    SessionManager session;
    ListView lv;
    ProgressDialog pDialog;
    JSONArray contacts = null;
    String username, first_name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akunku);
        //membuat session untuk user
            session = new SessionManager(getApplicationContext());
            Toast.makeText(getApplicationContext(), "User Login Status:" + session.isLoggedIn(), Toast.LENGTH_LONG).show();

            session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        username = user.get(SessionManager.KEY_USERNAME);
        first_name = user.get(SessionManager.KEY_FIRST_NAME);

        TextView status = (TextView)findViewById(R.id.status);
        status.setText(Html.fromHtml("Welcome,<b>"+first_name+"</b>"));

        //inisiasi tombol Logout dan memberi fungsi klik
        logout = (Button)findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(getApplicationContext(), Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

}
