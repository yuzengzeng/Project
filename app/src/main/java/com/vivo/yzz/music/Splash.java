package com.vivo.yzz.music;
import android.Manifest;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.vivo.yzz.music.music.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Splash extends AppCompatActivity {

    private final boolean AUTO_HIDE = true;

    private final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private Cursor cursor;
    private ContentResolver albumResolver;
    private static final String TAG = "Splash";
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_splash);


        contentResolver = getContentResolver();
        albumResolver = this.getContentResolver();

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {


                    cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
                    MyApplication.musicList=getMusic();
                    SystemClock.sleep(1000);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent=new Intent(Splash.this,MainActivity.class);

                            startActivity(intent);

                            Splash.this.finish();

                            Toast.makeText(Splash.this, "以为您加载"+MyApplication.musicList.size()+"首本地歌曲", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).start();


        }

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i=0;i<permissions.length;i++){
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
                    MyApplication.musicList=getMusic();

                    Intent intent=new Intent(Splash.this,MainActivity.class);

                    startActivity(intent);

                    Splash.this.finish();

                    Toast.makeText(Splash.this, "以为您加载"+MyApplication.musicList.size()+"首本地歌曲", Toast.LENGTH_SHORT).show();

                }else{

                    Intent intent=new Intent(Splash.this,MainActivity.class);

                    startActivity(intent);

                    Splash.this.finish();

                    Toast.makeText(Splash.this,"无法获取本地音乐", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    List<Music> getMusic() {

        List<Music> list = new ArrayList<>();

        if (cursor != null) {

            int titleCol = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int albumCol = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int authorCol = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            int dataCol = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int durationCol = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int idCol = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int sizeCol = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            //int lrcCol=cursor.getColumnIndex(MediaStore.Audio.Media.)

            String albumArt = new String();

            while (cursor.moveToNext()) {


                int albumKeyCol = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY);


                String albumKey = cursor.getString(albumKeyCol);


                Cursor albumCursor = albumResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, "album_key  = ? ", new String[]{albumKey}, null);

                if (albumCursor != null && albumCursor.getCount() > 0) {
                    albumCursor.moveToFirst();
                    int albumArtCol = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                    albumArt = albumCursor.getString(albumArtCol);
                }

                Music music =new Music();

                music.setTitle(cursor.getString(titleCol));
                music.setAlbum(cursor.getString(albumCol));
                music.setAuthor(cursor.getString(authorCol));
                music.setData(cursor.getString(dataCol));
                music.setDuration(cursor.getInt(durationCol));
                music.setId(cursor.getInt(idCol));
                music.setSize(cursor.getInt(sizeCol));
                music.setImageUri(albumArt);

                list.add(music);
            }
        }


        return list;
    }
}
