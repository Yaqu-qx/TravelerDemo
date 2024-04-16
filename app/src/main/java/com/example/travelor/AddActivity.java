package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.travelor.bean.Plans;
import com.example.travelor.datebase.PlansDbOpenHelper;
import com.example.travelor.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText etMainPlan, etDetails;

    private PlansDbOpenHelper mPlansDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etMainPlan = findViewById(R.id.et_name);
        etDetails = findViewById(R.id.et_describe);
        mPlansDbOpenHelper = new PlansDbOpenHelper(this);

        etMainPlan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etMainPlan.setSelected(true);
                } else {
                    etMainPlan.setSelected(false);
                }
            }
        });

        etDetails.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etDetails.setSelected(true);
                } else {
                    etDetails.setSelected(false);
                }
            }
        });
    }

    public void add(View view) {
        String mainPlan = etMainPlan.getText().toString();
        String details = etDetails.getText().toString();
        if (TextUtils.isEmpty(mainPlan)) {
            ToastUtil.toastShort(this, "活动名称不能为空！");
            return;
        }

        Plans plan = new Plans();

        plan.setMainPlan(mainPlan);
        plan.setDetails(details);
        plan.setCreateTime(getCurrentTimeFormat());
        long row = mPlansDbOpenHelper.insertData(plan);
        if (row != -1) {
            ToastUtil.toastShort(this, "添加成功！");
            this.finish();
        } else {
            ToastUtil.toastShort(this, "添加失败！");
        }
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}