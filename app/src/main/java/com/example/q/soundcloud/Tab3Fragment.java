package com.example.q.soundcloud;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Tab3Fragment extends Fragment {

    private TextView user_id, user_name, user_state, user_cash, user_follower, user_following, user_likes, user_musics;
    private Button buycash;

    String id = new String();
    String userinfo_string = new String();
    String likes = new String();

    private OnFragmentInteractionListener mListener;

    public Tab3Fragment() {
        // Required empty public constructor
    }


    public static Tab3Fragment newInstance() {
        Tab3Fragment fragment = new Tab3Fragment();
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
        View view = inflater.inflate(R.layout.tab3, container, false);
        Bundle b = getActivity().getIntent().getExtras();
        String userinfo = b.getString("userinfo");
        user_id = (TextView) view.findViewById(R.id.user_id);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_state = (TextView) view.findViewById(R.id.user_state);
        user_cash = (TextView) view.findViewById(R.id.user_cash);
        user_follower = (TextView) view.findViewById(R.id.user_follower);
        user_following = (TextView) view.findViewById(R.id.user_following);
        user_likes = (TextView) view.findViewById(R.id.user_likes);
        user_musics = (TextView) view.findViewById(R.id.user_musics);
        buycash = (Button) view.findViewById(R.id.buycash);
        Log.v("userinfo", userinfo);
        final AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userinfo", userinfo);
        client.get("http://143.248.48.39:8080/userinfo", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    id = responseString;
            }
        });

        client.get("http://143.248.48.39:8080/userinfo/" + id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                userinfo_string = responseString;
                try {
                    JSONObject obj = new JSONObject(userinfo_string);
                    Log.e("obj", obj.toString());
                    user_id.setText(obj.get("id").toString());
                    user_name.setText(obj.get("name").toString());
                    user_state.setText(obj.get("state").toString());
                    user_cash.setText(obj.get("cash").toString());
                    user_follower.setText(String.valueOf(obj.getJSONArray("follower").length()));
                    user_following.setText(String.valueOf(obj.getJSONArray("following").length()));
                    user_musics.setText(String.valueOf(obj.getJSONArray("music").length()));
                    Log.e("metal", obj.get("metal").toString());

                    if (likes == ""){
                       if (obj.getBoolean("POP")){
                            likes += "POP ";
                        }
                        if (obj.getBoolean("OST")){
                            likes += "OST ";
                        }
                        if (obj.getBoolean("rap")){
                            likes += "rap ";
                        }
                        if (obj.getBoolean("indi")){
                            likes += "indi ";
                        }
                        if (obj.getBoolean("metal")){
                            likes += "metal ";
                        }
                    }

                    user_likes.setText(likes);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        buycash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Buy Cash");
                alert.setMessage("How much?");
                final EditText input = new EditText(getContext());
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String value = input.getText().toString();
                                RequestParams params = new RequestParams();
                                params.put("value", value);
                                Log.e("Tab3",id);
                                client.put("http://143.248.48.39:8080/buycash/" + id, params, new TextHttpResponseHandler() {
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                        user_cash.setText(responseString);
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(Tab3Fragment.this).attach(Tab3Fragment.this).commit();
                                    }
                                });

                            }
                        }
                );
                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                );
                alert.show();

            }
        });
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
