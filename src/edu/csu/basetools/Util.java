package edu.csu.basetools;

import android.app.Activity;
import android.os.Environment;

public class Util {
    private static Util util;
    public static int flag = 0;

    private Util() {

    }

    public static Util getInstance() {
        if (util == null) {
            util = new Util();
        }
        return util;
    }

    /**
     * �ж��Ƿ���sdcard
     *
     * @return
     */
    public boolean hasSDCard() {
        boolean b = false;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            b = true;
        }
        return b;
    }

    /**
     * �õ�sdcard·��
     *
     * @return
     */
    public String getExtPath() {
        String path = "";
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory().getPath();
        }
        return path;
    }

    /**
     * �õ�/data/data/yanbin.imagedownloadĿ¼
     *
     * @param mActivity
     * @return
     */
    public String getPackagePath(Activity mActivity) {
        return mActivity.getFilesDir().toString();
    }

    /**
     * ����url�õ�ͼƬ��
     *
     * @param url
     * @return
     */
    public String getImageName(String url) {
        String imageName = "";
        if (url != null) {
            imageName = url.substring(url.lastIndexOf("/") + 1);
        }
        return imageName;
    }
}
