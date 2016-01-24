package com.axis.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jason on 2016/1/22.
 */
public class MusicDownload {
    public void MusicDownload(String urlString,String songName, String singerName, String pathString) throws IOException {
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        File path = new File(pathString);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(pathString + singerName + " - " + songName + ".mp3");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int i;
        byte[] bytes = new byte[1024];
        while ((i = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, i);
        }
        fileOutputStream.close();
        inputStream.close();
    }
}
