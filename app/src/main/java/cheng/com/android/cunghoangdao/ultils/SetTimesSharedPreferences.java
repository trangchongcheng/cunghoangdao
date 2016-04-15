package cheng.com.android.cunghoangdao.ultils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

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
        Toast.makeText(context, "Set True", Toast.LENGTH_SHORT).show();
        editor.commit();
    }
    public void setIsTimesFalse(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_TIMES, false);
        editor.commit();
        Toast.makeText(context, "Set False", Toast.LENGTH_SHORT).show();
    }
    public boolean getIsTimes(){
        return sharedPreferences.getBoolean(IS_TIMES,true);
    }
}
