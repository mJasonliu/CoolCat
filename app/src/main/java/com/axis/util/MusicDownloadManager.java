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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import com.axis.myinterface.IDownLoadManager;

/**
 * Created by Jason on 2016/1/4.
 */
public class MusicDownloadManager implements IDownLoadManager {

    @Override
    public boolean download(String url, String name, String path) {
        try {
            name = URLEncoder.encode(name, "utf-8");


            //获取百度API传回的response xml源文件
            MusicXmlParser mxp = new MusicXmlParser();
            URL xmlDownloadURL = new URL(url + name + "$$");
            URLConnection xmlUrlConnection = xmlDownloadURL.openConnection();
            if (xmlUrlConnection == null) {
                return false;
            }

            InputStream xmlIs = xmlUrlConnection.getInputStream();
            if (xmlIs == null) {
                return false;
            }
            List<String> list = mxp.getUrlList(xmlIs);
            if (list.size() != 0) {
                //download music file
                URL downloadURL = new URL(list.get(0));
                URLConnection urlConnection = downloadURL.openConnection();
                InputStream is = urlConnection.getInputStream();


                BufferedInputStream bis = new BufferedInputStream(is);
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                name = URLDecoder.decode(name, "utf-8");
                File f = new File(path + name + ".mp3");
                byte[] b = new byte[1024];
                int foot = 0;
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                while ((foot = bis.read(b)) != -1) {
                    bos.write(b, 0, foot);
                }
                bos.close();
                bis.close();
                is.close();
                xmlIs.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }

    @Override
    public boolean download(String url, String name, String singer, String path) {
        try {
            singer = URLEncoder.encode(singer, "utf-8");
            name = URLEncoder.encode(name, "utf-8");


            //获取百度API传回的response xml源文件
            MusicXmlParser mxp = new MusicXmlParser();
            URL xmlDownloadURL = new URL(url + name + "$$" + singer + "$$$$");
            URLConnection xmlUrlConnection = xmlDownloadURL.openConnection();
            if (xmlUrlConnection == null) return false;
            InputStream xmlIs = xmlUrlConnection.getInputStream();
            if (xmlIs == null) return false;
            List<String> list = mxp.getUrlList(xmlIs);
            if (list.size() != 0&&list!=null) {
                //download music file
                URL downloadURL = new URL(list.get(0));
                URLConnection urlConnection = downloadURL.openConnection();
                InputStream is = urlConnection.getInputStream();


                BufferedInputStream bis = new BufferedInputStream(is);
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                name = URLDecoder.decode(name, "utf-8");
                singer = URLDecoder.decode(singer, "utf-8");
                File f = new File(path + name + " - " + singer + ".mp3");
                byte[] b = new byte[1024];
                int foot = 0;
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
                while ((foot = bis.read(b)) != -1) {
                    bos.write(b, 0, foot);
                }
                bos.close();
                bis.close();
                is.close();
                xmlIs.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
        return true;

    }
}
