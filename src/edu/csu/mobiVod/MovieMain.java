package edu.csu.mobiVod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import edu.csu.movies.InternetMovie;
import edu.csu.movies.LocalMovie;

public class MovieMain extends Activity {
	TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondcurrent);
		Button button=(Button)findViewById(R.id.second_buttonbendi);
		title = (TextView) findViewById(R.id.default_title);
		title.setText("µÁ”∞");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MovieMain.this,LocalMovie.class));
			}
		});
		Button buttonnet=(Button)findViewById(R.id.second_buttonnet);
		buttonnet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MovieMain.this,InternetMovie.class));
			}
		});
		
	}
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
            {
        	Intent intent = new Intent(Intent.ACTION_MAIN);
        	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	intent.addCategory(Intent.CATEGORY_HOME);
        	startActivity(intent);
        	return true; 
            }
        return false;  
    }
}
