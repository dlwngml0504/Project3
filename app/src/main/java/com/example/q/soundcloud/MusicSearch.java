package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class MusicSearch  extends AsyncTask<String,Void,String> {
    private Context mcontext;
    public MusicSearch(Context context) {
        mcontext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params[1].equals("all")) {
            String  str= new HttpConnectionThread2().doInBackground(params[0]+"/all/music",params[1]);
            return str;
        }
        String  str= new HttpConnectionThread2().doInBackground(params[0]+"/search/music",params[1]);
        return str;
    }
}