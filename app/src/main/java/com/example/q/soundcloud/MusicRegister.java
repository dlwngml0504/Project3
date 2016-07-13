package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by q on 2016-07-11.
 */
public class MusicRegister extends AsyncTask<String,Void,Boolean> {
    private Context mcontext;
    private String music;
    private String user;
    public MusicRegister(Context context) {
        mcontext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Boolean bool = new HttpConnectionThread().doInBackground(params[0]+"/upload",params[1]);
        music = params[1];
        user = params[2];
        return bool;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Intent intent = new Intent(mcontext, SoundMainActivty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userinfo",user);
        mcontext.startActivity(intent);
    }
}
