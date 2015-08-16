package playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import playground.android.me.sr1.androidplayground.IMyAidlInterface;
import playground.android.me.sr1.androidplayground.utils.DataHelper;

public class MyService extends Service {

    public static final String TAG = "MyService";

    public MyService() {
    }

    public class IpcBinder extends IMyAidlInterface.Stub {

        @Override
        public String getThreadNameFast() throws RemoteException {
            return null;
        }

        @Override
        public String getThreadNameSlow() throws RemoteException {
            return null;
        }

        @Override
        public String getThreadNameBlocking() throws RemoteException {
            return null;
        }

        @Override
        public String getThreadNameUnblock() throws RemoteException {
            return null;
        }
    }

    public class MyBinder extends Binder {
        public DataHelper.MovieData getMovieData() {
            return MyService.this.getMovieData();
        }
    }

    ArrayList<DataHelper.MovieData> mMovieDatas;
    Random mRand = new Random();
    MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        mMovieDatas = DataHelper.getMovieData();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public DataHelper.MovieData getMovieData() {
        return mMovieDatas.get(mRand.nextInt(mMovieDatas.size()));
    }
}
