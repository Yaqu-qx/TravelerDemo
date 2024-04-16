package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.travelor.bean.Plans;
import com.example.travelor.datebase.PlansDbOpenHelper;
import com.example.travelor.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditPlanActivity extends AppCompatActivity {
    private TextView et_mainPlan;
    private TextView et_details;
    private TextView createTime;
    private Plans mPlan;
    private PlansDbOpenHelper mPlansDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        et_mainPlan = findViewById(R.id.et_name);
        et_details = findViewById(R.id.et_describe);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        mPlan = (Plans) intent.getSerializableExtra("plan");
        if (mPlan != null) {
            et_mainPlan.setText(mPlan.getMainPlan());
            et_details.setText(mPlan.getDetails());
        }
        mPlansDbOpenHelper = new PlansDbOpenHelper(this);
    }

    public void save(View view) {
        String mainPlan = et_mainPlan.getText().toString();
        String details = et_details.getText().toString();
        if (TextUtils.isEmpty(mainPlan)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

        mPlan.setMainPlan(mainPlan);
        mPlan.setDetails(details);
        mPlan.setCreateTime(getCurrentTimeFormat());
        long rowId = mPlansDbOpenHelper.updateData(mPlan);
        if (rowId != -1) {
            ToastUtil.toastShort(this, "修改成功！");
            this.finish();
        }else{
            ToastUtil.toastShort(this, "修改失败！");
        }

    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}