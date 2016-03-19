package playground.android.me.sr1.androidplayground.sample.mutilprocess.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author SR1s
 */
public class MainThreadUtils {

    private static final Handler sMainThreadHandler = new Handler(Looper.getMainLooper());

    public static void runOnMainThread(Runnable runnable) {
        sMainThreadHandler.post(runnable);
    }
}
