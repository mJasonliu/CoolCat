package com.axis.util;

import android.content.Context;

import com.axis.myinterface.ISearchFor;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by mjaso on 2016/1/18.
 */
public class SearchImpl implements ISearchFor {
    public static List<AboutSong> aboutSongList = null;

    @Override
    public void search(String want) throws IOException, JSONException {
        String musicNum = null;
        List<String> idList = new ArrayList<String>();
        List<String> strList = new ArrayList<String>();
        want = URLEncoder.encode(want, UTF_8);
        String urlString = "http://music.baidu.com/search?key=" + want;
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (httpURLConnection == null) return;
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER");
        InputStream inputStream = httpURLConnection.getInputStream();
        if (inputStream == null) return;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        String temp = null;
        byte[] bytes = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null) {
            stringBuilder.append(temp + "\n");
        }
        httpURLConnection.disconnect();
        bufferedReader.close();
        inputStream.close();


        //获取详细地址
        String urlString2 = stringBuilder.toString();
        urlString2 = StringEscapeUtils.unescapeHtml(urlString2);
//        System.out.println(urlString2);
        Pattern pSongsNum = Pattern.compile("(?<=\"number\">)\\d+");//搜索出的歌曲总数
        Pattern pList = Pattern.compile("(?<=data-songdata=\\'\\{ \"id\": \")\\d+(?=\")");//歌曲ID列表
        Matcher matcher = pSongsNum.matcher(urlString2);
//        System.out.println(matcher.matches());
        while (matcher.find()) {
//            System.out.println(matcher.group());
            musicNum = matcher.group();
        }
        if (musicNum.equals("0")) return;
        matcher = pList.matcher((urlString2));
        while (matcher.find()) {
            idList.add(matcher.group());
        }
        if (idList.size() == 0) return;
//        System.out.println(idList.size());

//        歌曲详细API：http://music.baidu.com/data/music/links?songIds={0}

        for (String str : idList) {

            url = new URL("http://music.baidu.com/data/music/links?songIds=" + str);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.154 Safari/537.36 LBBROWSER");
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            temp = null;
            stringBuilder = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            httpURLConnection.disconnect();
            bufferedReader.close();
            inputStream.close();
            want = stringBuilder.toString();
            strList.add(want);
        }


        aboutSongList = new ArrayList<AboutSong>();

        String songName, artistName, albumName, format, time, songPicSmall, songPicRadio, songLink, lyricLink;


        for (String str : strList) {
            SongInfo songInfo = new SongInfo(str);
            songName = songInfo.getSongName();
            artistName = songInfo.getArtistName();
            albumName = songInfo.getAlbumName();
            format = songInfo.getFormat();
            time = songInfo.getTime();
            songPicSmall = songInfo.getSongPicSmall();
            songPicRadio = songInfo.getSongPicRadio();
            songLink = songInfo.getSongLink();
            lyricLink = songInfo.getLrcLink();
            if (songName == null) {
                songName = "songName";
            }
            if (artistName == null) {
                artistName = "artistName";
            }
            if (albumName == null) {
                albumName = "albumName";
            }
            if (format == null) {
                format = "format";
            }
            if (time == null) {
                time = "time";
            }
            if (songPicSmall == null) {
                songPicSmall = "songPicSmall";
            }
            if (songPicRadio == null) {
                songPicRadio = "songPicRadio";
            }
            if (songLink == null) {
                songLink = "songLink";
            }
            aboutSongList.add(new AboutSong().setAll(songName, artistName, albumName, format, time, songPicSmall, songPicRadio, songLink, lyricLink));
        }
        DownloadManagerImpl d = new DownloadManagerImpl();

        for (AboutSong abs : aboutSongList) {
            d.downloadIMG(abs.getSongPicSmall(), abs.getSongName());
//            System.out.println("==========songlyric:" + abs.getSongLink());
        }

//        httpURLConnection = (HttpURLConnection) (new URL(songInfo.getSongLink())).openConnection();
//        System.out.println(songInfo.getSongLink());
//        inputStream = httpURLConnection.getInputStream();
//        File file = new File(songInfo.getSongName() + ".mp3");
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
//        int i;
//        bytes = new byte[1024];
//        while ((i = inputStream.read(bytes)) != -1) {
//            bufferedOutputStream.write(bytes, 0, i);
//        }
//        bufferedOutputStream.close();
//        inputStream.close();
//        httpURLConnection.disconnect();

    }
    private Context context = null;

    @Override
    public List<AboutSong> getResult(Context context, String want) throws IOException, JSONException {
        if (want == null || want.equals("")) return null;
        this.context = context;
        search(want);
        return aboutSongList;
    }
}
