package com.axis.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.axis.activity.FolderChooseActivity;
import com.axis.coolcat.R;
import com.axis.util.Constant;

/**
 * @author lq 2013-6-1 lq2625304@gmail.com
 */
public class SettingFragment extends PreferenceFragment implements
        OnSharedPreferenceChangeListener {
    // 与其preference.xml中对应的preference标签的key相对应
    public static final String KEY_DOWNLOAD_LYRIC_AUTOMATICALLY = "key_download_lyric_automatically";
    public static final String KEY_LYRIC_SAVE_PATH = "key_lyric_save_path";
    public static final String KEY_MUSIC_SAVE_PATH = "key_music_save_path";
    public static final String KEY_RESET_TO_DEFAULT = "key_reset_to_default";
    public static final String KEY_FILTER_BY_SIZE = "key_filter_by_size";
    public static final String KEY_FILTER_BY_DURATION = "key_filter_by_duration";

    private static final String TAG = SettingFragment.class.getSimpleName();
    private Preference mLyricSavePathPreference = null;
    private Preference mMusicSavePathPreference = null;

    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG, "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        // 加载配置文件
        getPreferenceManager().setSharedPreferencesName("settings");
        addPreferencesFromResource(R.xml.system_preference);

        // 获得歌词保存路径偏好设置对象
        mLyricSavePathPreference = getPreferenceScreen().findPreference(
                KEY_LYRIC_SAVE_PATH);
        // 获得歌曲保存路径偏好设置对象
        mMusicSavePathPreference = getPreferenceScreen().findPreference(
                KEY_MUSIC_SAVE_PATH
        );

    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        // 注册偏好变化监听器，以便更新当前内容
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

        // 显示歌词保存路径
        String summaryLyric = getPreferenceScreen()
                .getSharedPreferences()
                .getString(KEY_LYRIC_SAVE_PATH, Constant.LYRIC_SAVE_FOLDER_PATH);
        mLyricSavePathPreference.setSummary(summaryLyric);
        String summaryMusic = getPreferenceScreen()
                .getSharedPreferences()
                .getString(KEY_MUSIC_SAVE_PATH, Constant.MUSIC_SAVE_FOLDER_PATH);
        mMusicSavePathPreference.setSummary(summaryMusic);

    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    Intent intent;

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        Log.i(TAG, "onPreferenceTreeClick");
        if (preference.getKey().equals(KEY_RESET_TO_DEFAULT)) {
            // TODO 恢复默认设置,清空sharedPreference
            getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
                    .clear().commit();
            getActivity().finish();
            return true;
        } else if (preference.getKey().equals(KEY_LYRIC_SAVE_PATH)) {
            intent = new Intent(getActivity(), FolderChooseActivity.class);
            intent.putExtra("arg", KEY_LYRIC_SAVE_PATH);
            startActivity(intent);
        } else if (preference.getKey().equals(KEY_MUSIC_SAVE_PATH)) {
            intent = new Intent(getActivity(), FolderChooseActivity.class);
            intent.putExtra("arg", KEY_MUSIC_SAVE_PATH);
            startActivity(intent);
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        if (key.equals(KEY_LYRIC_SAVE_PATH)) {
            // 更新歌词保存路径的显示
            mLyricSavePathPreference.setSummary(sharedPreferences.getString(
                    KEY_LYRIC_SAVE_PATH, Constant.LYRIC_SAVE_FOLDER_PATH));
        }
        if (key.equals(KEY_MUSIC_SAVE_PATH)) {
            // 更新歌曲保存路径的显示
            mMusicSavePathPreference.setSummary(sharedPreferences.getString(
                    KEY_MUSIC_SAVE_PATH, Constant.MUSIC_SAVE_FOLDER_PATH));
        }
    }
}
