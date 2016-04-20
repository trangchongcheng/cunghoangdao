package cheng.com.android.cunghoangdao.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityChangeReceiver = new ConnectivityChangeReceiver(this);
        filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        setContentView();
        init();
        setValue(savedInstanceState);
        setEvent();
    }

    public abstract void setContentView();

    public abstract void init();

    public abstract void setValue(Bundle savedInstanceState);

    public abstract void setEvent();

    @Override
    protected void onStop() {
        Log.d(TAG, "onDestroy: ");
        super.onStop();
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public void onConnectivityChanged(boolean isConnected) {
        int times = 0;
        if (!isConnected && times < 1) {
            showDialog(this);
        }
        times++;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        registerReceiver(connectivityChangeReceiver, filter);
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onDestroy: ");
        super.onPause();
        try {
            unregisterReceiver(connectivityChangeReceiver);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    public void showDialog(Context context) {
        final MaterialDialog dialog;
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
            }
        });
        dialog.show();

    }
}
