package com.axis.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.axis.fragment.SettingFragment;
import com.axis.coolcat.R;
import com.umeng.analytics.MobclickAgent;

/**
 * @author lq 2013-6-1 lq2625304@gmail.com
 */
public class MyPreferenceActivity extends Activity {
    private static final String TAG = MyPreferenceActivity.class
            .getSimpleName();
    private View mView_Close = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_preference);
        mView_Close = findViewById(R.id.close_setting);
        mView_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPreferenceActivity.this.finish();
            }
        });

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_preference, new SettingFragment(),
                        SettingFragment.class.getSimpleName()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
