package cheng.com.android.cunghoangdao.activities.maincreen;

import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.PhongThuyActivity;
import cheng.com.android.cunghoangdao.activities.offline.OfflineActivity;
import cheng.com.android.cunghoangdao.fragments.ViewPageContainerFragment;
import cheng.com.android.cunghoangdao.model.Common;
import cheng.com.android.cunghoangdao.services.CheckTimesService;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.materialdialog.MaterialDialog;

import static cheng.com.android.cunghoangdao.R.string.openDrawer;

/**
 * Created by Welcome on 3/29/2016.
 */
public class MainScreenActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();

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
        int count = fragmentManager.getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            animationIcon();
            //CLOSE Nav Drawer!
        } else if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            fragmentManager.popBackStack();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void setContentView() {
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
        floatButton = (FloatingActionsMenu) headerView.findViewById(R.id.float_button);
        floatButton_item_edit = new FloatingActionButton(this);
        floatButton_item_edit.setTitle("Thay đổi thông tin");
        floatButton_item_edit.setSize(1);
        floatButton.addButton(floatButton_item_edit);

        floatButton_item_detail = new FloatingActionButton(this);
        floatButton_item_detail.setTitle("Chi tiết về bạn");
        floatButton_item_detail.setSize(1);
        floatButton.addButton(floatButton_item_detail);
        Log.d(TAG, "oncreate");

        Intent i = new Intent(this, CheckTimesService.class);
        this.startService(i);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout.setTitle("Cung Hoàng Đạo");
        tvName.setText("Hi " + Common.user.getUserName() + " !");
        // profile_image.setImageResource(Common.getImgAvatar());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;
                if (menuItem.isChecked()) menuItem.setChecked(false);
                //else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.itemPhongThuy:
                        intent = new Intent(MainScreenActivity.this, PhongThuyActivity.class);
                        startActivity(intent);
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.itemNgayTot:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.itemDownload:
                        intent = new Intent(MainScreenActivity.this, OfflineActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemTracNghiem:
                        intent = new Intent(MainScreenActivity.this, OfflineActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.itemAmDuong:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
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
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        if (savedInstanceState == null) {
            initScreen();
            Log.d(TAG, "savedInstanceState == null");
        } else {
            homeFragment = (ViewPageContainerFragment) getSupportFragmentManager().findFragmentByTag("home");
            Log.d(TAG, "savedInstanceState # null");
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
        // Creating the ViewPager container fragment once
        homeFragment = new ViewPageContainerFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_maincree_frame_container, homeFragment, "home")
                .commit();
        fragmentManager.executePendingTransactions();
    }

    private void registerReceivers() {
        this.registerReceiver(NetworkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private BroadcastReceiver NetworkChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, Intent intent) {
            int times = 0;
            if (!ConnectionUltils.isConnected(context)) {
                if(times<1){
                    Log.d(TAG, "showdialog");
                    showDialog(context);
                }
                ++times;
            }
        }
    };


    public void showDialog(Context context) {
        final MaterialDialog dialog;
        dialog = new MaterialDialog(context);
        dialog.setTitle("Không tìm thấy Network");
        dialog.setMessage("Vui lòng kiểm tra lại đường truyền");
        dialog.setNegativeButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CECK++: ");
                dialog.dismiss();
                Log.d(TAG, "onClick: ");
            }
        });
        dialog.setPositiveButton("Bật mạng", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        dialog.show();

    }

    @Override
    protected void onStart() {
        setTimeAlarm();
        super.onStart();
    }

    public void setTimeAlarm(){
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(this, CheckTimesService.class);
        PendingIntent pintent = PendingIntent
                .getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Start service every 20 seconds
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                5 * 1000, pintent);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        registerReceivers();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onStop: ");
        super.onPause();
        try {
            unregisterReceiver(NetworkChangeReceiver);
        } catch (IllegalArgumentException e) {
        }

    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
        try {
            unregisterReceiver(NetworkChangeReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        try {
            unregisterReceiver(NetworkChangeReceiver);
        } catch (IllegalArgumentException e) {
        }
    }
}
