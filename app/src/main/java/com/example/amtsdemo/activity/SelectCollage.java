package com.example.amtsdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amtsdemo.R;

import java.util.Locale;

public class SelectCollage extends AppCompatActivity {
    //EditText etCollage;
    Button btnNext;
    String strCollage;
    Boolean bCollage;
    Spinner sCollage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_collage);
        loadLocale();
        btnNext = findViewById(R.id.btnNext);
        sCollage = findViewById(R.id.spinneCollage);
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
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bCollage){
                    Intent i1 = new Intent(SelectCollage.this,StudentList.class);
                    i1.putExtra("Collage",strCollage);
                    startActivity(i1);
                }
                else{
                    Toast.makeText(SelectCollage.this,getResources().getString(R.string.enter_collage_name),Toast.LENGTH_LONG).show();
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