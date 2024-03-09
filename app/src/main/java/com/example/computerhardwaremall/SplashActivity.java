package com.example.computerhardwaremall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferences = getSharedPreferences("User", MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(preferences.getString("email",null) == null){
//                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                    finish();
//                }else {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
//                }
            }
        },3000);
    }
}