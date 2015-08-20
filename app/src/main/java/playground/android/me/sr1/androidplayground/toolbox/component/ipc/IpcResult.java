package playground.android.me.sr1.androidplayground.toolbox.component.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sr1 on 15/8/19.
 */
public class IpcResult<DataType extends Parcelable> implements Parcelable {

    private DataType mData;

    public IpcResult() {
        this.mData = null;
    }

    public IpcResult(DataType data) {
        this.mData = data;
    }

    protected IpcResult(Parcel in) {
        mData = in.readParcelable(mData.getClass().getClassLoader());
    }

    public static final Creator<IpcResult> CREATOR = new Creator<IpcResult>() {
        @Override
        public IpcResult createFromParcel(Parcel in) {
            return new IpcResult(in);
        }

        @Override
        public IpcResult[] newArray(int size) {
            return new IpcResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mData != null) {
            mData.writeToParcel(dest, flags);
        }
    }

    public void setData(DataType data) {
        this.mData = data;
    }

    public DataType getData() {
        return mData;
    }

}
