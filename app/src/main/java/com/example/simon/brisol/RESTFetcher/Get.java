package com.example.simon.brisol.RESTFetcher;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dennism501 on 8/21/17.
 */

public class Get {

    public String getData(String service) {

        String jsonResult = "";
        HttpURLConnection httpURLConnection;


        try {

            URL url = new URL(service);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String line;
                StringBuilder stringBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);

                    jsonResult = stringBuilder.toString();
                    Log.d("String builder", jsonResult);
                }
                reader.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResult;

    }

}
