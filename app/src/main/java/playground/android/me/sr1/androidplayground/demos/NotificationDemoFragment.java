package playground.android.me.sr1.androidplayground.demos;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.webview.IpcService;

/**
 * Created by srluo on 2015/9/9.
 */
public class NotificationDemoFragment extends Fragment {

    public static final String TAG = "NotificationDemoFrag";

    NotificationManager mNotificationManager;
    private RemoteProcessInterface mService;

    private Button mBindService, mOthrShow, mOthrClear, mMainShow, mMainClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demos_notification, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBindService = (Button) view.findViewById(R.id.demos_notification_bind_remote_process);
        mOthrShow = (Button) view.findViewById(R.id.demos_notification_othr_process_show);
        mOthrClear = (Button) view.findViewById(R.id.demos_notification_othr_process_clear);
        mMainShow = (Button) view.findViewById(R.id.demos_notification_main_process_show);
        mMainClear = (Button) view.findViewById(R.id.demos_notification_main_process_clear);

        View.OnClickListener lstner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.demos_notification_bind_remote_process:
                        bindService();
                        break;
                    case R.id.demos_notification_main_process_show:
                        showMainNotification();
                        break;
                    case R.id.demos_notification_main_process_clear:
                        clearMainNotification();
                        break;
                    case R.id.demos_notification_othr_process_show:
                        showOthrNotification();
                        break;
                    case R.id.demos_notification_othr_process_clear:
                        clearOthrNotification();
                        break;
                }
            }
        };

        mBindService.setOnClickListener(lstner);
        mMainShow.setOnClickListener(lstner);
        mMainClear.setOnClickListener(lstner);
        mOthrShow.setOnClickListener(lstner);
        mOthrClear.setOnClickListener(lstner);

        mNotificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void bindService() {
        getActivity().bindService(new Intent(getActivity(), RemoteProcessService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = RemoteProcessInterface.Stub.asInterface(service);
                mBindService.setClickable(false);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
                mBindService.setClickable(true);
            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void showMainNotification() {

        Notification notification = new Notification(R.drawable.logo, "来自主进程的通知", System.currentTimeMillis());
        notification.setLatestEventInfo(getActivity().getApplicationContext(), "来自主进程的通知", "来自主进程的通知", null);
        mNotificationManager.notify(11, notification);
    }

    private void clearMainNotification() {
        mNotificationManager.cancelAll();
    }

    private void showOthrNotification() {
        if (mService != null) {
            try {
                mService.showNotification();
            } catch (RemoteException e) {
                Log.e(TAG, "[showOthrNotification] Exception!!", e);
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "其他进程服务无连接", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearOthrNotification() {
        if (mService != null) {
            try {
                mService.clearNotification();
            } catch (RemoteException e) {
                Log.e(TAG, "[showOthrNotification] Exception!!", e);
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "其他进程服务无连接", Toast.LENGTH_SHORT).show();
        }
    }
}
