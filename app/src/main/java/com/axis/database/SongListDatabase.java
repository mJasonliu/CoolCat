package com.axis.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jason on 2016/1/23.
 */
public class SongListDatabase {

    private Context context= null;
    private List<Map<String, String>> mediaMaps = null;
    private Map<String, String> media = null;


    public SongListDatabase(Context context) {
        this.context = context;
        mediaMaps = new ArrayList<Map<String, String>>();
        media = new HashMap<String, String>();
        fun1();
    }
    private void fun1(){
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, new String[]{MediaStore.Audio.Media.TITLE}, null, null, MediaStore.Audio.Media.TITLE);
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                media.put(cursor.getColumnName(0), cursor.getString(0));
                mediaMaps.add(media);
            }

        }

    }
}
