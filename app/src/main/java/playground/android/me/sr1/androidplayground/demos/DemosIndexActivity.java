package playground.android.me.sr1.androidplayground.demos;

import android.support.annotation.NonNull;

import playground.android.me.sr1.androidplayground.IntroduceFragment;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.demos.service.ServiceDemoFragment;
import playground.android.me.sr1.androidplayground.demos.transition.TransitionMainActivity;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;

/**
 * Demos的列表
 * Created by sr1 on 15/8/25.
 */
public class DemosIndexActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @Override
    @NonNull
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("Introduce", new IntroduceFragment()),
                new DrawerItem("Notification相关", new NotificationDemoFragment()),
                new DrawerItem("Service相关", new ServiceDemoFragment()),
                new DrawerItem("Transition 动画分享", TransitionMainActivity.class)
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }
}
