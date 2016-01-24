package com.axis.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.axis.coolcat.R;
import com.axis.util.AboutSong;
import com.axis.util.Constant;
import com.axis.util.DownloadManagerImpl;
import com.axis.util.LyricDownloadManager;
import com.axis.util.SearchImpl;

import java.io.File;
import java.util.List;

/**
 * Created by Jason on 2016/1/19.
 */
public class SearchResultPage extends Fragment {
    List<AboutSong> list = null;
    ListView listView = null;
    DownloadManagerImpl downloadManager = null;

    public SearchResultPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_searchresult, container, false);
        list = SearchImpl.aboutSongList;
        listView = (ListView) root.findViewById(R.id.fragment_searchList);
        final MyAdapter myAdapter = new MyAdapter(getActivity(), list);
        downloadManager = new DownloadManagerImpl();

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println(list.get(i).getSongLink());
                downloadManager.downloadMuisc(getActivity(), list.get(i).getSongLink(), list.get(i).getSongName(),
                        list.get(i).getArtistName(), getActivity().getSharedPreferences("settings", getActivity().MODE_PRIVATE).getString("key_music_save_path",
                                Constant.MUSIC_SAVE_FOLDER_PATH));
                downloadManager.downloadLyric(getActivity(),list.get(i).getLyricLink(),list.get(i).getSongName(), list.get(i).getArtistName(), getActivity().getSharedPreferences("settings", getActivity().MODE_PRIVATE).getString("key_lyric_save_path",
                        Constant.LYRIC_SAVE_FOLDER_PATH));


            }
        });


        return root;
    }

    private static class ViewHoder {
        private ImageView img = null;
        private TextView tvSongName = null;
        private TextView tvSingerName = null;
        private TextView tvAlbumName = null;
        private TextView tvTime = null;
    }

    private class MyAdapter extends BaseAdapter {
        private List<AboutSong> list = null;
        private ViewHoder viewHoder = null;
        private Context context = null;

        public MyAdapter(Context context, List<AboutSong> list) {
            this.list = list;
            this.context = context;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                viewHoder = new ViewHoder();
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(R.layout.list_item_result, null);
                viewHoder.img = (ImageView) view.findViewById(R.id.list_item_imageView_songIMG);
                viewHoder.tvAlbumName = (TextView) view.findViewById(R.id.list_item_textView_albumName);
                viewHoder.tvSingerName = (TextView) view.findViewById(R.id.list_item_textView_singerName);
                viewHoder.tvSongName = (TextView) view.findViewById(R.id.list_item_textView_songName);
                viewHoder.tvTime = (TextView) view.findViewById(R.id.list_item_textView_Time);
                view.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) view.getTag();
            }
            if (new File(Constant.IMG_SAVE_FOLDER_PATH + list.get(i).getSongName() + ".jpg").exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(Constant.IMG_SAVE_FOLDER_PATH + list.get(i).getSongName() + ".jpg");
                viewHoder.img.setImageBitmap(bitmap);
            } else {
                viewHoder.img.setImageResource(R.drawable.ic_launcher);
            }
            viewHoder.tvAlbumName.setText(list.get(i).getAlbum());
            viewHoder.tvSingerName.setText(list.get(i).getArtistName());
            viewHoder.tvSongName.setText(list.get(i).getSongName());
            viewHoder.tvTime.setText(list.get(i).getTime());
            return view;
        }
    }
}
