package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import playground.android.me.sr1.androidplayground.toolbox.utils.ChapterFragment;
import playground.android.me.sr1.androidplayground.toolbox.utils.DataHelper;

/**
 * ContentTransition示例的跳转目的Activity
 * Created by sr1 on 15/9/25.
 */
public class ContentTransitionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataHelper.ChapterData data = DataHelper.getChapterData().get(0);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content,
                ChapterFragment.newInstance(data.mTitle, data.mDate, data.mContent, data.mImageId));
        fragmentTransaction.commit();
    }
}
