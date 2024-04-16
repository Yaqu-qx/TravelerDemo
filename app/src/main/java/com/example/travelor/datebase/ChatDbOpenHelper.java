package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelor.bean.Chats;
import com.example.travelor.bean.Hotels;

import java.util.ArrayList;
import java.util.List;

public class ChatDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "chatSQLite.db";
    private static final String TABLE_NAME_NOTE = "aiChat";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, " +
            "identity text, content text)";


    public ChatDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 5);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion <= 5){//当数据库版本小于版本2时，就要升级下面的所有字段
            db.execSQL("drop table if exists " + TABLE_NAME_NOTE);
            onCreate(db);
        }
    }

    public long insertData(Chats chat) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("identity", chat.getIdentity());
        values.put("content", chat.getContent());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    @SuppressLint("Range")
    public List<Chats> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<Chats> chats = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String identity = cursor.getString(cursor.getColumnIndex("identity"));
                String content = cursor.getString(cursor.getColumnIndex("content"));;

                Chats chat = new Chats();
                chat.setIdentity(identity);
                chat.setContent(content);

                chats.add(chat);
            }
            cursor.close();
        }

        return chats;
    }
}
