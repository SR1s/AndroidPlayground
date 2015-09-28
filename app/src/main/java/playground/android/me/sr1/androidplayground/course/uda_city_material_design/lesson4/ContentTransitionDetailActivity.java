package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.AnimationUtils;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.utils.ChapterFragment;
import playground.android.me.sr1.androidplayground.toolbox.utils.DataHelper;

/**
 * ContentTransition示例的跳转目的Activity
 * Created by sr1 on 15/9/25.
 */
public class ContentTransitionDetailActivity extends AppCompatActivity {

    public static final String TAG = "CttTranDetailAct";

    public static final String KEY_ENABLE_DEFAULT_EFFECT = "KEY_ENABLE_DEFAULT_EFFECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Boolean isUseDefaultEffect = false;
        if (intent != null) {
            isUseDefaultEffect = intent.getBooleanExtra(KEY_ENABLE_DEFAULT_EFFECT, false);
        }


        DataHelper.ChapterData data = DataHelper.getChapterData().get(0);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content,
                ChapterFragment.newInstance(data.mTitle, data.mDate, data.mContent, data.mImageId));
        fragmentTransaction.commit();

        Log.i(TAG, String.format("[Use dafault enter effect ? %b]", isUseDefaultEffect));
        if (isUseDefaultEffect) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Slide slide = new Slide(Gravity.BOTTOM);
                slide.addTarget(R.id.content);
                slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
                slide.setDuration(300);
                getWindow().setEnterTransition(slide);
            }
        }
    }
}
