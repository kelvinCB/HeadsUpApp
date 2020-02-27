package com.example.lk_heads_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class activity_splah_screen extends AppCompatActivity {

    private final int DURACION_SPLASH = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splah_screen);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(activity_splah_screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }


        }, DURACION_SPLASH);
    }

}
