package playground.android.me.sr1.androidplayground.toolbox.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import playground.android.me.sr1.androidplayground.R;

public class ChapterFragment extends Fragment {

    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_IMAGE = "image";

    private int mImageId;
    private String mTitle;
    private String mDate;
    private String mContent;

    private ImageView mImage;
    private TextView mSubTitleTv;
    private TextView mTitleTv;
    private TextView mContentTv;


    public static ChapterFragment newInstance(String title, String date, String content, int imageId) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putString(KEY_DATE, date);
        args.putString(KEY_CONTENT, content);
        args.putInt(KEY_IMAGE, imageId);
        fragment.setArguments(args);
        return fragment;
    }

    public ChapterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(KEY_TITLE);
            mDate = getArguments().getString(KEY_DATE);
            mContent = getArguments().getString(KEY_CONTENT);
            mImageId = getArguments().getInt(KEY_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubTitleTv = (TextView) view.findViewById(R.id.sub_title);
        mTitleTv = (TextView) view.findViewById(R.id.title);
        mContentTv = (TextView) view.findViewById(R.id.content);
        mImage = (ImageView) view.findViewById(R.id.image);

        mImage.setImageResource(mImageId);
        mSubTitleTv.setText(mDate);
        mTitleTv.setText(mTitle);
        mContentTv.setText(mContent);
    }
}
