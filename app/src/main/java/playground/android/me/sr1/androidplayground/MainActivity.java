package playground.android.me.sr1.androidplayground;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import playground.android.me.sr1.androidplayground.RefleshLayout.RefleshLayoutFragment;
import playground.android.me.sr1.androidplayground.ViewPager.ViewPagerFragment;
import playground.android.me.sr1.androidplayground.book.FiftyHacks.SwitcherFragment;
import playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Media.NotificationFragment;
import playground.android.me.sr1.androidplayground.book.FirstLineOfCode.Service.ServiceFragment;
import playground.android.me.sr1.androidplayground.sample.SampleActivity;
import playground.android.me.sr1.androidplayground.webview.BaseWebViewFragment;


public class MainActivity extends AppCompatActivity {


    ItemInfo[] mData = {
            new ItemInfo("主界面", MainActivity.class, ItemInfo.TYPE_ACTIVITY),
            new ItemInfo("RecycleView+SwipeRefleshLayout", RefleshLayoutFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("ViewPager", ViewPagerFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("webview", BaseWebViewFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("TextSwitcher", SwitcherFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("Notification", NotificationFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("Service", ServiceFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("IPC Test", IpcTestFragment.class, ItemInfo.TYPE_FRAGMENT),
            new ItemInfo("Sample Activity", SampleActivity.class, ItemInfo.TYPE_ACTIVITY)
    };
    ListView mList;
    DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.main_list);
        mDrawer = (DrawerLayout) findViewById(R.id.main_drawer);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, IntroduceFragment.newInstance());
        transaction.commit();

        ListAdapter adapter = new ArrayAdapter<ItemInfo>(MainActivity.this, R.layout.list_item, R.id.item_title, mData);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemInfo data = mData[position];
                switch (data.mType) {
                    case ItemInfo.TYPE_ACTIVITY:
                    startActivity(new Intent(MainActivity.this, data.mDest));
                        break;
                    case ItemInfo.TYPE_FRAGMENT:
                        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        try {
                            Fragment fragment = (Fragment) data.mDest.newInstance();
                            transaction.replace(R.id.main_content, fragment);
                            transaction.addToBackStack(fragment.getClass().getSimpleName());
                            transaction.commit();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
                mDrawer.closeDrawers();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ItemInfo {

        public static final int TYPE_ACTIVITY = 1;
        public static final int TYPE_FRAGMENT = 2;

        String mTitle;
        int mType;
        Class mDest;

        public ItemInfo(String title, Class dest, int type) {
            this.mTitle = title;
            this.mDest = dest;
            this.mType = type;
        }

        @Override
        public String toString() {
            return mTitle;
        }
    }
}
