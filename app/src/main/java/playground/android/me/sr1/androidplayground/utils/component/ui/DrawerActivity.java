package playground.android.me.sr1.androidplayground.utils.component.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import playground.android.me.sr1.androidplayground.R;

/**
 * Created by sr1 on 15/8/17.
 */
public abstract class DrawerActivity<DataType> extends Activity {

    protected static final int UI_NO_LAYOUT = 0;

    private FrameLayout mContainerLayout;
    private ListView mDrawerList;
    // 抽屉的列表的Item 布局Id
    private int mListItemLayoutResId;
    // 列表头部的布局id
    private int mDrawerHeaderResId;
    // 列表项数据
    private DataType[] mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utils_ui_activity_drawer);
        initWidget();
        gettingConfigure();
        settingWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 取得控件的引用
     */
    private void initWidget() {
        mContainerLayout = (FrameLayout) findViewById(R.id.utils_drawer_container);
        mDrawerList = (ListView) findViewById(R.id.utils_drawer_slider);
    }

    /**
     * 获取各项配置
     */
    private void gettingConfigure() {
        mListItemLayoutResId = getListItemLayout();
        mDrawerHeaderResId = getDrawerHeaderResId();
        mData = getDrawerItemData();
    }

    private void settingWidget() {
        if (mDrawerHeaderResId != 0) {
            mDrawerList.addHeaderView(getLayoutInflater().inflate(mDrawerHeaderResId, null));
        }

        mDrawerList.setAdapter(new ArrayAdapter<DataType>(DrawerActivity.this, R.layout.list_item, R.id.item_title, mData));
    }

    protected abstract int getListItemLayout();

    protected int getDrawerHeaderResId() {
        return UI_NO_LAYOUT;
    }

    protected abstract DataType[] getDrawerItemData();
}
