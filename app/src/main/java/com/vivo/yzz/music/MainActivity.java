package com.vivo.yzz.music;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vivo.yzz.music.music.Music;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private List<String> titles;
    private List<View> viewList;
    private View viewLocal;
    private View viewOnline;
    private ListView lvLocal;
    private ListView lvOnline;
    private SwipeRefreshLayout refresh;

    private static final String TAG = "MainActivity";
    private Button btnBottomPlay;
    private Button btnBottomNext;
    private TextView tvTitle;
    private ImageView ivBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager)findViewById(R.id.view_pager);

        pagerTabStrip = (PagerTabStrip)findViewById(R.id.pager_tab);
        pagerTabStrip.setTabIndicatorColor(Color.WHITE);
        pagerTabStrip.setTextColor(Color.WHITE);

        titles = new ArrayList<>();
        titles.add("本地音乐");
        titles.add("在线音乐");





        viewLocal = getLayoutInflater().inflate(R.layout.view_local,null);
        viewOnline = getLayoutInflater().inflate(R.layout.view_online,null);



        viewList = new ArrayList<>();
        viewList.add(viewLocal);
        viewList.add(viewOnline);



        MyPagerAdapter myPagerAdapter=new MyPagerAdapter();

        viewPager.setAdapter(myPagerAdapter);


        //初始化底部按钮
        btnBottomPlay = (Button)findViewById(R.id.bottom_play);
        btnBottomNext = (Button)findViewById(R.id.bottom_next);
        tvTitle = (TextView)findViewById(R.id.bottom_tv_title);
        ivBottom = (ImageView)findViewById(R.id.iv_album);

        initView();
    }


    public void playNext(View v){
        if (MyApplication.playingStyle==0){
            MyApplication.playingIndex=(MyApplication.playingIndex+1)%MyApplication.musicList.size();
            MyApplication.isPlaying=true;
            btnBottomPlay.setBackgroundResource(R.drawable.pause_black);
        }
    }

    public void playPause(View v){
        if (MyApplication.isPlaying){
            btnBottomPlay.setBackgroundResource(R.drawable.start_black);
            MyApplication.isPlaying=false;
        }else {
            btnBottomPlay.setBackgroundResource(R.drawable.pause_black);
            MyApplication.isPlaying=true;
        }

    }




    public void initView(){


        lvLocal = viewLocal.findViewById(R.id.lv_local);
        lvOnline = viewOnline.findViewById(R.id.lv_online);


        ArrayAdapter  arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        arrayAdapter.add("喜洋洋");
        arrayAdapter.add("懒洋洋");
        lvOnline.setAdapter(arrayAdapter);

        MyLocalMusicAdapter localMusicAdapter=new MyLocalMusicAdapter();

        lvLocal.setAdapter(localMusicAdapter);

        refresh = viewOnline.findViewById(R.id.refresh);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });



    }


    class MyLocalMusicAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyApplication.musicList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View itemView;
            if (view!=null){
                itemView=view;
            }else{
                itemView=getLayoutInflater().inflate(R.layout.item_music_local,null);
            }

            Music music=MyApplication.musicList.get(i);
            TextView textViewTitle=itemView.findViewById(R.id.tv_title);
            TextView textViewAuthor=itemView.findViewById(R.id.tv_author);
            TextView textViewDuration=itemView.findViewById(R.id.tv_duration);
            String duration=music.getDuration()/60000+"分"+music.getDuration()/1000%60+"秒";

            textViewTitle.setText(music.getTitle());
            textViewAuthor.setText(music.getAuthor());
            textViewDuration.setText(duration);

            return itemView;
        }
    }


    class MyPagerAdapter extends PagerAdapter{
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

           container.addView(viewList.get(position));

            return viewList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_author) {

        } else if (id == R.id.nav_album) {

        } else if (id == R.id.nav_step) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
