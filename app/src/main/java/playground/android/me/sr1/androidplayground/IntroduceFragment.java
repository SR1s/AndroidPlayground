package playground.android.me.sr1.androidplayground;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class IntroduceFragment extends Fragment {

    public static IntroduceFragment newInstance() {
        IntroduceFragment fragment = new IntroduceFragment();
        return fragment;
    }

    public IntroduceFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_introduce, container, false);
    }
}
