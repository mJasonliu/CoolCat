package com.axis.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jason on 2016/1/18.
 */
public class SongInfo {
    String temp = null;
    Matcher matcher = null;
    JSONObject jsonObject = null;
    String jsonString = null;


    public SongInfo(String temp) throws JSONException {
        this.temp = temp;
        jsonObject = new JSONObject(this.temp);
        Object obj = jsonObject.get("data");
        jsonString = obj.toString();
    }
    public String getAlbumName() {
        matcher = Pattern.compile("(?<=albumName\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String albumName =  matcher.group();
        return albumName;
    }

    public String getArtistName() {
        matcher = Pattern.compile("(?<=artistName\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String artistName = matcher.group();
        return artistName;
    }
    public String getTime() {
        matcher = Pattern.compile("(?<=time\":).+?(?=,\")").matcher(jsonString);
        matcher.find();
        String time = matcher.group();
        if (Integer.parseInt(time) % 60 < 10) {
            time = Integer.parseInt(time) / 60 + " : 0" + Integer.parseInt(time) % 60;
        } else {
            time = Integer.parseInt(time) / 60 + " : " + Integer.parseInt(time) % 60;
        }
        return time;
    }

    public String getSongName() {
        matcher = Pattern.compile("(?<=songName\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String songName = matcher.group();
        return songName;
    }

    public String getFormat() {
        matcher = Pattern.compile("(?<=format\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String format = matcher.group();
        return format;
    }

    public String getSongPicBig() {
        matcher = Pattern.compile("(?<=songPicBig\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String songPicBig = matcher.group();
        songPicBig = StringEscapeUtils.unescapeJava(songPicBig);
        return songPicBig;
    }

    public String getSongLink() {
        matcher = Pattern.compile("(?<=songLink\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String songLink = matcher.group();
        songLink = StringEscapeUtils.unescapeJava(songLink);
        return songLink;
    }

    public String getLrcLink() {
        matcher = Pattern.compile("(?<=lrcLink\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String temp = matcher.group();
        temp = StringEscapeUtils.unescapeJava(temp);
        String lrcLink = "http://music.baidu.com" + temp;
        return lrcLink;
    }

    public String getSongPicSmall() {
        matcher = Pattern.compile("(?<=songPicSmall\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String songPicSmall = matcher.group();
        songPicSmall = StringEscapeUtils.unescapeJava(songPicSmall);
        return songPicSmall;
    }

    public String getSongPicRadio() {
        matcher = Pattern.compile("(?<=songPicRadio\":\").+?(?=\")").matcher(jsonString);
        if (matcher.find() == false) return null;
        String songPicRadio = matcher.group();
        songPicRadio = StringEscapeUtils.unescapeJava(songPicRadio);
        return songPicRadio;
    }
}
