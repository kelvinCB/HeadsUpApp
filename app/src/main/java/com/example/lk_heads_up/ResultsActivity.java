package com.example.lk_heads_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }

    public void llamarViewMainActivity(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
