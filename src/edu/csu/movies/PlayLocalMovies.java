package edu.csu.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import edu.csu.basetools.BaseActivity;
import edu.csu.dlna.SearchDLNAActivity;
import edu.csu.mobiVod.R;
import edu.csu.movies.view.MyMediaController;

public class PlayLocalMovies extends BaseActivity {
    private static final String TAG = "PlayLocalMovies";
    public static final String LOCAL_MOVIE_PREFIX = "movies";
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlocalmovie);
        setNeedBackGesture(true);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        videoView = (VideoView) findViewById(R.id.playlocal_videoView);
        final File file = new File(path);
//        MediaController mediaController = new MediaController(PlayLocalMovies.this);
        final MyMediaController mediaController = new MyMediaController(this);
        if (file.exists()) {
            Log.d(TAG, "file path: " + file.getPath());
            videoView.setVideoPath(file.getPath());
            videoView.setMediaController(mediaController);
            videoView.requestFocus();
            videoView.start();
            getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {
                    mediaController.addDLNABtn();
                    mediaController.setDLNAOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(PlayLocalMovies.this, "dlna click!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PlayLocalMovies.this, SearchDLNAActivity.class);
                            i.putExtra("playUri", LOCAL_MOVIE_PREFIX + "/" + getIntent().getStringExtra("id"));
                            PlayLocalMovies.this.startActivity(i);
                            finish();
                            Log.d(TAG, "finish");
                        }
                    });
                }
            });
        }
    }
}
