package com.pb.testpb;

import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/*import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;*/

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.text);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*HttpResponse<JsonNode> response;
        try {
            response = Unirest.get("https://hryvna-today.p.mashape.com/v1/rates/averages")
                    .header("X-Mashape-Key", "iz24eFSAI3mshub8rVO2GBUtXMskp1Ymh1zjsnd2gZH43PPq4C")
                    .header("Accept", "applicatio   n/json").asJson();
            mTextView.setText(response.getBody().toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }*/

        HttpClient client = new DefaultHttpClient();
        HttpGet request;
        //request = new HttpGet("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=3");
        request = new HttpGet("https://hryvna-today.p.mashape.com/v1/rates/averages");
        request.addHeader("X-Mashape-Key", "tWIUFXZ7wymsh5MNtt1inL9tqA4mp1hIy9gjsnGwYe4GSaPG8g");
        request.addHeader("Accept", "application/json");

        try {
            HttpEntity httpEntity = client.execute(request).getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line+"n");
                }
                inputStream.close();
                mTextView.setText(stringBuilder.toString());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
