package com.example.q.soundcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Firststep_register extends AppCompatActivity {
    private String userinfo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firststep_register);
        Intent intent = getIntent();
        userinfo = intent.getStringExtra("user");
    }
    public void ButtonOnClick(View v) {
        switch (v.getId()) {
            case R.id.listener_reg:
                Intent intent = new Intent(Firststep_register.this,ListenerRegister.class);
                intent.putExtra("user",userinfo);
                startActivity(intent);
                break;
            case R.id.musician_reg:
                Intent intent2 = new Intent(getApplicationContext(),MusicianRegister.class);
                intent2.putExtra("user",userinfo);
                startActivity(intent2);
                break;
        }
    }
}
