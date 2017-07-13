package com.android.rdc.rdcblog.bloglist.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 2016/7/30.
 */
public class HistorySQLiteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    //历史搜索记录建表语句
    public static final String CREATE_HISTORY = "create table history_search("+
            "id integer primary key autoincrement,"+
            "history_search_name text)";

    public HistorySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
