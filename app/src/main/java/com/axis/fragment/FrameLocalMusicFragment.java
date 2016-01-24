package com.axis.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axis.coolcat.R;
import com.axis.util.Constant;

/**
 * @author lq 2013-6-1 lq2625304@gmail.com
 */
public class FrameLocalMusicFragment extends Fragment {
    private final String  TAG = this.getClass().getName();
    private final Fragment fragment = new TrackBrowserFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frame_for_nested_fragment,
                container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = new Bundle();
        args.putInt(Constant.PARENT, Constant.START_FROM_LOCAL_MUSIC);

        fragment.setArguments(args);
//        Log.i(TAG , "我执行了一遍");
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.frame_for_nested_fragment, fragment)
                .commit();
    }

    /**
     * Called when the hidden state (as returned by {@link #isHidden()} of
     * the fragment has changed.  Fragments start out not hidden; this will
     * be called whenever the fragment changes state from that.
     *
     * @param hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
//        Log.i(TAG, "================我执行了");

        if(hidden) {
//            Log.i(TAG, "================我隐藏了");

            getChildFragmentManager().beginTransaction().hide(fragment).commit();
        }else {
//            Log.i(TAG, "================我现身了");

            getChildFragmentManager().beginTransaction().show(fragment).commit();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
