package com.axis.myinterface;

import android.content.Context;

import com.axis.util.AboutSong;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by mjaso on 2016/1/18.
 */
public interface ISearchFor {
    String UTF_8 = "utf-8";
    String GB_2312 = "gb2312";

    void search(String want) throws IOException, JSONException;

    /**
     * @return return the result of search
     * use String as result's type
     */
    List<AboutSong> getResult(Context context, String want) throws IOException, JSONException;
}
