package playground.android.me.sr1.androidplayground.demos.transition;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;

/**
 * 简单变换实例
 * Created by SR1 on 15/10/27.
 */
public class SimpleTransitionActivity extends AppCompatActivity {

    private ViewGroup mRoot;
    private View mLeftTop, mLeftBottom, mRightTop, mRightBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demos_transition_activity_transition);

        mRoot = (ViewGroup) findViewById(android.R.id.content);
        mLeftTop = findViewById(R.id.leftTop);
        mLeftBottom = findViewById(R.id.leftBottom);
        mRightTop = findViewById(R.id.rightTop);
        mRightBottom = findViewById(R.id.rightBottom);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        final MenuItem fade = menu.add("Fade");
        final MenuItem explode = menu.add("Explode");
        final MenuItem slideStart = menu.add("SlideStart");
        final MenuItem slideTop = menu.add("SlideTop");
        final MenuItem slideEnd = menu.add("SlideEnd");
        final MenuItem slideBottom = menu.add("SlideBottom");

        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Visibility visibility = null;

                    if (item == fade) {
                        visibility = new Fade();
                    } else if (item == explode) {
                        visibility = new Explode();
                    } else if (item == slideStart) {
                        visibility = new Slide(Gravity.START);
                    } else if (item == slideTop) {
                        visibility = new Slide(Gravity.TOP);
                    } else if (item == slideEnd) {
                        visibility = new Slide(Gravity.END);
                    } else if (item == slideBottom) {
                        visibility = new Slide(Gravity.BOTTOM);
                    }


                    if (visibility != null) {
                        TransitionManager.beginDelayedTransition(mRoot, visibility);

                        boolean isVisible = mLeftTop.getVisibility() == View.VISIBLE;

                        int toVisibility = View.VISIBLE;
                        if (isVisible) {
                            toVisibility = View.INVISIBLE;
                        }

                        mLeftTop.setVisibility(toVisibility);
                        mLeftBottom.setVisibility(toVisibility);
                        mRightTop.setVisibility(toVisibility);
                        mRightBottom.setVisibility(toVisibility);

                        return true;
                    } else {
                        return false;
                    }
                } else {
                    Toast.makeText(App.getContext(), "System version lower than Lollipop", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        };

        fade.setOnMenuItemClickListener(listener);
        explode.setOnMenuItemClickListener(listener);
        slideStart.setOnMenuItemClickListener(listener);
        slideTop.setOnMenuItemClickListener(listener);
        slideEnd.setOnMenuItemClickListener(listener);
        slideBottom.setOnMenuItemClickListener(listener);

        return super.onCreatePanelMenu(featureId, menu);
    }
}
