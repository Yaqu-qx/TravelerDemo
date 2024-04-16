package com.example.travelor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelor.R;
import com.example.travelor.adapter.ChatAdapter;
import com.example.travelor.bean.Chats;
import com.example.travelor.datebase.ChatDbOpenHelper;
import com.example.travelor.util.ToastUtil;
import com.iflytek.sparkchain.core.LLM;
import com.iflytek.sparkchain.core.LLMConfig;
import com.iflytek.sparkchain.core.LLMOutput;

import java.util.ArrayList;
import java.util.List;


public class SparkAiActivity extends AppCompatActivity {

    private static final String TAG = "test";
    private String results;
//    private LLM llm;
    private Button btSend;
    private EditText etQuestion;
    private TextView tvBack;
    private List<Chats> mChats;
    private ChatAdapter mChatAdapter;
    private RecyclerView mRecyclerView;
    private ChatDbOpenHelper mChatDbOpenHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spark_ai);
//        initSpark();
        initData();
        initEvent();
    }

    private void initData() {
        btSend = findViewById(R.id.send);
        etQuestion = findViewById(R.id.question);
        mRecyclerView = findViewById(R.id.chat_list);
        tvBack = findViewById(R.id.back);

        mChats = new ArrayList<>();
        mChatDbOpenHelper = new ChatDbOpenHelper(this);
    }

    public void onResume() {
        super.onResume();
        refreshDataFromDb();
        setLayout();
    }

    private void setLayout() {
        mChatAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager); // 创建布局管理器
    }

    private void refreshDataFromDb() {
        mChats = getDataFromDB();
        mChatAdapter.refreshData(mChats);
    }
    private List<Chats> getDataFromDB() {
        return mChatDbOpenHelper.queryAllFromDb();
    }

    private void initEvent() {
        mChatAdapter = new ChatAdapter(this,mChats);
        mRecyclerView.setAdapter(mChatAdapter);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = etQuestion.getText().toString();
                etQuestion.setText(""); // 发送后设置为空

                Chats newUserChat = new Chats();
                newUserChat.setContent(content);
                newUserChat.setIdentity("user");
                addItem(newUserChat);

                String result = "亚布力雪乡位于中国黑龙江省，属于寒温带大陆性气候区。冬季气温极低，属于严寒地区。一般来说，12月至次年2月是雪乡最寒冷的季节，气温可达到零下20摄氏度甚至更低。在雪乡旅游时，一定要做好保暖工作，穿着厚重的冬装，戴上帽子、手套和围巾等防寒物品，以确保自己的身体能保持温暖。同时，还要注意防寒措施，如避免长时间暴露在室外、及时进食热食、多喝水等，以确保身体的健康和安全。";
//                String result = interact(content);
                Chats newAiChat = new Chats();
                newAiChat.setContent(result);
                newAiChat.setIdentity("ai");
                addItem(newAiChat);
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addItem(Chats newChat) {
        long row = mChatDbOpenHelper.insertData(newChat);
        if (row != -1) {
            ToastUtil.toastShort(this, "添加成功！");
        } else {
            ToastUtil.toastShort(this, "添加失败！");
        }
        refreshDataFromDb();
    }


//    private String interact(String question) {
//        LLMOutput syncOutput = llm.run(question);
//        String result = "对不起，我的能力不足，不能回答！";
//        if(syncOutput.getErrCode() == 0) {
//            Log.i(TAG, "同步调用：" +  syncOutput.getRole() + ":" + syncOutput.getContent());
//            results = syncOutput.getContent();
//            System.out.println(results);
//        }else {
//            Log.e(TAG, "同步调用：" +  "errCode" + syncOutput.getErrCode() + " errMsg:" + syncOutput.getErrMsg());
//        }
//        return result;
//    }
//
//    private void initSpark() {
//        LLMConfig llmConfig = LLMConfig.builder();
//        llmConfig.domain("generalv2");
//        llmConfig.url("ws(s)://spark-api.xf-yun.com/v2.1/chat");//如果使用generalv2，domain和url都可缺省，SDK默认；如果使用general，url可缺省，SDK会自动补充；如果是其他，则需要设置domain和url。
//        llm = new LLM(llmConfig);
//    }



}