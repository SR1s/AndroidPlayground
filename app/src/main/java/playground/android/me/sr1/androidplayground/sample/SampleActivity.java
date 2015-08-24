package playground.android.me.sr1.androidplayground.sample;

import playground.android.me.sr1.androidplayground.IntroduceFragment;
import playground.android.me.sr1.androidplayground.MainActivity;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.RefleshLayout.RefleshLayoutFragment;
import playground.android.me.sr1.androidplayground.ViewPager.ViewPagerFragment;
import playground.android.me.sr1.androidplayground.book.FiftyHacks.SwitcherFragment;
import playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Media.NotificationFragment;
import playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Service.ServiceFragment;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;
import playground.android.me.sr1.androidplayground.webview.BaseWebViewFragment;

/**
 * 侧边栏工具类的样例程序
 *
 * Created by sr1 on 15/8/17.
 */
public class SampleActivity extends DrawerActivity<DrawerActivity.DrawerItem> {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[]{

                new DrawerItem("主界面", MainActivity.class),
                new DrawerItem("介绍页面", new IntroduceFragment()),
                new DrawerItem("RecycleView+SwipeRefleshLayout", new RefleshLayoutFragment()),
                new DrawerItem("ViewPager", new ViewPagerFragment()),
                new DrawerItem("webview", new BaseWebViewFragment()),
                new DrawerItem("TextSwitcher", new SwitcherFragment()),
                new DrawerItem("Notification", new NotificationFragment()),
                new DrawerItem("Service", new ServiceFragment()),
                new DrawerItem("侧边栏工具类样例", SampleActivity.class)
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }
}
