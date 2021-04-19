package com.example.amtsdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.pojo.SeniorCitizen_Status;
import com.example.amtsdemo.pojo.Student_Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeniorCitizenBusPassActivity extends AppCompatActivity {

    TextView textViewName,textViewEmail,textViewPhone,textViewCategory,textViewBirthdate,textViewSource,textViewEndDate,textViewDestination,textViewStartDate;
    String strName,strEmail,strPhone,strCategory,strBirthdate,strSource,strDestination,strStartdate,strEnddate,strStatus;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_citizenbus_pass);
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewCategory = findViewById(R.id.textViewCategory);
        textViewBirthdate = findViewById(R.id.textViewBirthdate);
        textViewSource = findViewById(R.id.textViewSource);
        textViewDestination = findViewById(R.id.textViewDestination);

        uid = getIntent().getIntExtra("uid",-99);


        Call<SeniorCitizen_Status> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindSeniorcitizenPassStatus(uid);
        responseCall1.enqueue(new Callback<SeniorCitizen_Status>() {
            @Override
            public void onResponse(Call<SeniorCitizen_Status> call, Response<SeniorCitizen_Status> response) {
                SeniorCitizen_Status response1 = response.body();
                if (response1.error) {
                    Toast.makeText(SeniorCitizenBusPassActivity.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                } else {
                    strStatus = response1.data.get(0).getScstatus();
                    if(strStatus.matches("INVALID")){
                        textViewName.setText("Senior citizen Bus Pass Request is Pending");
                        textViewEmail.setVisibility(View.GONE);
                        textViewPhone.setVisibility(View.GONE);
                        textViewCategory.setVisibility(View.GONE);
                        textViewBirthdate.setVisibility(View.GONE);
                        textViewSource.setVisibility(View.GONE);
                        textViewDestination.setVisibility(View.GONE);
                    }
                    else{
                        textViewEmail.setVisibility(View.VISIBLE);
                        textViewPhone.setVisibility(View.VISIBLE);
                        textViewCategory.setVisibility(View.VISIBLE);
                        textViewBirthdate.setVisibility(View.VISIBLE);
                        textViewSource.setVisibility(View.VISIBLE);
                        textViewDestination.setVisibility(View.VISIBLE);
                        textViewName.setText(response1.data.get(0).getUname());
                        textViewEmail.setText("Email : "+response1.data.get(0).getEmail());
                        textViewPhone.setText("Mobile No : "+response1.data.get(0).getPhone());
                        textViewCategory.setText("Category : "+response1.data.get(0).getCategory());
                        textViewBirthdate.setText("Birth Date : "+response1.data.get(0).getBirthday());
                        textViewSource.setText("Source : "+response1.data.get(0).getSource());
                        textViewDestination.setText("Destination : "+response1.data.get(0).getDestination());
                    }
                    Toast.makeText(SeniorCitizenBusPassActivity.this, "successful", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SeniorCitizen_Status> call, Throwable t) {
                Toast.makeText(SeniorCitizenBusPassActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

    }
}