package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.travelor.bean.Attractions;
import com.example.travelor.bean.Hotels;
import com.example.travelor.bean.Plans;

import java.util.ArrayList;
import java.util.List;

public class HotelCollectDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "hotelCollectSQLite.db";
    private static final String TABLE_NAME_NOTE = "hotel_collect";


    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement," +
            "name text UNIQUE, location text, image text, rank text, score text, price text, attraction text)";

    public HotelCollectDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id}); // 创建包含单个元素的字符串数组
    }

    public long insertData(Hotels hotel) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", hotel.getHotelName());
        values.put("location", hotel.getHotelLocation());
        values.put("image", hotel.getHotelImage());
        values.put("rank", hotel.getHotelRank());
        values.put("score", hotel.getHotelScore());
        values.put("price", hotel.getHotelPrice());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    @SuppressLint("Range")
    public List<Hotels> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<Hotels> hotelList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String hotelName = cursor.getString(cursor.getColumnIndex("name"));
                String hotelRank = cursor.getString(cursor.getColumnIndex("rank"));
                String hotelImage = cursor.getString(cursor.getColumnIndex("image"));
                String hotelLocation = cursor.getString(cursor.getColumnIndex("location"));
                String hotelPrice = cursor.getString(cursor.getColumnIndex("price"));
                String hotelScore = cursor.getString(cursor.getColumnIndex("score"));

                Hotels hotel = new Hotels();
                hotel.setHotelName(hotelName);
                hotel.setHotelImage(hotelImage);
                hotel.setHotelLocation(hotelLocation);
                hotel.setHotelPrice(hotelPrice);
                hotel.setHotelScore(hotelScore);
                hotel.setHotelRank(hotelRank);

                hotelList.add(hotel);
            }
            cursor.close();
        }

        return hotelList;
    }
}
