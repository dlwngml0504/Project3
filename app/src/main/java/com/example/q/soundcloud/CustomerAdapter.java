package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {
    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_List;

    // 생성자
    public CustomerAdapter() {
        m_List = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return m_List.size();
    }
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listviewlayout, parent, false);

            TextView mtitle = (TextView) convertView.findViewById(R.id.mTitle);
            TextView msinger = (TextView) convertView.findViewById(R.id.mSinger);
            String musicinfo =  m_List.get(position);
            try {
                JSONObject jo = new JSONObject(musicinfo);
                mtitle.setText(jo.getString("title"));
                msinger.setText(jo.getString("singer"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Button btn = (Button) convertView.findViewById(R.id.mListen);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailMusic.class);
                    intent.putExtra("musicinfo", m_List.get(pos) );
                    context.startActivity(intent);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String musicinfo = m_List.get(pos);
                    try {
                        JSONObject jo = new JSONObject(musicinfo);
                        String url = jo.getString("URL");
                        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return convertView;
    }
    public void add(String _msg) {
        m_List.add(_msg);
    }
}
