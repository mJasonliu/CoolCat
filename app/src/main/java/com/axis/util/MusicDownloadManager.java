package com.axis.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Jason on 2016/1/4.
 */
public class MusicDownloadManager implements IDownLoadManager {

    @Override
    public void download(String url, String name, String singer, String path) {
        try {
            singer = URLEncoder.encode(singer, "utf-8");
            name = URLEncoder.encode(name, "utf-8");



            //获取百度API传回的response xml源文件
            MusicXmlParser mxp = new MusicXmlParser();
            URL xmlDownloadURL = new URL(url+name+"$$"+singer+"$$$$");
            URLConnection xmlUrlConnection = xmlDownloadURL.openConnection();
            InputStream xmlIs = xmlUrlConnection.getInputStream();
            List<String> list = mxp.getUrlList(xmlIs);

            //download music file
            URL downloadURL = new URL(list.get(0));
            URLConnection urlConnection = downloadURL.openConnection();
            InputStream is = urlConnection.getInputStream();




            BufferedInputStream bis = new BufferedInputStream(is);
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            File f = new File(path + name + " - "+singer + ".mp3");
            byte[] b = new byte[512];
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            while (bis.read(b) != -1){
                bos.write(b);
            }
            bos.close();
            bis.close();
            is.close();
            xmlIs.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
