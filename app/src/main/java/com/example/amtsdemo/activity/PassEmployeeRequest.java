package com.example.amtsdemo.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.CollageOpenHelper;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.pojo.Pass_Insert;
import com.example.amtsdemo.pojo.User_Insert;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassEmployeeRequest extends AppCompatActivity {
    EditText etJobProfile,etAddress,etStartDate,etEndDate;
    DatePickerDialog dStartDate,dEndDate;
    Spinner sSource,sDestination;
    Button btnPassRequest,btnCancel;
    String strSource,strDestination,strJobProfile,strAddress,strStartDate,strEndDate,strTotaldays;
    Boolean bSource,bDestination,bJobProfile,bAddress,bStartDate,bEndDate;
    MainOpenHelper mDB;
    final Calendar cldr = Calendar.getInstance();
    //int day,month,year;
    final Calendar sldr = Calendar.getInstance();
    Date startDate,endDate;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_employee_request);
        loadLocale();
        mDB = new MainOpenHelper(this);
        etJobProfile = findViewById(R.id.etJobProfile);
        etAddress = findViewById(R.id.etJobAddress);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        sSource = findViewById(R.id.spinnerSource);
        sDestination = findViewById(R.id.spinnerDestination);
        btnPassRequest = findViewById(R.id.btnPassRequest);
        btnCancel = findViewById(R.id.btnCancel);
        uid=getIntent().getIntExtra("uid",0);
        Log.d("EmployeeId", String.valueOf(uid));
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

        etStartDate.setOnClickListener(v -> {
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            cldr.add(month,1);
            // date picker dialog
            dStartDate = new DatePickerDialog(PassEmployeeRequest.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        etStartDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        cldr.set(year1,monthOfYear,dayOfMonth);
                        startDate= cldr.getTime();
                        Log.i("day",startDate.toString());
                        sldr.set(year1,monthOfYear,dayOfMonth);
                    }, year, month, day);

            dStartDate.getDatePicker().setMinDate(new Date().getTime());
            dStartDate.getDatePicker().setMaxDate(cldr.getTimeInMillis());
            dStartDate.show();
        });

        etEndDate.setOnClickListener(v -> {
            int day = sldr.get(Calendar.DAY_OF_MONTH);
            int month = sldr.get(Calendar.MONTH);
            int year = sldr.get(Calendar.YEAR);
            //sldr.compareTo(cldr);
            // date picker dialog

            dEndDate = new DatePickerDialog(PassEmployeeRequest.this,
                    (view, year1, monthOfYear, dayOfMonth) ->{
                        etEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        sldr.set(year1,monthOfYear,dayOfMonth);
                        endDate = sldr.getTime();
                        Log.i("day",endDate.toString());
                    },
                    year, month, day);


            //dEndDate.getDatePicker().setMinDate(Long.parseLong(etStartDate.getText().toString()));
            dEndDate.getDatePicker().setMinDate(cldr.getTime().getTime());
            sldr.add(month,2);
            dEndDate.getDatePicker().setMaxDate(sldr.getTimeInMillis());
            dEndDate.show();
        });

        btnPassRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                countTotalDays();
                Boolean i = doEmployeePassRequest();
                if(i)
                {

                    mDB.EmployeePass_insert("Employee",strJobProfile,strAddress,strSource,strDestination,strStartDate,strEndDate);
                    Call<Pass_Insert> responseCall2 = RetrofitClient.getInstance()
                            .getInterPreter().employeePassInsert("Employee",strJobProfile,strAddress,strSource,strDestination,strStartDate,strEndDate,strTotaldays,"INVALID",uid);
                    responseCall2.enqueue(new Callback<Pass_Insert>() {
                        @Override
                        public void onResponse(Call<Pass_Insert> call, Response<Pass_Insert> response) {
                            Pass_Insert response1 = response.body();
                            if(response1.error)
                            {
                                Toast.makeText(PassEmployeeRequest.this, "Error Occurred", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent i2 = new Intent(PassEmployeeRequest.this,UploadImage.class);
                                i2.putExtra("passid",response1.data.id);
                                i2.putExtra("uid",uid);
                                i2.putExtra("totaldays",strTotaldays);
                                Log.d("id", String.valueOf(response1.data.id));
                                startActivity(i2);
                                Toast.makeText(PassEmployeeRequest.this, "successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(PassEmployeeRequest.this,""+response1.message,Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Pass_Insert> call, Throwable t) {
                            Toast.makeText(PassEmployeeRequest.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });
                    /*Intent i2 = new Intent(PassEmployeeRequest.this,UploadImage.class);
                    startActivity(i2);
                    Toast.makeText(PassEmployeeRequest.this,"Successful",Toast.LENGTH_LONG).show();*/
                }
            }
        });
    }

    private void countTotalDays() {
        long diff = endDate.getTime() - startDate.getTime();
        Log.i("Days", String.valueOf(diff));
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        strTotaldays = String.valueOf(days);
        Log.i("Days",String.valueOf(days));
        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    private Boolean doEmployeePassRequest() {
        strJobProfile = etJobProfile.getText().toString().trim();
        strAddress = etAddress.getText().toString().trim();
        strStartDate = etStartDate.getText().toString();
        strEndDate = etEndDate.getText().toString();
        if(etJobProfile.getText().toString().length()==0){
            etJobProfile.setError(getResources().getString(R.string.enter_job_profile));
            etJobProfile.requestFocus();
            bJobProfile = false;
        }
        else{
            bJobProfile = true;
        }
        if(etAddress.getText().toString().length()==0){
            etAddress.setError(getResources().getString(R.string.enter_job_address));
            etAddress.requestFocus();
            bAddress = false;
        }
        else {
            bAddress = true;
        }
        if(etStartDate.getText().toString().length()==0){
            etStartDate.setError(getResources().getString(R.string.choose_start_date));
            etStartDate.requestFocus();
            bStartDate = false;
        }
        else if(strEndDate.equals(strStartDate)){
            bStartDate = false;
            etStartDate.setError(getResources().getString(R.string.start_date_and_end_date_can_not_be_same));
            etStartDate.requestFocus();
        }
        else {
            bStartDate = true;
        }
        if(etEndDate.getText().toString().length()==0){
            etEndDate.setError(getResources().getString(R.string.choose_end_date));
            etEndDate.requestFocus();
            bEndDate = false;
        }
        else if(strStartDate.equals(strEndDate)){
            bEndDate = false;
            etEndDate.setError(getResources().getString(R.string.start_date_and_end_date_can_not_be_same));
            etEndDate.requestFocus();
        }
        else{
            bEndDate = true;
        }
        if(strSource.equals(strDestination)){
            bSource = false;
            bDestination = false;
            sSource.requestFocus();
            sSource.setPrompt(getResources().getString(R.string.source_destination_not_same));
        }
        else {
            bSource = true;
            bDestination = true;
        }

        if(bJobProfile && bAddress && bSource && bDestination && bStartDate && bEndDate){
            return true;
        }
        return false;
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