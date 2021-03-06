package com.example.q.soundcloud;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailUploader extends AppCompatActivity {
    private JSONObject uploaderObj;
    private JSONObject userObj;
    private String id;
    private JSONObject obj;
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
        client.get("http://143.248.48.39:1337/userinfo/musics/" + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                uMusic.setText(responseString);
            }
        });

        client.get("http://143.248.48.39:1337/userinfo/" + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    obj = new JSONObject(responseString);
                    Log.e("defailt",(String.valueOf(obj.getJSONArray("follower").length())));
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
                    int num = obj.getJSONArray("follower").length();
                    num++;
                    uFollower.setText(String.valueOf(num));
                    Toast.makeText(getApplicationContext(),"팔로우합니다",Toast.LENGTH_LONG).show();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                new HttpConnectionThread2().doInBackground("http://143.248.48.39:1337/follow",jo.toString());
            }
        });

        Button cash_btn = (Button)findViewById(R.id.givecash);
        cash_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailUploader.this);
                alert.setTitle("Donate Cash");
                alert.setMessage("How much?");
                final EditText input = new EditText(getApplicationContext());
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value = input.getText().toString();
                                try {
                                    userObj = new JSONObject(userinfo);
                                    userObj.put("value",value);
                                    new HttpConnectionThread2().doInBackground("http://143.248.48.39:1337/donatecash",userObj.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                );
                alert.show();
            }
        });
    }
}
