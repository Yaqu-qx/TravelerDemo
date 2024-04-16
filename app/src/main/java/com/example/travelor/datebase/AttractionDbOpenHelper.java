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

public class AttractionDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "attractionSQLite.db";
    private static final String TABLE_NAME_NOTE = "attractions";

    private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME_NOTE + " (id integer primary key autoincrement, " +
            "name text, introduce text, location text, hotels text, images text, video text, rank text, price text, category text)";


    public AttractionDbOpenHelper(Context context) {
        super(context, DB_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
//        initDb();
    }

    private boolean isTableExit(SQLiteDatabase db) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        Cursor cursor = db.rawQuery(query, new String[]{TABLE_NAME_NOTE});
        boolean exists = false;
        if (cursor != null && cursor.moveToFirst()) {
            exists = true;
            cursor.close();
        }
        return exists;
    }

    private void initDb() {
        String[] names = {"西湖", "故宫博物院", "呼伦贝尔草原", "八达岭长城", "里维耶拉", "乌镇西栅"};
        String[] introduces = {"西湖的面积约为6.4平方公里，湖水清澈，四周环绕着青山绿树，水的色随季节和气变化而呈现出不同的色彩。湖中有三座岛屿，其中最大的是“花港观鱼”岛，另外还有“苏堤晓”岛和“照红楼”岛，每座岛屿都有着自的特色景观历史建筑西湖周边有多著名的景，如岳庙、雷峰塔、苏堤、白堤等。岳庙是供奉武林至尊岳飞的古建筑，是中国最重要的岳庙之一；雷峰是一座古老佛塔，以其独特的形状和悠久的历史而知名；苏堤和白堤是两条连接岸的人工堤岸，沿途植被繁茂，是游览西湖的佳路径。",
                "故宫博物院建于明代，始建于1406年，修建历时14年，总占地面积约72万平方米。整个建筑群呈北南走向，呈长方形，主要分为外朝和内廷两部分。外朝是宫廷的政治和宗教活动区域，包括三大殿（午门、太和殿、乾清宫），以及充满仪式感的文华殿、武英殿等；内廷是皇帝和后妃的居住区域，包括乾隆花园、紫禁城等。",
                "内蒙古的草原是其最具代表性的景观之一。草原广袤辽阔，牧场遍布，悠闲的牛羊们在碧绿的草地上吃草，成群结队的骏马在飞奔。特别是在夏季时，草原如同一副绿色的画卷，迷人而壮观，吸引着众多游客前往观赏和体验。",
                "万里长城是中国古代的一项伟大建筑工程，也是世界文化遗产之一，被誉为世界奇迹。它是中国古代防御体系的重要组成部分，始建于公元前7世纪，历经多个朝代的修建，形成了如今的规模。万里长城的主体建设始于公元前3世纪的秦朝，当时的秦始皇统一了中国大部分地区，为了抵御北方游牧民族的入侵，下令修筑长城。但目前所见的大部分长城是明朝时期的建筑，明朝时期的长城修建者主要是为了抵御满洲族的入侵。",
                "里维耶拉（Ribeira）是葡萄牙波尔图市（Porto）的一个历史街区，也是欧洲最古老的市中心之一，位于杜罗河（Douro River）畔。里维耶拉被列为联合国教科文组织世界遗产，并被誉为波尔图最具特色和迷人的地方之一",
                "乌镇西栅位于乌镇的西侧，是一个保存完好的古镇建筑群落，被誉为“东方小威尼斯”。西栅地区有着优美的水系，河道纵横交错，古老的石桥横跨其间，整个镇子几乎处处可见水景。一进入西栅，就能感受到它独特的水乡风情和古朴的民俗文化。"};
        String[] locations = {"浙江省杭州市", "北京市东城区", "内蒙古呼伦贝尔市", "北京市延庆区", "葡萄牙波尔图市", "浙江省嘉兴市"};
        String[] hotels = {"酒店1,酒店2", "酒店3", "酒店4,酒店5", "酒店4#酒店5", "酒店4#酒店5", "酒店4#酒店5"};
        String[] images = {"west_lake1#west_lake2#west_lake3",
                "gugong#west_lake2#west_lake3",
                "hulunbeier#west_lake2#west_lake3",
                "greate_wall#west_lake2#west_lake3",
                "ribeira#west_lake2#west_lake3",
                "wuzhen#west_lake2#west_lake3"};
        String[] videos = {"video1", "video2", "video3", "video4", "video5", "video6"};
        String[] ranks = {"AAAAA", "AAAA", "AAAAA", "AAAA", "AAAAA", "AAA"};
        String[] prices = {"123", "99", "144", "233", "112", "323"};
        String[] category = {"风景", "人文", "自然", "风景", "人文", "自然"};

        for(int i = 0; i < names.length; i++) {
            Attractions oneAttraction = new Attractions();
            oneAttraction.setName(names[i]);
            oneAttraction.setIntroduce(introduces[i]);
            oneAttraction.setLocation(locations[i]);
            oneAttraction.setHotels(hotels[i]);
            oneAttraction.setImages(images[i]);
            oneAttraction.setVideo(videos[i]);
            oneAttraction.setRank(ranks[i]);
            oneAttraction.setPrice(prices[i]);
            oneAttraction.setCategory(category[i]);

            insertData(oneAttraction);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion <= 7){//当数据库版本小于版本2时，就要升级下面的所有字段
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

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id});
//        return db.delete(TABLE_NAME_NOTE, "id is ?", new String[]{id});
        return db.delete(TABLE_NAME_NOTE, "id like ?", new String[]{id}); // 创建包含单个元素的字符串数组
    }

    @SuppressLint("Range")
    public List<Attractions> queryAllFromDb() {
//        initDb(); // todo
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

    @SuppressLint("Range")
    public List<Attractions> queryFromDbByName(String name) {
        if (TextUtils.isEmpty(name)) {
            return queryAllFromDb();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Attractions> attractionList = new ArrayList<>();
        // 模糊查询
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "name like ?", new String[]{"%"+name+"%"}, null, null, null);
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

    @SuppressLint("Range")
    public List<Attractions> queryFromDbByCategory(String category) {
        if (TextUtils.isEmpty(category)) {
            return queryAllFromDb();
        }
        SQLiteDatabase db = getWritableDatabase();
        List<Attractions> attractionList = new ArrayList<>();
        // 模糊查询
        Cursor cursor = db.query(TABLE_NAME_NOTE, null, "category = ?", new String[]{category}, null, null, null);
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
