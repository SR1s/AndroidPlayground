package playground.android.me.sr1.androidplayground.toolbox.component.mutilprocess.singleton;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * @author SR1s
 */
public class InstanceReceiver implements ServiceConnection {

    private static final String TAG = "InstanceReceiver";

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.i(TAG, "[onServiceConnected]" + name);
        try {
            /** 调用这句就会将单例(代理)实例传递过来了 */
            InstanceTransfer.Stub.asInterface(service).transfer();
        } catch (Exception e) {
            Log.e(TAG, "[onServiceConnected][exception when transfer instance]" + name, e);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        /** 意外断开绑定的情况,这里可以重写成发起重连 */
        Log.e(TAG, "[onServiceDisconnected][exception when service disconnected]" + name);
    }
}
