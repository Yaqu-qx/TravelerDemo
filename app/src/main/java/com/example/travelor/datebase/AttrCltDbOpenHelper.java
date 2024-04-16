package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.travelor.bean.Attractions;

import java.util.ArrayList;
import java.util.List;

public class AttrCltDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "attractionCollectSQLite.db";
    private static final String TABLE_NAME_NOTE = "attractions_collect";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, " +
            "name text UNIQUE, introduce text, location text, hotels text, images text, video text, rank text, price text, category text)";


    public AttrCltDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion <= 2){//当数据库版本小于版本2时，就要升级下面的所有字段
            db.execSQL("drop table if exists " + TABLE_NAME_NOTE);
            onCreate(db);
        }
    }

    public long insertData(Attractions attraction) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", attraction.getName());
        values.put("introduce", attraction.getIntroduce());
        values.put("location", attraction.getLocation());
        values.put("hotels", attraction.getHotels());
        values.put("images", attraction.getImages());
        values.put("video", attraction.getVideo());
        values.put("rank", attraction.getRank());
        values.put("price", attraction.getPrice());
        values.put("category", attraction.getCategory());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    public int deleteFromDbByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_NOTE, "name = ?", new String[]{name}); // 创建包含单个元素的字符串数组
    }

    @SuppressLint("Range")
    public List<Attractions> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<Attractions> attractionList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String attrName = cursor.getString(cursor.getColumnIndex("name"));
                String attrRank = cursor.getString(cursor.getColumnIndex("rank"));
                String attrImage = cursor.getString(cursor.getColumnIndex("images"));
                String attrLocation = cursor.getString(cursor.getColumnIndex("location"));
                String attrVideo = cursor.getString(cursor.getColumnIndex("video"));
                String attrHotels = cursor.getString(cursor.getColumnIndex("hotels"));
                String attrPrice = cursor.getString(cursor.getColumnIndex("price"));
                String attrIntroduce = cursor.getString(cursor.getColumnIndex("introduce"));
                String attrCategory = cursor.getString(cursor.getColumnIndex("category"));

                Attractions attraction = new Attractions();
                attraction.setImages(attrImage);
                attraction.setName(attrName);
                attraction.setRank(attrRank);
                attraction.setLocation(attrLocation);
                attraction.setPrice(attrPrice);
                attraction.setVideo(attrVideo);
                attraction.setHotels(attrHotels);
                attraction.setIntroduce(attrIntroduce);
                attraction.setCategory(attrCategory);

                attractionList.add(attraction);
            }
            cursor.close();
        }

        return attractionList;
    }

}
