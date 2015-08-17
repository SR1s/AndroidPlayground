package playground.android.me.sr1.androidplayground;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import playground.android.me.sr1.androidplayground.webview.IpcService;


/**
 * A simple {@link Fragment} subclass.
 */
public class IpcTestFragment extends Fragment {

    private static final String TAG = "IpcTestFragment";
    private static final String TITLE = "进程间通讯性能测试";

    private static final int TEST_TIMES = 1000;
    private static final int TEST_MINUTES = 5;

    TextView mResultTv;

    IIPCInterface mIpcInterface;

    public IpcTestFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(TITLE);
    }

    ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIpcInterface = IIPCInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIpcInterface = null;
        }
    };

    public void sendMessage() {
        Log.d(TAG, "sendMessage");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add("绑定服务").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getActivity().bindService(new Intent(getActivity(), IpcService.class), mConn, Context.BIND_AUTO_CREATE);
                return true;
            }
        });
        menu.add("解绑服务").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getActivity().unbindService(mConn);
                mIpcInterface = null;
                return true;
            }
        });

        final String title0 = "进程内方法调用";
        menu.add(title0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title0, new Runnable() {
                    @Override
                    public void run() {
                        sendMessage();
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });
        final String title1 = "进程间方法调用";
        menu.add(title1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title1, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.sendMessage();
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });
        final String title2 = "进程间方法调用(带返回值)";
        menu.add(title2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title2, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.getMessage();
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });
        final String title3 = "进程间方法调用(带返回值列表)";
        menu.add(title3).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title3, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.getMessageList();
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });
        final String title4 = "进程间方法调用(传参)";
        menu.add(title4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title4, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.getResultWithParams(TAG);
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });
        final String title5 = "进程间方法调用(20ms耗时操作)";
        menu.add(title5).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title5, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.getResultWithDelay(20);
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });
        final String title6 = "进程间方法调用(回调获得返回值20ms耗时)";
        menu.add(title6).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new MyAsyncTask(title6, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Object obj = new Object();
                            mIpcInterface.getResultViaCallback(new ICallback.Stub() {
                                @Override
                                public void onResultBack(String result) throws RemoteException {
                                    synchronized (obj) {
                                        obj.notify();
                                    }
                                }
                            });
                            synchronized (obj) {
                                try {
                                    obj.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }).execute(TEST_TIMES);
                return true;
            }
        });

        final AtomicInteger getMessageWithDelayCount = new AtomicInteger(0);
        final String title7 = "5分钟吞吐量(20ms耗时进程间调用获取返回值)";
        menu.add(title7).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new OutputsAsyncTask(title7, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.getResultWithDelay(20);
                            getMessageWithDelayCount.incrementAndGet();
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }, getMessageWithDelayCount).execute(TEST_MINUTES);
                return true;
            }
        });

        final String title8 = "5分钟吞吐量(20ms耗时进程间调用，通过回调获取返回值)";
        final AtomicInteger getMessageViaCallback = new AtomicInteger(0);
        menu.add(title8).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new OutputsAsyncTask(title8, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIpcInterface.getResultViaCallback(new ICallback.Stub() {
                                @Override
                                public void onResultBack(String result) throws RemoteException {
                                    getMessageViaCallback.incrementAndGet();
                                }
                            });
                        } catch (RemoteException e) {
                            Log.e(TAG, RemoteException.class.getSimpleName(), e);
                        }
                    }
                }, getMessageViaCallback).execute(TEST_MINUTES);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ipc_test, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mResultTv = (TextView) view.findViewById(R.id.result);
    }


    public class MyAsyncTask extends AsyncTask<Integer, Double, Double> {

        private String mTag;
        private Runnable mAction;
        private int mTimes;

        public MyAsyncTask(String tag, Runnable action) {
            mTag = tag;
            mAction = action;
        }

        @Override
        protected Double doInBackground(Integer... params) {

            mTimes = params[0];
            int totalCost = 0;

            for (int i = 0; i < mTimes; i++) {
                long start = System.currentTimeMillis();

                mAction.run();

                totalCost += (System.currentTimeMillis() - start);
            }

            return totalCost / ((double) (mTimes));
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            String earlyResult = String.valueOf(mResultTv.getText());
            mResultTv.setText(String.format("%s\n* 类型: %s \n  调用次数: %d \n  平均耗时: %.2f 毫秒\n", earlyResult, mTag, mTimes, aDouble));
        }
    }

    public class OutputsAsyncTask extends AsyncTask<Integer, Double, Integer> {

        private String mTag;
        private Runnable mAction;
        AtomicInteger mFinishCount;

        public OutputsAsyncTask(String tag, Runnable action, AtomicInteger finishCount) {
            mTag = tag;
            mAction = action;
            mFinishCount = finishCount;
        }

        int mMinus;

        @Override
        protected Integer doInBackground(Integer... params) {

            mMinus = params[0];
            int issueCount = 0;
            long end = System.currentTimeMillis() + mMinus * 1000 * 60;
            while (System.currentTimeMillis() < end) {
                issueCount++;
                mAction.run();
            }
            return issueCount;
        }

        @Override
        protected void onPostExecute(Integer issueCount) {
            super.onPostExecute(issueCount);
            String earlyResult = String.valueOf(mResultTv.getText());
            mResultTv.setText(String.format("%s\n* 类型: %s \n  时长: %d 分钟 \n  发起请求数: %d, 完成请求数: %d\n", earlyResult, mTag, mMinus, issueCount, mFinishCount.get()));
        }
    }
}
