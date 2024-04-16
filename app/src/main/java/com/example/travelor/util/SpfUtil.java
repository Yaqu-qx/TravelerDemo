package com.example.travelor.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpfUtil {

    private static String SPF_NAME = "noteSpf";

    public static void saveString(Context context, String key, String value) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getString(key, "");
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static Boolean getBoolean(Context context, String key) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getBoolean(key, false);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = spf.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getInt(key, -1);
    }

    public static int getIntWithDefault(Context context, String key, int defValue) {
        SharedPreferences spf = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spf.getInt(key, defValue);
    }
}
