package com.example.beeapp;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String URL = "https://raw.githubusercontent.com/apache/incubator-druid/master/server/src/test/resources/status.resource.test.runtime.properties";
    //String URL = "http://";
    String words = "NULL";
    Button theBeeButton;

    static JSONObject jObj = null;
    static String json = "";

    public MainActivity(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{words = read();}
        catch(Exception e){e.printStackTrace();}
        addButton();
    }


        public void addButton(){
            setContentView(R.layout.activity_main);
            theBeeButton = (Button) findViewById(R.id.theBeeButton);//get id of button 1
            BottomNavigationView navView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

            final TextView textiii = (TextView) findViewById(R.id.textiii);
            textiii.setText("the test");

            theBeeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textiii.setText(words);
                }
            });



        }



        public String read() throws MalformedURLException, IOException, JSONException {
            //URL url = new URL("https://raw.githubusercontent.com/jamiemaison/graphql-coffee-comparison/master/package.json");
            URL url = new URL("http://192.168.137.1:1337/disconnect");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            StringBuilder build = new StringBuilder();
            try{
               InputStream in = url.openStream();
               BufferedReader reader = new BufferedReader(new InputStreamReader(in));
               String line;
               while((line = reader.readLine()) != null){
                   build.append(line);
                   //JSONObject jsonObject = new JSONObject(line.toString());
               }
            }  finally {
                urlConnection.disconnect();
            }

            try{
                jObj = new JSONObject(build.toString());
            }catch (JSONException e) {
                System.out.println("error with json");
            }

            String n = jObj.getString("Description");

            System.out.println("heeeeeeeere!");
            // Instantiate a JSON object from the request response
            System.out.println(n.toString());
            return build.toString();
        }

}

