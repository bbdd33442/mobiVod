package edu.csu.mobiVod;

import android.os.Bundle;

import edu.csu.basetools.BaseActivity;
import edu.csu.basetools.C;
import edu.csu.basetools.SharedPreferencetool;
import edu.csu.movies.SendPost;

public class ResideMore extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.residemore);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendPost sendPost = new SendPost(ResideMore.this);
                SharedPreferencetool tool = new SharedPreferencetool(ResideMore.this);
                String token = tool.GetSharedPre("user-info", "token", "").replace("\n", "");
                String result = sendPost.sendPostParams(C.Logout,
                        "{\"device_token\":\"" + token + "\",\"device_logout_time\":\"" + System.currentTimeMillis() + "\"}");
                System.out.println(result);
            }
        }).start();
    }
}
