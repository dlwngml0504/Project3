package com.example.q.soundcloud;

/**
 * Created by q on 2016-07-12.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

public class Tab4Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private CheckBox cb1, cb2, cb3,cb4,cb5;


    public Tab4Fragment() {
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
        View view = inflater.inflate(R.layout.tab2, container, false);
        Button btn = (Button)view.findViewById(R.id.select_btn);
        cb1 = (CheckBox)view.findViewById(R.id.checkBox51);
        cb2 = (CheckBox)view.findViewById(R.id.checkBox52);
        cb3 = (CheckBox)view.findViewById(R.id.checkBox53);
        cb4 = (CheckBox)view.findViewById(R.id.checkBox54);
        cb5 = (CheckBox)view.findViewById(R.id.checkBox55);
        if (btn!=null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    MusicSearch musicsearch = new MusicSearch(getActivity().getApplicationContext());
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}