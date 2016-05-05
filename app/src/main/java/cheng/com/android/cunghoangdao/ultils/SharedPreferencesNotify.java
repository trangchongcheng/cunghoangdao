package cheng.com.android.cunghoangdao.ultils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Welcome on 5/4/2016.
 */
public class SharedPreferencesNotify {
    public static String IS_NOTIFY = "is_notify";
    private SharedPreferences sharedPreferences;
    private Context context;
    private static SharedPreferencesNotify preferences;

    public SharedPreferencesNotify(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("notify", 0);
    }
    public static SharedPreferencesNotify getInstance(Context context) {
        if (preferences == null) {
            preferences = new SharedPreferencesNotify(context);
        }
        return preferences;
    }
    public void setNotifyTrue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_NOTIFY, true);
        Toast.makeText(context, "Đã đăng ký nhận tin tử vi hàng ngày", Toast.LENGTH_SHORT).show();
        editor.commit();
    }
    public void setNotifyFalse(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_NOTIFY, false);
        Toast.makeText(context, "Đã hủy đăng ký nhận tin tử vi hàng ngày", Toast.LENGTH_SHORT).show();
        editor.commit();


    }
    public boolean getIsNotify(){
        return sharedPreferences.getBoolean(IS_NOTIFY,false);
    }
}
