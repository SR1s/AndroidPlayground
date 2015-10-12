package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;

/**
 * 网上的教程：http://mobile.51cto.com/aprogram-451555.htm
 * Created by SR1 on 15/10/11.
 */
public class MyShapeTransitionActivity extends AppCompatActivity {

    private Scene scene1, scene2;
    private Transition transition;

    private boolean start;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Toast.makeText(App.getContext(), "仅支持Android4.4以上版本", Toast.LENGTH_SHORT).show();
            finish();
        }

        setContentView(R.layout.fragment_course_uda_material_design_l4_my_transition_start_layout);

        RelativeLayout baseLayout = (RelativeLayout) findViewById(R.id.base);

        ViewGroup startView = (ViewGroup) getLayoutInflater().inflate(
                R.layout.fragment_course_uda_material_design_l4_my_transition_start_layout, baseLayout, false
        );
        ViewGroup endView = (ViewGroup) getLayoutInflater().inflate(
                R.layout.fragment_course_uda_material_design_l4_my_transition_end_layout, baseLayout, false
        );

        scene1 = new Scene(baseLayout, startView);
        scene2 = new Scene(baseLayout, endView);


        transition = new AutoTransition();
        transition.setDuration(300);
        transition.setInterpolator(new AccelerateDecelerateInterpolator());

        start = true;
    }

    public void changeScene(View v) {

        //check flag
        if (start) {
            TransitionManager.go(scene2, transition);
            start = false;
        } else {
            TransitionManager.go(scene1, transition);
            start = true;
        }
    }
}
