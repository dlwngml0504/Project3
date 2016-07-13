package com.example.q.soundcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by q on 2016-07-12.
 */
public class musics extends AppCompatActivity {
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ffm);
        textview = (TextView) findViewById(R.id.ffl);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://143.248.48.39:8080/userinfo/musics/" + id, new TextHttpResponseHandler() {
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

