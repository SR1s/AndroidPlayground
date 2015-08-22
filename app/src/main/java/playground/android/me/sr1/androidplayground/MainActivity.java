package playground.android.me.sr1.androidplayground;

import playground.android.me.sr1.androidplayground.book.FiftyHacks.FiftyHackIndexActivity;
import playground.android.me.sr1.androidplayground.book.FirstLineOfCode.FirstLineOfCodeIndexActivity;
import playground.android.me.sr1.androidplayground.sample.SampleActivity;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;

public class MainActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.id.list_item;
    }

    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("Introduce", new IntroduceFragment()),
                new DrawerItem("50 Android Hacks", FiftyHackIndexActivity.class),
                new DrawerItem("第一行代码", FirstLineOfCodeIndexActivity.class),
                new DrawerItem("Toolbox Demo", SampleActivity.class)
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }
}
