package com.example.amtsdemo.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.pojo.Root;
import com.example.amtsdemo.pojo.UserHelper;
import com.example.amtsdemo.pojo.User_Insert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistration extends AppCompatActivity {
    EditText etName,etEmail,etPhone,etBirthDate,etPassword,etConfirmPassword;
    RadioButton rbMale,rbFemale;
    Spinner sCategory;
    Button btnConfirm,btnCancel;
    DatePickerDialog datePicker;
    String sName,sEmail,sPhone,sBirthdate,sPassword,sConfirmPassword,sGender,Category;
    Boolean bName,bEmail,bPhone,bBirthdate,bPassword,bConfirmPassword,bGender,bCategory;
    TextView tvLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //private UserOpenHelper mDB;
    private MainOpenHelper mDB;
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    long maxid;
    Date birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        loadLocale();
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etMobile);
        etBirthDate = findViewById(R.id.etBirthDate);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        sCategory = findViewById(R.id.spinnerCategory);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvMember);
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("user").child("uid");
        mAuth = FirebaseAuth.getInstance();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxid = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // mDB = new UserOpenHelper(this);
        mDB = new MainOpenHelper(this);

        init();
    }

    private void init() {
        etBirthDate.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            datePicker = new DatePickerDialog(UserRegistration.this,
                    (DatePicker view, int year1, int monthOfYear, int dayOfMonth) -> {
                        etBirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                        cldr.set(year1,monthOfYear,dayOfMonth);
                      birthDate = cldr.getTime();
                    }, year, month, day);
            bBirthdate = true;
            datePicker.getDatePicker().setMaxDate(new Date().getTime());
            datePicker.show();
        });
        /*ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Student");
        arrayList.add("AMTS Manager");
        arrayList.add("Collage Authority");*/
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.category_array) );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(arrayAdapter);
        sCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category = parent.getItemAtPosition(position).toString().trim();
                bCategory = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean r = Register();
                if(r){
                    //mDB.user_Insert(sName,sEmail,sPhone,sGender,sBirthdate,Category,sPassword);
                    //Log.i("UserRegistration","Value Inserted");
                    System.out.println(UserRegistration.calculateAge(birthDate));
                    int age = calculateAge(birthDate);
                    String strAge=String.valueOf(age);
                    Log.d("age",String.valueOf(age));
                    Call<User_Insert> responseCall1 = RetrofitClient.getInstance()
                            .getInterPreter().userInsert(sName,sEmail,sPhone,sGender,sBirthdate,Category,sPassword,age);
                    responseCall1.enqueue(new Callback<User_Insert>() {
                        @Override
                        public void onResponse(Call<User_Insert> call, Response<User_Insert> response) {
                            User_Insert response1 = response.body();
                            if(response1.error)
                            {
                                Toast.makeText(UserRegistration.this, "Error Occurred..", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent i = new Intent(UserRegistration.this,MainDashboard.class);
                                startActivity(i);
                                Toast.makeText(UserRegistration.this,"Successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(UserRegistration.this, "successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(UserRegistration.this,""+response1.message,Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User_Insert> call, Throwable t) {
                            Toast.makeText(UserRegistration.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });
                   // UserHelper userHelper = new UserHelper(sBirthdate,Category,sEmail,sGender,sPhone,sName,sPassword);
                    //reference.setValue(userHelper);
                    /*reference.child(String.valueOf(maxid+1)).setValue("user");
                    mAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful()){
                                               UserHelper userHelper = new UserHelper(sBirthdate,Category,sEmail,sGender,sPhone,sName,sPassword);
                                               reference.setValue(userHelper);
                                               FirebaseDatabase.getInstance().getReference("user")
                                                       .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                       .setValue(userHelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if(task.isSuccessful()){
                                                           Toast.makeText(UserRegistration.this,"user Registered successfully",Toast.LENGTH_LONG).show();
                                                       }
                                                   }
                                               });
                                           }
                                            }
                                        });
                                        }

                                }
                            });
*/
                    }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancel();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserRegistration.this,MainDashboard.class);
                startActivity(i);
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
        sPassword = etPassword.getText().toString();
        sConfirmPassword = etConfirmPassword.getText().toString();
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
        if(sPassword.length()==0){
            etPassword.setError(getResources().getString(R.string.Password));
            bPassword = false;
        }
        else if(sPassword.length()<=8){
            etPassword.setError(getResources().getString(R.string.Password_length_must_be_8));
            bPassword = false;
        }
        else {
            bPassword = true;
            bConfirmPassword = true;
        }
        if(sConfirmPassword.length()==0){
            etConfirmPassword.setError(getResources().getString(R.string.ReenterPassword));
            bConfirmPassword = false;
        }
        else{
            bPassword = true;
            bConfirmPassword = true;
        }

        if(sPassword.equals(sConfirmPassword)){
            bPassword = true;
            bConfirmPassword = true;
        }else {
            bPassword = false;
            bConfirmPassword = false;
        }


        if(bName && bEmail && bPhone && bBirthdate && bGender && bCategory && bPassword && bConfirmPassword){
        return true;
        }
        return false;
/*
        if(bName && bEmail && bPhone && bBirthdate && bGender && bCategory && bPassword && bConfirmPassword){
            Toast.makeText(com.example.amtsdemo.activity.UserRegistration.this,"Successful",Toast.LENGTH_LONG).show();
        }*/


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