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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.database.CollageOpenHelper;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.database.StudentPassOpenHelper;
import com.example.amtsdemo.pojo.CollageHelper;
import com.example.amtsdemo.pojo.FindCollage;
import com.example.amtsdemo.pojo.Pass_Insert;
import com.example.amtsdemo.pojo.StudentPassHelper;
import com.example.amtsdemo.pojo.UserHelper;
import com.example.amtsdemo.pojo.User_Insert;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Long.parseLong;

public class Student_PASS_Request extends AppCompatActivity {
    StudentPassOpenHelper mSP;
    EditText etCollage,etDepartment,etEnrollmentNo,etStartDate,etEndDate;
    DatePickerDialog dStartDate,dEndDate;
    Spinner sCategory,sSource,sDestination,sYear,sCollage;
    Button btnPassRequest,btnCancel;
    int cid;
    String strCategory,strSource,strDestination,strCollage,strDepartment,strEnrollmentNo,strStartDate,strEndDate,strYear,strTotaldays;
    Boolean bCategory,bSource,bDestination,bCollage,bDepartment,bEnrollmentNo,bStartDate,bEndDate,bYear;
    CollageOpenHelper cOP;
    MainOpenHelper mDB;
    FirebaseDatabase rootNode;
    DatabaseReference reference1;
    DatabaseReference reference2;
    long maxSid,maxCid;
    Date startDate,endDate;
    final Calendar cldr = Calendar.getInstance();
    //int day,month,year;
    final Calendar sldr = Calendar.getInstance();
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__p_a_s_s__request);
        loadLocale();
        //mSP = new StudentPassOpenHelper(this);
        //cOP = new CollageOpenHelper(this);
        mDB = new MainOpenHelper(this);
        //sCategory = findViewById(R.id.spinnerCategory);
        //etCollage = findViewById(R.id.etCollage);
        sCollage = findViewById(R.id.spinneCollage);
        etDepartment = findViewById(R.id.etDepartment);
        etEnrollmentNo = findViewById(R.id.etEnNo);
        //pickerYear= findViewById(R.id.numberPickerYear);
        sYear = findViewById(R.id.spinnerYear);
        sSource = findViewById(R.id.spinnerSource);
        sDestination = findViewById(R.id.spinnerDestination);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        btnPassRequest = findViewById(R.id.btnPassRequest);
        btnCancel = findViewById(R.id.btnCancel);
        rootNode = FirebaseDatabase.getInstance();
        reference1 = rootNode.getReference("student_pass").child("sid");
        reference2 = rootNode.getReference("collage_data").child("cId");
        uid = getIntent().getIntExtra("uid",0);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxSid = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxCid = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        init();
    }

    private void init() {
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.category_array) );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategory.setAdapter(arrayAdapter);
        sCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCategory = parent.getItemAtPosition(position).toString();
                bCategory = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
*/

        ArrayAdapter<String> collageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Collage_array) );
        collageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCollage.setAdapter(collageAdapter);
        sCollage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCollage = parent.getItemAtPosition(position).toString();
                bCollage = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bYear = false;
            }
        });

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.year_array) );
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sYear.setAdapter(yearAdapter);
        sYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strYear = parent.getItemAtPosition(position).toString();
                bYear = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bYear = false;
            }
        });



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
            //cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            cldr.add(month,1);
            // date picker dialog
            dStartDate = new DatePickerDialog(Student_PASS_Request.this,
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
            //final Calendar cldr = Calendar.getInstance();
             int day = sldr.get(Calendar.DAY_OF_MONTH);
             int month = sldr.get(Calendar.MONTH);
             int year = sldr.get(Calendar.YEAR);
            //sldr.compareTo(cldr);
            // date picker dialog

            dEndDate = new DatePickerDialog(Student_PASS_Request.this,
                    (view, year1, monthOfYear, dayOfMonth) ->{
                            etEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
                            sldr.set(year1,monthOfYear,dayOfMonth);
                            endDate = sldr.getTime();
                        Log.i("day",endDate.toString());
                        },
                    year, month, day);


            //dEndDate.getDatePicker().setMinDate(Long.parseLong(etStartDate.getText().toString()));
            dEndDate.getDatePicker().setMinDate(cldr.getTime().getTime());
            sldr.add(month,6);
            dEndDate.getDatePicker().setMaxDate(sldr.getTimeInMillis());
            dEndDate.show();
        });

        btnPassRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countTotalDays();
                Boolean i = doPassRequest();
                if(i)
                {
                    int id = (int) mDB.collage_insert(strCollage);
                    mDB.studentPass_insert("Student",id ,strDepartment,strEnrollmentNo,strYear,strSource,strDestination,strStartDate,strEndDate);
                   /* Intent i1 = new Intent(Student_PASS_Request.this,StudentList.class);
                    i1.putExtra("Collage",strCollage);
                    startActivity(i1);*/

                    Call<User_Insert> responseCall1 = RetrofitClient.getInstance()
                            .getInterPreter().collageDataInsert(strCollage);
                    responseCall1.enqueue(new Callback<User_Insert>() {
                        @Override
                        public void onResponse(Call<User_Insert> call1, Response<User_Insert> response1) {
                            User_Insert response2 = response1.body();
                            if(response2.error)
                            {
                                Toast.makeText(Student_PASS_Request.this, "Error Occurred", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Student_PASS_Request.this, "successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(Student_PASS_Request.this,""+response2.message,Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<User_Insert> call1, Throwable t1) {
                            Toast.makeText(Student_PASS_Request.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });

                    /*Call<FindCollage> responseCall3 = RetrofitClient.getInstance()
                            .getInterPreter().FindCollageId(strCollage);
                    responseCall3.enqueue(new Callback<FindCollage>() {
                        @Override
                        public void onResponse(Call<FindCollage> call, Response<FindCollage> response) {
                            FindCollage response1 = response.body();
                            if(response1.error)
                            {
                                Toast.makeText(Student_PASS_Request.this, "Error Occurred", Toast.LENGTH_LONG).show();
                            }
                            else{
                                cid= Integer.parseInt(response1.data.id);
                                Toast.makeText(Student_PASS_Request.this, "successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(Student_PASS_Request.this,""+response1.data.id,Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<FindCollage> call, Throwable t) {
                            Toast.makeText(Student_PASS_Request.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });
*/

                    Call<Pass_Insert> responseCall2 = RetrofitClient.getInstance()
                            .getInterPreter().studentPassInsert(strCollage,"Student",strDepartment,strEnrollmentNo,strYear,strSource,strDestination,strStartDate,strEndDate,strTotaldays,"INVALID","INVALID",uid);
                    responseCall2.enqueue(new Callback<Pass_Insert>() {
                        @Override
                        public void onResponse(Call<Pass_Insert> call, Response<Pass_Insert> response) {
                            Pass_Insert response1 = response.body();
                            if(response1.error)
                            {
                                Toast.makeText(Student_PASS_Request.this, "Error Occurred", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent i2 = new Intent(Student_PASS_Request.this,UploadImage.class);
                                i2.putExtra("uid",uid);
                                i2.putExtra("passid",response1.data.id);
                                i2.putExtra("totaldays",strTotaldays);
                                Log.d("id", String.valueOf(response1.data.id));
                                startActivity(i2);
                                Toast.makeText(Student_PASS_Request.this, "successful",Toast.LENGTH_LONG).show();
                                Toast.makeText(Student_PASS_Request.this,""+response1.message,Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Pass_Insert> call, Throwable t) {
                            Toast.makeText(Student_PASS_Request.this, "Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    });


  /*                  Task<Void> cid = reference2.child(String.valueOf(maxCid+1)).setValue("cId");
                    Task<Void> sid = reference1.child(String.valueOf(maxSid+1)).setValue("sId");

                     CollageHelper collageHelper = new CollageHelper(strCollage);
                     FirebaseDatabase.getInstance().getReference("collage_data")
                             .setValue(collageHelper).
                             addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 reference2.setValue(collageHelper);
                                 Toast.makeText(Student_PASS_Request.this,"collage Registered successfully",Toast.LENGTH_LONG).show();
                             }
                         }
                     });

                    StudentPassHelper studentPassHelper = new StudentPassHelper("Not Valid","Not Valid","Student",cid,strEndDate,strEnrollmentNo,strSource,strStartDate,strYear,strDepartment,strDestination,sid);
                    FirebaseDatabase.getInstance().getReference("student_pass")
                            .setValue(studentPassHelper)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                reference1.setValue(studentPassHelper);
                                Toast.makeText(Student_PASS_Request.this,"Student Pass Request successfully",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
*/

                    Toast.makeText(Student_PASS_Request.this,"Successful",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCancel();
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


    private void doCancel() {

    }

    private boolean doPassRequest() {
//        strCollage = etCollage.getText().toString();
        strDepartment = etDepartment.getText().toString();
        strEnrollmentNo = etEnrollmentNo.getText().toString();
        strStartDate = etStartDate.getText().toString();
        strEndDate= etEndDate.getText().toString();
        /*if(etCollage.getText().toString().length()==0)
        {
            etCollage.setError(getResources().getString(R.string.enter_collage_name));
            etCollage.requestFocus();
            bCollage = false;
        }
        else
        {
            bCollage = true;
        }*/
        if(etDepartment.getText().toString().length()==0){
            etDepartment.setError(getResources().getString(R.string.enter_department_name));
            etDepartment.requestFocus();
            bDepartment = false;
        }
        else{
            bDepartment = true;
        }
        if(etEnrollmentNo.getText().toString().length()==0){
            etEnrollmentNo.setError(getResources().getString(R.string.enter_enrollment_no));
            etEnrollmentNo.requestFocus();
            bEnrollmentNo = false;
        }
        else {
            bEnrollmentNo = true;
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

        if(bCollage && bDepartment && bEnrollmentNo && bYear && bSource && bDestination && bStartDate && bEndDate){
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