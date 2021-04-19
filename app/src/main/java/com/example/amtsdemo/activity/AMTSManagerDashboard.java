package com.example.amtsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.R;

public class AMTSManagerDashboard extends AppCompatActivity {

    ImageView istudent,iemployee,isenior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        istudent = findViewById(R.id.imgStudent);
        iemployee = findViewById(R.id.imgEmployee);
        isenior = findViewById(R.id.imgSenior);

        istudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AMTSManagerDashboard.this,show_student_request.class);
                startActivity(i);
            }
        });

        iemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AMTSManagerDashboard.this,EmployeeList.class);
                startActivity(i);
            }
        });

        isenior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AMTSManagerDashboard.this,SeniorCitizenList.class);
                startActivity(i);
            }
        });
    }
}