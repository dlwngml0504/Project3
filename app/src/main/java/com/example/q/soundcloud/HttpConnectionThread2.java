package com.example.q.soundcloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpConnectionThread2  extends AsyncTask<String,Void, String> {
    private List<JSONObject> music_list = new ArrayList<JSONObject>();
    @Override
    protected String doInBackground(String... url) {
        URL murl;
        try {
            murl = new URL(url[0]);
            HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            conn.getOutputStream();
            OutputStream os =  conn.getOutputStream();
            os.write(url[1].getBytes("UTF-8"));
            os.flush();
            os.close();
            InputStream inputstream = conn.getInputStream();
            BufferedReader in = new BufferedReader( new InputStreamReader(inputstream ));

            String inputLine;
            if ((inputLine = in.readLine()) != null) {
                return inputLine.toString();
            }
            in.close();
        } catch (IOException e) {
        }
        return "";
    }
}
