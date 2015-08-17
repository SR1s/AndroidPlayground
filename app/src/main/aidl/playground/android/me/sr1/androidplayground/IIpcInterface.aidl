// IIPCInterface.aidl
package playground.android.me.sr1.androidplayground;

import playground.android.me.sr1.androidplayground.ICallback;

interface IIPCInterface {

    void sendMessage();

    String getMessage();

    List<String> getMessageList();

    String getResultWithParams(String information);

    String getResultWithDelay(int delay);

    void getResultViaCallback(ICallback callback);
}