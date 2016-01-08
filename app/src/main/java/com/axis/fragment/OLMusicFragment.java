package com.axis.fragment;


import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Handler;

import com.axis.coolcat.R;
import com.axis.util.Constant;
import com.axis.util.MusicDownloadManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Scanner;

public class OLMusicFragment extends Fragment implements View.OnClickListener {

    EditText eName = null;
    EditText eSinger = null;
    Button bSearch = null;
    View root = null;

    MusicDownloadManager md;

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
        eName = (EditText) root.findViewById(R.id.olMusic_eSongName);
        eSinger = (EditText) root.findViewById(R.id.olMusic_eSingerName);
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
            md = new MusicDownloadManager();
            String name = eName.getText().toString();
            String singer = eSinger.getText().toString();
            String folderPath = PreferenceManager.getDefaultSharedPreferences(
                    getActivity()).getString(SettingFragment.KEY_LYRIC_SAVE_PATH,
                    Constant.LYRIC_SAVE_FOLDER_PATH);
            if(name.equals("")){
                wf.get().sendEmptyMessage(2);
                return;
            }
            if (singer.equals("")) {
                if (md.download("http://box.zhangmen.baidu.com/x?op=12&count=1&title=", name, singer, folderPath)) {
                    wf.get().sendEmptyMessage(1);
                } else {
                    wf.get().sendEmptyMessage(2);
                }

            } else {
                if (md.download("http://box.zhangmen.baidu.com/x?op=12&count=1&title=", name, singer, folderPath)) {
                    wf.get().sendEmptyMessage(1);
                } else {
                    wf.get().sendEmptyMessage(2);
                }
            }
        }
    }
}
