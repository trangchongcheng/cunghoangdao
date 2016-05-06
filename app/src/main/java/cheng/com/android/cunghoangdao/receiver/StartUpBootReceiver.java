package cheng.com.android.cunghoangdao.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import cheng.com.android.cunghoangdao.services.CheckTimesService;

/**
 * Created by Welcome on 5/5/2016.
 */
public class StartUpBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StartUpBootReceiver", "onReceive: ");
        Toast.makeText(context, "Cheng", Toast.LENGTH_SHORT).show();
        Calendar cal = Calendar.getInstance();
        Intent i = new Intent(context, CheckTimesService.class);
        PendingIntent pintent = PendingIntent
                .getService(context, 0, i, 0);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Start service every 5 seconds
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                5 * 1000, pintent);
    }
}
