package playground.android.me.sr1.androidplayground.demos.transition.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.utils.ChapterFragment;
import playground.android.me.sr1.androidplayground.toolbox.utils.DataHelper;

/**
 * Fragment之间的切换
 * Created by SR1 on 15/10/27.
 */
public class FragmentTransitionFragment extends Fragment {

    public static FragmentTransitionFragment newInstance(FragmentManager fragmentManager) {
        FragmentTransitionFragment fragment = new FragmentTransitionFragment();
        fragment.setFragmentManager(fragmentManager);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.END);
            fragment.setEnterTransition(slide);
            fragment.setExitTransition(slide);
            fragment.setReenterTransition(slide);
            fragment.setReturnTransition(slide);

        }
        return fragment;
    }

    FragmentManager mFragmentManager;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.activity_course_uda_material_design_l4_content_transition, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter(App.getContext(), 100));
    }


    static boolean mWithSharedElement = false;

    class GridAdapter extends BaseAdapter {

        private Context mContext;
        private int mCount;
        private int[] mImageResourceIds = DataHelper.IMAGE_RESOURCE_IDS;

        public GridAdapter(Context context, int count) {
            mCount = count;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_course_uda_material_design_l4_content_transition, null);
                View imageView = convertView.findViewById(R.id.imageView);
                convertView.setTag(imageView);
            }

            final int imageId = mImageResourceIds[position % mImageResourceIds.length];
            final DataHelper.ChapterData data = DataHelper.getChapterData().get(position % DataHelper.getChapterData().size());

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            imageView.setImageResource(imageId);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

                        if (mWithSharedElement) {
                            FragmentTransaction transaction = mFragmentManager.beginTransaction();
                            Fragment fragment = ChapterFragment.newInstance(data.mTitle, data.mDate, data.mContent, imageId);

                            Slide slide = new Slide(Gravity.END);
                            fragment.setEnterTransition(slide);
                            fragment.setReturnTransition(slide);
                            fragment.setExitTransition(slide);
                            fragment.setReenterTransition(slide);

                            transaction.addSharedElement(v, v.getTransitionName());

                            transaction.replace(R.id.container, fragment)
                                    .addToBackStack("");
                            transaction.commit();

                        } else {
                            FragmentTransaction transaction = mFragmentManager.beginTransaction();
                            Fragment fragment = ChapterFragment.newInstance(data.mTitle, data.mDate, data.mContent, data.mImageId);

                            Slide slide = new Slide(Gravity.BOTTOM);
                            fragment.setEnterTransition(slide);
                            fragment.setReturnTransition(slide);
                            fragment.setExitTransition(slide);
                            fragment.setReenterTransition(slide);

                            transaction.replace(R.id.container, fragment)
                                    .addToBackStack("");
                            transaction.commit();

                        }
                        mWithSharedElement = !mWithSharedElement;
                    } else {
                        FragmentTransaction transaction = mFragmentManager.beginTransaction();
                        Fragment fragment = ChapterFragment.newInstance(data.mTitle, data.mDate, data.mContent, data.mImageId);

                        transaction.replace(R.id.container, fragment)
                                .addToBackStack("");
                        transaction.commit();
                    }
                }
            });

            return convertView;
        }
    }


}
