package com.example.lk_heads_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class WordsActivity extends AppCompatActivity {

    //Declarando variables
    Timer timer;
    int waitTime = 31;
    int valor = 0;
    int auxiliar = 1;
    String categoriaAuxiliar = MainActivity.categoriaAuxiliar;

    String[] categorias = {"Animales", "Frutas"};
    String[] animalsWords = {"Tortuga", "Flamenco", "Perro", "Gato", "Ballena"};
    String[] fruitsWords = {"Pera", "Manzana", "Kiwi", "Limón", "Chinola"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        BajarCronometros();
    }

    public void llamarGameOver() {

    }

    public void BajarCronometros() {

        TextView palabrasTiempo = findViewById(R.id.textViewTiempoPalabras);
        TextView palabrasCategoria = findViewById(R.id.textViewPalabraCategoria);
        changeTextViewEverySecond(palabrasTiempo, palabrasCategoria);

    }


    public void changeTextViewEverySecond(TextView tiempoDeEspera, final TextView palabras) {


        final TextView finalTiempoDeEspera = tiempoDeEspera;
        final TextView finalPalabras = palabras;
        Button animals = findViewById(R.id.btnAnimales);
        Button fruits = findViewById(R.id.btnFrutas);
        //   animals.setOnClickListener(this);
        // animals.setOnClickListener(this);

        Thread t = new Thread() {
            @Override
            public void run() {

                while (waitTime > 2) {
                    try {
                        Thread.sleep(1000);
                        valor++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (categoriaAuxiliar.equalsIgnoreCase(categorias[0])) {
                                    palabras.setText(animalsWords[auxiliar - 1]);
                                    if (valor == 5) {
                                        palabras.setText(animalsWords[auxiliar]);
                                        auxiliar++;
                                        valor = 0;
                                        if (auxiliar > animalsWords.length - 1) {
                                            auxiliar = 0;
                                        }
                                    }
                                } else if (categoriaAuxiliar.equalsIgnoreCase(categorias[1])) {
                                    palabras.setText(fruitsWords[auxiliar - 1]);
                                    if (valor == 5) {
                                        palabras.setText(fruitsWords[auxiliar]);
                                        auxiliar++;
                                        valor = 0;
                                        if (auxiliar > fruitsWords.length - 1) {
                                            auxiliar = 0;
                                        }
                                    }
                                } else {
                                    Toast.makeText(WordsActivity.this, "No encuentro la categoría", Toast.LENGTH_LONG).show();
                                }
                                waitTime--;
                                MediaPlayer mplayer1 = MediaPlayer.create(getApplicationContext(), R.raw.tic); // Hacen sonar cada segundo
                                mplayer1.start();
                                finalTiempoDeEspera.setText(String.valueOf(waitTime) + " segundos");
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
                Intent intent = new Intent(WordsActivity.this, GameOverActivity.class);
                startActivity(intent);
                finish();
            }
        }, (waitTime * 1000));

    }


}
