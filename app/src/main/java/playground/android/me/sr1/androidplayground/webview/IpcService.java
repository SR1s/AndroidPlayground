package playground.android.me.sr1.androidplayground.webview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import playground.android.me.sr1.androidplayground.ICallback;
import playground.android.me.sr1.androidplayground.IIPCInterface;

/**
 * Created by srluo on 2015/8/17.
 */
public class IpcService extends Service {

    private static final String TAG = "IpcService";

    private final IIPCInterface.Stub mBinder = new IIPCInterface.Stub() {
        @Override
        public void sendMessage() throws RemoteException {
            Log.d(TAG, String.format("[IIpcInterface : %s]", "sendMessage"));
            return;
        }

        @Override
        public String getMessage() throws RemoteException {
            Log.d(TAG, String.format("[IIpcInterface : %s]", "getMessage"));
            return TAG;
        }

        @Override
        public List<String> getMessageList() throws RemoteException {
            Log.d(TAG, String.format("[IIpcInterface : %s]", "getMessageList"));
            List<String> list = new ArrayList<String>();
            list.add(TAG);
            list.add(TAG);
            list.add(TAG);
            list.add(TAG);
            list.add(TAG);
            list.add(TAG);
            list.add(TAG);
            return list;
        }

        @Override
        public String getResultWithParams(String information) throws RemoteException {
            Log.d(TAG, String.format("[IIpcInterface : %s]", "getResultWithParams" + information));
            return TAG;
        }

        @Override
        public String getResultWithDelay(int delay) throws RemoteException {
            Log.d(TAG, String.format("[IIpcInterface : %s]", "getResultWithDelay" + delay));
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException", e);
            }
            return TAG;
        }

        @Override
        public void getResultViaCallback(final ICallback callback) throws RemoteException {
            Log.d(TAG, String.format("[IIpcInterface : %s]", "getResultViaCallback"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                        if (callback != null) {
                            callback.onResultBack(TAG);
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "RemoteException", e);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "InterruptedException", e);
                    }
                }
            }).start();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
