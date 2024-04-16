package com.example.travelor.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.travelor.AddActivity;
import com.example.travelor.DetailsPageActivity;
import com.example.travelor.R;
import com.example.travelor.adapter.PlanAdapter;
import com.example.travelor.bean.Plans;
import com.example.travelor.datebase.PlansDbOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<Plans> mPlans;
    private PlanAdapter mPlanAdapter;
    private FloatingActionButton addButton;
    private PlansDbOpenHelper mPlansDbOpenHelper;
    private TextView mDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.plan_layout, container, false);

        addButton = rootView.findViewById(R.id.btn_add);
        mDate = rootView.findViewById(R.id.date);

        initView(rootView);
        initData();
        initEvent();
        add();
        return rootView;
    }

    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setLayout();
    }

    // 更新视图
    private void setLayout() {
        mPlanAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager); // 创建布局管理器
    }

    private void refreshDataFromDb() {
        mPlans = getDataFromDB();
        mPlanAdapter.refreshData(mPlans);
    }

    private List<Plans> getDataFromDB() {
        return mPlansDbOpenHelper.queryAllFromDb();
    }

    private void initEvent() {
        mPlanAdapter = new PlanAdapter(requireContext(), mPlans);
        mRecyclerView.setAdapter(mPlanAdapter);
        mDate.setText(getCurrentTimeFormat());
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    private void initData() {
        mPlans = new ArrayList<>();
        mPlansDbOpenHelper = new PlansDbOpenHelper(requireContext());
    }

    private void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.plan_rlv);
    }

    public void add() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });
    }
}