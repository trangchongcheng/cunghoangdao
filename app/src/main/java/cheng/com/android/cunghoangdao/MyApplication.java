package cheng.com.android.cunghoangdao;

import android.app.Application;

import cheng.com.android.cunghoangdao.ultils.LocaleHelper;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.onCreate(this,"vi");
    }
}
