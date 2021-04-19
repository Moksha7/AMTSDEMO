package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.pojo.Student_Show;
import com.example.amtsdemo.pojo.User_Show;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUserData extends AppCompatActivity {
    TextView tvName,tvEmail,tvMobile,tvCategory,tvBirthdate,tvGender;

    int uid;
    String id;
    //PassStudent s;
    private static final int POS = -99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_data);
        loadLocale();
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvPhone);
        tvBirthdate = findViewById(R.id.tvBirthdate);
        tvCategory = findViewById(R.id.tvCategory);
        tvGender = findViewById(R.id.tvGender);

        int uid = getIntent().getIntExtra("uid",-99);
         Call<User_Show> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().findUserData(uid);
         responseCall1.enqueue(new Callback<User_Show>() {
                                  @Override
                                  public void onResponse(Call<User_Show> call, Response<User_Show> response) {
                                      User_Show response1 = response.body();
                                      if (response1.error) {
                                          Toast.makeText(ViewUserData.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                                      } else {
                                          tvName.append(" : " + response1.data.getUname());
                                          tvEmail.append(" : " + response1.data.getUemail());
                                          tvMobile.append(" : " + response1.data.getUmobile());
                                          tvBirthdate.append(" : " + response1.data.getUbirthdate());
                                          tvCategory.append(" : " + response1.data.getUcategory());
                                          tvGender.append(" : " + response1.data.getUgender());

                                          Toast.makeText(ViewUserData.this, "successful", Toast.LENGTH_LONG).show();
                                      }

                                  }

                                  @Override
                                  public void onFailure(Call<User_Show> call, Throwable t) {
                                      Toast.makeText(ViewUserData.this, "Error Occurred", Toast.LENGTH_LONG).show();
                                  }
                              });




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