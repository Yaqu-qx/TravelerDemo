package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.travelor.adapter.AttractionCltAdapter;
import com.example.travelor.adapter.HotelAdapter;
import com.example.travelor.bean.Attractions;
import com.example.travelor.datebase.AttrCltDbOpenHelper;
import com.example.travelor.util.ToastUtil;

import java.util.List;

public class AttractionCollectActivity extends AppCompatActivity {
    private TextView vtManage;
    private TextView vtBack;
    private ViewGroup llDelete;
    private TextView chooseAll;
    private Button btDelete;
    private Boolean isChooseAll = false;
    private Boolean managed = false;

    private List<Attractions> mAttraction;
    private AttrCltDbOpenHelper mAttrCltDbOpenHelper;
    private AttractionCltAdapter mAttractionCltAdapter;
    private RecyclerView collectRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_collect_layout);

        vtManage = findViewById(R.id.vt_manage);
        vtBack = findViewById(R.id.back);
        llDelete = findViewById(R.id.ll_delete);
        btDelete = findViewById(R.id.bt_delete);
        chooseAll = findViewById(R.id.choose_all);

        collectRecycler = findViewById(R.id.collect_rlv);
        mAttrCltDbOpenHelper = new AttrCltDbOpenHelper(this);

        initData();
        initEvent();
    }

    private void initEvent() {
        vtManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(managed == false) {
                    vtManage.setText("完成");
                    managed = true;
                    llDelete.setVisibility(View.VISIBLE);
                }
                else{
                    vtManage.setText("管理");
                    managed = false;
                    llDelete.setVisibility(View.INVISIBLE);
                }

                mAttractionCltAdapter.setCheckBoxStatus(managed);

                deleteManage();
            }
        });

        vtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void deleteManage() {
        chooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChooseAll == false) {
                    isChooseAll = true;
                    mAttractionCltAdapter.chooseAll();
                    chooseAll.setText("取消");
                }
                else {
                    isChooseAll = false;
                    mAttractionCltAdapter.cancelAll();
                    chooseAll.setText("全选");
                }

            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttractionCltAdapter.deleteChosen();
                ToastUtil.toastShort(AttractionCollectActivity.this, "删除成功！");
            }
        });
    }

    private void initData() {
        llDelete.setVisibility(View.INVISIBLE); // 初始是看不见的

        mAttraction = mAttrCltDbOpenHelper.queryAllFromDb();
        mAttractionCltAdapter = new AttractionCltAdapter(this, mAttraction);
        collectRecycler.setAdapter(mAttractionCltAdapter);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        collectRecycler.setLayoutManager(linearLayoutManager); // 创建布局管理器
        mAttractionCltAdapter.notifyDataSetChanged();
    }
}