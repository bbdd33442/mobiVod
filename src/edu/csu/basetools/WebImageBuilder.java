package edu.csu.basetools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class WebImageBuilder {

    /**
     * ͨ通过URL获取图片
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitMap(String url) {
        Log.i("111", url);
        URL myFileUrl = null;
        Bitmap bitmap = null;
        bitmap = C.BITMAPCATCH.get(url);
        if (bitmap != null) {
            return bitmap;
        }
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {

            InputStream is = myFileUrl.openStream();

            bitmap = BitmapFactory.decodeStream(is);

            is.close();
            C.BITMAPCATCH.put(url, bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
