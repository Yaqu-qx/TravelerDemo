package com.example.travelor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.R;
import com.example.travelor.bean.Hotels;
import com.example.travelor.datebase.HotelDbOpenHelper;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Hotels> mHotelList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public HotelAdapter(Context context, List<Hotels> mHotelList){
        this.mHotelList = mHotelList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.hotel_item_layout, parent, false);
        HtViewHolder htViewHolder = new HtViewHolder(view);

        return htViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((HtViewHolder) holder, position);
    }

    private void bindViewHolder(HtViewHolder holder, int position) {
        Hotels hotel = mHotelList.get(position);

        holder.mHotelName.setText(hotel.getHotelName());
        holder.mHotelPrice.setText(hotel.getHotelPrice());
        holder.mHotelScore.setText(hotel.getHotelScore());
        holder.mHotelRank.setText(hotel.getHotelRank());
        holder.mHotelLocation.setText(hotel.getHotelLocation());

        holder.mCardContain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo 收藏
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHotelList.size();
    }

    public void refreshData(List<Hotels> mHotels) {
        this.mHotelList = mHotels;
        notifyDataSetChanged();
    }

    class HtViewHolder extends RecyclerView.ViewHolder{
        ImageView mHotelImage;
        TextView mHotelName;
        TextView mHotelRank;
        TextView mHotelLocation;
        TextView mHotelScore;
        TextView mHotelPrice;
        ViewGroup mCardContain;

        public HtViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mHotelName = itemView.findViewById(R.id.hotel_name);
            this.mHotelImage = itemView.findViewById(R.id.hotel_image);
            this.mHotelLocation = itemView.findViewById(R.id.hotel_location);
            this.mHotelRank = itemView.findViewById(R.id.hotel_rank);
            this.mHotelScore = itemView.findViewById(R.id.hotel_score);
            this.mHotelPrice = itemView.findViewById(R.id.hotel_price);
            this.mCardContain = itemView.findViewById(R.id.hotel_card);
        }
    }
}
