package com.axis.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axis.coolcat.R;
import com.axis.util.AboutSong;
import com.axis.util.SearchImpl;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class OLMusicFragment extends Fragment implements View.OnClickListener {

    EditText eWant = null;
    Button bSearch = null;
    View root = null;

    List<AboutSong> aboutSongList = null;

    WeakReference<Handler> wf;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_olmusic, container, false);
        mInit();
        return root;
    }


    private void mInit() {
        eWant = (EditText) root.findViewById(R.id.olMusic_eWant);
        bSearch = (Button) root.findViewById(R.id.olMusic_bSearch);
        bSearch.setOnClickListener(this);

        wf = new WeakReference<Handler>(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1: {
                        done();
                        break;
                    }
                    case 2: {
                        fail();
                        break;
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.olMusic_bSearch) {
            System.out.println("按了----------------------------");
            new Thread(new myRun()).start();
        }

    }


    private void fail() {
        Toast.makeText(getActivity(), R.string.strOlMusic_toastFail, Toast.LENGTH_LONG).show();
    }

    private void done() {

        Toast.makeText(getActivity(), R.string.strOlMusic_toastDone, Toast.LENGTH_LONG).show();
    }

    class myRun implements Runnable {


        @Override
        public void run() {
            // FIXME: 2016/1/4
            System.out.println("running=================================" + Thread.currentThread().getName());
            SearchImpl search = new SearchImpl();
            try {
                aboutSongList = search.getResult(getActivity(), eWant.getText().toString().trim());
                if (aboutSongList == null){
                    return;
                }

                SearchResultPage searchResultPage = new SearchResultPage();
                getFragmentManager().beginTransaction().replace(R.id.fragment_olmusic,searchResultPage).commit();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}

