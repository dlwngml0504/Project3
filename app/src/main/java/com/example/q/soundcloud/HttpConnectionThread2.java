package com.example.q.soundcloud;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionThread2  extends AsyncTask<String,Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... url) {
        URL murl;
        String response = null;
        try {
            murl = new URL(url[0]);
            HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            // conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.connect();

            conn.getOutputStream();
            OutputStream os =  conn.getOutputStream();

            os.write(url[1].getBytes("UTF-8"));
            Log.e("HttpConnectionThread2",murl.toString());
            Log.e("HttpConnectionThread2",url[1].toString());
            os.flush();
            os.close();
            response = conn.getResponseMessage();
            Log.e("Httpresponde",response.toString());
            if (response.toString().equals("OK")) {
                Log.e("HttpConnectionThread2","TRUE");
                return true;
            }
        } catch (IOException e) {

        }
        Log.e("HttpConnectionThread2","FALSE");
        return false;
    }
}
