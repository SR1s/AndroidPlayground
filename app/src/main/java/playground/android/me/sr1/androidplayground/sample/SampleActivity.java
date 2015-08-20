package playground.android.me.sr1.androidplayground.sample;

import playground.android.me.sr1.androidplayground.IpcTestFragment;
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
 * Created by sr1 on 15/8/17.
 */
public class SampleActivity extends DrawerActivity<SampleActivity.ItemInfo> {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @Override
    protected ItemInfo[] getDrawerItemData() {
        return new ItemInfo[]{

                new ItemInfo("主界面", MainActivity.class),
                new ItemInfo("RecycleView+SwipeRefleshLayout", RefleshLayoutFragment.class),
                new ItemInfo("ViewPager", ViewPagerFragment.class),
                new ItemInfo("webview", BaseWebViewFragment.class),
                new ItemInfo("TextSwitcher", SwitcherFragment.class),
                new ItemInfo("Notification", NotificationFragment.class),
                new ItemInfo("Service", ServiceFragment.class),
                new ItemInfo("进程间通讯测试", IpcTestFragment.class)
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }

    public static class ItemInfo {
        Class mDest;
        String mTitle;

        public ItemInfo(String title, Class cls) {
            this.mDest = cls;
            this.mTitle = title;
        }

        public String toString() {
            return mTitle;
        }
    }
}
