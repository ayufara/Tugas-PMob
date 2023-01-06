package com.example.tugaspmob_ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText nim,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nim = findViewById(R.id.nim);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nimText = nim.getText().toString();
                String passwordText = password.getText().toString();
                if (nimText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Isikan Semua Data", Toast.LENGTH_SHORT).show();
                } else {
                    //jalankan query
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(nimText,passwordText);
                            if (userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Info Akun Tidak Valid", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String nama = userEntity.nama;
                                startActivity(new Intent(Login.this, HalamanLogin.class).putExtra("nama", nama));

                            }
                        }
                    }).start();
                }
            }
        });
    }
}