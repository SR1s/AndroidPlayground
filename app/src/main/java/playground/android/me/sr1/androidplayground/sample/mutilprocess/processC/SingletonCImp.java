package playground.android.me.sr1.androidplayground.sample.mutilprocess.processC;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
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
public class SingletonCImp extends SingletonC.Stub {

    public static final String TAG = "SingletonCImp";

    public static SingletonC INSTANCE;

    public static synchronized SingletonC getInstance() {
        if (ProcessUtils.isProcessC()) {
            if (INSTANCE == null) {
                INSTANCE = new SingletonCImp();
            }
            return INSTANCE;
        } else {
            if (INSTANCE == null) {
                /** 自发重连 */
                Intent intent = new Intent(App.getContext(), ServiceC.class);
                App.getContext().bindService(intent, new InstanceReceiver(), Context.BIND_AUTO_CREATE);
            }
            return INSTANCE;
        }
    }

    private SingletonCImp() {}

    @Override
    public void invokeC(final String aString) throws RemoteException {
        MainThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getContext(),
                        String.format("[SingletonC]\n[调用进程:%s]\n[执行进程:%s]", aString, AppUtils.currentProcessName()),
                        Toast.LENGTH_SHORT).show();
            }
        });
        Log.i(TAG, String.format("[%s][invokeC][pid=%d][tid=%d]",
                aString, android.os.Process.myPid(), android.os.Process.myTid()));
    }
}
