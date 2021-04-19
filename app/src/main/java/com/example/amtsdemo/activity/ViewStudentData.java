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
import com.example.amtsdemo.adapter.StudentListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.PassStudent;
import com.example.amtsdemo.pojo.SeniorCitizen_Show;
import com.example.amtsdemo.pojo.Student_Show;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewStudentData extends AppCompatActivity {
    TextView tvName,tvEmail,tvMobile,tvCollage,tvDepartment,tvEnrollment,tvYear;
    MainOpenHelper DB;
    Button btnNext;
    int uid;
    String id;
    //PassStudent s;
    private static final int POS = -99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_data);
        loadLocale();
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvMobile = findViewById(R.id.tvPhone);
        tvCollage = findViewById(R.id.tvCollage);
        tvDepartment = findViewById(R.id.tvDepartment);
        tvEnrollment = findViewById(R.id.tvEnrollment);
        tvYear = findViewById(R.id.tvYear);
        btnNext = findViewById(R.id.btnNext);

        int sid = getIntent().getIntExtra("StudentId",-99);
         Call<Student_Show> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindCollageStudentData(sid);
         responseCall1.enqueue(new Callback<Student_Show>() {
                                  @Override
                                  public void onResponse(Call<Student_Show> call, Response<Student_Show> response) {
                                      Student_Show response1 = response.body();
                                      if (response1.error) {
                                          Toast.makeText(ViewStudentData.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                                      } else {
                                          id=response1.data.get(0).getUid();
                                          tvName.append(" : " + response1.data.get(0).getUname());
                                          tvEmail.append(" : " + response1.data.get(0).getEmail());
                                          tvMobile.append(" : " + response1.data.get(0).getPhone() +
                                                  "\n"+"\nGender : "+response1.data.get(0).getGender() +
                                                  "\n"+"\nBirthdate :"+response1.data.get(0).getBirthday());
                                          tvCollage.append(" : " + response1.data.get(0).getCollage());
                                          tvDepartment.append(" : " + response1.data.get(0).getDepartment());
                                          tvEnrollment.append(" : " + response1.data.get(0).getEnrollment());
                                          tvYear.append(" : " + response1.data.get(0).getYear() +
                                                  "\n"+"\nAmount :"+response1.data.get(0).getAmount() +
                                                  "\n \nStatus : "+response1.data.get(0).getStatus());
                                          Toast.makeText(ViewStudentData.this, "successful", Toast.LENGTH_LONG).show();
                                      }

                                  }

                                  @Override
                                  public void onFailure(Call<Student_Show> call, Throwable t) {
                                      Toast.makeText(ViewStudentData.this, "Error Occurred", Toast.LENGTH_LONG).show();
                                  }
                              });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid=Integer.parseInt(id);
                Intent i1 = new Intent(ViewStudentData.this,ViewImage.class);
                i1.putExtra("uid",uid);
                startActivity(i1);
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