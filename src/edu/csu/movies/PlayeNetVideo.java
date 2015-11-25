package edu.csu.movies;

import edu.csu.mobiVod.R;
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.util.Log;
import android.view.SurfaceView;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.LinearLayout;
import android.widget.SeekBar;  
  
public class PlayeNetVideo extends Activity {  
    private SurfaceView surfaceView;  
    private Button btnPause, btnStop;  
    private SeekBar skbProgress;  
    private NetVideoPlayer player;  
    LinearLayout linearLayout;
    String url;
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.playnetvideo);  
        
        Intent intent=getIntent();
        url=intent.getStringExtra("fileaddress");
        Log.i("x1", url);
        //url="http://192.168.1.253/movie/2.mp4?start=1638.68";
        linearLayout=(LinearLayout)findViewById(R.id.playnetvideo_liner);
        linearLayout.setVisibility(View.VISIBLE);
        surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView1);
        
        surfaceView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Notilinear(linearLayout);
			}
		});
        
        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);  
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent()); 
        
        player = new NetVideoPlayer(surfaceView, skbProgress);
  
        btnPause = (Button) this.findViewById(R.id.btnPause);  
        btnPause.setOnClickListener(new ClickEvent());  
  
        btnStop = (Button) this.findViewById(R.id.btnStop);  
        btnStop.setOnClickListener(new ClickEvent());  
  
    }  
  
    protected void Notilinear(LinearLayout linearLayout) {
    	if(linearLayout.getVisibility()==View.VISIBLE)
    		linearLayout.setVisibility(View.INVISIBLE);
    	else {
			linearLayout.setVisibility(View.VISIBLE);
		}
	};

	class ClickEvent implements OnClickListener {  
  
        @Override  
        public void onClick(View arg0) {  
            if (arg0 == btnPause) {  
            	linearLayout.setVisibility(View.INVISIBLE);
                player.playUrl(url);
            } else if (arg0 == btnStop) {  
                player.pause();  
            }  
        }  
    }  
  
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {  
        int progress;  
  
        @Override  
        public void onProgressChanged(SeekBar seekBar, int progress,  
                boolean fromUser) {  
            this.progress = progress * player.mediaPlayer.getDuration()  
                    / seekBar.getMax();  
        }  
  
        @Override  
        public void onStartTrackingTouch(SeekBar seekBar) {  
        	
        }  
  
        @Override  
        public void onStopTrackingTouch(SeekBar seekBar) {  
            player.mediaPlayer.seekTo(progress);  
        }  
    }  
  
}  