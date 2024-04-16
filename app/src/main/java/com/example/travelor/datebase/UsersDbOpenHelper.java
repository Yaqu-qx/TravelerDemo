package com.example.travelor.datebase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelor.bean.Users;

public class UsersDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "usersSQLite.db";
    private static final String TABLE_NAME_NOTE = "users";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, account text, password text, username text, create_time text)";


    public UsersDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertData(Users users) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("account", users.getAccount());
        values.put("password", users.getPassword());
        values.put("username", users.getUserName());
        values.put("create_time", users.getCreatedTime());

        return db.insert(TABLE_NAME_NOTE, null, values);
    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id});
//        return db.delete(TABLE_NAME_NOTE, "id is ?", new String[]{id});
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id}); // 创建包含单个元素的字符串数组
    }



//    public int updateData(Note note) {
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put("title", note.getTitle());
//        values.put("content", note.getContent());
//        values.put("create_time", note.getCreatedTime());
//        // 返回整数值 表示受影响的行数
//        return db.update(TABLE_NAME_NOTE, values, "id like ?", new String[]{note.getId()});
//    }

//    public List<Note> queryAllFromDb() {
//
//        SQLiteDatabase db = getWritableDatabase();
//        List<Note> noteList = new ArrayList<>();
//
//        Cursor cursor = db.query(TABLE_NAME_NOTE, null, null, null, null, null, null);
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String id = cursor.getString(cursor.getColumnIndex("id"));
//                String title = cursor.getString(cursor.getColumnIndex("title"));
//                String content = cursor.getString(cursor.getColumnIndex("content"));
//                String createTime = cursor.getString(cursor.getColumnIndex("create_time"));
//
//                Note note = new Note();
//                note.setId(id);
//                note.setTitle(title);
//                note.setContent(content);
//                note.setCreatedTime(createTime);
//
//                noteList.add(note);
//            }
//            cursor.close();
//        }
//
//        return noteList;
//
//    }
//
    @SuppressLint("Range")
    public String queryFromDbByAccount(String account) {
        SQLiteDatabase db = getWritableDatabase();
        String password = "";

        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "account=?", new String[]{account}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
        }
        return password;
    }


}
