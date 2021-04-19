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
import com.example.amtsdemo.adapter.StudentListAdapter;
import com.example.amtsdemo.adapter.UserListAdapter;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.pojo.Collage_Select;
import com.example.amtsdemo.pojo.Collage_Select_Data;
import com.example.amtsdemo.pojo.UserListData;
import com.example.amtsdemo.pojo.User_List;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserList extends AppCompatActivity {
    private MainOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;
    List<UserListData> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        loadLocale();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerviewStudentList);

        Call<User_List> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().userList("user");
        responseCall1.enqueue(new Callback<User_List>() {
            @Override
            public void onResponse(Call<User_List> call, Response<User_List> response) {
                User_List response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(UserList.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
                else{
                    userList = response1.data;
                    mAdapter = new UserListAdapter(UserList.this,userList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(UserList.this));
                    mRecyclerView.setAdapter(mAdapter);

                    Toast.makeText(UserList.this, "successful",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<User_List> call, Throwable t) {
                Toast.makeText(UserList.this, "Error Occurred", Toast.LENGTH_LONG).show();
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