package cheng.com.android.cunghoangdao.ultils;

/**
 * Created by Welcome on 4/14/2016.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkBroadcastUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean getConnectivityStatusString(Context context) {
        int conn = NetworkBroadcastUtil.getConnectivityStatus(context);
        boolean isNetwork = false;
        if (conn == NetworkBroadcastUtil.TYPE_WIFI) {
            isNetwork = true;
        } else if (conn == NetworkBroadcastUtil.TYPE_MOBILE) {
            isNetwork = true;
        } else if (conn == NetworkBroadcastUtil.TYPE_NOT_CONNECTED) {
            isNetwork = false;
        }
        return isNetwork;
    }
}

