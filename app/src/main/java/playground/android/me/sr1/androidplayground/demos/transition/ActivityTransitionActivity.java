package playground.android.me.sr1.androidplayground.demos.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import playground.android.me.sr1.androidplayground.R;

/**
 * Activity之间的变换例子
 * Created by SR1 on 15/10/24.
 */
public class ActivityTransitionActivity extends AppCompatActivity {

    ArrayList<Item> mItems = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        RecyclerView conceptList = new RecyclerView(this);
        conceptList.setLayoutManager(new LinearLayoutManager(this));
        conceptList.setItemAnimator(new DefaultItemAnimator());
        conceptList.setAdapter(new ConceptAdapter());

        mItems.add(new Item("Activity之间的变换:",
                "假定有两个Activity，分别为A和B。A启动了B。\n\n" +
                        "跟Activity相关的API围绕着：Exit、Enter、Return、ReEnter来构建。\n\n" +
                        "Exit: 当A启动B的时候，A的View的动画效果；\n" +
                        "Enter: 当A启动B的时候，B的View的动画效果；" +
                        "Return: 当从B返回A的时候，B的View的动画效果；" +
                        "ReEnter: 当从B返回A的时候，A的动画效果。"));

        setContentView(R.layout.demos_transition_activity_transition);
    }

    class Item {
        String mTitle;
        String mContent;

        public Item(String title, String content) {
            mTitle = title;
            mContent = content;
        }
    }

    class ConceptAdapter extends RecyclerView.Adapter<ConceptAdapter.MainViewHolder> {


        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(ActivityTransitionActivity.this).inflate(
                    R.layout.fragment_chapter, parent, false
            ));
        }

        @Override
        public void onBindViewHolder(final MainViewHolder holder, int position) {
            final Item item = mItems.get(position % mItems.size());

            holder.mTitle.setText(item.mTitle);
            holder.mContent.setText(item.mContent);

        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView mTitle;
            TextView mContent;

            public MainViewHolder(final View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.title);
                mContent = (TextView) itemView.findViewById(R.id.content);
                itemView.findViewById(R.id.sub_title).setVisibility(View.GONE);
                itemView.findViewById(R.id.image).setVisibility(View.GONE);
            }
        }
    }
}
