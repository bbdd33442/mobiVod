package edu.csu.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelp extends SQLiteOpenHelper {
    private static final String CREATE_TBL = " create table if not exists  MusicTable(_id integer primary key autoincrement,name text, artist text,album text,duration text,url text) ";
    private static final String TBL_NAME = "MusicTable";
    private SQLiteDatabase db;

    public DBhelp(Context c, String string, int a) {
        super(c, string, null, a);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(CREATE_TBL);
    }

    public void insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        //db.insert(TBL_NAME,"name",values);
        String SQL = "INSERT INTO [" + TBL_NAME + "]([name],[artist],[album],[duration],[url])"
                + " SELECT '" + values.getAsString("name").replaceAll("\'", "''") + "','" + values.getAsString("artist").replaceAll("\'", "''") + "','" +
                values.getAsString("album").replaceAll("\'", "''") + "','" + values.getAsString("duration") + "','" + values.getAsString("url").replaceAll("\'", "''") + "'"
                + " WHERE NOT EXISTS(select 1 from [" + TBL_NAME + "] where [name]='" + values.getAsString("name").replaceAll("\'", "''") + "');";
        db.execSQL(SQL);
        db.close();
    }

    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        //String[] projection = { "name" ,"artist","duration" };
        Cursor c = db.query(TBL_NAME, null, null, null, null, null, null, null);
        return c;
    }

    public void del(int id) {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TBL_NAME, "_id=?", new String[]{String.valueOf(id)});
    }

    @Override
    public void close() {
        if (db != null)
            db.close();
    }

    public void updata(String what, String url) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update " + TBL_NAME + " set title='" + what + "' where url='" + url + "';");
        db.close();
    }

    public void getcount() {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String queryurl(String selectname) {
        SQLiteDatabase db = getWritableDatabase();
        //String[] projection={"url"};
        String selection = "name=?";
        String[] selectionArgs = {selectname};
        Cursor c = db.query("MusicTable", null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            String path = c.getString(5);
            c.close();
            return path;
        } else
            return null;


    }


}