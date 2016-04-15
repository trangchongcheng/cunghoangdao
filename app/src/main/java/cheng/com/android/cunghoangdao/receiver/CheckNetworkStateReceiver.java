package cheng.com.android.cunghoangdao.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
        if(status && !SetTimesSharedPreferences.getInstance(context).getIsTimes()){
            Intent i = new Intent(context, PushNotifyService.class);
            context.startService(i);
            SetTimesSharedPreferences.getInstance(context).setIsTimesTrue();
        }
    }
}