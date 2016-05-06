package cheng.com.android.cunghoangdao.ultils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Welcome on 4/15/2016.
 */
public class SetTimesSharedPreferences {
    public static String IS_TIMES = "is_times";
    private SharedPreferences sharedPreferences;
    private Context context;
    private static SetTimesSharedPreferences preferences;

    public SetTimesSharedPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("times_preferences", 0);
    }
    public static SetTimesSharedPreferences getInstance(Context context) {
        if (preferences == null) {
            preferences = new SetTimesSharedPreferences(context);
        }
        return preferences;
    }
    public void setIsTimesTrue() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_TIMES, true);
        Log.d("SetTimes", "setIsTimesTrue: ");
        editor.commit();
    }
    public void setIsTimesFalse(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d("SetTimes", "setIsTimesFalse: ");
        editor.putBoolean(IS_TIMES, false);
        editor.commit();
    }
    public boolean getIsTimes(){
        return sharedPreferences.getBoolean(IS_TIMES,false);
    }
}
