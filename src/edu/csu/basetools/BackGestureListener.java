package edu.csu.basetools;

import edu.csu.mobiVod.R;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
/**
 * �������Ƽ����ӿ�
 */
public class BackGestureListener implements OnGestureListener {
    BaseActivity activity;

    public BackGestureListener(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        if ((e2.getX() - e1.getX()) > 150 && Math.abs(e1.getY() - e2.getY()) < 30) {
            activity.onBackPressed();
            activity.finish();
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

}
