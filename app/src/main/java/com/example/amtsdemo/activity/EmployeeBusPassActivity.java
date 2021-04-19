package com.example.amtsdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.pojo.Employee_Status;
import com.example.amtsdemo.pojo.Student_Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeBusPassActivity extends AppCompatActivity {

    TextView textViewName,textViewEmail,textViewPhone,textViewCategory,textViewBirthdate,textViewSource,textViewEndDate,textViewDestination,textViewStartDate;
    String strName,strEmail,strPhone,strCategory,strBirthdate,strSource,strDestination,strStartdate,strEnddate,strStatus;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_pass);
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewCategory = findViewById(R.id.textViewCategory);
        textViewBirthdate = findViewById(R.id.textViewBirthdate);
        textViewSource = findViewById(R.id.textViewSource);
        textViewDestination = findViewById(R.id.textViewDestination);
        textViewStartDate = findViewById(R.id.textViewStartDate);
        textViewEndDate = findViewById(R.id.textViewEndDate);

        uid = getIntent().getIntExtra("uid",-99);


        Call<Employee_Status> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().FindEmployeePassStatus(uid);
        responseCall1.enqueue(new Callback<Employee_Status>() {
            @Override
            public void onResponse(Call<Employee_Status> call, Response<Employee_Status> response) {
                Employee_Status response1 = response.body();
                if (response1.error) {
                    Toast.makeText(EmployeeBusPassActivity.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                } else {
                    strStatus = response1.data.get(0).getEstatus();
                    if(strStatus.matches("INVALID")){
                        textViewName.setText(R.string.employee_bus_pass);
                        textViewEmail.setVisibility(View.GONE);
                        textViewPhone.setVisibility(View.GONE);
                        textViewCategory.setVisibility(View.GONE);
                        textViewBirthdate.setVisibility(View.GONE);
                        textViewSource.setVisibility(View.GONE);
                        textViewDestination.setVisibility(View.GONE);
                        textViewStartDate.setVisibility(View.GONE);
                        textViewEndDate.setVisibility(View.GONE);
                    }
                    else{
                        textViewEmail.setVisibility(View.VISIBLE);
                        textViewPhone.setVisibility(View.VISIBLE);
                        textViewCategory.setVisibility(View.VISIBLE);
                        textViewBirthdate.setVisibility(View.VISIBLE);
                        textViewSource.setVisibility(View.VISIBLE);
                        textViewDestination.setVisibility(View.VISIBLE);
                        textViewStartDate.setVisibility(View.VISIBLE);
                        textViewEndDate.setVisibility(View.VISIBLE);
                        textViewName.setText(response1.data.get(0).getUname());
                        textViewEmail.setText("Email : "+response1.data.get(0).getEmail());
                        textViewPhone.setText("Mobile No : "+response1.data.get(0).getPhone());
                        textViewCategory.setText("Category : "+response1.data.get(0).getCategory());
                        textViewBirthdate.setText("Birth Date : "+response1.data.get(0).getBirthday());
                        textViewStartDate.setText("Start Date :"+response1.data.get(0).getStartdate());
                        textViewEndDate.setText("End Date : "+response1.data.get(0).getEnddate());
                        textViewSource.setText("Source : "+response1.data.get(0).getSource());
                        textViewDestination.setText("Destination : "+response1.data.get(0).getDestination());
                    }
                    Toast.makeText(EmployeeBusPassActivity.this, "successful", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Employee_Status> call, Throwable t) {
                Toast.makeText(EmployeeBusPassActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

    }
}