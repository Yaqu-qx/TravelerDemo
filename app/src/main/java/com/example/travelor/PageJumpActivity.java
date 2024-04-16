package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.travelor.fragment.FrontPageFragment;

import com.example.travelor.fragment.MineFragment;
import com.example.travelor.fragment.PlanFragment;
import com.example.travelor.fragment.SparkAiFragment;

public class PageJumpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_layout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FrontPageFragment fgm_front_page = new FrontPageFragment();
        ft.replace(R.id.main_fragment, fgm_front_page);
        ft.commit();
    }

    public void page_jump(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        FrontPageFragment fgm_front_page = new FrontPageFragment();
        PlanFragment fgm_plan_page = new PlanFragment();
        MineFragment fgm_mine_page = new MineFragment();
        SparkAiFragment fgm_spark_page = new SparkAiFragment();

        switch (view.getId()){
            case R.id.front_page:
                ft.replace(R.id.main_fragment, fgm_front_page);
                break;
            case R.id.plan:
                ft.replace(R.id.main_fragment, fgm_plan_page);
                break;
            case R.id.mine:
                ft.replace(R.id.main_fragment, fgm_mine_page);
                break;
            case R.id.spark:
                ft.replace(R.id.main_fragment, fgm_spark_page);
                break;
        }
        ft.commit();
    }

}