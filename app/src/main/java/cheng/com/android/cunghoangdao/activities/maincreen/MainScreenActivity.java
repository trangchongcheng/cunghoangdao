package cheng.com.android.cunghoangdao.activities.maincreen;

import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.AmDuongActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.BoiCunghoangdaoActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.ChitietActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.DanhmucActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.TuvitrondoiActivity;
import cheng.com.android.cunghoangdao.activities.offline.OfflineActivity;
import cheng.com.android.cunghoangdao.fragments.ViewPageContainerFragment;
import cheng.com.android.cunghoangdao.provider.DataHandlerSaveContent;
import cheng.com.android.cunghoangdao.services.CheckTimesService;
import cheng.com.android.cunghoangdao.ultils.CustomToast;
import cheng.com.android.cunghoangdao.ultils.SharedPreferencesNotify;

import static cheng.com.android.cunghoangdao.R.string.openDrawer;

/**
 * Created by Welcome on 3/29/2016.
 */
public class MainScreenActivity extends BaseMainActivity {
    private final String TAG = getClass().getSimpleName();
    public static final String TYPE_CATEGORY = "type_category";
    public static final String TOOLBAR_NAME = "toolbar_name";
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    Intent intent;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView profile_image;
    private TextView tvName;
    private View headerView;
    private ViewPageContainerFragment homeFragment;
    private FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int state = 0;
    private Menu menu;
    private TextView slideshow,gallery,tvHot;
    private SwitchCompat switchCompat;
    private DataHandlerSaveContent db;
    private Random random;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            animationIcon();
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        CustomToast.show(this,getResources().getString(R.string.toast_exit));
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void init() {
        random = new Random();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        headerView = navigationView.getHeaderView(0);
        profile_image = (ImageView) headerView.findViewById(R.id.image);
        profile_image.setImageResource(getResources().obtainTypedArray(R.array.cunghoangdaoimage).getResourceId(random.nextInt(11), -1));
        Log.d(TAG, "oncreateMainScreenActivity");
        db = new DataHandlerSaveContent(this);

        gallery=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.itemDownload));
        switchCompat=(SwitchCompat) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.itemThongbao));
        if(SharedPreferencesNotify.getInstance(this).getIsNotify()){
            switchCompat.setChecked(true);
        }else {
            switchCompat.setChecked(false);
        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setTimeAlarm();
                    SharedPreferencesNotify.getInstance(getApplicationContext()).setNotifyTrue();
                }else {
                    stopService(new Intent(MainScreenActivity.this,CheckTimesService.class));
                    SharedPreferencesNotify.getInstance(getApplicationContext()).setNotifyFalse();
                }

            }
        });
        initializeCountDrawer();
        if(switchCompat.isChecked()){
            setTimeAlarm();
        }
    }
    private void initializeCountDrawer(){
        gallery.setGravity(Gravity.CENTER_VERTICAL);
        gallery.setTypeface(null, Typeface.BOLD);
        gallery.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        gallery.setText(db.GetCount()+"");
    }


    @Override
    public void setValue(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                drawerLayout.closeDrawers();
                Intent intent =null;
                switch (menuItem.getItemId()) {
                    case R.id.itemXemgi:
                        intent = new Intent(MainScreenActivity.this, ChitietActivity.class);
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_chi_tiet_ve_bạn));
                        startActivity(intent);
                        return true;
                    case R.id.itemCunghoangdao:
                        intent = new Intent(MainScreenActivity.this, DanhmucActivity.class);
                        intent.putExtra(TYPE_CATEGORY, getResources().getString(R.string.type_cung_hoang_dao));
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_cunghoangdao));
                        startActivity(intent);
                        return true;
                    case R.id.itemTuvi:
                        intent = new Intent(MainScreenActivity.this, DanhmucActivity.class);
                        intent.putExtra(TYPE_CATEGORY, getResources().getString(R.string.type_tu_vi));
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_tu_vi));
                        startActivity(intent);
                        return true;
                    case R.id.itemPhongThuy:
                         intent = new Intent(MainScreenActivity.this, DanhmucActivity.class);
                        intent.putExtra(TYPE_CATEGORY, getResources().getString(R.string.phong_thuy));
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_phong_thuy));
                        startActivity(intent);
                        return true;
                    case R.id.itemXemtuong:
                         intent = new Intent(MainScreenActivity.this, DanhmucActivity.class);
                        intent.putExtra(TYPE_CATEGORY, getResources().getString(R.string.xem_tuong));
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_xem_tuong));
                          startActivity(intent);
                        return true;
                    case R.id.itemTamlinh:
                         intent = new Intent(MainScreenActivity.this, DanhmucActivity.class);
                        intent.putExtra(TYPE_CATEGORY, getResources().getString(R.string.tam_linh));
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_tam_linh));
                        startActivity(intent);
                        return true;
                    case R.id.itemClip:
                        intent = new Intent(MainScreenActivity.this, DanhmucActivity.class);
                        intent.putExtra(TYPE_CATEGORY, getResources().getString(R.string.video_phong_thuy));
                        intent.putExtra(TOOLBAR_NAME, getResources().getString(R.string.menu_item_video_phong_thuy));
                          startActivity(intent);
                        return true;
                    case R.id.itemDownload:
                        intent = new Intent(MainScreenActivity.this, OfflineActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTracNghiem:
                        intent = new Intent(MainScreenActivity.this, BoiCunghoangdaoActivity.class);
                        intent.putExtra(MainScreenActivity.TOOLBAR_NAME, getResources().getString(R.string.tienich_boingaysinh));
                         startActivity(intent);
                        return true;
                    case R.id.itemTuvitrondoi:
                        intent = new Intent(MainScreenActivity.this, TuvitrondoiActivity.class);
                        intent.putExtra(MainScreenActivity.TOOLBAR_NAME, getResources().getString(R.string.tienich_tuvitrondoi));
                         startActivity(intent);
                        return true;
                    case R.id.itemAmDuong:
                        intent = new Intent(MainScreenActivity.this, AmDuongActivity.class);
                        intent.putExtra(MainScreenActivity.TOOLBAR_NAME, getResources().getString(R.string.tienich_doilichamduong));
                         startActivity(intent);
                        return true;
                    case R.id.itemBinhchon:
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(myAppLinkToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        return true;
                    case R.id.itemGopy:
                        intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","cheng.cit@gmail.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.danh_gia)+"("+android.os.Build.MODEL+",Android "+ Build.VERSION.RELEASE+")");
                        try {
                            startActivity(Intent.createChooser(intent, "Không tìm thấy ứng dụng Email."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getApplicationContext(), getString(R.string.gmail), Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.itemThongbao:
                        if (switchCompat.isChecked()) {
                            switchCompat.setChecked(false);
                        } else {
                            switchCompat.setChecked(true);
                        }
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }

            }

        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, mToolbar, openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        if (savedInstanceState == null) {
            initScreen();
        } else {
            homeFragment = (ViewPageContainerFragment) getSupportFragmentManager().findFragmentByTag("home");
        }

    }

    public void animationIcon() {
        ValueAnimator anim = ValueAnimator.ofFloat(1, 0);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                actionBarDrawerToggle.onDrawerSlide(drawerLayout, slideOffset);
            }
        });
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(2000);
        anim.start();
    }

    @Override
    public void setEvent() {
    }

    private void initScreen() {
        homeFragment = new ViewPageContainerFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_maincree_frame_container, homeFragment, "home")
                .commit();
        fragmentManager.executePendingTransactions();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setTimeAlarm() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, CheckTimesService.class);
        PendingIntent pintent = PendingIntent
                .getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Start service every 5 seconds
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                5 * 1000, pintent);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResumeMainCreen: ");
        gallery.setText(db.GetCount()+"");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStopMainCreen: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroyMainCreen: ");
        if(switchCompat.isChecked()){
            setTimeAlarm();
        }else {
            stopService(new Intent(MainScreenActivity.this,CheckTimesService.class));
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPauseMainCreen: ");
        super.onPause();
    }
}
