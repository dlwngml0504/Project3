package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class UserRegister extends AsyncTask<String,Void,Boolean>{
    private Context mcontext;
    private String user;
    public UserRegister (Context context) {
        mcontext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Boolean bool = new HttpConnectionThread().doInBackground(params[0]+"/register",params[1]);
        user = params[1];
        //new HttpConnectionThread().doInBackground(params[0]+"/register",params[1]);
        Log.e("UserLogin",bool.toString());
        return bool;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.e("UserRegister","true");
        Intent intent = new Intent(mcontext, SoundMainActivty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mcontext.startActivity(intent);
    }
}
