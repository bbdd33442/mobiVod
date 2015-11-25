package edu.csu.mobiVod;


import com.google.gson.Gson;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import edu.csu.basetools.C;
import edu.csu.basetools.SharedPreferencetool;
import edu.csu.movies.DeviceEntity;
import edu.csu.movies.SendPost;
import edu.csu.remote.RemoteActivity;

@SuppressWarnings("deprecation")
public class MeauActivity extends TabActivity implements View.OnClickListener{
	
	TabHost tabHost;
    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemRemote;
    private ResideMenuItem itemMore;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				SendPost sendPost=new SendPost(MeauActivity.this);
				DeviceEntity  deviceEntity=new DeviceEntity();
				SharedPreferencetool tool=new SharedPreferencetool(MeauActivity.this);
				deviceEntity.setDevice_id("");
				deviceEntity.setDevice_login_time(String.valueOf(System.currentTimeMillis()));
				deviceEntity.setDevice_logout_time("");
				deviceEntity.setDevice_mac(GetMac());
		        deviceEntity.setDevice_name(Build.MODEL);
		        deviceEntity.setDevice_online("1");
		        deviceEntity.setDevice_token(tool.GetSharedPre("user-info","token","").replace("\n",""));
		        deviceEntity.setDevice_type(isTabletDevice());
		        Gson gson=new Gson();
		       // System.out.println(gson.toJson(deviceEntity));
		        String result= sendPost.sendPostParams(C.Login,gson.toJson(deviceEntity));
		        tool.EditShared("user-info","token",result);
		        System.out.println("fffff"+result);
			}
		}).start();
        
        setUpMenu();
        
    }
	
	 public String isTabletDevice() {
	        TelephonyManager telephony = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	        int type = telephony.getPhoneType();
	        if (type == TelephonyManager.PHONE_TYPE_NONE) {
	        	return "0";//平板
	        } else {
	            return "1";//手机
	        }
	}
	private String GetMac(){
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled()) {
			WifiInfo info = wifi.getConnectionInfo();
			return info.getMacAddress();
		}else
			return "";
	}
    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);

        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,"多媒体");
        itemRemote  = new ResideMenuItem(this, R.drawable.icon_profile, "遥控");
        itemMore = new ResideMenuItem(this, R.drawable.icon_settings, "更多");

        itemHome.setOnClickListener(this);
        itemRemote.setOnClickListener(this);
        itemMore.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemRemote, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMore, ResideMenu.DIRECTION_LEFT);
        
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        intent=new Intent().setClass(this, MediaMainActivity.class);
        spec=tabHost.newTabSpec("一").setIndicator("一").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,RemoteActivity.class);
        spec=tabHost.newTabSpec("二").setIndicator("二").setContent(intent);
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this,ResideMore.class);
        spec=tabHost.newTabSpec("三").setIndicator("三").setContent(intent);
        tabHost.addTab(spec);
        
        tabHost.setCurrentTabByTag("一");
        
      
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeActivity(1);
        }else if (view == itemRemote){
            changeActivity(2);
        }else if (view == itemMore){
            changeActivity(3);
        }
        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            //Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void closeMenu() {
           // Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeActivity(int i){
    	if (i==1) {
    		tabHost.setCurrentTabByTag("一");
		}if (i==2) {
			tabHost.setCurrentTabByTag("二");
		}if (i==3) {
			tabHost.setCurrentTabByTag("三");
		}
    }

    public ResideMenu getResideMenu(){
        return resideMenu;
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
