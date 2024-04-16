package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.travelor.adapter.HotelAdapter;
import com.example.travelor.adapter.ImagePagerAdapter;
import com.example.travelor.bean.Attractions;
import com.example.travelor.bean.Hotels;
import com.example.travelor.bean.Plans;
import com.example.travelor.datebase.AttrCltDbOpenHelper;
import com.example.travelor.datebase.HotelDbOpenHelper;
import com.example.travelor.fragment.FrontPageFragment;
import com.example.travelor.util.SpfUtil;
import com.example.travelor.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class DetailsPageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<Integer> imageList;
    private ArrayList<View> dots = new ArrayList<>();
    private Attractions mAttraction;

    private TextView tvName;
    private TextView tvRank;
    private TextView tvLocation;
    private List<ImageView> imageViews = new ArrayList<>();
    private TextView tvPrice;
    private TextView tvIntroduce;
    private TextView back;

    private VideoView videoView;
    private Button btnToggle;
    private int currentPosition = 1;
    private boolean isPlaying = false;

    private Context mContext;
    private View likeItIcon;
    private View locationIcon;
    public static final String KEY_COLLECTED = "collected";

    private RecyclerView hotelRecycler;
    private List<Hotels> mHotels;
    private HotelAdapter mHotelAdapter;
    private HotelDbOpenHelper mHotelDbOpenHelper;

    private Button btCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_page_layout);
        mContext = this;

        tvName = findViewById(R.id.name);
        tvRank = findViewById(R.id.rank);
        tvPrice = findViewById(R.id.price);
        tvIntroduce = findViewById(R.id.introduce);
        tvLocation = findViewById(R.id.location);
        btCollect = findViewById(R.id.buy_now);
        imageViews.add((ImageView)findViewById(R.id.photo1));
        imageViews.add((ImageView)findViewById(R.id.photo2));
        imageViews.add((ImageView)findViewById(R.id.photo3));

        hotelRecycler = findViewById(R.id.hotel_rl);
        mHotels = new ArrayList<>();
        mHotelDbOpenHelper = new HotelDbOpenHelper(this);

        initData();
        initEvent();
    }

    private void initEvent() {
        imageSwitch();
        videoPlayer();
        setLikeIt();
        viewRoute();
        backEvent();
        collect();
    }

    private void collect() {
        btCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttrCltDbOpenHelper attractionCollectDbOpenHelper = new AttrCltDbOpenHelper(mContext);
                long row = attractionCollectDbOpenHelper.insertData(mAttraction);
                if (row != -1) {
                    ToastUtil.toastShort(mContext, "收藏成功！");
                } else {
                    ToastUtil.toastShort(mContext, "已收藏！");
                }
            }
        });
    }

    private void backEvent() {
        back = findViewById(R.id.turn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // 查看路线
    private void viewRoute() {
        locationIcon = findViewById(R.id.location_icon);
        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsPageActivity.this, GaodeMapActivity.class);
                intent.putExtra("attraction", mAttraction);
                startActivity(intent);
                finish();
            }
        });
    }

    // 设置收藏点亮
    private void setLikeIt() {
        likeItIcon = findViewById(R.id.likeIt_icon);

        likeItIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isCollected = SpfUtil.getBoolean(DetailsPageActivity.this, KEY_COLLECTED);
                if(isCollected) {
                    likeItIcon.setSelected(false);
                    SpfUtil.saveBoolean(DetailsPageActivity.this, KEY_COLLECTED, false);
                }
                else {
                    likeItIcon.setSelected(true);
                    SpfUtil.saveBoolean(DetailsPageActivity.this, KEY_COLLECTED, true);
                }
            }
        });
    }

    // 风景图设置
    private void setImages(String imagesString) {
        String[] imageNames = imagesString.split("#");

        for (int i = 0; i < imageNames.length; i++) {
            int id = getResources().getIdentifier(imageNames[i], "drawable", getPackageName());
            imageViews.get(i).setImageResource(id);
        }
    }

    // 接收数据并初始化
    private void initData() {
        Intent intent = getIntent();
        mAttraction = (Attractions) intent.getSerializableExtra("attraction");
        if (mAttraction != null) {
            tvName.setText(mAttraction.getName());
            tvRank.setText(mAttraction.getRank());
            tvIntroduce.setText("        " + mAttraction.getIntroduce());
            tvLocation.setText("地理位置: "+mAttraction.getLocation());
            tvPrice.setText("￥ " + mAttraction.getPrice());
            setImages(mAttraction.getImages());
        }

        initHotel(mAttraction.getName());
    }

    // 处理酒店视图
    private void initHotel(String mAttraction) {
        mHotels = mHotelDbOpenHelper.queryFromDbByAttraction("杭州西湖"); // todo 动态景点名称
        mHotelAdapter = new HotelAdapter(this, mHotels);
        hotelRecycler.setAdapter(mHotelAdapter);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        hotelRecycler.setLayoutManager(linearLayoutManager); // 创建布局管理器
        mHotelAdapter.notifyDataSetChanged();
    }

    //视频播放
    private void videoPlayer() {
        videoView = findViewById(R.id.videoView);
        btnToggle = findViewById(R.id.btnToggle);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.west_lack;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.seekTo(currentPosition);

        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlaying) {
                    videoView.seekTo(currentPosition); // 设置播放位置
                    videoView.start();
                    isPlaying = true;
                    btnToggle.setSelected(true);
                    btnToggle.setVisibility(View.INVISIBLE);
                    setBtnVisible();
                }
                else { // 如果正在播放，则停止播放
                    videoView.pause();
                    currentPosition = videoView.getCurrentPosition(); // 记录当前播放位置;
                    isPlaying = false;
                    btnToggle.setSelected(false);
                }
            }
        });
    }

    // 开始停止播放逻辑
    private void setBtnVisible() {
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnToggle.getVisibility() == View.INVISIBLE)
                    btnToggle.setVisibility(View.VISIBLE);
                else btnToggle.setVisibility(View.INVISIBLE);
            }
        });
    };

    // 图片轮回切换
    private void imageSwitch() {
        viewPager = findViewById(R.id.viewPager);
        imageList = new ArrayList<>();  // 存储图片资源的列表
        final int[] position = {0};  // 当前显示的图片索引

        // 添加图片资源到列表
        imageList.add(R.drawable.west_lake1);
        imageList.add(R.drawable.west_lake2);
        imageList.add(R.drawable.west_lake3);

        dots.add(findViewById(R.id.p1));
        dots.add(findViewById(R.id.p2));
        dots.add(findViewById(R.id.p3));

        // 创建适配器并设置给ViewPager
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageList);
        viewPager.setAdapter(adapter);

        // 设置自动切换
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int oldPosition = position[0];
                position[0] = (position[0] + 1) % imageList.size();

                viewPager.setCurrentItem(position[0], true);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position[0]).setBackgroundResource(R.drawable.dot_focus);

                handler.postDelayed(this, 1000);  // 设置延时时间（单位：毫秒）
            }
        };

        // 启动自动切换
        handler.postDelayed(runnable, 1000);  // 设置初始延时时间（单位：毫秒）
    }


}