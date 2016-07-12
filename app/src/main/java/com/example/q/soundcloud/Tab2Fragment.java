package com.example.q.soundcloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tab2Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private CheckBox cb1, cb2, cb3,cb4,cb5;
    private ListView                m_ListView;
    private CustomerAdapter    m_Adapter;

    public Tab2Fragment() {
        // Required empty public constructor
    }


    public static Tab2Fragment newInstance() {
        Tab2Fragment fragment = new Tab2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getActivity().getIntent().getExtras();
        final View view = inflater.inflate(R.layout.tab2, container, false);
        Button btn = (Button)view.findViewById(R.id.select_btn);
        cb1 = (CheckBox)view.findViewById(R.id.checkBox51);
        cb2 = (CheckBox)view.findViewById(R.id.checkBox52);
        cb3 = (CheckBox)view.findViewById(R.id.checkBox53);
        cb4 = (CheckBox)view.findViewById(R.id.checkBox54);
        cb5 = (CheckBox)view.findViewById(R.id.checkBox55);
        if (btn!=null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    JSONObject jo = new JSONObject();
                    try {
                        if (cb1.isChecked()){
                            jo.put("pop",true);
                        }
                        if (cb2.isChecked()){
                            jo.put("OST",true);
                        }
                        if (cb3.isChecked()){
                            jo.put("rap",true);
                        }
                        if (cb4.isChecked()){
                            jo.put("indi",true);
                        }
                        if (cb5.isChecked()){
                            jo.put("metal",true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MusicSearch musicsearch = new MusicSearch(getActivity().getApplicationContext()){
                        @Override
                        protected void onPostExecute(String result) {
                            super.onPostExecute(result);
                            try {
                                JSONArray ja = new JSONArray(result);
                                m_Adapter = new CustomerAdapter();
                                // Xml에서 추가한 ListView 연결
                                m_ListView = (ListView) view.findViewById(R.id.musicList);
                                // ListView에 어댑터 연결
                                m_ListView.setAdapter(m_Adapter);
                                // ListView 아이템 터치 시 이벤트 추가
                                //m_ListView.setOnItemClickListener(onClickListItem);
                                for (int i=0;i<ja.length();i++){
                                    m_Adapter.add(ja.getJSONObject(i).toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    musicsearch.execute("http://143.248.47.56:1337",jo.toString());
                }
            });
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            Toast.makeText(getActivity().getApplicationContext(),"CLI~~~~k", Toast.LENGTH_SHORT).show();
        }
    };*/
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}