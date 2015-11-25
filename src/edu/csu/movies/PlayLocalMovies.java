package edu.csu.movies;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import edu.csu.basetools.BaseActivity;
import edu.csu.mobiVod.R;

public class PlayLocalMovies extends BaseActivity  {
	VideoView videoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlocalmovie);
		setNeedBackGesture(true);
		Intent intent=getIntent();
		String path=intent.getStringExtra("path");
		videoView=(VideoView)findViewById(R.id.playlocal_videoView);
		File file=new File(path);
		MediaController mediaController=new MediaController(PlayLocalMovies.this);
		if(file.exists()){
			videoView.setVideoPath(file.getPath());
			videoView.setMediaController(mediaController);
			videoView.requestFocus();
			videoView.start();
		}
	}
}
