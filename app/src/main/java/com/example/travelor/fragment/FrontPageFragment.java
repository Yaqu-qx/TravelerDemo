package com.example.travelor.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.travelor.R;
import com.example.travelor.adapter.AttractionCardAdapter;
import com.example.travelor.bean.Attractions;

import java.util.ArrayList;
import java.util.List;

import com.example.travelor.datebase.AttractionDbOpenHelper;

public class FrontPageFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private List<Attractions> mAttractions;
    private AttractionCardAdapter mAttrAdapter;
    private SearchView searchView;
    private TextView showAttraction;
    private TextView showAll;
    private TextView showHumanity;
    private TextView showNature;

    private AttractionDbOpenHelper mAttractionDbOpenHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.front_page_layout, container, false);
        initView(rootView);
        initData();
        initEvent();

        // 搜索框
        searchView = rootView.findViewById(R.id.searchView);
        showAttraction = rootView.findViewById(R.id.show_attraction);
        showAll = rootView.findViewById(R.id.show_all);
        showNature = rootView.findViewById(R.id.show_nature);
        showHumanity = rootView.findViewById(R.id.show_humanity);

        search();
        categoryAct();

        return rootView;
    }

    // 按类别查询
    private void categoryAct() {
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttractions = mAttractionDbOpenHelper.queryAllFromDb();
                mAttrAdapter.refreshData(mAttractions);
                setLayout();
            }
        });

        showAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttractions = mAttractionDbOpenHelper.queryFromDbByCategory("风景");
                mAttrAdapter.refreshData(mAttractions);
                setLayout();
            }
        });

        showNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttractions = mAttractionDbOpenHelper.queryFromDbByCategory("自然");
                mAttrAdapter.refreshData(mAttractions);
                setLayout();
            }
        });

        showHumanity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttractions = mAttractionDbOpenHelper.queryFromDbByCategory("人文");
                mAttrAdapter.refreshData(mAttractions);
                setLayout();
            }
        });
    }

    // 景点名搜索
    private void search() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        mAttractions = mAttractionDbOpenHelper.queryFromDbByName(query);
        mAttrAdapter.refreshData(mAttractions);
        setLayout();
    }

    // 回溯
    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setLayout();
    }

    // 更新视图
    private void setLayout() {
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager); // 创建布局管理器
        mAttrAdapter.notifyDataSetChanged();
    }

    private void refreshDataFromDb() {
        mAttractions = getDataFromDB();
        mAttrAdapter.refreshData(mAttractions);
    }

    private List<Attractions> getDataFromDB() {
        return mAttractionDbOpenHelper.queryAllFromDb();
    }

    private void initEvent() {
        mAttrAdapter = new AttractionCardAdapter(requireContext(), mAttractions);
        mRecyclerView.setAdapter(mAttrAdapter);
    }

    private void initData() {
        mAttractions = new ArrayList<>();
        mAttractionDbOpenHelper = new AttractionDbOpenHelper(requireContext());
    }

    private void initView(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rlv);
    }

}