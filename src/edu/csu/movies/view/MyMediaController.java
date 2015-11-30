package edu.csu.movies.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;

/**
 * Created by liuxi on 2015/11/27.
 * 加入dlna功能
 */
public class MyMediaController extends MediaController {
    private static final String TAG = "MyMediaController";
    private ImageButton mDLNABtn;

    public MyMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public MyMediaController(Context context) {
        super(context);
    }

    /**
     * 添加dlna按钮
     */
    public void addDLNABtn() {
        mDLNABtn = new ImageButton(this.getContext());
        mDLNABtn.setBackgroundResource(android.R.color.holo_purple);
        LinearLayout ll = (LinearLayout) ((LinearLayout) getChildAt(0)).getChildAt(0);
        Log.i(TAG, "add dlna button at index: " + ll.getChildCount());
        ll.addView(mDLNABtn, ll.getChildCount());
    }

    public void setDLNAOnClickListener(OnClickListener listener) {
        if (mDLNABtn != null) {
            mDLNABtn.setOnClickListener(listener);
        }
    }
/*
    @Override
    public void onFinishInflate() {
        Log.d(TAG, "finish inflate");
        super.onFinishInflate();
        addDLNABtn();
    }*/
}
