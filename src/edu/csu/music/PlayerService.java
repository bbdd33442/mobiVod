package edu.csu.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;

public class PlayerService extends Service {
    public MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mediaPlayer = new MediaPlayer();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String path = intent.getStringExtra("filepath");
        this.playMusic(path);
        return super.onStartCommand(intent, flags, startId);

    }

    void playMusic(String path) {
        try {
            if (mediaPlayer.isPlaying())
                mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mediaPlayer.stop();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
} 