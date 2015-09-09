package playground.android.me.sr1.androidplayground.demos;

import playground.android.me.sr1.androidplayground.IntroduceFragment;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;

/**
 * Created by sr1 on 15/8/25.
 */
public class DemosIndexActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("Introduce", new IntroduceFragment()),
                new DrawerItem("Notification相关", new NotificationDemoFragment())
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }
}
