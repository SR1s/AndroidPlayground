package playground.android.me.sr1.androidplayground.toolbox.component.ipc;

interface IpcCallback {
    /**
     * 跨进程回调接口
     */
    void onResult(in String cmd, in android.os.Bundle data);
}
