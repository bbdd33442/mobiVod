package edu.csu.movies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Video.VideoColumns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import edu.csu.basetools.BaseActivity;
import edu.csu.datebase.DBhelp;
import edu.csu.mobiVod.R;
import edu.csu.xlistview.XListView;
import edu.csu.xlistview.XListView.IXListViewListener;

public class LocalMovie extends BaseActivity implements IXListViewListener{
	XListView xListView;
	private ArrayList<HashMap<String, String>> list;
	private ListViewMovie listItemAdapter;
	HashMap<String, String> map;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.localmovie);
		setNeedBackGesture(true);
		init();
		
	}
	private void GetDateFromDB() {
		DBhelp help = new DBhelp(LocalMovie.this,"movie.db",1);  
	    Cursor c = help.query(null);
	    int count=0;
	    list = new ArrayList<HashMap<String, String>>();
	    while(c.moveToNext()) 
        {   
	    map= new HashMap<String, String>(); 
		map.put("movietitle",c.getString(1));
		map.put("movielasttime",c.getString(4));
		map.put("moviefile",c.getString(5));
        list.add(map);
        count++;//item数量自增
        }
	    c.close();help.close();
	    if(count==0)
	    	Addnewmovie();
	    else{
			listItemAdapter= new ListViewMovie(LocalMovie.this,list,xListView);
			xListView.setAdapter(listItemAdapter);
			xListView.setSelection(0);
			xListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					Intent intent=new Intent();
					intent.putExtra("path",list.get(position-1).get("moviefile") );
					intent.setClass(LocalMovie.this, PlayLocalMovies.class);
					startActivity(intent);
					
				}
			});
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private void Addnewmovie() {
		 final TextView textView=(TextView)findViewById(R.id.localmovie_textkongkong);
		 final Button button=(Button)findViewById(R.id.localmovie_buttonadd);
		 textView.setVisibility(View.VISIBLE);
		 button.setVisibility(View.VISIBLE);
		 button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Cursor cursor = LocalMovie.this.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
		                 null, null,null, null);
				 int totalCount =cursor.getCount();
				 cursor.moveToFirst();
				 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 DateFormat df2 = new SimpleDateFormat("mm:ss");
				 DBhelp help = new DBhelp(LocalMovie.this,"movie.db",1);
				for( int i = 0;i < totalCount;i++){
				     String fileaddress = cursor.getString(cursor.getColumnIndex(MediaColumns.DATA));//路径
				     String adddate = cursor.getString(cursor.getColumnIndex(MediaColumns.DATE_ADDED));//添加时间
				     String lasttime = cursor.getString(cursor.getColumnIndex(VideoColumns.DURATION));//经历时间时长
				     String title = cursor.getString(cursor.getColumnIndex(MediaColumns.TITLE));//标题
				     ContentValues values = new ContentValues();
			    	 values.put("moviefile",fileaddress);
			         values.put("moviedate",df.format(new Date(Long.parseLong(adddate)*1000)));
			    	 values.put("movielasttime",df2.format(new Date(  Integer.parseInt(lasttime)-8*60*60*1000 )));
			    	 values.put("movietitle",title);
			    	 help.insert(values);
				     cursor.moveToNext();
				 }
				cursor.close();
				help.close();
				Toast.makeText(LocalMovie.this,"扫描完毕", Toast.LENGTH_LONG).show();
				textView.setVisibility(View.GONE);
				button.setVisibility(View.GONE);
				GetDateFromDB(); 
			}
		});
		 
	}
	
	private void init() {
		xListView=(XListView)this.findViewById(R.id.localmovie_listview);
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(LocalMovie.this);
		GetDateFromDB();
	}
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK)  
            {
        	onBackPressed();finish();
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        	return true; 
            }
        else if(keyCode == KeyEvent.KEYCODE_MENU) {//MENU键
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
