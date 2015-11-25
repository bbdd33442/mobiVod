package edu.csu.music;




import org.apache.http.util.EncodingUtils;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;

public class SystemMusicData {
	public DBhelp dbHelp;
	private Cursor cursor;
	private ContentValues asong;
	Context mcontext;
	public SystemMusicData(Context context) {
	    mcontext=context;
		dbHelp = new DBhelp(context,"musicdata.db",1);	
	}
	public void InsertMusicInfoToDatabase()
	{
		cursor=allSongs();
		 if (cursor.moveToFirst()) {			 
			 do {
				 asong= new ContentValues();
	    		 asong.put("name",this.getTitle());
	    	     asong.put("duration",this.getDuartion() );
	    	     asong.put("url",this.getUrl());
	    	     asong.put("artist",this.getArtist());
	    	     asong.put("album",this.getAlbum());
	    	     dbHelp.insert(asong);
				 Log.v("music", this.getArtist());
			 }while (cursor.moveToNext());
		 }	 
	}
	public Cursor allSongs(){
		if (cursor != null)
			return cursor;
		ContentResolver resolver = mcontext.getContentResolver();
		cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		return cursor;

	}
	

	public String getArtist(){

		return cursor.getString(cursor.getColumnIndexOrThrow(AudioColumns.ARTIST));
		
		
	}
	

	public String getTitle(){
		String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.DISPLAY_NAME));
		try {
			title=EncodingUtils.getString(title.getBytes(), "UTF-8");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return title;
	}
	

	public String getAlbum() {
		return cursor.getString(cursor.getColumnIndexOrThrow(AudioColumns.ALBUM));
	}
	public String getUrl(){
		
		return cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.DATA));
	}
	public String getDuartion() 	
	{
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("mm£ºss");
		date.setTime(cursor.getInt(cursor.getColumnIndexOrThrow(AudioColumns.DURATION)));
		String str = sdf.format(date);
		return str;
	}
}
