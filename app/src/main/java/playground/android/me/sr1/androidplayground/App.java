package playground.android.me.sr1.androidplayground;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by sr1 on 15/8/11.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
