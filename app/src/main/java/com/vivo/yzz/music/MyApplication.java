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
    static public boolean isPlaying=false;
    static public int playingIndex=0;
    static public long playingDuration=0;
    static public int playingStyle=0;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        activityList=new ArrayList<Activity>();


    }

}
