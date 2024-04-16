package com.example.travelor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.R;
import com.example.travelor.bean.Chats;
import com.example.travelor.datebase.ChatDbOpenHelper;
import com.example.travelor.datebase.PlansDbOpenHelper;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Chats> mChatsList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ChatDbOpenHelper mChatDbOpenHelper;

    private int viewType;

    public static int TYPE_AI_LAYOUT = 0;
    public static int TYPE_USER_LAYOUT = 1;

    public ChatAdapter(Context context, List<Chats> mChatsList){
        this.mChatsList = mChatsList;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mChatDbOpenHelper = new ChatDbOpenHelper(mContext); // 创建数据库管理对象
    }

    public int getItemViewType(int position) {
        String identity = mChatsList.get(position).getIdentity();
        if (identity.equals("ai")) viewType = TYPE_AI_LAYOUT;
        else viewType = TYPE_USER_LAYOUT;
        return viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_AI_LAYOUT){
            View view = mLayoutInflater.inflate(R.layout.ai_response_item, parent, false);
            AiViewHolder aiViewHolder = new AiViewHolder(view);
            return aiViewHolder;
        }else if(viewType == TYPE_USER_LAYOUT){
            View view = mLayoutInflater.inflate(R.layout.user_ask_item, parent, false);
            UserViewHolder myGridViewHolder = new UserViewHolder(view);
            return myGridViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        if(holder instanceof AiViewHolder){ // 判断holder的类型
            bindAiViewHolder((AiViewHolder) holder, position);
        } else if (holder instanceof UserViewHolder) {
            bindUserViewHolder((UserViewHolder) holder, position);
        }
    }

    private void bindUserViewHolder(UserViewHolder holder, int position) {
        Chats chat = mChatsList.get(position);

        holder.userContent.setText(chat.getContent());
        // todo 头像
    }

    private void bindAiViewHolder(AiViewHolder holder, int position) {
        Chats chat = mChatsList.get(position);
        holder.aiContent.setText(chat.getContent());
    }


    @Override
    public int getItemCount() {
        return mChatsList.size();
    }

    // 刷新数据
    public void refreshData(List<Chats> chats) {
        this.mChatsList = chats;
        notifyDataSetChanged();
    }

    class AiViewHolder extends RecyclerView.ViewHolder{
        TextView aiContent;
        public AiViewHolder(@NonNull View itemView) {
            super(itemView);
            this.aiContent = itemView.findViewById(R.id.ai_content);
        }
    }
    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView userContent;
        ViewGroup chatContain;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userContent = itemView.findViewById(R.id.user_content);
            this.chatContain = itemView.findViewById(R.id.user_contain);
        }
    }
}
