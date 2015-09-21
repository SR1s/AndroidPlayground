package playground.android.me.sr1.androidplayground.toolbox.component.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 进程间通讯的基础类
 * Created by sr1 on 15/9/19.
 */
public abstract class AbsInterProcessService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new InterProcessImp();
    }

    /**
     * 这里处理同步请求
     * @param cmd 命令字
     * @param data 传递过来的数据
     * @return 返回的数据
     */
    protected abstract Bundle handleSyncRequest(String cmd, Bundle data);

    /**
     * 这里处理异步请求<br/>
     * 注意，这里需要讲具体的执行过程异步化<br/>
     * 返回值仅为了告诉调用方这里是否正确的接受了请求(而不是处理完)
     * @param cmd 命令字
     * @param data 传递过来的数据
     * @param callback 回调接口
     * @return 状态码
     */
    protected abstract int handleAsyncRequest(String cmd, Bundle data, IpcCallback callback);

    public class InterProcessImp extends IpcInterface.Stub {

        @Override
        public Bundle invokeSync(String cmd, Bundle data) throws RemoteException {
            return handleSyncRequest(cmd, data);
        }

        @Override
        public int invokeAsync(String cmd, Bundle data, IpcCallback callback) throws RemoteException {
            return handleAsyncRequest(cmd, data, callback);
        }
    }
}
