package edu.csu.movies;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import edu.csu.basetools.BaseActivity;
import edu.csu.basetools.C;
import edu.csu.basetools.WebImageBuilder;
import edu.csu.mobiVod.R;
import edu.csu.xlistview.XListView;
import edu.csu.xlistview.XListView.IXListViewListener;

public class InternetMovie extends BaseActivity implements IXListViewListener {
    XListView xListView;
    private ArrayList<HashMap<String, String>> list;
    private ListViewMovie listItemAdapter;
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
                    Toast.makeText(InternetMovie.this, "δ֪ԭ��ʧ�ܡ�", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internetmovie);
        setNeedBackGesture(true);
        init();
    }

    private void Initlist() {
        listItemAdapter = new ListViewMovie(InternetMovie.this, list, xListView);
        xListView.setAdapter(listItemAdapter);
        xListView.setSelection(0);
        Log.v("fffffffffff", list.toString());
        xListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InternetMovie.this, DetailMovie.class);
                intent.putExtra("id", list.get(position - 1).get("movie_id"));
                startActivity(intent);
            }
        });
    }

    private void init() {
        xListView = (XListView) this.findViewById(R.id.internetmovie_listview);
        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(InternetMovie.this);
        list = new ArrayList<HashMap<String, String>>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SendPost sendPost = new SendPost(InternetMovie.this);
                String getjson = sendPost.sendPostParams(C.Movie, "{}");

                //String getjson="{\"BODY\":{\"result\":\"W3sibW92aWVfZGF0ZSI6IjIwMTXlubQxMuaciCIsIm1vdmllX2lkIjoiMTEiLCJtb3ZpZV9waWMiOiIiLCJtb3ZpZV90aW1lIjoiMTIwIiwibW92aWVfdGl0bGUiOiLmtYvor5UxIn1d\"},\"HEAD\":{\"count\":\"1\",\"msg\":\"OK\",\"result_code\":\"1\"}}";
                try {
                    Log.v("ffffffssssssssssssssssssssssssssssssssssss", getjson);
                    JSONObject jb = new JSONObject(getjson);
                    Body b = (Body) FromJsonToJavas.fromJsonToJava(jb.getJSONObject("BODY"), Body.class);
                    Log.v("ffffffffffff", b.getResult());
                    String json = new String(Base64.decode(b.getResult(), Base64.DEFAULT), "UTF-8");
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MovieListVO ml = (MovieListVO) FromJsonToJavas.fromJsonToJava(jsonArray.getJSONObject(i), MovieListVO.class);
                        System.out.println(ml.getMovie_id());
                        map = new HashMap<String, String>();
                        map.put("movie_id", ml.getMovie_id());
                        map.put("movietitle", ml.getMovie_title());
                        map.put("movielasttime", ml.getMovie_time());
                        map.put("coverurl", ml.getMovie_pic());
                        Bitmap bit = WebImageBuilder.returnBitMap(map.get("coverurl"));

                        C.BITMAPCATCH.put(map.get("coverurl"), bit);

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
