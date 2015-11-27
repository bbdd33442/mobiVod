package edu.csu.basetools;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * ͼƬ�첽������ɺ�ص�
 */
public interface OnImageDownload {
    void onDownloadSucc(Bitmap bitmap, String c_url, ImageView imageView);
}
