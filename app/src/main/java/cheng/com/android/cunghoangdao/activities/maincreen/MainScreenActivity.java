package cheng.com.android.cunghoangdao.activities.maincreen;

import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.DanhmucActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.TienichActivity;
import cheng.com.android.cunghoangdao.activities.offline.OfflineActivity;
import cheng.com.android.cunghoangdao.fragments.ViewPageContainerFragment;
import cheng.com.android.cunghoangdao.model.Common;
import cheng.com.android.cunghoangdao.services.CheckTimesService;
import cheng.com.android.cunghoangdao.ultils.CustomToast;
import cheng.com.android.cunghoangdao.ultils.LocaleHelper;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private CircleImageView profile_image;
    private TextView tvName;
    private View headerView;
    private FloatingActionsMenu floatButton;
    private FloatingActionButton floatButton_item_edit, floatButton_item_detail;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPageContainerFragment homeFragment;
    private FragmentManager fragmentManager;
    boolean doubleBackToExitPressedOnce = false;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private int state = 0;

    @Override
    public void onBackPressed() {
//        int count = fragmentManager.getBackStackEntryCount();
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
//        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainScreenActivity.this, PREF_USER_FIRST_TIME, "true"));
//        Intent introIntent = new Intent(MainScreenActivity.this, PagerActivity.class);
//        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);
//        if (isUserFirstTime)
//            startActivity(introIntent);
        setContentView(R.layout.activity_maincreen);
    }

    @Override
    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        headerView = navigationView.getHeaderView(0);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        profile_image = (CircleImageView) headerView.findViewById(R.id.profile_image);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        Log.d(TAG, "oncreateMainScreenActivity");
        Intent i = new Intent(this, CheckTimesService.class);
        this.startService(i);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        tvName.setText(getResources().getString(R.string.xin_chao) + Common.user.getUserName() + " !");
        // profile_image.setImageResource(Common.getImgAvatar());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;
                if (menuItem.isChecked()) menuItem.setChecked(false);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
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
                        intent = new Intent(MainScreenActivity.this, TienichActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemAmDuong:
                        Toast.makeText(getApplicationContext(), "Hihi Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.itemLanguage:
                        showChangeLangDialog();
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
        setTimeAlarm();
        super.onStart();
    }

    public void setTimeAlarm() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, CheckTimesService.class);
        PendingIntent pintent = PendingIntent
                .getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Start service every 20 seconds
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                5 * 1000, pintent);
    }



    public void showChangeLangDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button btnChange = (Button) dialogView.findViewById(R.id.dialog_btnChange);
        final RadioButton rdbtnVN = (RadioButton) dialogView.findViewById(R.id.dialog_rdbtnVN);
        final RadioButton rdbtnEN = (RadioButton) dialogView.findViewById(R.id.dialog_rdbtnEN);
        final AlertDialog b = dialogBuilder.create();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbtnVN.isChecked()) {
                    LocaleHelper.setLocale(getApplicationContext(), LocaleHelper.VIETNAM);
                } else if (rdbtnEN.isChecked()) {
                    LocaleHelper.setLocale(getApplicationContext(), LocaleHelper.ENGLISH);
                }
                b.dismiss();
                if (android.os.Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    finish();
                    Intent intent = getIntent();
                    startActivity(intent);

                }
            }
        });

        b.show();
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "onResumeMainCreen: ");
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
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPauseMainCreen: ");
        super.onPause();
    }
}
