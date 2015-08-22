package playground.android.me.sr1.androidplayground.toolbox.component.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.awt.font.TextAttribute;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.utils.TimeUtils;

/**
 * 参考大部分具有侧边栏的App(魅族商店Top30)的交互
 * 这个侧边栏抽象类的逻辑是：
 * 如果要侧边栏点击后打开的是Fragment，则将主页面的内容换为那个Fragment，然后自动关闭侧边栏
 * 如果要侧边栏点击后打开的是Activity，则跳转到那个Activity，侧边栏维持打开的状态
 * 在界面上点击返回按钮，进行一次拦截，提示再次点击返回关闭，如果1s 时间内再次点击，则关闭Acitivy
 * 通过setIsInterruptBackPressed为true，取消拦截
 * <p/>
 * Created by sr1 on 15/8/17.
 */
public abstract class DrawerActivity<DataType extends DrawerActivity.DrawerItem> extends AppCompatActivity {

    public static final String TAG = "DrawerActivity";

    protected static final int UI_NO_LAYOUT = 0;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mContainerLayout;
    private ListView mDrawerList;

    // 是否对点击返回按钮进行一次拦截
    private boolean mIsInterruptBackPressed = true;
    // 抽屉的列表的Item 布局Id
    private int mListItemLayoutResId;
    // 列表头部的布局id
    private int mDrawerHeaderResId;
    // 列表项数据
    private DataType[] mData;
    // 上一次点击的返回键的时间(拦截的时候进行记录，关闭侧边栏的时候不记录)
    private long mLastBackPressedTime = 0;
    // 两次点击的间隔
    private long mInterruptInterval = TimeUtils.ONE_MINUTE * 2;
    // 当前主页面对应的侧边栏的item位置，如果没有指定fragment的话，默认是第一个fragment
    private int mCurrentItemPosition = -1;
    // 第一个要显示的fragment在列表里的位置
    private int mFirstShowFragmentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utils_ui_activity_drawer);
        initWidget();
        gettingConfigure();
        settingWidget();
        showFirstFragment();
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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.utils_drawer_layout);
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

    /**
     * 设置各个控件
     */
    private void settingWidget() {
        if (mDrawerHeaderResId != 0) {
            mDrawerList.addHeaderView(getLayoutInflater().inflate(mDrawerHeaderResId, null));
        }

        mDrawerList.setAdapter(new ArrayAdapter<DataType>(DrawerActivity.this, R.layout.list_item, R.id.item_title, mData));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (getDrawerHeaderResId() != UI_NO_LAYOUT) {
                    // 如果ListView有Header的话，Header也作为一个Item
                    // 但在这里我们不把它当作一个Item，因此position的值要减去1
                    position -= 1;
                }

                if (mCurrentItemPosition == position) {
                    // 当前项目是被点击的那个，不需要跳转，关闭侧边栏即可
                    mDrawerLayout.closeDrawer(mDrawerList);
                    return;
                }

                DataType data = mData[position];
                if (data.mDest instanceof Class) {
                    // 如果打开的是Activity，则维持侧边栏打开状态，不改变显示区域，所以标记位置不需要改动
                    Intent intent = new Intent(getApplicationContext(), (Class) data.mDest);
                    startActivity(intent);
                } else if (data.mDest instanceof Fragment) {
                    // 如果打开的是Fragment，则关闭侧边栏，将显示区域换为那个Fragment，更新标记位置
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.utils_drawer_container, (Fragment) data.mDest);
                    transaction.commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                    mCurrentItemPosition = position;
                }
            }
        });
    }

    /**
     * 展示第一个fragment
     */
    private void showFirstFragment() {
        Fragment firstFragment = null;
        if (mFirstShowFragmentPosition < mData.length && mFirstShowFragmentPosition > 0) {
            // 位置有效的话，看下那个位置的是不是Fragment，不然的话不理了
            DataType data = mData[mFirstShowFragmentPosition];
            if (data.mDest instanceof Fragment) {
                firstFragment = (Fragment) data.mDest;
                mCurrentItemPosition = mFirstShowFragmentPosition;
            } else {
                Log.w(TAG, String.format("[mDest is not a fragment] [firstShowFragmentPosition: %d]", mFirstShowFragmentPosition));
            }
        } else {
            // 位置无效的话，查找第一个fragment
            for (int i = 0; i < mData.length; i++) {
                if (mData[i].mDest instanceof Fragment) {
                    firstFragment = (Fragment) mData[i].mDest;
                    mCurrentItemPosition = i;
                    break;
                }
            }
        }

        if (firstFragment != null) {
            // 有fragment能显示就显示出来
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.utils_drawer_container, firstFragment);
            transaction.commit();
        } else {
            Log.w(TAG, String.format("[no valid fragment] [firstShowFragmentPosition: %d]", mFirstShowFragmentPosition));
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            if (mIsInterruptBackPressed) {
                long time = System.currentTimeMillis();
                if (time - mLastBackPressedTime < mInterruptInterval) {
                    super.onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.utils_double_press_back_to_close, Toast.LENGTH_SHORT).show();
                    mLastBackPressedTime = time;
                }
            }
        }
    }

    /**
     * 设置是否进行在关闭Activity的时候进行一次按返回键的拦截
     *
     * @param isInterrupt 是否拦截
     */
    protected void setIsInterruptBackPressed(boolean isInterrupt) {
        mIsInterruptBackPressed = isInterrupt;
    }

    protected abstract int getListItemLayout();

    protected int getDrawerHeaderResId() {
        return UI_NO_LAYOUT;
    }

    protected abstract DataType[] getDrawerItemData();

    public static class DrawerItem {
        // 要么是Fragment的实例，要么是Activity的Class类
        public Object mDest;
        public String mTitle;

        public DrawerItem(@NonNull String title, @NonNull Object dest) {
            this.mTitle = title;
            this.mDest = dest;
        }

        @Override
        public String toString() {
            return mTitle;
        }
    }

    /**
     * 覆盖此方法提供第一次打开界面时显示的fragment在列表里的位置，
     * 否则将会选取列表里第一个fragment作为第一个
     *
     * @return
     */
    public void setFirstShowFragmentPosition(int position) {
        mFirstShowFragmentPosition = position;
    }
}
