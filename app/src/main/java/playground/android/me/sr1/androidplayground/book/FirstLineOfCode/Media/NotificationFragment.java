package playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Media;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;

import playground.android.me.sr1.androidplayground.MainActivity;
import playground.android.me.sr1.androidplayground.R;

public class NotificationFragment extends Fragment {

    private Button mNotificationBtn;
    private Button mIntentNotification;
    private Button mSoundNotification;
    private Button mVibareNotification;
    private Button mLedNotification;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }

    public NotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNotificationBtn = (Button) view.findViewById(R.id.notification);
        mIntentNotification = (Button) view.findViewById(R.id.notification_with_intent);
        mSoundNotification = (Button) view.findViewById(R.id.notification_with_sound);
        mVibareNotification = (Button) view.findViewById(R.id.notification_with_vibare);
        mLedNotification = (Button) view.findViewById(R.id.notification_with_led);

        mNotificationBtn.setOnClickListener(listener);
        mIntentNotification.setOnClickListener(listener);
        mSoundNotification.setOnClickListener(listener);
        mVibareNotification.setOnClickListener(listener);
        mLedNotification.setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            final Notification notification;
            switch (v.getId()) {
                case R.id.notification:
                    notification = new Notification(R.drawable.logo, "1 from AndroidPlayground", System.currentTimeMillis());
                    notification.setLatestEventInfo(getActivity().getApplicationContext(), "1 from AndroidPlayground", "1 from AndroidPlayground", null);
                    notificationManager.notify(1, notification);
                    break;
                case R.id.notification_with_intent:
                    notification = new Notification(R.drawable.logo, "2 from AndroidPlayground", System.currentTimeMillis());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    notification.setLatestEventInfo(getActivity().getApplicationContext(), "2 from AndroidPlayground", "2 from AndroidPlayground", pi);
                    notificationManager.notify(2, notification);
                    break;
                case R.id.notification_with_sound:
                    notification = new Notification(R.drawable.logo, "3 from AndroidPlayground", System.currentTimeMillis());
                    notification.setLatestEventInfo(getActivity().getApplicationContext(), "3 from AndroidPlayground", "3 from AndroidPlayground", null);
                    notification.sound = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
                    notificationManager.notify(3, notification);
                    break;
                case R.id.notification_with_vibare:
                    notification = new Notification(R.drawable.logo, "4 from AndroidPlayground", System.currentTimeMillis());
                    notification.setLatestEventInfo(getActivity().getApplicationContext(), "4 from AndroidPlayground", "4 from AndroidPlayground", null);
                    notification.vibrate = new long[] {1000, 1000, 2000, 2000, 3000, 3000};
                    notificationManager.notify(4, notification);
                    break;
                case R.id.notification_with_led:
                    notification = new Notification(R.drawable.logo, "5 from AndroidPlayground", System.currentTimeMillis());
                    notification.setLatestEventInfo(getActivity().getApplicationContext(), "5 from AndroidPlayground", "5 from AndroidPlayground", null);
                    notification.ledARGB = Color.BLUE;
                    notification.ledOffMS = 3000;
                    notification.ledOffMS = 1000;
                    notification.flags = Notification.FLAG_SHOW_LIGHTS;
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            notificationManager.notify(5, notification);
                        }
                    }, 5000);
                    break;
                default:
                    break;
            }
        }
    };
}
