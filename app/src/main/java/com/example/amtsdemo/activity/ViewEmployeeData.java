package com.example.amtsdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.adapter.EmployeeListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.EmployeePass;
import com.example.amtsdemo.pojo.Employee_Show;
import com.example.amtsdemo.pojo.SeniorCitizen_Show;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewEmployeeData extends AppCompatActivity {
    TextView Name, Email, Mobile, Source, Destination, JobProfile, JobAddress, startDate, endDate;
    MainOpenHelper DB;
    Button btnNext;
    String id;
    int uid;
    //EmployeePass s;
    private static final int POS = -99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee_data);
        loadLocale();
        Name = findViewById(R.id.tvName);
        Email = findViewById(R.id.tvEmail);
        Mobile = findViewById(R.id.tvPhone);
        Source = findViewById(R.id.tvSource);
        Destination = findViewById(R.id.tvDestination);
        JobProfile = findViewById(R.id.tvJobProfile);
        JobAddress = findViewById(R.id.tvJobAddress);
        startDate = findViewById(R.id.tvStartdate);
        endDate = findViewById(R.id.tvEndDate);
        btnNext = findViewById(R.id.btnNext);
        int eid = getIntent().getIntExtra("EmployeeId",-99);

        Call<Employee_Show> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindEmployeeData(eid);
        responseCall1.enqueue(new Callback<Employee_Show>() {
            @Override
            public void onResponse(Call<Employee_Show> call, Response<Employee_Show> response) {
                Employee_Show response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(ViewEmployeeData.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                }
                else{
                    id=response1.data.get(0).getUid();
                    Name.append(" : " + response1.data.get(0).getUname());
                    Email.append(" : " + response1.data.get(0).getEmail());
                    Mobile.append(" : " + response1.data.get(0).getPhone() +
                            "\n"+"\nGender : "+response1.data.get(0).getGender() +
                            "\n"+"\nBirthdate :"+response1.data.get(0).getBirthday());
                    Source.append(" : " + response1.data.get(0).getSource());
                    Destination.append(" : " + response1.data.get(0).getDestination());
                    JobProfile.append(" : "+ response1.data.get(0).getJobprofile());
                    JobAddress.append(" : "+response1.data.get(0).getJobaddress());
                    startDate.append(" : "+response1.data.get(0).getStartdate());
                    endDate.append(" : "+response1.data.get(0).getEnddate() +
                            "\n"+"\nTotal Days : "+response1.data.get(0).getTotaldats() +
                    "\n"+"\nAmount :"+response1.data.get(0).getAmount() +
                            "\n \nStatus : "+response1.data.get(0).getStatus());
                    Toast.makeText(ViewEmployeeData.this, "successful",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Employee_Show> call, Throwable t) {
                Toast.makeText(ViewEmployeeData.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = Integer.parseInt(id);
                Intent i1 = new Intent(ViewEmployeeData.this, ViewImage.class);
                i1.putExtra("uid", uid);
                startActivity(i1);
            }
            });

       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DB = new MainOpenHelper(this);
            int pos1 = extras.getInt(EmployeeListAdapter.POS, POS);
            int pos = extras.getInt(EmployeeListAdapter.EXTRA_ID, -99);
            s = DB.getEmployeeData(pos);
            Name.append(" : " + s.getEname());
            Email.append(" : " + s.geteEmail());
            Mobile.append(" : " + s.geteMobile());
            Source.append(" : " + s.geteSource());
            Destination.append(" : " + s.geteDestination());
            JobProfile.append(" : "+s.geteJobProfile());
            JobAddress.append(" : "+s.geteJobAddress());
            startDate.append(" : "+s.geteStartDate());
            endDate.append(" : "+s.geteEndDate());
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