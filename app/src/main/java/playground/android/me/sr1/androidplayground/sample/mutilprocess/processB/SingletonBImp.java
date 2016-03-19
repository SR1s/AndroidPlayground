package playground.android.me.sr1.androidplayground.sample.mutilprocess.processB;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.widget.Toast;

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.utils.MainThreadUtils;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.utils.ProcessUtils;
import playground.android.me.sr1.androidplayground.toolbox.component.mutilprocess.singleton.InstanceReceiver;
import playground.android.me.sr1.androidplayground.toolbox.utils.AppUtils;

/**
 * @author SR1s
 */
public class SingletonBImp extends SingletonB.Stub {

    public static final String TAG = "SingletonBImp";

    public static SingletonB INSTANCE;

    public static synchronized SingletonB getInstance() {
        if (ProcessUtils.isProcessB()) {
            if (INSTANCE == null) {
                INSTANCE = new SingletonBImp();
            }
            return INSTANCE;
        } else {
            if (INSTANCE == null) {
                /** 自发重连 */
                Intent intent = new Intent(App.getContext(), ServiceB.class);
                App.getContext().bindService(intent, new InstanceReceiver(), Context.BIND_AUTO_CREATE);
            }
            return INSTANCE;
        }
    }

    private SingletonBImp() {}

    @Override
    public void invokeB(final String aString) throws RemoteException {
        MainThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(),
                        String.format("[SingletonB]\n[调用进程:%s]\n[执行进程:%s]", aString, AppUtils.currentProcessName()),
                        Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, String.format("[%s][invokeB][pid=%d][tid=%d]",
                aString, android.os.Process.myPid(), android.os.Process.myTid()));
    }
}
