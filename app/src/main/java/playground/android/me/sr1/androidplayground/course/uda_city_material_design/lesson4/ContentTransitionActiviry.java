package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.toolbox.utils.DataHelper;

/**
 * ContentTransition示例类
 * Created by srluo on 2015/9/24.
 */
public class ContentTransitionActiviry extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_uda_material_design_l4_content_transition);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter(this, 100));
    }
}

class GridAdapter extends BaseAdapter {

    private Context mContext;
    private int mCount;
    private int[] mImageResourceIds =DataHelper.IMAGE_RESOURCE_IDS;

    private boolean mSetExitTransition = true;

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
                Intent intent = new Intent(mContext, ContentTransitionDetailActivity.class);

                intent.putExtra(ContentTransitionDetailActivity.KEY_CHAPTER_TITLE, data.mTitle);
                intent.putExtra(ContentTransitionDetailActivity.KEY_CHAPTER_DATE, data.mDate);
                intent.putExtra(ContentTransitionDetailActivity.KEY_CHAPTER_IMAGE_ID, imageId);
                intent.putExtra(ContentTransitionDetailActivity.KEY_CHAPTER_CONTENT, data.mContent);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (!mSetExitTransition) {
                        intent.putExtra(ContentTransitionDetailActivity.KEY_ENABLE_DEFAULT_EFFECT, true);
                    }
                    // 不加上这个是没有Transition效果的
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                            v, v.getTransitionName()).toBundle(); // Shared Element Transitions
                    mContext.startActivity(intent, bundle);
                    mSetExitTransition = !mSetExitTransition;
                } else {
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }
}
