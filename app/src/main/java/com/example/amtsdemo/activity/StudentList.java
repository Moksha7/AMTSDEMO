package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.adapter.StudentListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.PassStudent;
import com.example.amtsdemo.pojo.Collage_Select;
import com.example.amtsdemo.pojo.Collage_Select_Data;
import com.example.amtsdemo.pojo.FindCollage;
import com.example.amtsdemo.pojo.Root;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentList extends AppCompatActivity {
    /*private StudentPassOpenHelper sDB;
    private UserOpenHelper uDB;
    */
    private MainOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private StudentListAdapter mAdapter;
    String strCollage;
    //List<PassStudent> studentList;
    List<Collage_Select_Data> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        loadLocale();
        /*sDB = new StudentPassOpenHelper(this);
        uDB = new UserOpenHelper(this);
        */
        mDB = new MainOpenHelper(this);
        strCollage = getIntent().getStringExtra("Collage");
        //studentList =  mDB.makeStudentList(strCollage);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewStudentList);

        Call<Collage_Select> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindCollageStudentList(strCollage);
        responseCall1.enqueue(new Callback<Collage_Select>() {
            @Override
            public void onResponse(Call<Collage_Select> call, Response<Collage_Select> response) {
                Collage_Select response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(StudentList.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
                else{
                    studentList = response1.data;
                    mAdapter = new StudentListAdapter(StudentList.this,studentList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(StudentList.this));
                    mRecyclerView.setAdapter(mAdapter);

                    Toast.makeText(StudentList.this, "successful",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Collage_Select> call, Throwable t) {
                Toast.makeText(StudentList.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

        // mDB.onCreate();

/*
        //mAdapter = new StudentListAdapter(this,studentList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
*/

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