package playground.android.me.sr1.androidplayground.sample.mutilprocess.processA;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
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
public class SingletonAImp extends SingletonA.Stub {

    public static SingletonA INSTANCE;

    public static synchronized SingletonA getInstance() {
        if (ProcessUtils.isProcessA()) {
            if (INSTANCE == null) {
                INSTANCE = new SingletonAImp();
            }
            return INSTANCE;
        } else {
            if (INSTANCE == null) {
                /** 自发重连 */
                Intent intent = new Intent(App.getContext(), ServiceA.class);
                App.getContext().bindService(intent, new InstanceReceiver(), Context.BIND_AUTO_CREATE);
            }
            return INSTANCE;
        }
    }

    private SingletonAImp() {}

    public static final String TAG = "SingletonAImp";

    @Override
    public void invokeA(final String aString) throws RemoteException {
        MainThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(),
                        String.format("[SingletonA]\n[调用进程:%s]\n[执行进程:%s]\n", aString, AppUtils.currentProcessName()),
                        Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, String.format("[%s][invokeA][pid=%d][tid=%d]",
                aString, android.os.Process.myPid(), Process.myTid()));
    }
}
