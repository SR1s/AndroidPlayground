package playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.utils.DataHelper;

public class ServiceFragment extends Fragment {

    public static final String TAG = "ServiceFragment";

    public static ServiceFragment newInstance(String param1, String param2) {
        ServiceFragment fragment = new ServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    volatile MyService.MyBinder mBinder;
    volatile boolean mIsEnterBackground = true;

    public ServiceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mBinder = (MyService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mBinder = null;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.add("启动服务").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getActivity(), MyService.class);
                getActivity().startService(intent);
                return true;
            }
        });
        menu.add("绑定服务").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent bindIntent = new Intent(getActivity(), MyService.class);
                getActivity().bindService(bindIntent, connection, Context.BIND_AUTO_CREATE);
                return true;
            }
        });
        menu.add("断开服务").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getActivity().unbindService(connection);
                mBinder = null;
                return true;
            }
        });
        menu.add("停止服务").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getActivity(), MyService.class);
                getActivity().stopService(intent);
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_switcher, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextSwitcher textSwitcher = (TextSwitcher) view.findViewById(R.id.textSwitcher);
        final ImageSwitcher imageSwitcher = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);

        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);

        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);

        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return getActivity().getLayoutInflater().inflate(R.layout.switch_text, null);
            }
        });

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return getActivity().getLayoutInflater().inflate(R.layout.switch_image, null);
            }
        });

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!mIsEnterBackground) {
                    if (mBinder != null) {
                        if (!mIsEnterBackground) {
                            DataHelper.MovieData data = mBinder.getMovieData();
                            textSwitcher.setText(data.mTitle);
                            imageSwitcher.setImageResource(data.mPicResId);
                        }
                    } else {
                        Toast.makeText(getActivity(), "服务未连接，无法更新数据", Toast.LENGTH_SHORT).show();
                    }
                }
                handler.postDelayed(this, 3000);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsEnterBackground = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsEnterBackground = true;
    }
}
