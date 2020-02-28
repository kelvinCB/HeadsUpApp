package com.example.lk_heads_up;

import androidx.appcompat.app.AppCompatActivity;

//import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import devazt.devazt.networking.HttpClient;
import devazt.devazt.networking.OnHttpRequestComplete;
import devazt.devazt.networking.Response;

public class MainActivity extends AppCompatActivity {

    //Declarando variables

    TextView categoriasView;
    LinearLayout stackContent;
    public static String categoriaAuxiliar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // prueba  = (TextView) findViewById(R.id.textViewPrueba);
        //getApi2();

    }

    public void llamarView(View view) {

        Intent intent = new Intent(this, WaitBeforeWordsActivity.class);
        startActivity(intent);
        switch (view.getId()) {
            case R.id.btnAnimales:
                categoriaAuxiliar = "Animales";
                break;
            case R.id.btnFrutas:
                categoriaAuxiliar = "Frutas";
                break;
        }
    }

    public void getApi2() {


        categoriasView = (TextView) findViewById(R.id.textViewCategorias);
        stackContent = (LinearLayout) findViewById(R.id.StackContent);

        HttpClient client = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {

                if (status.isSuccess()) {
                    Gson gson = new GsonBuilder().create();
                    try {
                        JSONObject jsono = new JSONObject(status.getResult());
                        JSONArray jsonArray = jsono.getJSONArray("categorias");
                        ArrayList<Person> categorias = new ArrayList<Person>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String cate = jsonArray.getString(i);
                            Person p = gson.fromJson(cate, Person.class);
                            categorias.add(p);
                            System.err.println(p.getName());
                            categoriasView = new TextView(getBaseContext());
                            categoriasView.setText(p.getCity());
                            stackContent.addView(categoriasView);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(MainActivity.this,status.getResult(),Toast.LENGTH_SHORT).show();
                }
            }

        });

        client.excecute("https://www.w3schools.com/angular/customers.php");

    }


    public void getDataApi() {

        String sql = "http://localhost:3000/categorias/1";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection connection;
        try {

            url = new URL(sql);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;

            StringBuffer response = new StringBuffer();

            String json = "";

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            json = response.toString();

            JSONArray jsonArray = null;

            jsonArray = new JSONArray(json);

            String mensaje = "";
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d("categorias", jsonObject.optString("tipo_categoria"));
                mensaje += "categorias" + i + " " + jsonObject.optString("tipo_categoria") + "\n";
            }
            //   prueba.setText(mensaje);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
