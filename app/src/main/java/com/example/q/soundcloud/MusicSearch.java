package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by q on 2016-07-12.
 */
public class MusicSearch  extends AsyncTask<String,Void,String> {
    private Context mcontext;
    private String musicinfo;
    public MusicSearch(Context context) {
        mcontext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.e("MusicSearch",params[1]);
        String  str= new HttpConnectionThread2().doInBackground(params[0]+"/search/music",params[1]);
        musicinfo = params[1];
       // Log.e("UserLogin",bool.toString());
        return str;
    }

    /*@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONArray ja = new JSONArray(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}