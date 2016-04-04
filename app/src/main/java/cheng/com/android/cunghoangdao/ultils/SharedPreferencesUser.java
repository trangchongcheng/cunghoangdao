package cheng.com.android.cunghoangdao.ultils;

import android.content.Context;
import android.content.SharedPreferences;

import cheng.com.android.cunghoangdao.model.Common;

/**
 * Created by Welcome on 1/18/2016.
 */
public class SharedPreferencesUser {
    public static String USER_NAME = "user_name";
    public static String DATE_OF_BITRH = "day_of_birth";
    public static String MONTH = "month";
    public static String YEAR = "year";
    public static String CUNG_HOANG_DAO = "cung_hoang_dao";
    public static String IMAGE_CUNGHOANGDAO = "image_cung_hoang_dao";
    private Context context;
    private SharedPreferences sharedPreferences;
    private static SharedPreferencesUser preferencesUser;

    public SharedPreferencesUser(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("share_preferences", 0);
    }

    public static SharedPreferencesUser getInstance(Context context) {
        if (preferencesUser == null) {
            preferencesUser = new SharedPreferencesUser(context);
        }
        return preferencesUser;
    }

    public void saveInfo() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, Common.user.getUserName());
        editor.putInt(DATE_OF_BITRH, Common.user.getDao());
        editor.putInt(MONTH, Common.user.getMonth());
        editor.putInt(MONTH, Common.user.getYear());
        editor.putInt(IMAGE_CUNGHOANGDAO, Common.getImgAvatar());
        editor.commit();
    }

    public void getInfo() {
        Common.user.setUserName(sharedPreferences.getString(USER_NAME, ""));
        Common.user.setDao((sharedPreferences.getInt(DATE_OF_BITRH, 0)));
        Common.user.setMonth((sharedPreferences.getInt(MONTH, 0)));
        Common.user.setYear((sharedPreferences.getInt(YEAR, 0)));
        Common.setImgAvatar(sharedPreferences.getInt(IMAGE_CUNGHOANGDAO,0));
    }
    public  boolean isLogin(){
        return !sharedPreferences.getString(USER_NAME,"").equals("");
    }


}
