package com.example.amtsdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.pojo.SeniorCitizen_Show;
import com.example.amtsdemo.pojo.ViewImageData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewImage extends AppCompatActivity {
    ImageView imageView;
    int uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        imageView = (ImageView) findViewById(R.id.imageView);
        uid = getIntent().getIntExtra("uid",-99);


        Call<ViewImageData> responseCall1 = RetrofitClient.getInstance()
                .getInterPreter().viewImage(uid);
        responseCall1.enqueue(new Callback<ViewImageData>() {
            @Override
            public void onResponse(Call<ViewImageData> call, Response<ViewImageData> response) {
                ViewImageData response1 = response.body();
                if(response1.error)
                {
                    Toast.makeText(ViewImage.this, "Error Occurred....", Toast.LENGTH_LONG).show();
                }
                else{
                    String path = response1.data.path;
                    Log.d("Path",path);
                    Glide.with(ViewImage.this) //1
                            .load(path)
                            .placeholder(R.drawable.ic_admin_background)
                            .error(R.drawable.ic_attendance_recap)
                            .skipMemoryCache(true) //2
                            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                            //4
                            .into(imageView);

                    Toast.makeText(ViewImage.this, "successful",Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ViewImageData> call, Throwable t) {
                Toast.makeText(ViewImage.this, "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

    }
}