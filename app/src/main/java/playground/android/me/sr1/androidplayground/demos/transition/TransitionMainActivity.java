package playground.android.me.sr1.androidplayground.demos.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import playground.android.me.sr1.androidplayground.R;

/**
 * Transition 动画分享的App主页面
 * Created by SR1 on 15/10/23.
 */
public class TransitionMainActivity extends AppCompatActivity {

    ArrayList<Item> mItems = new ArrayList<Item>();

    public static int sTransitionTypeIndex = 0;

    public static final String TAG_TRANSITION_TYPE = "TAG_TRANSITION_TYPE";
    public static final int TRANSITION_SLIDE = 0;
    public static final int TRANSITION_FADE = 1;
    public static final int TRANSITION_EXPLODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sTransitionTypeIndex++;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

            int type = sTransitionTypeIndex % 3;
            Visibility visibility;
            switch (type) {
                case TRANSITION_EXPLODE:
                    visibility = new Explode();
                    break;
                case TRANSITION_FADE:
                    visibility = new Fade();
                    break;
                case TRANSITION_SLIDE:
                    visibility = new Slide(Gravity.END);
                    break;
                default:
                    visibility = new Slide(Gravity.END);
                    break;
            }
            getWindow().setExitTransition(visibility);
            getWindow().setReturnTransition(visibility);
            getWindow().setReenterTransition(visibility);
            getWindow().setEnterTransition(visibility);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demos_transition_activity_main);
        RecyclerView mainList = (RecyclerView) findViewById(R.id.demos_transition_main_list);

        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.setItemAnimator(new DefaultItemAnimator());

        mItems.add(new Item("基本概念",
                R.color.sugar_red, TransitionConceptActivity.class));
        mItems.add(new Item("简单的变换",
                R.color.sugar_blue, SimpleTransitionActivity.class));
        mItems.add(new Item("Activity之间",
                R.color.sugar_green, ActivityTransitionActivity.class));
        mItems.add(new Item("Fragment之间", R.color.sugar_yellow, null));
        mItems.add(new Item("使用代码还是使用Xml", R.color.sugar_pink, null));
        mItems.add(new Item("工具类: AnimationUtils, Interpolaor", R.color.sugar_red, null));
        mItems.add(new Item("矢量图形: svg格式", R.color.sugar_blue, null));
        mItems.add(new Item("动画设计的一些原则", R.color.sugar_green, null));
        mItems.add(new Item("视频里的习题", R.color.sugar_yellow, null));
        mItems.add(new Item("遇到的一些坑", R.color.sugar_pink, null));

        mainList.setAdapter(new MainAdapter());
    }

    class Item {
        String mText;
        int mColorId;
        Class mDestActivity;

        public Item(String text, int colorId, Class destActivity) {
            mText = text;
            mColorId = colorId;
            mDestActivity = destActivity;
        }
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {


        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(TransitionMainActivity.this).inflate(
                    R.layout.demos_transition_main_item, parent, false
            ));
        }

        @Override
        public void onBindViewHolder(final MainViewHolder holder, int position) {
            final Item item = mItems.get(position % mItems.size());

            holder.mText.setText(item.mText);
            holder.mBackground.setBackgroundColor(getResources().getColor(item.mColorId));
            if (item.mDestActivity != null) {
                holder.mBackground.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                                    TransitionMainActivity.this).toBundle();
                            startActivity(new Intent(TransitionMainActivity.this, item.mDestActivity), bundle);
                        } else {
                            startActivity(new Intent(TransitionMainActivity.this, item.mDestActivity));
                        }

                    }
                });
            } else {
                holder.mBackground.setOnClickListener(null);
            }

        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView mText;
            ViewGroup mBackground;

            public MainViewHolder(final View itemView) {
                super(itemView);
                mText = (TextView) itemView.findViewById(R.id.demos_transition_main_item_text);
                mBackground = (ViewGroup) itemView.findViewById(R.id.demos_transition_main_item_background);
            }
        }
    }
}
