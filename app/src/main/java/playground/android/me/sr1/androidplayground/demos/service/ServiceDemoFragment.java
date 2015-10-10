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

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;

/**
 * Created by srluo on 2015/9/10.
 */
public class ServiceDemoFragment extends Fragment {

    Button mStart, mStop, mStartIntentFilterServiceWithAction, mStartIntentFilterServiceWithoutAction, mStopIntentFilterSerice;

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
        mStartIntentFilterServiceWithAction = (Button) view.findViewById(R.id.demos_service_start_intent_filter_service_with_action);
        mStartIntentFilterServiceWithoutAction = (Button) view.findViewById(R.id.demos_service_start_intent_filter_service_without_action);
        mStopIntentFilterSerice = (Button) view.findViewById(R.id.demos_service_stop_intent_filter_service);

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
                    case R.id.demos_service_start_intent_filter_service_with_action:
                        startIntentFilterServiceWithAction();
                        break;
                    case R.id.demos_service_start_intent_filter_service_without_action:
                        startIntentFilterServiceWithoutAction();
                        break;
                    case R.id.demos_service_stop_intent_filter_service:
                        stopIntentFilterSerice();
                        break;
                }
            }
        };

        mStart.setOnClickListener(lstner);
        mStop.setOnClickListener(lstner);
        mStartIntentFilterServiceWithAction.setOnClickListener(lstner);
        mStartIntentFilterServiceWithoutAction.setOnClickListener(lstner);
        mStopIntentFilterSerice.setOnClickListener(lstner);
    }

    private void startService() {
        Context ctx = App.getContext();
        Intent intent = new Intent(ctx, MainProcessService.class);
        ctx.startService(intent);
    }

    private void stopService() {
        Context ctx = App.getContext();
        Intent intent = new Intent(ctx, MainProcessService.class);
        ctx.stopService(intent);
    }

    private void startIntentFilterServiceWithAction() {
        Context ctx = App.getContext();
        Intent intent = new Intent(getString(R.string.action_main_service_with_intent_filter));
        intent.setPackage(ctx.getPackageName());
        ctx.startService(intent);
    }

    private void startIntentFilterServiceWithoutAction() {
        Context ctx = App.getContext();
        Intent intent = new Intent(ctx, MainProcessServiceWithIntentFilter.class);
        ctx.startService(intent);
    }

    private void stopIntentFilterSerice() {
        Context ctx = App.getContext();
        Intent intent = new Intent(ctx, MainProcessServiceWithIntentFilter.class);
        ctx.stopService(intent);
    }
}
