package cheng.com.android.cunghoangdao.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.robohorse.gpversionchecker.GPVersionChecker;
import com.robohorse.gpversionchecker.base.CheckingStrategy;
import com.startapp.android.publish.StartAppSDK;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.ultils.ConnectivityChangeReceiver;

/**
 * Created by Welcome on 4/18/2016.
 */
public abstract class BaseMainActivity extends AppCompatActivity
        implements ConnectivityChangeReceiver.OnConnectivityChangedListener {
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private final String TAG = getClass().getSimpleName();
    private Context context;
    private IntentFilter filter;
    private BroadcastReceiver changeLocaleReceiver;
    public static int isBack=0;
    int times;
    public InterstitialAd mInterstitialAd;
    private int isDestroy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this,getString(R.string.banner_startapp), true);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_video));
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
        Log.d(TAG, "onCreate: "+"hjhjhj");
        new GPVersionChecker.Builder(this).create().setCheckingStrategy(CheckingStrategy.ONE_PER_DAY);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        setContentView();
        init();
        setValue(savedInstanceState);
        setEvent();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            // toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
//        }
    }


    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startGame();
        }
    }
    private void startGame() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

    }

    public abstract void setContentView();

    public abstract void init();

    public abstract void setValue(Bundle savedInstanceState);

    public abstract void setEvent();

    @Override
    protected void onStop() {
        Log.d(TAG, "onStopBaseMainActivity: ");
        super.onStop();
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        if (!isConnected) {
            showDialog(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: cheng");
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        times = 0;
        super.onResume();
        Log.d(TAG, "onResumeBaseMainActivity: ");
        if (times < 1) {
            registerReceiver(connectivityChangeReceiver, filter);
            ++times;
            Log.d(TAG, "times: " + times);
        }

    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        Log.d(TAG, "onPauseBaseMainActivity: ");
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStartBaseMainActivity: ");
    }

    public void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getResources().getString(R.string.khong_co_ket_noi));
        builder.setMessage(getResources().getString(R.string.kiem_tra_ket_noi));
        builder.setPositiveButton(getResources().getString(R.string.dong_y), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.bat_mang),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    dialog.dismiss();
            }
        });
        AlertDialog diag = builder.create();
        diag.show();
    }

    @Override
    public void onBackPressed() {
        isBack++;
        Log.d(TAG, "onBackPressed: "+isBack+MainScreenActivity.doubleBackToExitPressedOnce);
        if(isBack==4||isBack==8||isBack==12||isBack==16||isBack==20){
            if(!MainScreenActivity.doubleBackToExitPressedOnce){
                showInterstitial();
            }
        }
        super.onBackPressed();

    }
}
