package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.DetailsPageActivity;
import com.example.travelor.R;
import com.example.travelor.bean.Attractions;

import java.util.List;

import com.example.travelor.datebase.AttractionDbOpenHelper;

public class AttractionCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Attractions> mAttractionList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private AttractionDbOpenHelper mAttractionDbOpenHelper;

    public AttractionCardAdapter(Context context, List<Attractions> mAttractionList){
        this.mAttractionList = mAttractionList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mAttractionDbOpenHelper = new AttractionDbOpenHelper(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.attraction_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((MyViewHolder) holder, position);
    }

    private void bindViewHolder(MyViewHolder holder, int position) {
        Attractions attraction = mAttractionList.get(position);
        holder.mAttrName.setText(attraction.getName());
        holder.mAttrRank.setText(attraction.getRank());
        holder.mAttrLocation.setText(attraction.getLocation());

//        holder.mAttrImage.setImageResource(R.drawable.circular_image);
        // 动态设置图片
        holder.mAttrImage.setImageResource(getResourceId(attraction.getImages()));

        holder.mCardContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        notifyDataSetChanged();
    }

    // 用于保存 list_item.xml 中的视图组件
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mAttrImage;
        TextView mAttrName;
        TextView mAttrRank;
        TextView mAttrLocation;
        ViewGroup mCardContain;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mAttrImage = itemView.findViewById(R.id.attraction_image);
            this.mAttrName = itemView.findViewById(R.id.attraction_name);
            this.mAttrRank = itemView.findViewById(R.id.rank);
            this.mAttrLocation = itemView.findViewById(R.id.location);
            this.mCardContain = itemView.findViewById(R.id.card_contain);
        }
    }
}
