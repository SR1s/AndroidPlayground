package playground.android.me.sr1.androidplayground.sample.mutilprocess.utils;

import android.util.Log;

import playground.android.me.sr1.androidplayground.toolbox.utils.AppUtils;

/**
 * @author SR1s
 */
public class ProcessUtils {

    private static final String TAG = "ProcessUtils";

    public static boolean isProcessA() {
        return AppUtils.isMainProcess();
    }

    public static boolean isProcessB() {
        Log.i(TAG, "isProcessB >>> " + AppUtils.currentProcessName());
        return AppUtils.currentProcessName().endsWith(":B");
    }

    public static boolean isProcessC() {
        Log.i(TAG, "isProcessC >>> " + AppUtils.currentProcessName());
        return AppUtils.currentProcessName().endsWith(":C");
    }

}
