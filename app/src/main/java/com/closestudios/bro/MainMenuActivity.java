package com.closestudios.bro;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.closestudios.bro.networking.Bro;
import com.closestudios.bro.networking.BroMessage;
import com.closestudios.bro.networking.ServerApiCalls;
import com.closestudios.bro.util.BroHub;
import com.closestudios.bro.util.BroPreferences;
import com.closestudios.bro.util.TabController;
import com.closestudios.bro.util.TabGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainMenuActivity extends AppCompatActivity implements ServerApiCalls.UpdateCallback, ServerApiCalls.BroCallback, ServerApiCalls.BroMessageCallback {

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

    MenuItem updateLocation;
    MainMenuSlideAdapter mPagerAdapter;
    TabController tabController;
    SpinnerDialogFragment spinnerDialogFragment;

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
        updateLocation = menu.findItem(R.id.update_location);
        updateLocation.setChecked(BroPreferences.getPrefs(this).getSendLocationUpdates());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {

            BroPreferences.getPrefs(this).signOut();
            finish();
            startActivity(new Intent(this, SignInActivity.class));

            return true;
        }
        if (id == R.id.update_location) {

            updateLocation.setChecked(!updateLocation.isChecked());
            BroPreferences.getPrefs(this).setSendLocationUpdates(updateLocation.isChecked(), this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSpinner(String title, boolean cancelable) {
        dismissSpinner();

        spinnerDialogFragment = SpinnerDialogFragment.getInstance(title, cancelable);
        spinnerDialogFragment.show(getSupportFragmentManager(), "loading");
    }

    public void dismissSpinner() {
        if(spinnerDialogFragment != null) {
            spinnerDialogFragment.dismiss();
            spinnerDialogFragment = null;
        }
    }

    @Override
    public void onSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissSpinner();
            }
        });

    }

    @Override
    public void onSuccess(Bro[] bros) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Ignore
                dismissSpinner();
            }
        });

    }

    @Override
    public void onSuccessMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Ignore
                dismissSpinner();
                Toast.makeText(MainMenuActivity.this, "Bro Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSuccess(BroMessage message) {

    }

    @Override
    public void onError(final String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dismissSpinner();
                Toast.makeText(MainMenuActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
