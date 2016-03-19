package playground.android.me.sr1.androidplayground.sample.mutilprocess.processA;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processB.ActivityB;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processB.ServiceB;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processB.SingletonBImp;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processC.ServiceC;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processC.SingletonCImp;
import playground.android.me.sr1.androidplayground.toolbox.component.mutilprocess.singleton.InstanceReceiver;
import playground.android.me.sr1.androidplayground.toolbox.utils.AppUtils;

/**
 * @author SR1s
 */
public class ActivityA extends AppCompatActivity {

    private static String TAG = "ActivityA";

    @Bind(R.id.textView1) TextView mTextView1;

    @Bind(R.id.button0) Button mBtn0;

    @Bind(R.id.button1) Button mBtn1;

    @Bind(R.id.button2) Button mBtn2;

    @Bind(R.id.button3) Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abc);
        ButterKnife.bind(this);

        mTextView1.setText("这里是主进程A");
        mBtn0.setText("启动进程B");
        mBtn1.setText("调用本主进程A的方法)");
        mBtn2.setText("调用其他进程B的方法");
        mBtn3.setText("调用其他进程C的方法");

        /** 把其他进程的单例绑定过来 */
        bindService(ServiceB.class);
        bindService(ServiceC.class);
    }

    void bindService(Class<?> clz) {
        Intent intent = new Intent(this, clz);
        bindService(intent, new InstanceReceiver(), Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.button0)
    void startActivty() {
        Intent intent = new Intent(this, ActivityB.class);
        startActivity(intent);
    }

    @OnClick(R.id.button1)
    void invokeAInstance() {
        try {
            Log.i(TAG, String.format("[%s][invokeA][pid=%d][tid=%d]", AppUtils.currentProcessName(),
                    android.os.Process.myPid(), android.os.Process.myTid()));
            SingletonAImp.getInstance().invokeA(AppUtils.currentProcessName());
            Toast.makeText(App.getContext(), "调用A单例方法", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "[invokeA][fail]", e);
        }
    }

    @OnClick(R.id.button2)
    void invokeBInstance() {
        try {
            Log.i(TAG, String.format("[%s][invokeB][pid=%d][tid=%d]", AppUtils.currentProcessName(),
                    android.os.Process.myPid(), android.os.Process.myTid()));
            SingletonBImp.getInstance().invokeB(AppUtils.currentProcessName());
            Toast.makeText(App.getContext(), "调用B单例方法", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "[invokeB][fail]", e);
        }
    }

    @OnClick(R.id.button3)
    void invokeCInstance() {
        try {
            Log.i(TAG, String.format("[%s][invokeC][pid=%d][tid=%d]", AppUtils.currentProcessName(),
                    android.os.Process.myPid(), android.os.Process.myTid()));
            SingletonCImp.getInstance().invokeC(AppUtils.currentProcessName());
            Toast.makeText(App.getContext(), "调用单例C方法", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "[invoke][fail]", e);
        }
    }
}
