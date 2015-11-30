package edu.csu.mobiVod;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MediaMainActivity extends TabActivity {
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, MusicMain.class);
        spec = tabHost.newTabSpec("һ").setIndicator("һ").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MovieMain.class);
        spec = tabHost.newTabSpec("��").setIndicator("��").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MoreMain.class);
        spec = tabHost.newTabSpec("��").setIndicator("��").setContent(intent);
        tabHost.addTab(spec);


        tabHost.setCurrentTabByTag("һ");
        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.main_tab_group);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab_1:
                        tabHost.setCurrentTabByTag("һ");
                        break;
                    case R.id.main_tab_2:
                        tabHost.setCurrentTabByTag("��");
                        break;
                    case R.id.main_tab_3:
                        tabHost.setCurrentTabByTag("��");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return false;
    }

}
