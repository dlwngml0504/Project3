package com.example.q.soundcloud;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerAdapter extends BaseAdapter {
    // 문자열을 보관 할 ArrayList
    private ArrayList<String> m_List;

    private StreamingMediaPlayer audioStreamer;

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ProgressBar progressBar;
    private String userinfo;
    private Context mContext;

    private boolean isPlaying;

    // 생성자
    public CustomerAdapter(Context context, ProgressBar progressbar, ImageButton imageButton1, ImageButton imageButton2, String user) {
        m_List = new ArrayList<String>();
        this.mContext=context;
        playButton = imageButton1;
        pauseButton = imageButton2;
        progressBar = progressbar;
        userinfo = user;
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
                    intent.putExtra("userinfo",userinfo);
                    context.startActivity(intent);
                }
            });

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

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String musicinfo = m_List.get(pos);
                    try {
                        JSONObject jo = new JSONObject(musicinfo);
                        String url = jo.getString("URL");
                        startStreamingAudio(url);
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

    private void startStreamingAudio(String url) {
        try {
            if ( audioStreamer != null) {
                audioStreamer.interrupt();
            }
            audioStreamer = new StreamingMediaPlayer(mContext, playButton, progressBar);
            audioStreamer.startStreaming(url,5208, 216);
        } catch (IOException e) {
            Log.e(getClass().getName(), "Error starting to stream audio.", e);
        }
    }
}
