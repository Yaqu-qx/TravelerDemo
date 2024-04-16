package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.DetailsPageActivity;
import com.example.travelor.LoginActivity;
import com.example.travelor.R;
import com.example.travelor.SignInActivity;
import com.example.travelor.bean.Attractions;
import com.example.travelor.datebase.AttrCltDbOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AttractionCltAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Attractions> mAttractionList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Boolean checkboxVisible = false;
    private Boolean mChooseAll = false;
    private AttrCltDbOpenHelper mAttrCltDbOpenHelper;
    private boolean mCancelAll = false;
    private boolean[] checkedPositions; // 跟踪复选框的选中状态

    public AttractionCltAdapter(Context context, List<Attractions> mAttraction) {
        this.mAttractionList = mAttraction;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mAttrCltDbOpenHelper = new AttrCltDbOpenHelper(mContext);
        checkedPositions = new boolean[mAttraction.size()];
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.attraction_collect_card, parent, false);
        AttractionCltAdapter.MyViewHolder myViewHolder = new AttractionCltAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((AttractionCltAdapter.MyViewHolder) holder, position);
    }

    private void bindViewHolder(AttractionCltAdapter.MyViewHolder holder, int position) {
        Attractions attraction = mAttractionList.get(position);
        holder.mAttrName.setText(attraction.getName());
        holder.mAttrLocation.setText(attraction.getLocation());
//        holder.mAttrImage.setImageResource(R.drawable.west_lake1); // todo

        // 动态设置图片
        holder.mAttrImage.setImageResource(getResourceId(attraction.getImages()));
        String introduce = attraction.getIntroduce();
        // 卡片简介不能太长
        holder.mAttrIntroduce.setText("简介：" + introduce.substring(0, Math.min(introduce.length(), 15)));

        if (checkboxVisible) {
            holder.mChoice.setVisibility(View.VISIBLE);
        } else {
            holder.mChoice.setVisibility(View.INVISIBLE);
            checkedPositions[position] = false;
        }

        holder.mChoice.setChecked(checkedPositions[position]);
        holder.mChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedPositions[position] = isChecked; // 更新选中状态
            }
        });

        holder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsPageActivity.class);
                intent.putExtra("attraction", attraction);
                mContext.startActivity(intent);
            }
        });
    }

    public int getResourceId(String fileNames) {
        String[] imageNames = fileNames.split("#");
        String resourceName = imageNames[0];
        String resourceType = "drawable";
        String packageName = mContext.getPackageName();
        return mContext.getResources().getIdentifier(resourceName, resourceType, packageName);
    }

    @Override
    public int getItemCount() {
        return mAttractionList.size();
    }

    public void refreshData(List<Attractions> mAttractions) {
        this.mAttractionList = mAttractions;
        for (int i = 0; i < mAttractionList.size(); i++)
            checkedPositions[i] = false;
        notifyDataSetChanged();
    }

    public void setCheckBoxStatus(Boolean managed) {
        this.checkboxVisible = managed;
        notifyDataSetChanged();
    }

    public void chooseAll() {
        for (int i = 0; i < mAttractionList.size(); i++)
            checkedPositions[i] = true;
        notifyDataSetChanged();
    }

    public void deleteChosen() {
        List<Attractions> newAttractionList = new ArrayList<>();
        for (int i = 0; i < mAttractionList.size(); i++) {
            if (checkedPositions[i])
                mAttrCltDbOpenHelper.deleteFromDbByName(mAttractionList.get(i).getName());
            else newAttractionList.add(mAttractionList.get(i));
        }
        refreshData(newAttractionList);
    }

    public void cancelAll() {
        for (int i = 0; i < mAttractionList.size(); i++)
            checkedPositions[i] = false;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mAttrImage;
        TextView mAttrName;
        TextView mAttrLocation;
        TextView mAttrIntroduce;
        TextView mDetails;
        CheckBox mChoice;
        ViewGroup mCardContain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mAttrImage = itemView.findViewById(R.id.image);
            this.mAttrName = itemView.findViewById(R.id.name);
            this.mAttrLocation = itemView.findViewById(R.id.location);
            this.mAttrIntroduce = itemView.findViewById(R.id.introduce);
            this.mCardContain = itemView.findViewById(R.id.card_contain);
            this.mDetails = itemView.findViewById(R.id.details);
            this.mChoice = itemView.findViewById(R.id.cb_choice);
        }
    }
}
