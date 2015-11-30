package edu.csu.music;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import edu.csu.basetools.ImageDownloader;
import edu.csu.basetools.WebImageBuilder;
import edu.csu.mobiVod.R;

public class MusicPlayerActivity extends Activity {
    private ImageDownloader mDownloader;
    private ImageView musicpic;
    private String picurl = null;
    private String musicname = null;
    private String musicartist = null;
    private String musicurl = null;
    private SeekBar mSeekBar;
    private TextView nameTextview;
    private TextView artistTextview;
    private Button playButton = null;
    private Button mFrontButton = null;
    private Button mLastButton = null;
    private Bitmap b;
    NetPlayer np;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            musicpic.setImageBitmap(b);
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        musicpic = (ImageView) this.findViewById(R.id.music_ablum);
        mSeekBar = (SeekBar) this.findViewById(R.id.audioTrack);
        nameTextview = (TextView) this.findViewById(R.id.musicTitle);
        artistTextview = (TextView) this.findViewById(R.id.musicArtist);
        playButton = (Button) findViewById(R.id.play_music);
        mFrontButton = (Button) findViewById(R.id.previous_music);
        mLastButton = (Button) findViewById(R.id.next_music);
        mDownloader = new ImageDownloader();
        Intent intent = getIntent();
        musicurl = intent.getStringExtra("filepath");
        musicname = intent.getStringExtra("name");
        musicartist = intent.getStringExtra("artist");
        nameTextview.setText(musicname);
        artistTextview.setText(musicartist);
        if (intent.getStringExtra("type").equals("netmusic")) {
            picurl = intent.getStringExtra("music_cover_url");
            np = new NetPlayer(mSeekBar, musicurl);
            np.playUrl();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    b = WebImageBuilder.returnBitMap(picurl);
                    handler.sendEmptyMessage(1);
                }
            }).start();
        } else {
            Intent intent2 = new Intent(this, PlayerService.class);
            intent2.putExtra("filepath", musicurl);
            this.startService(intent2);
            mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {

                }
            });
        }

        //new DownLoadImage(musicpic).execute(picurl);
        //new AlertDialog.Builder(MusicPlayerActivity.this).setTitle("���׸���·��").setMessage(picurl).setPositiveButton("ȷ��", null).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (np != null)
            np.stop();
    }


}
