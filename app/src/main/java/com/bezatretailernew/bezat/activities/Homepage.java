package com.bezatretailernew.bezat.activities;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bezatretailernew.bezat.R;
import com.bezatretailernew.bezat.fragments.BlankFragment;
import com.bezatretailernew.bezat.fragments.Dashboard;
import com.bezatretailernew.bezat.fragments.MyProfile;
import com.bezatretailernew.bezat.fragments.Notification;
import com.bezatretailernew.bezat.fragments.Settings;
import com.bezatretailernew.bezat.utils.SharedPrefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

public class Homepage extends AppCompatActivity {

    Fragment currentFragment = null;
    FragmentTransaction ft;
    ViewPagerAdapter adapter;
    ViewPager viewPager;
    FrameLayout frameLayout;
    MenuItem prevMenuItem;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    if(lang.equals("a")){
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(3);
                    }else{
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(0);
                    }
                    frameLayout.setClickable(false);
                    break;
                case R.id.navigation_bell:
                    if(lang.equals("a")){
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(2);
                    }else{
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(1);
                    }
                    frameLayout.setClickable(false);
                    break;
                case R.id.navigation_profile:
                    if(lang.equals("a")){
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(1);
                    }else{
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(2);
                    }
                    frameLayout.setClickable(false);
                    break;
                case R.id.navigation_settings:
                    if(lang.equals("a")){
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(0);
                    }else{
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container, new BlankFragment());
                        ft.commit();
                        viewPager.setCurrentItem(3);
                    }
                    frameLayout.setClickable(false);
                    break;
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

    String lang;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        frameLayout.setClickable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefs.getKey(this, "selectedlanguage").contains("ar")) {
            lang = "a";
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            setLocale("ar");
        } else {
            lang = "e";
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        verifyStoragePermissions(Homepage.this);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel
                    ("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = ("Successful");
                        if (!task.isSuccessful()) {
                            msg = ("Failed");
                        }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
//-------------------------------------------------
        BottomNavigationView navView = findViewById(R.id.navigation);
        frameLayout = findViewById(R.id.container);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new BlankFragment());
        ft.commit();

        currentFragment = new Dashboard();

        viewPager = findViewById(R.id.viewPagerhome);
        if(lang.equals("a")){
            addTabsArabic(viewPager);
        }else{
            addTabsEnglish(viewPager);
        }

        frameLayout.setClickable(false);

        setPageChangeListener(navView);

    }

    private void setPageChangeListener(BottomNavigationView navView) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    navView.getMenu().getItem(0).setChecked(false);

                if (lang.equals("a")) {
                    navView.getMenu().getItem(3 - position).setChecked(true);
                } else {
                    navView.getMenu().getItem(position).setChecked(true);
                }
                prevMenuItem = navView.getMenu().getItem(position);
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, new BlankFragment());
                ft.commit();
                frameLayout.setClickable(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addTabsEnglish(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Dashboard(), " ");
        adapter.addFrag(new Notification(), " ");
        adapter.addFrag(new MyProfile(), " ");
        adapter.addFrag(new Settings(), " ");
        viewPager.setAdapter(adapter);
    }

    private void addTabsArabic(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Settings(), " ");
        adapter.addFrag(new MyProfile(), " ");
        adapter.addFrag(new Notification(), " ");
        adapter.addFrag(new Dashboard(), " ");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(3);
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
