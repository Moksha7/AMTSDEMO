package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.adapter.EmployeeListAdapter;
import com.example.amtsdemo.adapter.SeniorCitizenListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.EmployeePass;
import com.example.amtsdemo.model.SeniorCitizenPass;
import com.example.amtsdemo.pojo.Employee_Select;
import com.example.amtsdemo.pojo.SeniorCitizen_Select;
import com.example.amtsdemo.pojo.SeniorCitizen_Select_Data;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeniorCitizenList extends AppCompatActivity {
    /*private StudentPassOpenHelper sDB;
    private UserOpenHelper uDB;
    */
    private MainOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private SeniorCitizenListAdapter mAdapter;

   // List<SeniorCitizenPass> seniorCitizenPassList;
    List<SeniorCitizen_Select_Data> seniorCitizenPassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        loadLocale();
        /*sDB = new StudentPassOpenHelper(this);
        uDB = new UserOpenHelper(this);
        */
        mDB = new MainOpenHelper(this);
       //seniorCitizenPassList =  mDB.makeSeniorCitizenList();

        // mDB.onCreate();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewStudentList);
        String astatus = "INVALID";
        Call<SeniorCitizen_Select> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindSeniorCitizenList(astatus);
        responseCall1.enqueue(new Callback<SeniorCitizen_Select>() {
            @Override
            public void onResponse(Call<SeniorCitizen_Select> call, Response<SeniorCitizen_Select> response) {
                SeniorCitizen_Select response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(SeniorCitizenList.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
                else{
                    seniorCitizenPassList = response1.data;

                    mAdapter = new SeniorCitizenListAdapter(SeniorCitizenList.this,seniorCitizenPassList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(SeniorCitizenList.this));
                    mRecyclerView.setAdapter(mAdapter);


                    Toast.makeText(SeniorCitizenList.this, "successful",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<SeniorCitizen_Select> call, Throwable t) {
                Toast.makeText(SeniorCitizenList.this, "Error Occurred", Toast.LENGTH_LONG).show();
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