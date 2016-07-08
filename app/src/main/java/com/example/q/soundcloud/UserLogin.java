package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by q on 2016-07-08.
 */
public class UserLogin extends AsyncTask<String,Void,Boolean>{
    private Context mcontext;
    public UserLogin (Context context) {
        mcontext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        new HttpConnectionThread().doInBackground(params[0]+"/login",params[1]);
        return true;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            Log.e("UserLoginjava","true");
            Intent intent = new Intent(mcontext, SoundMainActivty.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mcontext.startActivity(intent);
        }
        else {
            Log.e("UserLoginjava","false");
            //new HttpConnectionThread().doInBackground(params[0]+"/register",params[1]);
        }
    }
}
