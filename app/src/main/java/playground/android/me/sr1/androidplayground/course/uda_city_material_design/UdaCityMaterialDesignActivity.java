package playground.android.me.sr1.androidplayground.course.uda_city_material_design;

import android.support.annotation.NonNull;

import playground.android.me.sr1.androidplayground.IntroduceFragment;
import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4.ContentTransitionActiviry;
import playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4.MyShapeTransitionActivity;
import playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4.ScenceFragment;
import playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4.TwoScenceFragment;
import playground.android.me.sr1.androidplayground.toolbox.component.activity.DrawerActivity;

/**
 * UdaCity: Material Design for Android Developers <br/>
 * Url: https://www.udacity.com/course/progress#!/c-ud862 <br/>
 *
 * Created by sr1 on 15/9/21.
 */
public class UdaCityMaterialDesignActivity extends DrawerActivity {
    @Override
    protected int getListItemLayout() {
        return R.layout.list_item;
    }

    @NonNull
    @Override
    protected DrawerItem[] getDrawerItemData() {
        return new DrawerItem[] {
                new DrawerItem("课程简介", new IntroduceFragment()),
                new DrawerItem("Lesson 4: Scence", new ScenceFragment()),
                new DrawerItem("Lesson 4: Transition between Scence", new TwoScenceFragment()),
                new DrawerItem("Lesson 4: Content Transition", ContentTransitionActiviry.class),
                new DrawerItem("Lesson 4: My Shape Transition", MyShapeTransitionActivity.class)
        };
    }

    @Override
    protected int getDrawerHeaderResId() {
        return R.layout.fragment_introduce;
    }
}
