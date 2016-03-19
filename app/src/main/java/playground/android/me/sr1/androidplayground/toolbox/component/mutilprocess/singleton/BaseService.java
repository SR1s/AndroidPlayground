package playground.android.me.sr1.androidplayground.toolbox.component.mutilprocess.singleton;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 每个进程需要有一个Service继承自这个Service,用于绑定单例IPC代理
 * 同时,在AndroidManifest定义里,进程名要指定好
 * 进程启动的时候,即可发起向其他进程的绑定(bindService)
 * @author SR1s
 */
public class BaseService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new InstanceTransferImp();
    }
}
