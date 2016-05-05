package cheng.com.android.cunghoangdao.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Locale;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.ultils.ConnectivityChangeReceiver;
import me.drakeet.materialdialog.MaterialDialog;

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
    int times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public abstract void setContentView();

    public abstract void init();

    public abstract void setValue(Bundle savedInstanceState);

    public abstract void setEvent();

    @Override
    protected void onStop() {
        Log.d(TAG, "onStopBaseMainActivity: ");
        super.onStop();
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        if (!isConnected) {
            showDialog(this);
        }

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroyBaseMainActivity: ");
        super.onDestroy();
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
        }

    }

    @Override
    protected void onResume() {
        String locale = Locale.getDefault().getLanguage();
        Log.d("TAG", locale);
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
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStartBaseMainActivity: ");
    }

    private MaterialDialog dialog;

    public void showDialog(Context context) {
        if( dialog != null ) return;
            dialog = new MaterialDialog(context);
            dialog.setTitle(getResources().getString(R.string.khong_co_ket_noi));
            dialog.setMessage(getResources().getString(R.string.kiem_tra_ket_noi));
            dialog.setNegativeButton(getResources().getString(R.string.dong_y), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setPositiveButton(getResources().getString(R.string.bat_mang), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    dialog.dismiss();
                }
            });
            dialog.show();

    }
}
