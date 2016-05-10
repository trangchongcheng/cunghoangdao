package cheng.com.android.cunghoangdao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import cheng.com.android.cunghoangdao.services.PushNotifyService;
import cheng.com.android.cunghoangdao.ultils.NetworkBroadcastUtil;
import cheng.com.android.cunghoangdao.ultils.SetTimesSharedPreferences;


/**
 * Created by Welcome on 4/14/2016.
 */
public class CheckNetworkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = NetworkBroadcastUtil.getConnectivityStatusString(context);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.d("TAG", "onReceive: "+status + SetTimesSharedPreferences.getInstance(context).getIsTimes());
        if(status && !SetTimesSharedPreferences.getInstance(context).getIsTimes()){
            if(6<hour && hour<23){
                Intent i = new Intent(context, PushNotifyService.class);
                context.startService(i);

            }

        }
    }
}