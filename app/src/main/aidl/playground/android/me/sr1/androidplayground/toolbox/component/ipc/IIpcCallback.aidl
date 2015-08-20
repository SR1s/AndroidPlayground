package playground.android.me.sr1.androidplayground.toolbox.component.ipc;

import playground.android.me.sr1.androidplayground.toolbox.component.ipc.IpcResult;

/**
 * 进程间通讯的回调类
 */
interface IIpcCallback {

    oneway void onResult(in IpcResult result);
}
