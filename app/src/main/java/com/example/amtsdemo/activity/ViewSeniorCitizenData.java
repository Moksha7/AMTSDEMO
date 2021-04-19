package com.example.amtsdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.amtsdemo.adapter.SeniorCitizenListAdapter;
import com.example.amtsdemo.adapter.StudentListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.PassStudent;
import com.example.amtsdemo.model.SeniorCitizenPass;
import com.example.amtsdemo.pojo.Employee_Select;
import com.example.amtsdemo.pojo.SeniorCitizen_Show;
import com.example.amtsdemo.pojo.SeniorCitizen_Show_Data;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSeniorCitizenData extends AppCompatActivity {
    TextView Name,Email,Mobile,Source,Destination;
    MainOpenHelper DB;
    //SeniorCitizenPass s;
    private static final int POS = -99;
    Button btnNext;
    String id;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_senior_citizen_data);
        loadLocale();
        Name = findViewById(R.id.tvName);
        Email = findViewById(R.id.tvEmail);
        Mobile = findViewById(R.id.tvPhone);
        Source = findViewById(R.id.tvSource);
        Destination = findViewById(R.id.tvDestination);
        btnNext = findViewById(R.id.btnNext);

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {*/
            /*DB= new MainOpenHelper(this);
            int pos1 = extras.getInt(SeniorCitizenListAdapter.POS,POS);
            int pos = extras.getInt(SeniorCitizenListAdapter.EXTRA_ID,-99);
            s = DB.getSeniorCitizenData(pos);
            */
            int scid=getIntent().getIntExtra("SeniorCitizenId",-99);
            Call<SeniorCitizen_Show> responseCall1 = RetrofitClient.getInstance()
                    .getInterPreter().FindSeniorCitizenData(scid);
            responseCall1.enqueue(new Callback<SeniorCitizen_Show>() {
                @Override
                public void onResponse(Call<SeniorCitizen_Show> call, Response<SeniorCitizen_Show> response) {
                    SeniorCitizen_Show response1 = response.body();
                    if(response1.error)
                    {
                        Toast.makeText(ViewSeniorCitizenData.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                    }
                    else{
                        id = response1.data.get(0).getUid();
                        Name.append(" : "+ response1.data.get(0).getUname());
                        Email.append(" : "+ response1.data.get(0).getEmail());
                        Mobile.append(" : "+ response1.data.get(0).getPhone() +
                        "\n"+"\nGender : "+response1.data.get(0).getGender() +
                                "\n"+"\nBirthdate :"+response1.data.get(0).getBirthday());
                        Source.append(" : "+ response1.data.get(0).getSource());
                        Destination.append(" : "+ response1.data.get(0).getDestination() +
                                "\n"+"\nAmount :"+response1.data.get(0).getAmount() +
                                "\n \nStatus : "+response1.data.get(0).getStatus());
                        Toast.makeText(ViewSeniorCitizenData.this, "successful",Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<SeniorCitizen_Show> call, Throwable t) {
                    Toast.makeText(ViewSeniorCitizenData.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = Integer.parseInt(id);
                Intent i1 = new Intent(ViewSeniorCitizenData.this, ViewImage.class);
                i1.putExtra("uid", uid);
                startActivity(i1);
            }
        });

        //}
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