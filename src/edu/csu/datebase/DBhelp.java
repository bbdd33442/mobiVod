package edu.csu.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelp extends SQLiteOpenHelper {
    private static final String CREATE_TBL = " create table if not exists  Movie(_id integer primary key autoincrement,"
            + "MovieTitle text, MovieDate date,MovieOpenTime date,MovieLastTime date,MovieFile text,"
            + "MovieWhere date, MovieLike int, Moiveflag int ) ";
    private static final String TBL_NAME = "Movie";
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
        String SQL = "INSERT INTO [" + TBL_NAME + "]([MovieTitle],[MovieDate],[MovieLastTime],[MovieFile],"
                + "[MovieWhere],[MovieLike],[Moiveflag])"
                + " SELECT '" + values.getAsString("movietitle") + "','" + values.getAsString("moviedate") + "','" +
                values.getAsString("movielasttime") + "','" + values.getAsString("moviefile")
                + "','00:00','0','0'"
                + " WHERE NOT EXISTS(select 1 from [" + TBL_NAME + "] where [MovieFile]='" + values.getAsString("moviefile") + "');";
        db.execSQL(SQL);
        db.close();
    }

    public Cursor query(String sqldesc) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TBL_NAME, null, null, null, null, null, sqldesc, null);
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
        db.execSQL("update " + TBL_NAME + " set what='" + what + "' where url='" + url + "';");
        db.close();
    }

    public void getcount() {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}