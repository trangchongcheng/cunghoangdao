package cheng.com.android.cunghoangdao.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.model.Article;
import cheng.com.android.cunghoangdao.provider.DataNewfeeds;


/**
 * Created by Welcome on 4/14/2016.
 */
public class PushNotifyService extends Service {
    SharedPreferences preferences;
    Context content;
    private static final String DOCUMENT_VIEW_STATE_PREFERENCES = "DjvuDocumentViewState";

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private NotificationManager mNM;
    String downloadUrl;
    private DataNewfeeds db;
    public static boolean serviceState=false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        db = new DataNewfeeds(getApplicationContext());
        serviceState=true;
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        HandlerThread thread = new HandlerThread("ServiceStartArguments",1);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE-ONCOMMAND","onStartCommand");
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("SERVICE-DESTROY","DESTORY");
        serviceState=false;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            db.addArticle(Article.paserJsoupCategory("http://lichngaytot.com/tu-vi-12-cung-hoang-dao.html"));
            showNotification();
            stopSelf(msg.arg1);
        }
    }

    void showNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Article article;
        article = db.getArticle(0);
        builder.setContentTitle(article.getmTitle())
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentText(article.getmDescription())
                .setSmallIcon(R.drawable.chipi);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        Intent mainIntent = new Intent(getApplicationContext(), ViewingActivity.class);
        mainIntent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT,article.getmContent());
        mainIntent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_NOTIFY,"type_notify");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
       // builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));
        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }


}
