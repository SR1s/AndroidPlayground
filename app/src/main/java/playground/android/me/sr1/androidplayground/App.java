package playground.android.me.sr1.androidplayground;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by sr1 on 15/8/11.
 */
public class App extends Application {

    private static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        Fresco.initialize(this);
    }

    /**
     * 获取Application级别的Context
     * 要注意在多进程的App上，获取到的并不是同一个实例。
     * @return
     */
    public static Context getContext() {
        return sAppContext;
    }
}
