package com.example.travelor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//import android.util.Log;
//
//import com.iflytek.sparkchain.core.SparkChain;
//import com.iflytek.sparkchain.core.SparkChainConfig;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "test";
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tomainActivity();
        }
    };

    private void tomainActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initSpark();

        handler.postDelayed(runnable, 1000);
    }

//    private void initSpark() {
//        SparkChainConfig config =  SparkChainConfig.builder()
//                .appID("cb12df0e")
//                .apiKey("5ccdd362e25ff16b77acd893af903ac0")
//                .apiSecret("ZGZmYTgyNWFmYWVjYzdlZTgwYzkyOGM0");//从平台获取的授权appid，apikey,apisecrety
//        int ret = SparkChain.getInst().init(getApplicationContext(), config);
//        Log.d(TAG,"sdk init:"+ret);
//    }

}