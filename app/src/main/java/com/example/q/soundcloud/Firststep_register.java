package com.example.q.soundcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Firststep_register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firststep_register);

    }
    public void registerListener(View v){
        Intent intent = new Intent(Firststep_register.this,ListenerRegister.class);
        startActivity(intent);
    }
    public void registerMusician(View v){
        Intent intent = new Intent(Firststep_register.this,MusicianRegister.class);
        startActivity(intent);
    }
}
