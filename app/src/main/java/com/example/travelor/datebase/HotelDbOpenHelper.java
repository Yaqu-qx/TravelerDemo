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

import java.util.ArrayList;
import java.util.List;

public class HotelDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "travelerSQLite.db";
    private static final String TABLE_NAME_NOTE = "hotel";

    private static final String[][] INITIAL_DATA = {
            {"杭州武林万怡酒店", "浙江省 杭州市 湖墅南路28号", "src/main/res/drawable/hotel1.jpg", "★★★★ 四星级酒店", "8.3", "￥709", "杭州西湖"},
            {"浙江西子宾馆", "浙江省 杭州市 南山路37号", "src/main/res/drawable/hotel1.jpg", "★★★★★ 五星级酒店", "9.0", "￥1399", "杭州西湖"},
            {"杭州君悦酒店", "浙江省 杭州市 湖滨路28号", "src/main/res/drawable/hotel1.jpg", "★★★★★ 五星级酒店", " 8.1", "￥1699", "杭州西湖"},
    };

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement," +
            "name text UNIQUE, location text, image text, rank text, score text, price text, attraction text)";

    public HotelDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);

        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        for (String[] data : INITIAL_DATA) {
            values.put("name", data[0]);
            values.put("location",data[1]);
            values.put("image", data[2]);
            values.put("rank", data[3]);
            values.put("score", data[4]);
            values.put("price", data[5]);
            values.put("attraction", data[6]);

            db.insert(TABLE_NAME_NOTE, null, values);
            // 清空ContentValues，以便下一次插入数据重新设置值
            values.clear();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion <= 2){//当数据库版本小于版本2时，就要升级下面的所有字段
            db.execSQL("drop table if exists " + TABLE_NAME_NOTE);
            onCreate(db);
        }
    }

    @SuppressLint("Range")
    public List<Hotels> queryFromDbByAttraction(String attraction) {
        SQLiteDatabase db = getWritableDatabase();
        List<Hotels> hotelList = new ArrayList<>();
        // 模糊查询
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "attraction = ?", new String[]{attraction}, null, null, null);
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
