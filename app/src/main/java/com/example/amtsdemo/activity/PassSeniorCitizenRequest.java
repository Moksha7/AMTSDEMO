package com.example.amtsdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.pojo.Pass_Insert;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassSeniorCitizenRequest extends AppCompatActivity {
    Spinner sSource,sDestination;
    Button btnPassRequest,btnCancel;
    String strSource,strDestination;
    Boolean bSource,bDestination;
    MainOpenHelper mDB;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_senior_citizen_request);
        loadLocale();
        sSource = findViewById(R.id.spinnerSource);
        sDestination = findViewById(R.id.spinnerDestination);
        btnPassRequest = findViewById(R.id.btnPassRequest);
        btnCancel = findViewById(R.id.btnCancel);
        mDB = new MainOpenHelper(this);
        uid = getIntent().getIntExtra("uid",0);
        init();
    }

    private void init() {
        ArrayAdapter<String> sourceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.area_array) );
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSource.setAdapter(sourceAdapter);
        sSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSource = parent.getItemAtPosition(position).toString();
                bSource = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bSource = false;
            }
        });


        ArrayAdapter<String> destinationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.area_array) );
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDestination.setAdapter(destinationAdapter);
        sDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strDestination = parent.getItemAtPosition(position).toString();
                bDestination = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bDestination = false;
            }
        });

        btnPassRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bSource && bDestination)
                {
                    mDB.seniorCitizenPass_insert("Senior Citizen",strSource,strDestination);
                    Call<Pass_Insert> responseCall2 = RetrofitClient.getInstance()
                            .getInterPreter().seniorCitizenPassInsert("Senior Citizen",strSource,strDestination,"INVALID",uid);
                    responseCall2.enqueue(new Callback<Pass_Insert>() {
                        @Override
                        public void onResponse(Call<Pass_Insert> call, Response<Pass_Insert> response) {
                            Pass_Insert response1 = response.body();
                            if(response1.error)
                            {
                                Toast.makeText(PassSeniorCitizenRequest.this, "Error Occurred..", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent i2 = new Intent(PassSeniorCitizenRequest.this,UploadImage.class);
                                i2.putExtra("passid",response1.data.id);
                                i2.putExtra("uid",uid);
                                i2.putExtra("totaldays","160");
                                Log.d("id", String.valueOf(response1.data.id));
                                Log.d("SeniorCitizenId",String.valueOf(uid));
                                startActivity(i2);
                                Toast.makeText(PassSeniorCitizenRequest.this, "successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(PassSeniorCitizenRequest.this,""+response1.message,Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Pass_Insert> call, Throwable t) {
                            Toast.makeText(PassSeniorCitizenRequest.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent i2 = new Intent(PassSeniorCitizenRequest.this,UploadImage.class);
                    startActivity(i2);
                    Toast.makeText(PassSeniorCitizenRequest.this,"Successful",Toast.LENGTH_LONG).show();
                }
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