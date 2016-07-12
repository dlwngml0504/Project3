package com.example.q.soundcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadMusic extends AppCompatActivity {
    private CheckBox cb1,cb2,cb3,cb4,cb5;
    private String userinfoString, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_music);
        final Intent intent = getIntent();
        userinfoString = intent.getStringExtra("userinfo");
        name = intent.getStringExtra("name");
        Log.e("name", name);
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
                            musicinfo.put("pop",true);
                        }
                        if (cb2.isChecked()){
                            musicinfo.put("OST",true);
                        }
                        if (cb3.isChecked()){
                            musicinfo.put("rap",true);
                        }
                        if (cb4.isChecked()){
                            musicinfo.put("indi",true);
                        }
                        if (cb5.isChecked()){
                            musicinfo.put("metal",true);
                        }
                        musicinfo.put("uploader",userinfoString);
                        musicinfo.put("URL","https://s3.amazonaws.com/kaistcs4961/"+ name );
                        Log.e("URL", "https://s3.amazonaws.com/kaistcs4961/"+ name);
                        MusicRegister register = new MusicRegister(getApplicationContext());
                        register.execute("http://143.248.47.56:1337",musicinfo.toString(),userinfoString);
                        Intent intent2 = new Intent(UploadMusic.this,SoundMainActivty.class);
                        intent2.putExtra("userinfo",userinfoString);
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
