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

import java.util.ArrayList;

/**
 * Created by q on 2016-07-12.
 */
public class CustomerAdapter extends BaseAdapter {
    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_List;

    // 생성자
    public CustomerAdapter() {
        m_List = new ArrayList<String>();
    }

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return m_List.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
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


            // 버튼을 터치 했을 때 이벤트 발생
            Button btn = (Button) convertView.findViewById(R.id.mListen);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailMusic.class);
                    intent.putExtra("musicinfo", m_List.get(pos) );
                    context.startActivity(intent);
                    //Toast.makeText(context, m_List.get(pos), Toast.LENGTH_SHORT).show();
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 터치 시 해당 아이템 이름 출력
                    Toast.makeText(context, "리스트 클릭 : ", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return convertView;
    }

    private class CustomHolder {
        TextView    m_TextView;
        Button      m_Btn;
    }

    public void add(String _msg) {
        m_List.add(_msg);

    }
}
