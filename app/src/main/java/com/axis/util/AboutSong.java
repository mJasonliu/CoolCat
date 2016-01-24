package com.axis.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jason on 2016/1/19.
 */
public class AboutSong {
    int mData;
    private String songName, artistName, album, format, songPicSmall, songPicRadio, songLink, time, lyricLink;

    public AboutSong setAll(String songName, String artistName, String album, String format, String time, String songPicSmall, String songPicRadio, String songLink, String lyricLink){

        this.album = album;
        this.songName = songName;
        this.artistName = artistName;
        this.format = format;
        this.songPicSmall = songPicSmall;
        this.songPicRadio = songPicRadio;
        this.songLink = songLink;
        this.time = time;
        this.lyricLink = lyricLink;
        return this;
    }

    public String getLyricLink() {
        return lyricLink;
    }

    public void setLyricLink(String lyricLink) {
        this.lyricLink = lyricLink;
    }

    public String getSongName() {
        return songName;
    }
    public String getTime() {
        return time;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSongPicSmall() {
        return songPicSmall;
    }

    public void setSongPicSmall(String songPicSmall) {
        this.songPicSmall = songPicSmall;
    }

    public String getSongPicRadio() {
        return songPicRadio;
    }

    public void setSongPicRadio(String songPicRadio) {
        this.songPicRadio = songPicRadio;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }
}
