package playground.android.me.sr1.androidplayground.demos.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import playground.android.me.sr1.androidplayground.R;

/**
 * Transition动画相关概念
 * Created by SR1 on 15/10/24.
 */
public class TransitionConceptActivity extends AppCompatActivity {

    ArrayList<Item> mItems = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView conceptList = new RecyclerView(this);
        conceptList.setLayoutManager(new LinearLayoutManager(this));
        conceptList.setItemAnimator(new DefaultItemAnimator());
        conceptList.setAdapter(new ConceptAdapter());

        mItems.add(new Item("Transition(变换):",
                "Transition是Android Kitkat(4.4)开始提供的新特性。Transition相关的API为在不同的UI状态之间产生动画效果提供了更加便捷的API。\n\n" +
                        "Scene(场景)和Transition(变换)是其中两个最基本的概念。\n\n" +
                        "Scene 定义了UI的布局状态，Transition表示了两个Scene相互切换时动画的变化过程。\n\n" +
                        "当场景改变时候，Transition负责：\n" +
                        "1. 记录每个View在开始和结束时的状态(表示为Scene);\n" +
                        "2. 根据开始和结束状态的区别创建一个Animator。"));

        mItems.add(new Item("Scene(场景):",
                "Scene存储了View层级结构的状态，包括所有的子View和相关的属性的值。\n\n" +
                        "Transition框架允许我们使用布局资源文件或者ViewGroup对象来创建Scene。"));

        mItems.add(new Item("Transition(变换):",
                "在Transition框架里，Animation(动画)生成了一系列描述开始场景和结束场景之间的变化的帧。\n\n" +
                        "这些Animation的相关信息就存储在一个Transition对象里。"));

        mItems.add(new Item("TransitionManager:",
                "为了执行Transition对象里的动画的效果，我们需要使用TransitionManager来使用这个Transition对象。"));

        mItems.add(new Item("TransitionSet:",
                ""));

        mItems.add(new Item("SharedElement(共享元素):",
                ""));

        mItems.add(new Item("相关限制:",
                "根据官方文档，Transition框架存在以下限制：\n" +
                        "1. 应用到SurfaceView上得动画效果可能无法正常的表现。" +
                        "原因在于SurfaceView对象的界面更新并不是由UI线程来执行的。" +
                        "这些更新可能无法跟同时进行动画的其他View保持同步。\n\n" +
                        "2. 某些Transition类型可能无法在TextureView上产生期望的效果。\n\n" +
                        "3. 继承自AdapterView的类，如ListView，管理自身的子View的方式跟Transition框架不兼容，" +
                        "如果尝试在继承自AdapterView的类上进行动画操作，设备的显示可能会挂起。\n\n" +
                        "4. 如果在动画里尝试对一个TextView调整大小，在调整大小操作完成前，文本内容会弹到一个新的位置。" +
                        "为了避免这个问题，不要在动画里对包含文本内容的View进行调整大小的操作。"));

        setContentView(conceptList);
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
            return new MainViewHolder(LayoutInflater.from(TransitionConceptActivity.this).inflate(
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
