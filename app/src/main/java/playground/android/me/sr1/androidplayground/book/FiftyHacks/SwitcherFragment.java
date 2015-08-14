package playground.android.me.sr1.androidplayground.book.FiftyHacks;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Random;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.utils.DataHelper;

public class SwitcherFragment extends Fragment {

    private ArrayList<DataHelper.MovieData> mMovieList;

    public static SwitcherFragment newInstance(String param1, String param2) {
        SwitcherFragment fragment = new SwitcherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SwitcherFragment() {
    }

    volatile boolean mIsEnterBackground = true;

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

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        mMovieList = DataHelper.getMovieData();
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

        Animation in = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), android.R.anim.fade_out);

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

        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);

        final Random random = new Random();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!mIsEnterBackground) {
                    DataHelper.MovieData data = mMovieList.get(random.nextInt(mMovieList.size()));
                    textSwitcher.setText(data.mTitle);
                    imageSwitcher.setImageResource(data.mPicResId);
                }
                mHandler.postDelayed(this, 3000);
            }
        });

    }
}
