package playground.android.me.sr1.androidplayground.toolbox.component.mutilprocess.singleton;

import android.os.RemoteException;

/**
 * 用于传输单例(代理)实例的AIDL接口实现
 * @author SR1s
 */
public class InstanceTransferImp extends InstanceTransfer.Stub {
    @Override
    public InstanceCarrier transfer() throws RemoteException {
        return new InstanceCarrier();
    }
}
