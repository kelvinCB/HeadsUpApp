package com.example.lk_heads_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class WordsActivity extends AppCompatActivity {

    //Declarando variables
    Timer timer;

    int waitTime = 31;
    int valor = 0;
    // int auxiliar = 0;
    int posPalabra = 0;
    String categoriaAuxiliar = MainActivity.categoriaAuxiliar;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int latigo = 0;

    LinearLayout layoutWordActivity;
    // = findViewById(R.id.textViewPalabraCategoria);


    String[] categorias = {"Animales", "Frutas"};
    String[] animalsWords = {"León", "Gato", "Perro", "Cotorra", "Flamenco", "Burro", "Vaca",
            "TorTuga", "Elefante", "Hormiga", "Tigre", "Tiburón", "Cocodrilo", "Girafa", "Ballena"};
    String[] fruitsWords = {"Pera", "Piña", "Mango", "Aguacate", "Fresa", "Cereza", "Arándanos",
            "Kiwi", "Coco", "Manzana", "Limón", "China", "Chinola", "Mandarina", "Lechoza"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        TextView palabras = findViewById(R.id.textViewPalabraCategoria);
        if (categoriaAuxiliar.equalsIgnoreCase(categorias[0])) {
            palabras.setText(animalsWords[posPalabra]);
        } else if (categoriaAuxiliar.equalsIgnoreCase(categorias[1])) {
            palabras.setText(fruitsWords[posPalabra]);
        } else {
            Toast.makeText(WordsActivity.this, "No encuentro la categoría", Toast.LENGTH_LONG).show();
        }
        bajarCronometro();
        accelerometro();
    }

    public void accelerometro() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        final TextView palabrasCategoria1 = findViewById(R.id.textViewPalabraCategoria);
        final TextView palabrasCategoria2 = findViewById(R.id.textViewPalabraCategoria);
        if (sensor == null) {
            Toast.makeText(this, "No cuentas con el senso", Toast.LENGTH_SHORT);
            finish();
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[2]; //Eje z
                System.out.println("valor giro" + x);
                if (x < -5 && latigo == 0) {
                    latigo++;
                    posPalabra++;
                    changeWhenGuess(palabrasCategoria1);

                } else if (x > 5 && latigo == 1) {
                    latigo++;
                    posPalabra++;
                    changeWhenByPass(palabrasCategoria2);

                }
                if (latigo == 2) {
                    latigo = 0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }

    private void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    private void sound1() {
        MediaPlayer mplayer1 = MediaPlayer.create(getApplicationContext(), R.raw.timbre);
        mplayer1.start();
    }

    private void sound2() {
        MediaPlayer mplayer1 = MediaPlayer.create(getApplicationContext(), R.raw.sound2);
        mplayer1.start();
    }

    private void sound3() {
        MediaPlayer mplayer1 = MediaPlayer.create(getApplicationContext(), R.raw.sound3);
        mplayer1.start();
    }

    private void changeWhenGuess(TextView palabras) {
        final TextView finalPalabras = palabras;
        sound1();
        layoutWordActivity = findViewById(R.id.LinearWordActivity);
        layoutWordActivity.setBackgroundResource(R.color.colorPrimary);
        for (int i = posPalabra; i < animalsWords.length; i++) {
            if (categoriaAuxiliar.equalsIgnoreCase(categorias[0])) {
                palabras.setText(animalsWords[posPalabra]);
            } else if (categoriaAuxiliar.equalsIgnoreCase(categorias[1])) {
                palabras.setText(animalsWords[i]);
            } else {
                Toast.makeText(WordsActivity.this, "No encuentro la categoría", Toast.LENGTH_LONG).show();
            }
        }
        //   layoutWordActivity.setBackgroundResource(R.color.basicBlue);
    }

    private void changeWhenByPass(TextView palabras) {
        final TextView finalPalabras = palabras;
        sound2();
        layoutWordActivity = findViewById(R.id.LinearWordActivity);
        layoutWordActivity.setBackgroundResource(R.color.colorAccent);
        for (int i = posPalabra; i < animalsWords.length; i++) {
            if (categoriaAuxiliar.equalsIgnoreCase(categorias[0])) {
                palabras.setText(animalsWords[posPalabra]);
            } else if (categoriaAuxiliar.equalsIgnoreCase(categorias[1])) {
                palabras.setText(fruitsWords[i]);
            } else {
                Toast.makeText(WordsActivity.this, "No encuentro la categoría", Toast.LENGTH_LONG).show();
            }
        }

        //layoutWordActivity.setBackgroundResource(R.color.basicBlue);

    }


    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }

    public void bajarCronometro() {

        TextView palabrasTiempo = findViewById(R.id.textViewTiempoPalabras);
        TextView palabrasCategoria = findViewById(R.id.textViewPalabraCategoria);
        changeTextViewEverySecond(palabrasTiempo);

    }


    public void changeTextViewEverySecond(TextView tiempoDeEspera) {

        final TextView finalTiempoDeEspera = tiempoDeEspera;

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
                sound3();
                Intent intent = new Intent(WordsActivity.this, GameOverActivity.class);
                startActivity(intent);
                finish();
            }
        }, (waitTime * 1000));

    }


}
