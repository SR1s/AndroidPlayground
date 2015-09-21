package playground.android.me.sr1.androidplayground.course;

import android.support.annotation.NonNull;

import playground.android.me.sr1.androidplayground.IntroduceFragment;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.course.uda_city_material_design.UdaCityMaterialDesignActivity;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;

/**
 * 课程列表
 * Created by sr1 on 15/9/21.
 */
public class CourseIndexActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @NonNull
    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("简介", new IntroduceFragment()),
                new DrawerItem("UDACity: Material Design", UdaCityMaterialDesignActivity.class)
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }
}
