package cheng.com.android.cunghoangdao;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import cheng.com.android.cunghoangdao.ultils.LocaleHelper;

public class MyApplication extends Application {
    private final String TAG = getClass().getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LocaleHelper.onCreate(this,LocaleHelper.VIETNAM);
        Log.d(TAG, "onCreate: "+"HIHI");

    }
    public static Context getContext(){
        return mContext;
    }
}
