package com.example.q.soundcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadMusic extends AppCompatActivity {
    private CheckBox cb1,cb2,cb3,cb4,cb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_music);
        final Intent intent = getIntent();
        Log.e("UploadMusic","1");
/*        JSONObject userinfo = null;
        try {
            userinfo = new JSONObject(intent.getStringExtra("userinfo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //Log.e("UploadMusic",intent.getStringExtra("userinfo"));
        cb1 = (CheckBox)findViewById(R.id.checkBox21);
        cb2 = (CheckBox)findViewById(R.id.checkBox22);
        cb3 = (CheckBox)findViewById(R.id.checkBox23);
        cb4 = (CheckBox)findViewById(R.id.checkBox24);
        cb5 = (CheckBox)findViewById(R.id.checkBox25);
        final EditText title = (EditText)findViewById(R.id.music_title);
        final EditText lyricist = (EditText)findViewById(R.id.music_lyricist);
        final EditText composer = (EditText)findViewById(R.id.music_composer);
        final EditText singer = (EditText)findViewById(R.id.music_singer);
        Button btn = (Button)findViewById(R.id.upload_btn);

        if (btn!=null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        JSONObject musicinfo = new JSONObject();
                        musicinfo.put("title",title.getText());
                        musicinfo.put("lyricist",lyricist.getText());
                        musicinfo.put("composer",composer.getText());
                        musicinfo.put("singer",singer.getText());
                        if (cb1.isChecked()){
                            musicinfo.put("song",true);
                        }
                        else {
                            musicinfo.put("song",false);
                        }
                        if (cb2.isChecked()){
                            musicinfo.put("OST",true);
                        }
                        else {
                            musicinfo.put("OST",false);
                        }
                        if (cb3.isChecked()){
                            musicinfo.put("rap",true);
                        }
                        else {
                            musicinfo.put("song",false);
                        }
                        if (cb4.isChecked()){
                            musicinfo.put("indi",true);
                        }
                        else {
                            musicinfo.put("indi",false);
                        }
                        if (cb5.isChecked()){
                            musicinfo.put("metal",true);
                        }
                        else {
                            musicinfo.put("metal",false);
                        }
                        MusicRegister register = new MusicRegister(getApplicationContext());
                        register.execute("http://143.248.47.56:1337",musicinfo.toString());
                        Intent intent2 = new Intent(UploadMusic.this,SoundMainActivty.class);
                        intent2.putExtra("musicinfo",intent.getStringExtra("musicinfo"));
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
