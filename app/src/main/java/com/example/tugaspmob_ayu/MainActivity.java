package com.example.tugaspmob_ayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nama,nim,password;
    TextView register;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nama = findViewById(R.id.nama);
        nim = findViewById(R.id.nim);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tambah entitas user
                UserEntity userEntity = new UserEntity();
                userEntity.setNama(nama.getText().toString());
                userEntity.setNim(nim.getText().toString());
                userEntity.setPassword(password.getText().toString());
                if (validateInput(userEntity)){
                    //operasi insert
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //register user
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Terdaftar", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                } else{
                    Toast.makeText(getApplicationContext(), "Isi Semua Data!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));

            }
        });

    }

    private Boolean validateInput(UserEntity userEntity){
        if (userEntity.getNama().isEmpty() || userEntity.getNim().isEmpty() || userEntity.getPassword().isEmpty()){
            return false;
        }
        return true;
    }
}