package com.example.q.soundcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ListenerRegister extends AppCompatActivity {
    private Spinner mSpinner = null;
    // 스피너에 뿌려질 Array형식의 Data를 담을 Adapter
    private ArrayAdapter<String> mSpinnerAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_listener);

    }
}
