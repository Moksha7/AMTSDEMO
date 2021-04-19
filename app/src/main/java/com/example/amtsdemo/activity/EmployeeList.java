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
import com.example.amtsdemo.adapter.RequestStudentPassListAdapter;
import com.example.amtsdemo.adapter.StudentListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.EmployeePass;
import com.example.amtsdemo.model.PassStudent;
import com.example.amtsdemo.pojo.Collage_Select;
import com.example.amtsdemo.pojo.Employee_Select;
import com.example.amtsdemo.pojo.Employee_Select_Data;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeList extends AppCompatActivity {
    /*private StudentPassOpenHelper sDB;
    private UserOpenHelper uDB;
    */
    private MainOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private EmployeeListAdapter mAdapter;

    //List<EmployeePass> employeeList;
    List<Employee_Select_Data> employeeList;
    String astatus="INVALID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        loadLocale();
        /*sDB = new StudentPassOpenHelper(this);
        uDB = new UserOpenHelper(this);
        */
       /* mDB = new MainOpenHelper(this);
       employeeList =  mDB.makeEmployeeList();
*/
        // mDB.onCreate();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewStudentList);
        Call<Employee_Select> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindEmployeeList(astatus);
        responseCall1.enqueue(new Callback<Employee_Select>() {
            @Override
            public void onResponse(Call<Employee_Select> call, Response<Employee_Select> response) {
                Employee_Select response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(EmployeeList.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
                else{
                    employeeList = response1.data;
                    mAdapter = new EmployeeListAdapter(EmployeeList.this,employeeList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(EmployeeList.this));
                    mRecyclerView.setAdapter(mAdapter);

                    Toast.makeText(EmployeeList.this, "successful",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Employee_Select> call, Throwable t) {
                Toast.makeText(EmployeeList.this, "Error Occurred", Toast.LENGTH_LONG).show();
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