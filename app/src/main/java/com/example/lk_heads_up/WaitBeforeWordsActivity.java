package com.example.lk_heads_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class WaitBeforeWordsActivity extends AppCompatActivity {

    //Declarando variables
    Timer timer;
    int waitTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_before_words);
        TextView beforeWords = findViewById(R.id.textViewTiempoEspera);
        changeTextViewEverySecond(beforeWords);
    }


    public void changeTextViewEverySecond(TextView tiempoDeEspera) {


        final TextView finalTiempoDeEspera = tiempoDeEspera;
        Thread t = new Thread() {
            @Override
            public void run() {

                while (waitTime > 2) {
                    try {
                        Thread.sleep(1000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                waitTime--;
                                MediaPlayer mplayer1 = MediaPlayer.create(getApplicationContext(), R.raw.tic); // Hacen sonar cada segundo
                                mplayer1.start();
                                finalTiempoDeEspera.setText(String.valueOf(waitTime));
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        t.start();
        timeForWait();
    }

    public void timeForWait() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WaitBeforeWordsActivity.this, WordsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);

    }

}
