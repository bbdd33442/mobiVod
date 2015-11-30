package edu.csu.music;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edu.csu.basetools.BaseActivity;
import edu.csu.basetools.C;
import edu.csu.mobiVod.R;
import edu.csu.movies.SendPost;
import edu.csu.xlistview.XListView;
import edu.csu.xlistview.XListView.IXListViewListener;

public class InternetMusic extends BaseActivity implements IXListViewListener {
    XListView xListView;
    private ArrayList<HashMap<String, String>> list;
    private ListViewMusic listItemAdapter;
    HashMap<String, String> map;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Initlist();
                    break;
                case 0:
                    Toast.makeText(InternetMusic.this, "δ֪ԭ��ʧ�ܡ�", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internetmusic);
        setNeedBackGesture(true);
        init();
    }

    private void Initlist() {
        listItemAdapter = new ListViewMusic(InternetMusic.this, list, xListView);
        xListView.setAdapter(listItemAdapter);
        xListView.setSelection(0);
        Log.v("fffffffffff", list.toString());
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InternetMusic.this, MusicPlayerActivity.class);
                intent.putExtra("type", "netmusic");
                intent.putExtra("filepath", list.get(position - 1).get("fileurl"));
                intent.putExtra("music_cover_url", list.get(position - 1).get("music_cover_url"));
                intent.putExtra("musicname", list.get(position - 1).get("name"));
                intent.putExtra("artist", list.get(position - 1).get("artist"));
                startActivity(intent);
            }
        });
    }

    private void init() {
        xListView = (XListView) this.findViewById(R.id.internetmusic_listview);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(InternetMusic.this);
        list = new ArrayList<HashMap<String, String>>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendPost sendPost = new SendPost(InternetMusic.this);
                String getjson = sendPost.sendPostParams(C.Music, "{}");
                //String getjson="{\"BODY\":{\"result\":\"W3sibW92aWVfZGF0ZSI6IjIwMTXlubQxMuaciCIsIm1vdmllX2lkIjoiMTEiLCJtb3ZpZV9waWMiOiIiLCJtb3ZpZV90aW1lIjoiMTIwIiwibW92aWVfdGl0bGUiOiLmtYvor5UxIn1d\"},\"HEAD\":{\"count\":\"1\",\"msg\":\"OK\",\"result_code\":\"1\"}}";
                try {
                    Log.v("ffffff", getjson);
                    JSONObject jb = new JSONObject(getjson);
                    Body b = (Body) FromJsonToJavas.fromJsonToJava(jb.getJSONObject("BODY"), Body.class);
                    Log.v("ffffffffffff", b.getResult());
                    String json = new String(Base64.decode(b.getResult(), Base64.DEFAULT), "UTF-8");
                    Log.v("json", json);
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MusicListVO ml = (MusicListVO) FromJsonToJavas.fromJsonToJava(jsonArray.getJSONObject(i), MusicListVO.class);
                        //System.out.println(ml.getMusic_id());
                        map = new HashMap<String, String>();
                        map.put("music_id", ml.getMusic_id());
                        map.put("name", ml.getMusic_title());
                        map.put("artist", ml.getMusic_artist());
                        map.put("fileurl", ml.getMusic_time());
                        map.put("music_cover_url", ml.getMusic_pic());
                        list.add(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {//MENU��
            return true;
        }
        return false;

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
