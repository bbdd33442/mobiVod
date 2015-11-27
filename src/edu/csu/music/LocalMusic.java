package edu.csu.music;

import java.util.ArrayList;
import java.util.HashMap;

import edu.csu.basetools.BaseActivity;
import edu.csu.mobiVod.R;
import edu.csu.xlistview.XListView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class LocalMusic extends BaseActivity {
    private XListView playlist;
    private SystemMusicData smd;
    private ArrayList<HashMap<String, String>> list;
    private String selectName;
    private String selectArtist;
    private DBhelp help;
    private Cursor c;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localmusic);
        smd = new SystemMusicData(this);
        list = new ArrayList<HashMap<String, String>>();
        textView = (TextView) findViewById(R.id.localmusic_textkongkong);
        button = (Button) findViewById(R.id.localmusicbtnadd);
        help = new DBhelp(this.getApplicationContext(), "musicdata.db", 1);
        c = help.query();
        playlist = (XListView) findViewById(R.id.show_play_list);

        if (c.getCount() == 0) {
            textView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    smd.InsertMusicInfoToDatabase();
                    Toast.makeText(LocalMusic.this, "ɨ�����", Toast.LENGTH_LONG).show();
                    textView.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    GetItemFromDB();
                }
            });
        }
        GetItemFromDB();
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this, list, R.layout.musicitem,
                new String[]{"name", "artist", "duration"},
                new int[]{R.id.musicName, R.id.musicArtist,
                        R.id.musicduration});
        playlist.setAdapter(simpleAdapter);
        setListener();
    }

    private void setListener() {
        playlist.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                selectName = list.get(arg2 - 1).get("name").toString();
                Intent intent = new Intent(LocalMusic.this, MusicPlayerActivity.class);
                intent.putExtra("name", list.get(arg2 - 1).get("name").toString());
                intent.putExtra("artist", list.get(arg2 - 1).get("artist").toString());
                intent.putExtra("filepath", help.queryurl(selectName));
                intent.putExtra("type", "localmusic");
                startActivity(intent);
            }
        });


    }

    public void GetItemFromDB() {
        Cursor c = help.query();

        while (c.moveToNext()) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("name", c.getString(1));
            item.put("artist", c.getString(2));
            item.put("duration", c.getString(4));
            list.add(item);
        }
        c.close();
        help.close();
    }


}
