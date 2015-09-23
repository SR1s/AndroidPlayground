package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;

/**
 * 在两个场景(Scence)之间切换示例
 * Created by sr1 on 15/9/24.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class TwoScenceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_uda_material_design_l4_two_scence_from, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewGroup root = (ViewGroup) view.findViewById(R.id.root);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(
                        Scene.getSceneForLayout(root,
                                R.layout.fragment_course_uda_material_design_l4_two_scence_to,
                                App.getContext())
                );
            }
        });
    }
}
