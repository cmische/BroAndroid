package com.closestudios.bro;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.closestudios.bro.util.TabController;
import com.closestudios.bro.util.TabGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainMenuActivity extends AppCompatActivity {

    @InjectView(R.id.pager)
    ViewPager mPager;

    @InjectView(R.id.flBros)
    FrameLayout flBros;
    @InjectView(R.id.vBros)
    View vBros;
    @InjectView(R.id.flBroMap)
    FrameLayout flBroMap;
    @InjectView(R.id.vBroMap)
    View vBroMap;
    @InjectView(R.id.flLeaderboard)
    FrameLayout flLeaderboard;
    @InjectView(R.id.vLeaderboard)
    View vLeaderboard;

    MainMenuSlideAdapter mPagerAdapter;
    TabController tabController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.inject(this);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MainMenuSlideAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());

        tabController = new TabController();

        // Add Tabs
        tabController.addTab(new TabGroup(flBros, vBros));
        tabController.addTab(new TabGroup(flBroMap, vBroMap));
        tabController.addTab(new TabGroup(flLeaderboard, vLeaderboard));

        // Set View Pager
        tabController.setViewPager(mPager);
        tabController.setActiveTab(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

}
