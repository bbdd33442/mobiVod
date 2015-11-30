package edu.csu.dlna.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import org.cybergarage.upnp.ControlPoint;
import org.cybergarage.upnp.std.av.server.Directory;
import org.cybergarage.upnp.std.av.server.MediaServer;
import org.cybergarage.upnp.std.av.server.directory.file.FileDirectory;

import java.io.IOException;

import edu.csu.dlna.DMCApplication;
import edu.csu.dlna.SearchDLNAActivity;
import edu.csu.dlna.engine.SearchThread;
import edu.csu.dlna.util.LogUtil;

/**
 * The service to search the DLNA Device in background all the time.
 *
 * @author CharonChui
 */
public class DLNAService extends Service {
    private static final String TAG = "DLNAService";
    private ControlPoint mControlPoint;
    private SearchThread mSearchThread;
    private WifiStateReceiver mWifiStateReceiver;
    private HttpServer mHttpServer;
    private MediaServer mMediaServer;

    /**
     * 用于推送本地资源
     */

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unInit();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startThread();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        mControlPoint = new ControlPoint();
        DMCApplication.getInstance().setControlPoint(mControlPoint);
        mSearchThread = new SearchThread(mControlPoint);
        registerWifiStateReceiver();
        //startup httpServer
        try {
            mHttpServer = new HttpServer(SearchDLNAActivity.DEFAULT_PORT);
        } catch (IOException e) {
            Log.e(TAG, "can not create http server!", e);
        }
        mMediaServer = new MediaServer();
        addDemoDir();
        new Thread() {
            @Override
            public void run() {
                mMediaServer.start();
            }
        }.start();

    }

    private void addDemoDir() {
        Directory dir = new FileDirectory("title-test", "/storage/sdcard0");
        mMediaServer.addContentDirectory(dir);
    }

    private void unInit() {
        stopThread();
        unregisterWifiStateReceiver();
        mHttpServer.stop();
//        mMediaServer.stop();
    }

    /**
     * Make the thread start to search devices.
     */
    private void startThread() {
        if (mSearchThread != null) {
            LogUtil.d(TAG, "thread is not null");
            mSearchThread.setSearcTimes(0);
        } else {
            LogUtil.d(TAG, "thread is null, create a new thread");
            mSearchThread = new SearchThread(mControlPoint);
        }

        if (mSearchThread.isAlive()) {
            LogUtil.d(TAG, "thread is alive");
            mSearchThread.awake();
        } else {
            LogUtil.d(TAG, "start the thread");
            mSearchThread.start();
        }
    }

    private void stopThread() {
        if (mSearchThread != null) {
            mSearchThread.stopThread();
            mControlPoint.stop();
            mSearchThread = null;
            mControlPoint = null;
            LogUtil.w(TAG, "stop dlna service");
        }
    }

    private void registerWifiStateReceiver() {
        if (mWifiStateReceiver == null) {
            mWifiStateReceiver = new WifiStateReceiver();
            registerReceiver(mWifiStateReceiver, new IntentFilter(
                    ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    private void unregisterWifiStateReceiver() {
        if (mWifiStateReceiver != null) {
            unregisterReceiver(mWifiStateReceiver);
            mWifiStateReceiver = null;
        }
    }

    private class WifiStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context c, Intent intent) {
            Bundle bundle = intent.getExtras();
            int statusInt = bundle.getInt("wifi_state");
            switch (statusInt) {
                case WifiManager.WIFI_STATE_UNKNOWN:
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    LogUtil.e(TAG, "wifi enable");
                    startThread();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    LogUtil.e(TAG, "wifi disabled");
                    break;
                default:
                    break;
            }
        }
    }

}