package playground.android.me.sr1.androidplayground.toolbox.component.ipc;


interface IpcInterface {
    /**
     * 这个是同步接口，数据通过Bundle传入，通过Bundle传回
     */
    android.os.Bundle invokeSync(in String cmd, in android.os.Bundle data);

    /**
     * 这个是异步化接口，实际的异步要在实现端保证，
     * 返回值是为了告诉调用方是否正常交付处理
     */
    // int invokeAsync(in String cmd, in android.os.Bundle data, int IpcCallback callback);
}
