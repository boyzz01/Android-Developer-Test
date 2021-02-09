package com.ardy.androiddevelopertest.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ardy.androiddevelopertest.R;
import com.ardy.androiddevelopertest.Util.PrefManager;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button login;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (user.equals("ardi") && pass.equals("123456")){
                        Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                        prefManager.setLogin(true);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void init() {
        username = findViewById(R.id.uname);
        password = findViewById(R.id.password);
        login = findViewById(R.id.masuk);



    }

    @Override
    protected void onStart() {
        prefManager = new PrefManager("data",this);

        if (prefManager.isLogin()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        super.onStart();
    }
}