package playground.android.me.sr1.androidplayground.toolbox.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

import playground.android.me.sr1.androidplayground.App;

/**
 * @author SR1s
 */
public class AppUtils {

    public static boolean isMainProcess() {
        String packageName = App.getContext().getPackageName();
        return packageName.equals(currentProcessName());
    }

    public static String currentProcessName() {

        int pid = android.os.Process.myPid();

        ActivityManager am = (ActivityManager) App.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processList = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcess : processList) {
            if (appProcess != null && appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
