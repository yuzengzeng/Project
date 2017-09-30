package com.vivo.yzz.music;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.vivo.yzz.music.music.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/25.
 */

public class MyApplication extends Application {
    static public Context context;
    static public List<Activity>  activityList;
    static public List<Music> musicList=new ArrayList<>();
    static public List<Music> playingList=new ArrayList<>();
    static public boolean isPlaying=false;
    static public int playingIndex=0;
    static public long playingDuration=0;
    static public int playingStyle=0;
    static public boolean isLogin=false;
    static public String currentUser="未登录，点击登录";
    static public boolean serviceIsStarted=false;
    static public int state=0;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        activityList=new ArrayList<Activity>();
        initInfo();

    }

    void initInfo(){
        String name=getSharedPreferences("user",MODE_PRIVATE).getString("name",null);
        if (name!=null){
            currentUser=name;
        }
    }

}
