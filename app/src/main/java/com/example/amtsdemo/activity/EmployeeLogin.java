package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.User;
import com.example.amtsdemo.pojo.Root;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeLogin extends AppCompatActivity {
    EditText etEmail,etPassword;
    String email,password;
    Button btnLogin,btnCancel;
    TextView tvNewAccount;
    //  UserOpenHelper mDB;
    MainOpenHelper mDB;
    User user;
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
        user = new User();
        tvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployeeLogin.this, UserRegistration.class);
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

        Call<Root> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().EmployeeLogin(email,password);
        responseCall1.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(EmployeeLogin.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent i1 = new Intent(EmployeeLogin.this,EmployeeDashboardActivity.class);
                    i1.putExtra("uid",response1.data.id);
                    i1.putExtra("uname",response1.data.name);
                    i1.putExtra("uemail",response1.data.email);
                    Log.d("EmployeeId", String.valueOf(response1.data.id));
                    startActivity(i1);
                    Toast.makeText(EmployeeLogin.this, "successful",Toast.LENGTH_LONG).show();
                    Toast.makeText(EmployeeLogin.this,""+response1.data.name,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(EmployeeLogin.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

        /*if(mDB.checkEmployee(email,password))
        {

            Intent i1 = new Intent(EmployeeLogin.this,PassEmployeeRequest.class);
            startActivity(i1);

        }
        else{
            Toast.makeText(EmployeeLogin.this,"Not Successful",Toast.LENGTH_LONG).show();
        }*/

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
