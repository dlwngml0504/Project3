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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MusicianRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    ArrayList<String> arraylist1;
    private String bank_name;
    private CheckBox cb1,cb2,cb3,cb4,cb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_musitian);
        final EditText accountnumber = (EditText)findViewById(R.id.accountnumber);
        Button btn = (Button)findViewById(R.id.musitian_registerbtn);
        cb1 = (CheckBox)findViewById(R.id.checkBox1);
        cb2 = (CheckBox)findViewById(R.id.checkBox2);
        cb3 = (CheckBox)findViewById(R.id.checkBox3);
        cb4 = (CheckBox)findViewById(R.id.checkBox4);
        cb5 = (CheckBox)findViewById(R.id.checkBox5);
        arraylist1 = new ArrayList<String>();
        arraylist1.add("우리은행");
        arraylist1.add("우체국");
        arraylist1.add("신한은행");
        arraylist1.add("국민은행");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_dropdown_item, arraylist1);

        Spinner sp1 = (Spinner) this.findViewById(R.id.spinner1);
        sp1.setPrompt("골라봐"); // 스피너 제목
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(this);


        if (btn!=null){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    try {
                        JSONObject userinfo = new JSONObject(intent.getStringExtra("user"));
                        userinfo.put("name",userinfo.getString("name"));
                        userinfo.put("id",userinfo.getString("id"));
                        userinfo.put("accountnumber",accountnumber.getText());
                        userinfo.put("state","Musician");
                        userinfo.put("bank",bank_name);
                        if (cb1.isChecked()){
                            userinfo.put("pop",true);
                        }
                        if (cb2.isChecked()){
                            userinfo.put("OST",true);
                        }
                        if (cb3.isChecked()){
                            userinfo.put("rap",true);
                        }
                        if (cb4.isChecked()){
                            userinfo.put("indi",true);
                        }
                        if (cb5.isChecked()){
                            userinfo.put("metal",true);
                        }
                        Log.e("MusicianRegister",userinfo.toString());
                        UserRegister register = new UserRegister(getApplicationContext());
                        register.execute("http://143.248.47.56:1337",userinfo.toString());
                        Intent intent2 = new Intent(MusicianRegister.this,SoundMainActivty.class);
                        intent2.putExtra("userinfo",intent.getStringExtra("user"));
                        Log.e("MusicianRegister",intent.getStringExtra("user").toString());
                        startActivity(intent2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, arraylist1.get(i), Toast.LENGTH_LONG).show();//해당목차눌렸을때
        bank_name = arraylist1.get(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }
}
