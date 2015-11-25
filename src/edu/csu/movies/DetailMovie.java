package edu.csu.movies;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import edu.csu.basetools.BaseActivity;
import edu.csu.basetools.C;
import edu.csu.basetools.WebImageBuilder;
import edu.csu.mobiVod.R;

public class DetailMovie extends BaseActivity {

	ImageView movieCover;
	MovieEntity me;
	TextView movieIntroduction;
	TextView movieName;
	TextView movieDirector;
	TextView movieActors;
	TextView movieDate;
	TextView movieTime;
	TextView movieScore;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				ImageView imageView = (ImageView) findViewById(R.id.detail_bofang);
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// url+=getLocalMacAddress();
						Intent intent = new Intent(DetailMovie.this,
								PlayeNetVideo.class);
						intent.putExtra("fileaddress", url);
						startActivity(intent);
					}
				});
				break;

			default:
				break;
			}
		};
	};
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailmovie);
		setNeedBackGesture(true);
		Intent intent = getIntent();
		iniView();

		final String id = intent.getStringExtra("id");
		new Thread(new Runnable() {
			@Override
			public void run() {
				SendPost sendPost = new SendPost(DetailMovie.this);
				String get = sendPost.sendPostParams(C.MovieDetial,
						"{movie_id:'" + id + "'}");
				// String
				// get=sendPost.sendPostParams("http://192.168.1.109:8081/dpc/movie/detail/"+id,"");
				/*
				 * String get=
				 * "{\"BODY\":{\"result\":\"eyJtb3ZpZV9hY3RvciI6IuW8oOS4ie+8jOadjuWbmyIsIm1vdmllX2NsYXNzZXMiOiLl"
				 * +
				 * "lpzliaciLCJtb3ZpZV9kYXRlIjoiMjAxNeW5tDEy5pyIIiwibW92aWVfZGlyZWN0b3IiOiLlsI/mmI4iLCJtb3ZpZV9maWxlYWRkcmVzcyI6Imh0dHA6"
				 * +
				 * "Ly8xOTIuMTY4LjEuMTI2OjgwODAvVmlkZW9UZXN0U2VydmVyLzIubXA0IiwibW92aWVfaWQiOiIxMSIsIm1vdmllX2ludHJvZHVjdGlvbiI6Iua1i+ivle"
				 * +
				 * "eUqOaVsOaNriIsIm1vdmllX3BpYyI6IiIsIm1vdmllX3Njb3JlIjoiOC4wIiwibW92aWVfdGltZSI6IjEyMCIsIm1vdmllX3RpdGxlIjoi5rWL6K+VMSI"
				 * +
				 * "sIm1vdmllX3pvbmUiOiLkuK3lm73lpKfpmYYifQ==\"},\"HEAD\":{\"count\":\"0\",\"msg\":\"OK\",\"result_code\":\"1\"}}"
				 * ;
				 */JSONObject jb;
				try {
					jb = new JSONObject(get);
					Body b = (Body) FromJsonToJavas.fromJsonToJava(
							jb.getJSONObject("BODY"), Body.class);
					String json = new String(Base64.decode(b.getResult(),
							Base64.DEFAULT), "UTF-8");
					me = (MovieEntity) FromJsonToJavas.fromJsonToJava(
							new JSONObject(json), MovieEntity.class);
					System.out.println(me.getMovie_fileaddress());
					url = me.getMovie_fileaddress();
					movieCover.setImageBitmap(WebImageBuilder.returnBitMap(me
							.getMovie_pic()));
					movieIntroduction.setText(me.getMovie_introduction());
					movieName.setText(me.getMovie_title());
					movieDirector.setText(me.getMovie_director());
					movieActors.setText(me.getMovie_actor());
					movieDate.setText(me.getMovie_date());
					movieTime.setText(me.getMovie_time());
					movieScore.setText(me.getMovie_score());
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	void iniView() {
		movieCover = (ImageView) findViewById(R.id.detail_bofang);
		movieIntroduction = (TextView) findViewById(R.id.movie_introduction);
		movieName = (TextView) findViewById(R.id.movie_name);
		movieDirector = (TextView) findViewById(R.id.movie_director);
		movieActors = (TextView) findViewById(R.id.movie_actors);
		movieDate = (TextView) findViewById(R.id.movie_date);
		movieTime = (TextView) findViewById(R.id.movie_time);
		movieScore = (TextView) findViewById(R.id.movie_score);
	}

	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
}
