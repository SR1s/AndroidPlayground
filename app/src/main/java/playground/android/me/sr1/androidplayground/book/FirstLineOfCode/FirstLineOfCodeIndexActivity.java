package playground.android.me.sr1.androidplayground.book.FirstLineOfCode;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Media.NotificationFragment;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;
import playground.android.me.sr1.androidplayground.webview.BaseWebViewFragment;

/**
 * Created by sr1 on 15/8/23.
 */
public class FirstLineOfCodeIndexActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("8. 手机多媒体: Notification", new NotificationFragment()),
                new DrawerItem("10. 网络技术: WebView", new BaseWebViewFragment())
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.drawer_header_first_line_of_code;
    }
}
