package cheng.com.android.cunghoangdao;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cheng.com.android.cunghoangdao.ultils.LocaleHelper;

public class MyApplication extends Application {
    private final String TAG = getClass().getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Process exec = null;
        String locale = null;
        try {
            exec = Runtime.getRuntime().exec(new String[]{"getprop", "persist.sys.language"});
            locale = new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine();
        } catch (IOException e) {
            LocaleHelper.onCreate(this,LocaleHelper.VIETNAM);
        }
        LocaleHelper.onCreate(this,locale);
        Log.d(TAG, "onCreate: "+"cheng");
    }
    public static Context getContext(){
        return mContext;
    }
}
