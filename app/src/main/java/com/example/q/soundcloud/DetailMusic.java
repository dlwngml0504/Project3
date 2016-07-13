package com.example.q.soundcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailMusic extends AppCompatActivity {
    private JSONObject musicObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_music);

        final Intent intent = getIntent();
        final TextView mtitle = (TextView) findViewById(R.id.detail_title);
        final TextView mLyricist = (TextView)findViewById(R.id.detail_lyricist);
        final TextView mComposer = (TextView)findViewById(R.id.detail_composer);
        final TextView mSinger = (TextView)findViewById(R.id.detail_singer);
        final TextView mUpload = (TextView)findViewById(R.id.detail_uploadDate);
        final TextView mLikeNum = (TextView)findViewById(R.id.detail_likeNum);
        Button btn = (Button)findViewById(R.id.detail_btn);
        final Button like_btn = (Button)findViewById(R.id.like);
        try {
            musicObj = new JSONObject(intent.getStringExtra("musicinfo"));
            mtitle.setText(musicObj.getString("title"));
            mLyricist.setText(musicObj.getString("lyricist"));;
            mComposer.setText(musicObj.getString("composer"));
            mSinger.setText(musicObj.getString("singer"));
            String date = musicObj.getString("date").split("T")[0];
            mUpload.setText(date);
            like_btn.setText(musicObj.getString("like_num"));
            mLikeNum.setText(musicObj.getString("like_num"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    String uploaderinfo = musicObj.getString("uploader");
                    String userinfo = intent.getStringExtra("userinfo");
                    Intent intent2 = new Intent(getApplicationContext(),DetailUploader.class);
                    intent2.putExtra("uploaderinfo",uploaderinfo);
                    intent2.putExtra("userinfo",userinfo);
                    startActivity(intent2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        like_btn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                new HttpConnectionThread2().doInBackground("http://143.248.48.39:1337/likemusic",musicObj.toString());
                int num = 1;
                num += Integer.parseInt(mLikeNum.getText().toString());
                mLikeNum.setText(Integer.toString(num));
                like_btn.setText(Integer.toString(num));
                try {
                    musicObj.put("like_num",num);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
