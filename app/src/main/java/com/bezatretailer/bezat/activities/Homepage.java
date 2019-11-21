package com.bezatretailer.bezat.activities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bezatretailer.bezat.R;
import com.bezatretailer.bezat.fragments.Dashboard;
import com.bezatretailer.bezat.fragments.MyProfile;
import com.bezatretailer.bezat.fragments.Notification;
import com.bezatretailer.bezat.fragments.Settings;
import com.bezatretailer.bezat.utils.SharedPrefs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class Homepage extends AppCompatActivity {

    Fragment currentFragment = null;
    FragmentTransaction ft;
    ViewPagerAdapter adapter;
    ViewPager viewPager;
    FrameLayout frameLayout;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_dashboard:
                    getSupportFragmentManager().popBackStack();
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_bell:
                    getSupportFragmentManager().popBackStack();

                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().popBackStack();

                    viewPager.setCurrentItem(2);
                    return true;

                case R.id.navigation_settings:
                    getSupportFragmentManager().popBackStack();

                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA

    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefs.getKey(this, "selectedlanguage").contains("ar")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setLocale("ar");
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        verifyStoragePermissions(Homepage.this);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.container);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        currentFragment = new Dashboard();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.viewPagerhome, currentFragment);
        ft.commit();
        viewPager = findViewById(R.id.viewPagerhome);
        addTabs(viewPager);
        setPageChangeListener(navView);

    }

    private void setPageChangeListener(BottomNavigationView navView) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    frameLayout.setVisibility(View.VISIBLE);
                }
                navView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addTabs(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Dashboard(), " ");
        adapter.addFrag(new Notification(), " ");
        adapter.addFrag(new MyProfile(), " ");
        adapter.addFrag(new Settings(), " ");
        viewPager.setAdapter(adapter);
    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
}
