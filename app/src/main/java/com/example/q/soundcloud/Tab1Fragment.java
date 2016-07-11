package com.example.q.soundcloud;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.IOException;

public class Tab1Fragment  extends Fragment {

    private ImageButton playButton, pauseButton, playerButton;
    private ProgressBar progressBar;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab1, container, false);
        playButton = (ImageButton) view.findViewById(R.id.button_play);
        pauseButton = (ImageButton) view.findViewById(R.id.button_pause);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        playerButton = (ImageButton) view.findViewById(R.id.button_showmenu);
        final LinearLayout menu = (LinearLayout) view.findViewById(R.id.menu);

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
                startStreamingAudio();
            }});

        pauseButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioStreamer.getMediaPlayer().isPlaying()) {
                    audioStreamer.getMediaPlayer().pause();
                } else {
                    audioStreamer.getMediaPlayer().start();
                    audioStreamer.startPlayProgressUpdater();
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

    private void startStreamingAudio() {
        try {
            if ( audioStreamer != null) {
                audioStreamer.interrupt();
            }
            audioStreamer = new StreamingMediaPlayer(getActivity(), playButton, progressBar);
            audioStreamer.startStreaming("https://s3.amazonaws.com/kaistcs4961/let+it+go+-+idina+menzel.mp3",5208, 216);
        } catch (IOException e) {
            Log.e(getClass().getName(), "Error starting to stream audio.", e);
        }
    }
}