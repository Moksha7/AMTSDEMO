package com.example.amtsdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amtsdemo.R;

import java.util.Locale;

public class MainDashboard extends AppCompatActivity {

    ImageView iuser,iamts,icollage;
    TextView user,amtsManger,collage,admin,tvChangeLauguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_demoo);
        user = findViewById(R.id.user);
        amtsManger = findViewById(R.id.amtsManager);
        collage = findViewById(R.id.collage);
        admin = findViewById(R.id.admin);
        iuser = findViewById(R.id.img_user);
        iamts = findViewById(R.id.img_amtsmanager);
        icollage = findViewById(R.id.img_collage);
        tvChangeLauguage = findViewById(R.id.tvchangeLauguage);
        iuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,UserDashboard.class);
                startActivity(i);

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,UserDashboard.class);
                startActivity(i);
            }
        });

        icollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,CollageLogin.class);
                startActivity(i);
            }
        });

        iamts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,AmtsManagerLogin.class);
                startActivity(i);
            }
        });

        amtsManger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,AmtsManagerLogin.class);
                startActivity(i);
            }
        });

        collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,CollageLogin.class);
                startActivity(i);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboard.this,UserLogin.class);
                startActivity(i);
            }
        });

        tvChangeLauguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage(tvChangeLauguage);
            }
        });
    }

    public void changeLanguage(View view) {
        final String[] items ={"English","हिंदी","ગુજરાતી"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainDashboard.this);
        mBuilder.setTitle("Select Language");
        mBuilder.setSingleChoiceItems(items, -1, (dialog, i) -> {
            if(i==0){
                setLocale("en");
                recreate();
            }
            else if(i==1){
                setLocale("hi");
                recreate();
            }
            else if(i==2){
                setLocale("gu");
                recreate();
            }
            dialog.dismiss();
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
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