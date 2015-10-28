package playground.android.me.sr1.androidplayground.demos.transition;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.utils.ChapterFragment;
import playground.android.me.sr1.androidplayground.toolbox.utils.DataHelper;

/**
 * ContentTransition示例的跳转目的Activity
 * Created by sr1 on 15/9/25.
 */
public class ActivityTransitionDetailActivity extends AppCompatActivity {

    public static final String TAG = "ActTranDetail";

    public static final String KEY_CHAPTER_TITLE = "KEY_CHAPTER_TITLE";
    public static final String KEY_CHAPTER_DATE = "KEY_CHAPTER_DATE";
    public static final String KEY_CHAPTER_CONTENT = "KEY_CHAPTER_CONTENT";
    public static final String KEY_CHAPTER_IMAGE_ID = "KEY_CHAPTER_IMAGE_ID";

    DataHelper.ChapterData mChapterData = DataHelper.getChapterData().get(0);

    TextView mTitleTv, mSubTitleTv, mContentTv;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            Slide slide = new Slide(Gravity.END);
            getWindow().setExitTransition(slide);
            getWindow().setReturnTransition(slide);
            getWindow().setReenterTransition(slide);
            getWindow().setEnterTransition(slide);
        }

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {

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

        setContentView(R.layout.fragment_chapter);


        mSubTitleTv = (TextView) findViewById(R.id.sub_title);
        mTitleTv = (TextView) findViewById(R.id.title);
        mContentTv = (TextView) findViewById(R.id.content);
        mImage = (ImageView) findViewById(R.id.image);

        mSubTitleTv.setText(mChapterData.mDate);
        mTitleTv.setText(mChapterData.mTitle);
        mContentTv.setText(mChapterData.mContent);
        mImage.setImageResource(mChapterData.mImageId);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}
