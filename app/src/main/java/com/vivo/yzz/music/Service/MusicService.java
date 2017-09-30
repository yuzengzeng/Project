package com.vivo.yzz.music.Service;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.vivo.yzz.music.MyApplication;
import com.vivo.yzz.music.R;

import java.io.IOException;

public class MusicService extends Service {

    private static final String TAG = "MusicService";
    private Notification notification;

    public static final int REQUEST_CODE_PLAY=100;
    public  static final String ACTION_PAUSE="pause";
    public static final String ACTION_START="start";
    public static final String ACTION_NEXT="next";
    public static final String ACTION_PREVIOUS="previous";
    public static final String ACTION_UPDATE="update";

    public PlayerReceiver playerReceiver;
    private MediaPlayer mediaPlayer;
    private RemoteViews remoteViews;

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //动态注册广播接收器
        playerReceiver = new PlayerReceiver();
        IntentFilter mFilter=new IntentFilter();
        mFilter.addAction(ACTION_START);
        mFilter.addAction(ACTION_PAUSE);
        mFilter.addAction(ACTION_PREVIOUS);
        mFilter.addAction(ACTION_NEXT);

        registerReceiver(playerReceiver,mFilter);
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next();
                notificateUI();
            }
        });

        //默认列表为本地
        MyApplication.playingList=MyApplication.musicList;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification);

        Notification.Builder builder=new Notification.Builder(this.getApplicationContext()).
                setContent(remoteViews)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.music);
                notification = builder.getNotification();
                startForeground(110, notification);

        return super.onStartCommand(intent,flags,startId);

    }

    @Override
    public void onDestroy() {

        stopForeground(true);
        if (playerReceiver!=null){
            unregisterReceiver(playerReceiver);
        }
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        super.onDestroy();
    }


    //通知主界面更新UI
    public void notificateUI(){
        Intent intentUpdate=new Intent(ACTION_UPDATE);
        sendBroadcast(intentUpdate);
    }

    public void updateNotification(){
        //remoteViews.setCharSequence(R.id.no_tv_title,MyApplication.playingList.get(MyApplication.playingIndex).getTitle(),);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void start(){
        //remoteViews.setCharSequence(R.id.no_tv_title,"HHH",MyApplication.playingList.get(MyApplication.playingIndex).getTitle());
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(MyApplication.playingList.get(MyApplication.playingIndex).getData());
            mediaPlayer.prepare();
            mediaPlayer.start();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            MyApplication.state=1;
        }

    }

    public void next(){


        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(MyApplication.playingList.get(MyApplication.playingIndex).getData());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void previous(){
        if (MyApplication.playingIndex>=1){
            MyApplication.playingIndex--;
        }else{
            MyApplication.playingIndex=MyApplication.playingList.size()-1;
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(MyApplication.playingList.get(MyApplication.playingIndex).getData());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    class PlayerReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            switch (action){
                case ACTION_START:
                    if (MyApplication.state==0){

                        start();

                    }else if (MyApplication.state==1){

                        mediaPlayer.start();
                    }
                    MyApplication.state=1;
                    break;
                case ACTION_PAUSE:pause();MyApplication.state=1;break;
                case ACTION_NEXT:next();break;
                case ACTION_PREVIOUS:previous();break;
            }
        }
    }
}
