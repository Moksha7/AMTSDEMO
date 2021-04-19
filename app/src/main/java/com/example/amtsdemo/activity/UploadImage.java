package com.example.amtsdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.pojo.Image;
import com.example.amtsdemo.pojo.User_Insert;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class UploadImage extends AppCompatActivity implements View.OnClickListener {
    private static final int READEXTERNALREQUESTCODE = 101;
    ImageView imageView;
    Button buttonSelect;
    Button buttonUpload;
    Button buttonNext;
    private Uri fileuri;
    private String path = null;
    File file = null;
    String type = null;
    EditText etAadhardCard;
    int uid,passid;
    String strTotaldays;
    boolean bAadharId;
    String strAadhardNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        loadLocale();
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonSelect = findViewById(R.id.buttonSelect);
        buttonUpload = findViewById(R.id.buttonUpload);
        etAadhardCard = findViewById(R.id.editTextImageName);
        buttonNext = findViewById(R.id.buttonNext);
        buttonSelect.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        uid = getIntent().getIntExtra("uid",0);
        passid = getIntent().getIntExtra("passid",0);
        strTotaldays = getIntent().getStringExtra("totaldays");
        if(ActivityCompat.checkSelfPermission(UploadImage.this, Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_GRANTED){
            //LoaderManager.getInstance(MainActivity.this).initLoader(CONTACT_LOAD,null,this);
        }
        else{
            if(ActivityCompat.checkSelfPermission(UploadImage.this, Manifest.permission.READ_EXTERNAL_STORAGE)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(UploadImage.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , READEXTERNALREQUESTCODE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            fileuri = data.getData();
            try {
                path = getRealpath(fileuri);
                if (fileuri != null && path == null) {
                    file = new File(fileuri.getPath());
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(fileuri.toString()));
                } else if (path != null) {
                    file = new File(path);
                    type = getContentResolver().getType(fileuri);
                }
                if (type.startsWith("image")) {
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), fileuri);
                    imageView.setImageBitmap(image);
                } else {
                    Toast.makeText(this, "Invalid Image", Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Toast.makeText(this, "Problem", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getRealpath(Uri fileuri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(UploadImage.this, fileuri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (cursor != null) {
            int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            String result = cursor.getString(column);
            cursor.close();
            return result;
        } else {
            return null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSelect:
                Intent intent;
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 101);
                break;
            case R.id.buttonUpload:
                if (fileuri == null && path == null) {
                    //uploadData();
                } else {
                    uploadProfile();

                }
                break;
            case R.id.buttonNext :
                if(bAadharId){
                Intent i = new Intent(UploadImage.this,PaymentActivity.class);
                i.putExtra("uid",uid);
                i.putExtra("passid",passid);
                i.putExtra("totaldays",strTotaldays);
                startActivity(i);}
                break;
        }

    }



    private void uploadProfile() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        RequestBody passuid= RequestBody.create(MediaType.parse("text/plain"), String.valueOf(uid));

        Call<Image> call = RetrofitClient.getInstance().getInterPreter().uploadImage(requestBody, body , passuid);
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Image response1 = response.body();
                if (response1.error) {
                    Toast.makeText(UploadImage.this, "Error while Image Uploading", Toast.LENGTH_SHORT).show();

                } else {
                    int imgid = response1.data.id;
                    String imagepath = response1.message;
                    Log.d("UploadImage", String.valueOf(imgid));
                    Toast.makeText(UploadImage.this,response1.message,Toast.LENGTH_LONG).show();
                    uploadData(imgid,imagepath);
                    //Toast.makeText(UploadImage.this,"Image Successfully Uploaded",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Toast.makeText(UploadImage.this, "Error while Image Uploading", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadData(int uploadid,String imagepath) {
        if(etAadhardCard.getText().toString().isEmpty()){
            etAadhardCard.requestFocus();
            etAadhardCard.setError("Enter your aadhardcard number");
            bAadharId = false;
        }
        else if(etAadhardCard.getText().toString().length()!=12){
            etAadhardCard.requestFocus();
            etAadhardCard.setError("Please enter valid aadhardcard number");
            bAadharId = false;
        }
        else {
            strAadhardNo = etAadhardCard.getText().toString();
            // int aadharcardid = Integer.parseInt(strAadhardNo);
            Log.d("UploadImage", strAadhardNo);
            Log.d("UploadImage", String.valueOf(uid));
            Log.d("UploadImage", String.valueOf(passid));
            Log.d("UploadImage", String.valueOf(uploadid));
            bAadharId = true; }

        if(bAadharId) {
            Call<User_Insert> call = RetrofitClient.getInstance().getInterPreter().mapUploadId(uid, passid, strAadhardNo, uploadid, imagepath);
            call.enqueue(new Callback<User_Insert>() {
                @Override
                public void onResponse(Call<User_Insert> call, Response<User_Insert> response) {
                    User_Insert response1 = response.body();
                    if (response1.error) {
                        Toast.makeText(UploadImage.this, "Error while Image Uploading", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(UploadImage.this, response1.message, Toast.LENGTH_LONG).show();
                        //Toast.makeText(UploadImage.this,"Image Successfully Uploaded",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<User_Insert> call, Throwable t) {
                    Toast.makeText(UploadImage.this, "Error while Image Uploading", Toast.LENGTH_SHORT).show();

                }
            });

        }
        else{
            etAadhardCard.requestFocus();
            etAadhardCard.setError("Please enter valid aadhardcard number");
        }
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