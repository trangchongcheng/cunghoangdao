package cheng.com.android.cunghoangdao;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private final String TAG = getClass().getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        Process exec = null;
//        String locale = null;
//        try {
//            exec = Runtime.getRuntime().exec(new String[]{"getprop", "persist.sys.language"});
//            locale = new BufferedReader(new InputStreamReader(exec.getInputStream())).readLine();
//        } catch (IOException e) {
//            LocaleHelper.onCreate(this,LocaleHelper.VIETNAM);
//        }
//        LocaleHelper.onCreate(this,locale);
//        Log.d(TAG, "onCreate: "+"cheng");
    }
    public static Context getContext(){
        return mContext;
    }
}
