package com.axis.myinterface;

import android.content.Context;

/**
 * Created by Jason on 2016/1/4.
 */
public interface IDownLoadManager{
    boolean downloadIMG(String url, String songName);
    boolean downloadMuisc(Context context, String url, String songName, String singerName, String path);
    boolean downloadLyric(final Context context, String url, String songName, String singerName, String path);
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
//    public abstract void download();
}
