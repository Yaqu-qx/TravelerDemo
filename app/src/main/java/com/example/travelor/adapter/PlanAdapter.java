package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.EditPlanActivity;
import com.example.travelor.R;
import com.example.travelor.bean.Plans;
import com.example.travelor.datebase.PlansDbOpenHelper;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Plans> mPlansList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private boolean isSwipeActive = false;
    private final int SWIPE_THRESHOLD = 4;
    private float startX;
    private PlansDbOpenHelper mPlansDbOpenHelper;

    public PlanAdapter(Context context, List<Plans> mPlansList){
        this.mPlansList = mPlansList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mPlansDbOpenHelper = new PlansDbOpenHelper(mContext); // 创建数据库管理对象
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.one_plan, parent, false);
        PlanAdapter.MyViewHolder myViewHolder = new PlanAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((PlanAdapter.MyViewHolder) holder, position);
    }

    private void bindViewHolder(PlanAdapter.MyViewHolder holder, int position) {
        Plans plan = mPlansList.get(position);

        holder.planIndex.setText(Integer.toString(position + 1));
        holder.mainPlan.setText(plan.getMainPlan());
        holder.planDetails.setText(plan.getDetails());
        holder.createTime.setText(plan.getCreateTime());

        holder.planContain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        isSwipeActive = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float distanceX = event.getX() - startX;
                        if (distanceX < SWIPE_THRESHOLD) {
                            isSwipeActive = true;
                            holder.handleContain.setVisibility(View.VISIBLE);
                        }
                        else if(distanceX > SWIPE_THRESHOLD) {
                            isSwipeActive = false;
                            holder.handleContain.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
                return true;
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int row = mPlansDbOpenHelper.deleteFromDbById(plan.getId());
                if (row > 0) {
                    removeData(position);
                }
            }
        });

        holder.editButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditPlanActivity.class);
                intent.putExtra("plan", plan);
                mContext.startActivity(intent);
            }
        }));

    }

    private void removeData(int position) {
        mPlansList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mPlansList.size();
    }

    public void refreshData(List<Plans> mPlansList) {
        this.mPlansList = mPlansList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView planIndex;
        TextView mainPlan;
        TextView planDetails;
        TextView createTime;
        ViewGroup planContain;
        ViewGroup handleContain;
        ImageButton editButton;
        ImageButton deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.planIndex = itemView.findViewById(R.id.plan_index);
            this.mainPlan = itemView.findViewById(R.id.main_plan);
            this.planDetails = itemView.findViewById(R.id.details);
            this.createTime = itemView.findViewById(R.id.create_time);
            this.planContain = itemView.findViewById(R.id.plan_contain);
            this.handleContain = itemView.findViewById(R.id.handle_buttons);
            this.editButton = itemView.findViewById(R.id.edit_button);
            this.deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
