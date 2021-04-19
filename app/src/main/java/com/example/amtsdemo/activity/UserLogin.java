package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.User;
import com.example.amtsdemo.pojo.Root;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLogin extends AppCompatActivity {
    EditText etEmail,etPassword;
    String email,password;
    Button btnLogin,btnCancel;
    TextView tvNewAccount;
  //  UserOpenHelper mDB;
    MainOpenHelper mDB;
    User user;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        loadLocale();
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword1);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        tvNewAccount = findViewById(R.id.tvNewAccount);
        //mDB = new UserOpenHelper(this);
        mDB = new MainOpenHelper(this);
        mAuth = FirebaseAuth.getInstance();
        user = new User();
        tvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLogin.this, UserRegistration.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });


    }

    private void init() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if(email.matches("admin@gmail.com")&& password.matches("admin@123")){
            Toast.makeText(UserLogin.this,"Admin Login Successful",Toast.LENGTH_LONG).show();
            Intent i = new Intent(UserLogin.this,UserList.class);
            startActivity(i);
        }

        else{
            etEmail.setError("please enter valid email");
            etEmail.requestFocus();
            etPassword.setError("please enter valid password");
            etPassword.requestFocus();
        }

    }

    private void loadLocale() {
        SharedPreferences sp = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sp.getString("Lang","");
        setLocale(language);
    }
    private void setLocale(String s) {
        Locale locale = new Locale(s);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("Lang",s);
        editor.apply();
    }
}