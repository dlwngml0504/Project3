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
                            userinfo.put("song",true);
                        }
                        else {
                            userinfo.put("song",false);
                        }
                        if (cb2.isChecked()){
                            userinfo.put("OST",true);
                        }
                        else {
                            userinfo.put("OST",false);
                        }
                        if (cb3.isChecked()){
                            userinfo.put("rap",true);
                        }
                        else {
                            userinfo.put("song",false);
                        }
                        if (cb4.isChecked()){
                            userinfo.put("indi",true);
                         }
                        else {
                            userinfo.put("indi",false);
                        }
                        if (cb5.isChecked()){
                            userinfo.put("metal",true);
                        }
                        else {
                            userinfo.put("metal",false);
                        }
                        UserRegister register = new UserRegister(getApplicationContext());
                        register.execute("http://143.248.47.56:1337",userinfo.toString());
                        Intent intent2 = new Intent(ListenerRegister.this,SoundMainActivty.class);
                        intent2.putExtra("useinfo",intent.getStringExtra("user"));
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}
