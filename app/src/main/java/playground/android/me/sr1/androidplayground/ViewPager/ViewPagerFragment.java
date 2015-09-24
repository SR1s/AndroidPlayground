package playground.android.me.sr1.androidplayground.ViewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.utils.ChapterFragment;
import playground.android.me.sr1.androidplayground.toolbox.utils.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {


    private ViewPager mViewPager;
    private ArrayList<DataHelper.ChapterData> mData;
    private ArrayList<Fragment> mFragments;


    public ViewPagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加了这句自定义的菜单栏才会生效
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mData = DataHelper.getChapterData();
        mFragments = new ArrayList<Fragment>();
        for (DataHelper.ChapterData data : mData) {
            mFragments.add(ChapterFragment.newInstance(data.mTitle, data.mDate, data.mContent, data.mImageId));
        }

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getActivity().setTitle(mData.get(position).mTitle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getActivity().setTitle(mData.get(0).mTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        final MenuItem defaultMu = menu.add("Default");
        final MenuItem zoomOutMu = menu.add("ZoomOut");
        final MenuItem deptMu = menu.add("Depth");

        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item == defaultMu) {
                    mViewPager.setPageTransformer(true, null);
                    return true;
                } else if (item == zoomOutMu) {
                    mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                    return true;
                } else if (item == deptMu) {
                    mViewPager.setPageTransformer(true, new DepthPageTransformer());
                    return true;
                }
                return false;
            }
        };

        defaultMu.setOnMenuItemClickListener(listener);
        zoomOutMu.setOnMenuItemClickListener(listener);
        deptMu.setOnMenuItemClickListener(listener);
    }

    public static class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
