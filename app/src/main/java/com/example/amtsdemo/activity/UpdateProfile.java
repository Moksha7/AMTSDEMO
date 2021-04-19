package com.example.amtsdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.pojo.User_Insert;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {

    EditText etName,etEmail,etPhone,etBirthDate;
    RadioButton rbMale,rbFemale;
    Button btnConfirm,btnCancel;
    DatePickerDialog datePicker;
    String sName,sEmail,sPhone,sBirthdate,sGender;
    Boolean bName,bEmail,bPhone,bBirthdate,bGender;
    TextView tvLogin;
    Date birthDate;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        loadLocale();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etMobile);
        etBirthDate = findViewById(R.id.etBirthDate);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnUpdate);

        uid = getIntent().getIntExtra("uid",-99);
        init();
    }

    private void loadLocale() {
        SharedPreferences sp = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = sp.getString("Lang","");
        setLocale(language);
    }


    private void init() {
        etBirthDate.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            datePicker = new DatePickerDialog(UpdateProfile.this,
                    (DatePicker view, int year1, int monthOfYear, int dayOfMonth) -> {
                        etBirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        cldr.set(year1, monthOfYear, dayOfMonth);
                        birthDate = cldr.getTime();
                    }, year, month, day);
            bBirthdate = true;
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
            datePicker.show();
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean r = Register();
                if (r) {
                    System.out.println(UpdateProfile.calculateAge(birthDate));
                    int age = calculateAge(birthDate);
                    String strAge = String.valueOf(age);
                    Log.d("age", String.valueOf(age));
                    Call<User_Insert> responseCall1 = RetrofitClient.getInstance()
                            .getInterPreter().userUpdate(sName, sEmail, sPhone, sGender, sBirthdate,age,uid);
                    responseCall1.enqueue(new Callback<User_Insert>() {
                        @Override
                        public void onResponse(Call<User_Insert> call, Response<User_Insert> response) {
                            User_Insert response1 = response.body();
                            if (response1.error) {
                                Toast.makeText(UpdateProfile.this, "Error Occurred..", Toast.LENGTH_LONG).show();
                            } else {
                                Intent i = new Intent(UpdateProfile.this, DashboardActivity.class);
                                startActivity(i);
                                Toast.makeText(UpdateProfile.this, "Successful", Toast.LENGTH_LONG).show();
                                Toast.makeText(UpdateProfile.this, "successful", Toast.LENGTH_LONG).show();
                                Toast.makeText(UpdateProfile.this, "" + response1.message, Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User_Insert> call, Throwable t) {
                            Toast.makeText(UpdateProfile.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cancel();
                        }
                    });

                }
            }
        });
    }


    private void Cancel() {
    }

    public static int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();

        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }

        }
        return yearDifference;
    }

    private boolean Register() {
        sName = etName.getText().toString();
        sEmail = etEmail.getText().toString();
        sBirthdate = etBirthDate.getText().toString();
        sPhone = etPhone.getText().toString();
        //  sGender += (rbMale.isChecked()) ? "Male" : (rbFemale.isChecked()) ? "Female" : "";
        if(rbMale.isChecked()){
            sGender = "Male";
        }
        else if(rbFemale.isChecked()){
            sGender = "Female";
        }
        if(etName.getText().toString().length()==0)
        {
            etName.setError(getResources().getString(R.string.studentName));
            etName.requestFocus();
            bName = false;
        }
        else
        {
            bName = true;
        }
        if(etEmail.getText().toString().length()==0)
        {
            etEmail.setError(getResources().getString(R.string.email));
            etEmail.requestFocus();
            bEmail = false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError(getResources().getString(R.string.email_error1));
            etEmail.requestFocus();
            bEmail = false;
        }
        else
        {
            bEmail = true;
        }
        if(etBirthDate.getText().toString().length()==0)
        {
            etBirthDate.setError(getResources().getString(R.string.Select_BirthDate));
            etBirthDate.requestFocus();
            bBirthdate = false;
        }
        else
        {
            bBirthdate=true;
        }
        if(etPhone.getText().toString().length()==0)
        {
            etPhone.setError(getResources().getString(R.string.mobileno));
            etPhone.requestFocus();
            bPhone = false;
        }
        else if(!Patterns.PHONE.matcher(etPhone.getText().toString()).matches()){
            etPhone.setError(getResources().getString(R.string.mobileno));
            etPhone.requestFocus();
            bPhone=false;
        }
        else
        {
            bPhone=true;
        }
        if(sGender.length()==0)
        {
            rbMale.setError(getResources().getString(R.string.select_Gender));
            bGender = false;
        }
        else {
            bGender = true;
        }

        if(bName && bEmail && bPhone && bBirthdate && bGender){
            return true;
        }
        return false;
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

