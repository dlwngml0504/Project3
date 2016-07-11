package com.example.q.soundcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ListenerRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> arraylist2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_listener);

        arraylist2 = new ArrayList<String>();
        arraylist2.add("우리은행");
        arraylist2.add("우체국");
        arraylist2.add("신한은행");
        arraylist2.add("국민은행");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_dropdown_item, arraylist2);

        Spinner sp2 = (Spinner) this.findViewById(R.id.spinner2);
        sp2.setPrompt("골라봐"); // 스피너 제목
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, arraylist2.get(i), Toast.LENGTH_LONG).show();//해당목차눌렸을때
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO Auto-generated method stub
    }
}
