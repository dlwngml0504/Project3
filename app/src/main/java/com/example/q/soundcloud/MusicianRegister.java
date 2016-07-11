package com.example.q.soundcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MusicianRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_musitian);
        final EditText state = (EditText)findViewById(R.id.state);
        final EditText bank = (EditText)findViewById(R.id.bank);
        final EditText accountnumber = (EditText)findViewById(R.id.accountnumber);
        Button btn = (Button)findViewById(R.id.musitian_registerbtn);

        if (btn!=null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    try {
                        JSONObject userinfo = new JSONObject(intent.getStringExtra("user"));
                        userinfo.put("name",userinfo.getString("name"));
                        userinfo.put("id",userinfo.getString("id"));
                        userinfo.put("bank",bank.getText());
                        userinfo.put("accountnumber",accountnumber.getText());
                        userinfo.put("state",state.getText());
                        Log.e("MusicianRegister",userinfo.toString());
                        UserRegister register = new UserRegister(getApplicationContext());
                        register.execute("http://143.248.47.56:1337",userinfo.toString());
                        Intent intent2 = new Intent(MusicianRegister.this,SoundMainActivty.class);
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
