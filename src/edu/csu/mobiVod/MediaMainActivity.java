package edu.csu.mobiVod;

import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
@SuppressWarnings("deprecation")
public class MediaMainActivity extends TabActivity {
	TabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent=new Intent().setClass(this, MusicMain.class);
        spec=tabHost.newTabSpec("一").setIndicator("一").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,MovieMain.class);
        spec=tabHost.newTabSpec("二").setIndicator("二").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,MoreMain.class);
        spec=tabHost.newTabSpec("三").setIndicator("三").setContent(intent);
        tabHost.addTab(spec);
        
        
        tabHost.setCurrentTabByTag("一");
        RadioGroup radioGroup=(RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.main_tab_1:
					tabHost.setCurrentTabByTag("一");
					break;
				case R.id.main_tab_2:
					tabHost.setCurrentTabByTag("二");
					break;
				case R.id.main_tab_3:
					tabHost.setCurrentTabByTag("三");
					break;
				default:
					break;
				}
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
