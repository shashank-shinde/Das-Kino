package com.sas_apps.daskino;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sas_apps.daskino.adaptor.ViewPagerAdaptor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    private static final String SHORTCUT_NOW_PLAYING = "com.sas_apps.daskino.shortcutNowPlaying";
    private static final String SHORTCUT_UPCOMING = "com.sas_apps.daskino.shortcutUpcoming";
    private static final String SHORTCUT_POPULAR = "com.sas_apps.daskino.shortcutPopular";
    private static final String SHORTCUT_TOP = "com.sas_apps.daskino.shortcutTop";


    ViewPagerAdaptor adaptor;
    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarMain);
        adaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        initTabLayout();

//      Shortcut intent
        if (SHORTCUT_NOW_PLAYING.equals(getIntent().getAction())){
            viewPager.setCurrentItem(0);
        } else if (SHORTCUT_UPCOMING.equals(getIntent().getAction())) {
            viewPager.setCurrentItem(1);
        }else if (SHORTCUT_POPULAR.equals(getIntent().getAction())) {
            viewPager.setCurrentItem(2);
        }else if (SHORTCUT_TOP.equals(getIntent().getAction())) {
            viewPager.setCurrentItem(3);
        }
    }

    void initTabLayout() {
        adaptor.addFragment(new NowPlayingFragment(), "");
        adaptor.addFragment(new UpComingFragment(), "");
        adaptor.addFragment(new PopularFragment(), "");
        adaptor.addFragment(new TopRatedFragment(), "");
        viewPager.setAdapter(adaptor);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.play);
        tabLayout.getTabAt(1).setIcon(R.drawable.upcoming);
        tabLayout.getTabAt(2).setIcon(R.drawable.fire);
        tabLayout.getTabAt(3).setIcon(R.drawable.top);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
//        Log.d(TAG, "onPageSelected: position " + position);
        if (position == 0) {
            toolbarMain.setTitle("Now Playing");
        } else if (position == 1) {
            toolbarMain.setTitle("Up Coming");
        } else if (position == 2) {
            toolbarMain.setTitle("Popular");
        } else if (position == 3) {
            toolbarMain.setTitle("Top Rated");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.menu_preferences:
                Toast.makeText(this, "Pref", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
        }


        return true;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
