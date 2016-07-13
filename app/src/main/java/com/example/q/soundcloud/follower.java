package com.example.q.soundcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class follower extends AppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ffm);
        textview = (TextView) findViewById(R.id.ffl);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://143.248.48.39:1337/userinfo/follower/" + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                textview.setText(responseString);
            }
        });
    }
}
