package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
    public static final String KEY_CHAPTER_TITLE = "KEY_CHAPTER_TITLE";
    public static final String KEY_CHAPTER_DATE = "KEY_CHAPTER_DATE";
    public static final String KEY_CHAPTER_CONTENT = "KEY_CHAPTER_CONTENT";
    public static final String KEY_CHAPTER_IMAGE_ID = "KEY_CHAPTER_IMAGE_ID";

    DataHelper.ChapterData mChapterData = DataHelper.getChapterData().get(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Boolean isUseDefaultEffect = false;
        if (intent != null) {
            isUseDefaultEffect = intent.getBooleanExtra(KEY_ENABLE_DEFAULT_EFFECT, false);

            String title = intent.getStringExtra(KEY_CHAPTER_TITLE);
            String date = intent.getStringExtra(KEY_CHAPTER_DATE);
            String content = intent.getStringExtra(KEY_CHAPTER_CONTENT);
            int imageId = intent.getIntExtra(KEY_CHAPTER_IMAGE_ID, Integer.MIN_VALUE);

            title = TextUtils.isEmpty(title) ? mChapterData.mTitle : title;
            date = TextUtils.isEmpty(date) ? mChapterData.mDate : date;
            content = TextUtils.isEmpty(content) ? mChapterData.mContent : content;
            imageId = imageId == Integer.MIN_VALUE ? mChapterData.mImageId : imageId;

            Log.i(TAG, String.format("[Chapter Data: title=%s, date=%s, imageId=%d, content: %15s]", title, date, imageId, content));
            mChapterData = new DataHelper.ChapterData(title, date, content, imageId);
        }

        DataHelper.ChapterData data = DataHelper.getChapterData().get(0);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(android.R.id.content,
                ChapterFragment.newInstance(mChapterData.mTitle, mChapterData.mDate, mChapterData.mContent, mChapterData.mImageId));
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
