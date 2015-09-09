package playground.android.me.sr1.androidplayground.demos;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import playground.android.me.sr1.androidplayground.R;

/**
 * Created by srluo on 2015/9/9.
 */
public class RemoteProcessService extends Service {

    NotificationManager mNotificationManager;

    Handler mHandler = new Handler(Looper.getMainLooper());

    IBinder mBinder = new RemoteProcessInterface.Stub() {

        @Override
        public void showNotification() throws RemoteException {

//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
                    Notification notification = new Notification(R.drawable.logo, "来自其他进程的通知", System.currentTimeMillis());
                    notification.setLatestEventInfo(getApplicationContext(), "来自其他进程的通知", "来自其他进程的通知", null);
                    mNotificationManager.notify(21, notification);
//                }
//            });

        }

        @Override
        public void clearNotification() throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mNotificationManager.cancelAll();
                }
            });
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
