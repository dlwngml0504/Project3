package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by q on 2016-07-12.
 */
public class MusicSearch  extends AsyncTask<String,Void,Boolean> {
    private Context mcontext;
    private String musicinfo;
    public MusicSearch(Context context) {
        mcontext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Log.e("MusicSearch",params[1]);
        Boolean bool = new HttpConnectionThread2().doInBackground(params[0]+"/search/music",params[1]);
        musicinfo = params[1];
       // Log.e("UserLogin",bool.toString());
        return bool;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }
}