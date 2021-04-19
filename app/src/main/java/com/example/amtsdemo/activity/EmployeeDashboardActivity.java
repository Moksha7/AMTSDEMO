package com.example.amtsdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.amtsdemo.R;
import com.google.android.material.navigation.NavigationView;

public class EmployeeDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SAVED_IS_SIGNED_IN = "isSignedIn";
    private static final String SAVED_USER_NAME = "userName";
    private static final String SAVED_USER_EMAIL = "userEmail";
    private static final String SAVED_USER_TIMEZONE = "userTimeZone";

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    protected NavigationView navigationView;

    private View mHeaderView;
    private boolean mIsSignedIn = true;
    private String mUserName = null;
    private String mUserEmail = null;
    int mid = -99;
    private String mUserTimeZone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_view);

        mHeaderView = navigationView.getHeaderView(0);
        setSignedInState(mIsSignedIn);
        mid = getIntent().getIntExtra("uid",-99);
        mUserName = getIntent().getStringExtra("uname");
        mUserEmail = getIntent().getStringExtra("uemail");
        Log.d("AMTS","Student id"+String.valueOf(mid));
        Log.d("AMTS","Student id"+String.valueOf(mUserName));
        Log.d("AMTS","Student id"+String.valueOf(mUserEmail));

        navigationView.setNavigationItemSelectedListener(EmployeeDashboardActivity.this);

        if (savedInstanceState == null) {
            // Load the home fragment by default on startup
            openHomeFragment(mUserName);
        } else {
            // Restore state
            mIsSignedIn = savedInstanceState.getBoolean(SAVED_IS_SIGNED_IN);
            mUserName = savedInstanceState.getString(SAVED_USER_NAME);
            mUserEmail = savedInstanceState.getString(SAVED_USER_EMAIL);
            mUserTimeZone = savedInstanceState.getString(SAVED_USER_TIMEZONE);
            setSignedInState(mIsSignedIn);
        }

        showProgressBar();
        // Get the authentication helper

                    if (!mIsSignedIn) {
                        //doSilentSignIn(false);
                    } else {
                        hideProgressBar();
                    }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_IS_SIGNED_IN, mIsSignedIn);
        outState.putString(SAVED_USER_NAME, mUserName);
        outState.putString(SAVED_USER_EMAIL, mUserEmail);
        outState.putString(SAVED_USER_TIMEZONE, mUserTimeZone);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgressBar()
    {
        FrameLayout container = findViewById(R.id.fragment_container);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        container.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar()
    {
        FrameLayout container = findViewById(R.id.fragment_container);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    // Update the menu and get the user's name and email
    private void setSignedInState(boolean isSignedIn) {
        mIsSignedIn = isSignedIn;

        // Set the user name and email in the nav drawer
        TextView userName = mHeaderView.findViewById(R.id.user_name);
        TextView userEmail = mHeaderView.findViewById(R.id.user_email);

        if (isSignedIn) {
            userName.setText(mUserName);
            userEmail.setText(mUserEmail);
        } else {
            mUserName = null;
            mUserEmail = null;
            mUserTimeZone = null;

            userName.setText(mUserName);
            userEmail.setText(mUserEmail);
        }
    }

    // Load the "Home" fragment
    public void openHomeFragment(String userName) {
        HomeFragment fragment = HomeFragment.createInstance(userName);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        navigationView.setCheckedItem(R.id.mMyAccount);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id== R.id.mPassRequest){
            Intent i1 = new Intent(EmployeeDashboardActivity.this, PassEmployeeRequest.class);
            i1.putExtra("uid",mid);
            i1.putExtra("uname",mUserName);
            i1.putExtra("uemail",mUserEmail);
            startActivity(i1);
            Toast.makeText(EmployeeDashboardActivity.this, "You Clicked on Student Pass Request", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.mMyAccount) {
            Intent i1 = new Intent(EmployeeDashboardActivity.this, EmployeeDashboardActivity.class);
            startActivity(i1);
            Toast.makeText(EmployeeDashboardActivity.this, "You Clicked on My Account", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.mSettings) {
            Intent i2 = new Intent(EmployeeDashboardActivity.this, UpdateProfile.class);
            i2.putExtra("uid",mid);
            startActivity(i2);
            Toast.makeText(EmployeeDashboardActivity.this, "You Clicked on Settings", Toast.LENGTH_SHORT).show();
        }
        else if(id== R.id.mLogout){
            Intent i2 = new Intent(EmployeeDashboardActivity.this, MainDashboard.class);
            startActivity(i2);
            Toast.makeText(EmployeeDashboardActivity.this,"You Clicked on Logout",Toast.LENGTH_LONG).show();
        }
        else if(id==R.id.mPassStatus){
            Intent i1 = new Intent(EmployeeDashboardActivity.this,EmployeeBusPassActivity.class);
            i1.putExtra("uid",mid);
            startActivity(i1);
            Toast.makeText(EmployeeDashboardActivity.this, "You Clicked on Student bus pass status", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
