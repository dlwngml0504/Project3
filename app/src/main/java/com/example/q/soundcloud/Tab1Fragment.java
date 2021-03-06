package com.example.q.soundcloud;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class Tab1Fragment  extends Fragment {

    private ImageButton playButton, pauseButton, playerButton;
    private ProgressBar progressBar;
    private ListView musicList;
    private CustomerAdapter adapter;
    private boolean isPlaying;
    private StreamingMediaPlayer audioStreamer;
    private OnFragmentInteractionListener mListener;

    public Tab1Fragment() {
        // Required empty public constructor
    }


    public static Tab1Fragment newInstance() {
        Tab1Fragment fragment = new Tab1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab1, container, false);
        final Bundle b = getActivity().getIntent().getExtras();
        playButton = (ImageButton) view.findViewById(R.id.button_play);
        pauseButton = (ImageButton) view.findViewById(R.id.button_pause);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        playerButton = (ImageButton) view.findViewById(R.id.button_showmenu);
        musicList = (ListView) view.findViewById(R.id.musiclist);

        final LinearLayout menu = (LinearLayout) view.findViewById(R.id.menu);

       MusicSearch musicsearch = new MusicSearch(getActivity().getApplicationContext()){
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                try {
                    JSONArray ja = new JSONArray(result);
                    adapter = new CustomerAdapter(getActivity(), progressBar, playButton, pauseButton,b.getString("userinfo"));
                    musicList = (ListView) view.findViewById(R.id.musiclist);
                    musicList.setAdapter(adapter);
                    for (int i=0;i<ja.length();i++){
                       adapter.add(ja.getJSONObject(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        musicsearch.execute("http://143.248.48.39:1337","all");

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String title = (String) adapter.getItem(position);
                Toast.makeText(getContext(), "Play  " + title, Toast.LENGTH_SHORT).show();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                client.get("http://143.248.48.39:1337/getmusic/" + title, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.e("URL123", responseString);
                        startStreamingAudio(responseString);
                    }
                });
            }
        });

        playerButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (menu.getVisibility() == menu.GONE){
                    menu.setVisibility(menu.VISIBLE);
                }
                else if (menu.getVisibility() == menu.VISIBLE){
                    menu.setVisibility(menu.GONE);
                }
            }});

        playButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!audioStreamer.getMediaPlayer().isPlaying()){
                    audioStreamer.getMediaPlayer().start();
                    audioStreamer.startPlayProgressUpdater();
                }
                isPlaying = !isPlaying;
            }});

        pauseButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioStreamer.getMediaPlayer().isPlaying()) {
                    audioStreamer.getMediaPlayer().pause();
                }
                isPlaying = !isPlaying;
            }});

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

    private void startStreamingAudio(String url) {
        try {
            if ( audioStreamer != null) {
                audioStreamer.interrupt();
            }
            audioStreamer = new StreamingMediaPlayer(getActivity(), playButton, progressBar);
            audioStreamer.startStreaming(url,5208, 216);
        } catch (IOException e) {
            Log.e(getClass().getName(), "Error starting to stream audio.", e);
        }
    }

}