package com.android.rdc.rdcblog.bloglist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2016/7/30.
 */
public class HistorySearchDB {
    //数据库名称
    public static final String DB_NAME = "history_data";
    //数据库版本
    public static final int VERSION = 1;
    public static HistorySearchDB historySearchDB;
    public SQLiteDatabase db;

    /**
     * 实例化HistorySQLiteOpenHelper
     */
    private HistorySearchDB(Context context){
        HistorySQLiteOpenHelper dbHelper = new HistorySQLiteOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取HistorySearchDB实例
     */
    public static HistorySearchDB newInstance(Context context){
        if(historySearchDB == null){
            synchronized (HistorySearchDB.class){
                if(historySearchDB == null){
                    historySearchDB = new HistorySearchDB(context);
                }
            }
        }
        return historySearchDB;
    }

    /**
     * 将搜索的每一项存储到数据库中
     */
    public void saveHistory(String string){
        if (string != null){
            ContentValues values = new ContentValues();
            values.put("history_search_name",string);
            db.insert("history_search",null,values);
        }
    }

    /**
     * 从数据库中读取所有历史纪录
     */
    public List<String> loadHistory(){
        List<String> list = new ArrayList<>();
        Cursor cursor = db.query("history_search",null, null, null, null, null, null);
        if (cursor.moveToLast()){
            do{
                list.add(cursor.getString(cursor.getColumnIndex("history_search_name")));
            }while (cursor.moveToPrevious());
        }
        return list;
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    public boolean hasData(String tempName){
        Cursor cursor = db.rawQuery("select id as _id,history_search_name from history_search where history_search_name =?",new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 删除已有的一条记录
     */
    public void deleteSingleHistory(String string){
        db.delete("history_search","history_search_name==?",new String[]{string});
    }

    /**
     * 删除所有的历史纪录
     */
    public void deleteAllHistory(){
        db.delete("history_search",null,null);
    }
}
