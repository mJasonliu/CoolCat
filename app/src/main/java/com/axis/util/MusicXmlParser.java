package com.axis.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jason on 2016/1/4.
 */
public class MusicXmlParser {
    public List<String> getUrlList(InputStream is) {
        try {
            ArrayList<String> list1 = new ArrayList<String>();
            ArrayList<String> list2 = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "gb2312"));
            StringBuilder sb = new StringBuilder();
            String var;
            while ( (var = bufferedReader.readLine()) != null){
                sb.append(var);
            }
            var = sb.toString();

            sb.setLength(0);
            Pattern pattern = Pattern.compile("(?<=A\\[)h.*?/\\d+?/(?=(.*?]]))");
            Matcher matcher = pattern.matcher(var);
            while (matcher.find()){
                list1.add(matcher.group());
            }
            pattern = Pattern.compile("(\\d{6,8}.mp.*?\\d)(?=]]></)");
            matcher = pattern.matcher(var);
            while (matcher.find()){
                list2.add(matcher.group());
            }


            List<ArrayList<String>> mlist = new ArrayList<ArrayList<String>>();
            mlist.add(list1);
            mlist.add(list2);
            mlist = deal(mlist);//得到了真正的urlpartlist


            List<String> list_1 = new ArrayList<String>();
            List<String> list_2 = new ArrayList<String>();
            list_1 = mlist.get(0);
            list_2 = mlist.get(1);
            List<String> urlList = new ArrayList<String>();

//            for (int i = 0; i < list2.size(); i++) {
//                urlList.add(list_1.get(i) + list_2.get(i));
//            }
            urlList.add(list_2.get(0)+list_1.get(0));
            System.out.println(urlList.get(0));
            return urlList;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<ArrayList<String>> deal(List<ArrayList<String>> temp) {
        List<ArrayList<String>> mlist = new ArrayList<ArrayList<String>>();
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        List<String> list3 = new ArrayList<String>();
        list1 = temp.get(0);
        list2 = temp.get(1);
        for (int i = 0; i < list1.size(); i++) {
            String s1 = list1.get(i);
            StringBuilder sb = new StringBuilder();
            Pattern p1 = Pattern.compile("(.*/)");
            Matcher m1 = p1.matcher(s1);
            while (m1.find()) {
                list3.add(m1.group());
            }
        }
        mlist.add((ArrayList<String>) list2);
        mlist.add((ArrayList<String>) list3);
        return mlist;
    }
}