package edu.csu.mobiVod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class MoreMain extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thirdcurrent);
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
