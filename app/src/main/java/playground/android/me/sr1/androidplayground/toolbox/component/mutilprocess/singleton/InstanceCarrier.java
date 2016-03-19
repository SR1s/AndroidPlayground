package playground.android.me.sr1.androidplayground.toolbox.component.mutilprocess.singleton;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import playground.android.me.sr1.androidplayground.sample.mutilprocess.processA.SingletonA;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processA.SingletonAImp;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processB.SingletonB;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processB.SingletonBImp;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processC.SingletonC;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.processC.SingletonCImp;
import playground.android.me.sr1.androidplayground.sample.mutilprocess.utils.ProcessUtils;

/**
 * 进程间进行传递(代理)实例在这里添加
 * @author SR1s
 */
public class InstanceCarrier implements Parcelable {

    private static final String TAG = "InstanceCarrier";

    private static final int PROCESS_A = 1;
    private static final int PROCESS_B = 2;
    private static final int PROCESS_C = 3;

    /**
     * 在这里把单例转成IBinder传输到其他进程
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /**
         * if (主进程) {
         *     dest.writeInt(mainProcessCode);
         *     dest.writeStrongBinder(主进程单例1);
         *     dest.writeStrongBinder(主进程单例2);
         *     dest.writeStrongBinder(主进程单例3);
         * }
         *
         * if (其他进程A) {
         *     dest.writeInt(processACode);
         *     dest.writeStrongBinder(进程A单例1);
         *     dest.writeStrongBinder(进程A单例2);
         *     dest.writeStrongBinder(进程A单例3);
         * }
         *
         * if (其他进程) {
         *     dest.writeInt(processBCode);
         *     dest.writeStrongBinder(进程B单例1);
         *     dest.writeStrongBinder(进程B单例2);
         *     dest.writeStrongBinder(进程B单例3);
         * }
         */

        if (ProcessUtils.isProcessA()) {
            dest.writeInt(PROCESS_A);
            dest.writeStrongInterface(SingletonAImp.getInstance());
            Log.i(TAG, String.format("[write][PROCESS_A][processCode=%s]", PROCESS_A));
        }else if (ProcessUtils.isProcessB()) {
            dest.writeInt(PROCESS_B);
            dest.writeStrongInterface(SingletonBImp.getInstance());
            Log.i(TAG, String.format("[write][PROCESS_B][processCode=%s]", PROCESS_B));
        }else if (ProcessUtils.isProcessC()) {
            dest.writeInt(PROCESS_C);
            dest.writeStrongInterface(SingletonCImp.getInstance());
            Log.i(TAG, String.format("[write][PROCESS_C][processCode=%s]", PROCESS_C));
        }
    }

    /**
     * 在这里把跨进程传递过来的IBinder赋值给对应的实例
     * @param in
     */
    protected InstanceCarrier(Parcel in) {

        int processCode = in.readInt();

        /**
         * switch(processCode) {
         *     case mainProcessCode:
         *         主进程单例1.INSTANCE = 主进程单例1的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         主进程单例2.INSTANCE = 主进程单例2的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         主进程单例3.INSTANCE = 主进程单例3的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         break;
         *     case processACode:
         *         进程A单例1.INSTANCE = 进程A单例1的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         进程A单例2.INSTANCE = 进程A单例2的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         进程A单例3.INSTANCE = 进程A单例3的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         break;
         *     case processBCode:
         *         进程B单例1.INSTANCE = 进程B单例1的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         进程B单例2.INSTANCE = 进程B单例2的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         进程B单例3.INSTANCE = 进程B单例3的AIDL生成类.Stub.asInterface(in.readStrongBinder());
         *         break;
         *     default:
         *         return;
         * }
         */

        switch (processCode) {
            case PROCESS_A:
                SingletonAImp.INSTANCE = SingletonA.Stub.asInterface(in.readStrongBinder());
                Log.i(TAG, String.format("[read][PROCESS_A][processCode=%s]", processCode));
                break;
            case PROCESS_B:
                SingletonBImp.INSTANCE = SingletonB.Stub.asInterface(in.readStrongBinder());
                Log.i(TAG, String.format("[read][PROCESS_B][processCode=%s]", processCode));
                break;
            case PROCESS_C:
                SingletonCImp.INSTANCE = SingletonC.Stub.asInterface(in.readStrongBinder());
                Log.i(TAG, String.format("[read][PROCESS_C][processCode=%s]", processCode));
                break;
            default:
                Log.w(TAG, String.format("[unknown][processCode=%s]", processCode));
        }
    }

    public InstanceCarrier() {}

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InstanceCarrier> CREATOR = new Creator<InstanceCarrier>() {
        @Override
        public InstanceCarrier createFromParcel(Parcel in) {
            return new InstanceCarrier(in);
        }

        @Override
        public InstanceCarrier[] newArray(int size) {
            return new InstanceCarrier[size];
        }
    };
}
