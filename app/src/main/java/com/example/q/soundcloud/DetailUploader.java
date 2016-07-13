package com.example.q.soundcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailUploader extends AppCompatActivity {
    private JSONObject uploaderObj;
    private JSONObject userObj;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_uploader);

        Intent intent = getIntent();
        final String uploaderinfo = intent.getStringExtra("uploaderinfo");
        final String userinfo = intent.getStringExtra("userinfo");
        TextView uName = (TextView)findViewById(R.id.up_name);
        final TextView uMusic = (TextView)findViewById(R.id.up_musiclist);
        final TextView uFollower = (TextView)findViewById(R.id.up_follower);
        try {
            uploaderObj = new JSONObject(uploaderinfo);
            id = uploaderObj.getString("id");
            uName.setText(uploaderObj.getString("name"));
            Log.e("asdf", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://143.248.48.39:8080/userinfo/musics/" + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                uMusic.setText(responseString);
            }
        });

        client.get("http://143.248.48.39:8080/userinfo/" + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject obj = new JSONObject(responseString);
                    uFollower.setText(String.valueOf(obj.getJSONArray("follower").length()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button follow_btn = (Button)findViewById(R.id.doFollow);
        follow_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                JSONObject jo = new JSONObject();
                try {
                    userObj = new JSONObject(userinfo);
                    jo.put("followed",uploaderObj.getString("id"));
                    jo.put("following", userObj.getString("id"));
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                new HttpConnectionThread2().doInBackground("http://143.248.47.56:1337/follow",jo.toString());
            }
        });

        Button cash_btn = (Button)findViewById(R.id.givecash);
        cash_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

    }
}
