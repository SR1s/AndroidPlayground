package playground.android.me.sr1.androidplayground.book.FiftyHacks;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;

/**
 * Created by sr1 on 15/8/23.
 */
public class FiftyHackIndexActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("Hack 5: Switcher", new SwitcherFragment())
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.drawer_header_fifty_hacks;
    }
}
