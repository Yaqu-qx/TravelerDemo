package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelor.bean.Plans;
import com.example.travelor.bean.Users;

import java.util.ArrayList;
import java.util.List;

public class PlansDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "plansSQLite.db";
    private static final String TABLE_NAME_NOTE = "plans";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, main_plan text, details text, create_time text)";


    public PlansDbOpenHelper(Context context) {
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

    public long insertData(Plans plan) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("main_plan", plan.getMainPlan());
        values.put("details", plan.getDetails());
        values.put("create_time", plan.getCreateTime());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id}); // 创建包含单个元素的字符串数组
    }

    @SuppressLint("Range")
    public List<Plans> queryAllFromDb() {

        SQLiteDatabase db = getWritableDatabase();
        List<Plans> planList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String main_plan = cursor.getString(cursor.getColumnIndex("main_plan"));
                String details = cursor.getString(cursor.getColumnIndex("details"));
                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));

                Plans plan = new Plans();
                plan.setId(id);
                plan.setMainPlan(main_plan);
                plan.setDetails(details);
                plan.setCreateTime(createTime);

                planList.add(plan);
            }
            cursor.close();
        }

        return planList;

    }

    public int updateData(Plans plan) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("main_plan", plan.getMainPlan());
        values.put("details", plan.getDetails());
        values.put("create_time", plan.getCreateTime());

        return db.update(TABLE_NAME_NOTE, values, "id = ?", new String[]{plan.getId()});
    }
}
