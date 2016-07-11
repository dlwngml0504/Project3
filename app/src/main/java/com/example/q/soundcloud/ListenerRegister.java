package com.example.q.soundcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListenerRegister extends AppCompatActivity {
    private CheckBox cb1,cb2,cb3,cb4,cb5;
    private String interest="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_listener);

        cb1 = (CheckBox)findViewById(R.id.checkBox11);
        cb2 = (CheckBox)findViewById(R.id.checkBox12);
        cb3 = (CheckBox)findViewById(R.id.checkBox13);
        cb4 = (CheckBox)findViewById(R.id.checkBox14);
        cb5 = (CheckBox)findViewById(R.id.checkBox15);

        Button btn = (Button)findViewById(R.id.listener_registerbtn);
        if (btn!=null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    try {
                        JSONObject userinfo = new JSONObject(intent.getStringExtra("user"));
                        userinfo.put("name",userinfo.getString("name"));
                        userinfo.put("id",userinfo.getString("id"));
                        userinfo.put("state","Listener");
                        if (cb1.isChecked()){
                            interest += "가요";
                        }
                        if (cb2.isChecked()){
                            interest += " OST";
                        }
                        if (cb3.isChecked()){
                            interest += " 인디음악";
                        }
                        if (cb4.isChecked()){
                            interest += " 랩/힙합";
                        }
                        if (cb5.isChecked()){
                            interest += " 록/메탈";
                        }
                        userinfo.put("interest",interest);
                        UserRegister register = new UserRegister(getApplicationContext());
                        register.execute("http://143.248.47.56:1337",userinfo.toString());
                        Intent intent2 = new Intent(ListenerRegister.this,SoundMainActivty.class);
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
