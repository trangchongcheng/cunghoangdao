package cheng.com.android.cunghoangdao.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

import cheng.com.android.cunghoangdao.ultils.SetTimesSharedPreferences;


/**
 * Created by Welcome on 4/15/2016.
 */
public class CheckTimesService extends Service {
    private final String TAG = getClass().getSimpleName();
    private int times = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //UnBound will call
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int ds = c.get(Calendar.AM_PM);
        Log.d(TAG, "onStartCommand: " + hour +"times:"+ times);
        if (hour > 22 && hour < 25 && times < 1) {
            SetTimesSharedPreferences.getInstance(getApplicationContext()).setIsTimesFalse();
            times = times + 1;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Service onDestroy");
    }
}
