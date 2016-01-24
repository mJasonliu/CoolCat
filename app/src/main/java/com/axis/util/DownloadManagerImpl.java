package com.axis.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.axis.myinterface.IDownLoadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jason on 2016/1/4.
 */
public class DownloadManagerImpl implements IDownLoadManager {
    private final String TAG = this.getClass().getSimpleName();

    public boolean downloadIMG(String url, String songName) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
//                    System.out.println(strings[0]);
                    URL url = new URL(strings[0]);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    File path = new File(Constant.IMG_SAVE_FOLDER_PATH);
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    File file = new File(Constant.IMG_SAVE_FOLDER_PATH + strings[1] + ".jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    int i;
                    byte[] bytes = new byte[1024];
                    while ((i = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, i);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute(url, songName);
        return true;
    }

    @Override
    public boolean downloadMuisc(final Context context, String url, String songName, String singerName, String path) {
        new AsyncTask<String, Integer, Boolean>() {

            ProgressDialog progressDialog;
            String url, songName, singerName, path;

            @Override
            protected void onPreExecute() {
//                System.out.println("开始下载");
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("音乐下载对话框");
                progressDialog.setMessage("音乐下载中...");
                progressDialog.show();
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(String... strings) {

                url = strings[0];
                songName = strings[1];
                singerName = strings[2];
                path = strings[3];
                try {
                    if (url.equals("\",")) {
                        return false;
                    }
                    MusicDownload musicDownload = new MusicDownload();
//                    System.out.println(strings[0]);
                    musicDownload.MusicDownload(url, songName, singerName, path);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Toast toast;
//                System.out.println("下载成功");
                progressDialog.dismiss();
                if (result == true) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    //path+ singerName + " - " + songName + ".mp3")
                    intent.setData(Uri.fromFile(new File(path + singerName + " - " + songName + ".mp3")));
                    Log.i(TAG, Uri.fromFile(new File(path + singerName + " - " + songName + ".mp3")).toString());
                    context.sendBroadcast(intent);
                    toast = Toast.makeText(context, "音乐下载成功，已添加至本地播放列表！", Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(context, "音乐下载失败，下载地址无效！", Toast.LENGTH_LONG);
                }
                toast.show();
                super.onPostExecute(result);
            }
        }.execute(url, songName, singerName, path);
        return false;
    }


    public boolean downloadLyric(final Context context, String url, String songName, String singerName, String path) {
        new AsyncTask<String, Integer, Boolean>() {
            LyricDownloadManager lyricDownloadManager;


            @Override
            protected void onPreExecute() {
//                System.out.println("开始下载");
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                lyricDownloadManager = new LyricDownloadManager(context);
                lyricDownloadManager.fetchLyricContent(context, strings[1], strings[2], strings[0]);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
//                System.out.println("下载成功");

            }
        }.execute(url, songName, singerName, path);
        return false;
    }
}
