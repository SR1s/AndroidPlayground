package playground.android.me.sr1.androidplayground.demos.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import playground.android.me.sr1.androidplayground.R;

/**
 * Created by srluo on 2015/9/10.
 */
public class ServiceDemoFragment extends Fragment {

    Button mStart, mStop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demos_service, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStart = (Button) view.findViewById(R.id.demos_service_start_service);
        mStop = (Button) view.findViewById(R.id.demos_service_stop_service);

        View.OnClickListener lstner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.demos_service_start_service:
                        startService();
                        break;
                    case R.id.demos_service_stop_service:
                        stopService();
                        break;
                }
            }
        };

        mStart.setOnClickListener(lstner);
        mStop.setOnClickListener(lstner);
    }

    private void startService() {
        Context ctx = getActivity().getApplication();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainProcessService.class);
        ctx.startService(intent);
    }

    private void stopService() {
        Context ctx = getActivity().getApplication();
        Intent intent = new Intent(getActivity().getApplicationContext(), MainProcessService.class);
        ctx.stopService(intent);
    }
}
