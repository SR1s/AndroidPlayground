package playground.android.me.sr1.androidplayground.course.uda_city_material_design.lesson4;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import playground.android.me.sr1.androidplayground.App;
import playground.android.me.sr1.androidplayground.R;

/**
 * 场景动画示例
 * Created by sr1 on 15/9/21.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScenceFragment extends Fragment {

    int mValue = Gravity.TOP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_uda_material_desgin_l4_scence, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Slide slide = new Slide();

                slide.setSlideEdge(mValue);

                ViewGroup root = (ViewGroup) getActivity().findViewById(android.R.id.content);
                TransitionManager.beginDelayedTransition(root, slide);

                if (imageView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final GravityOptions[] options = new GravityOptions[] {
            new GravityOptions("BOTTOM", Gravity.BOTTOM),
            new GravityOptions("END", Gravity.END),
            new GravityOptions("LEFT", Gravity.LEFT),
            new GravityOptions("RIGHT", Gravity.RIGHT),
            new GravityOptions("START", Gravity.START),
            new GravityOptions("TOP", Gravity.TOP),
        };
        ArrayAdapter<GravityOptions> adapter = new ArrayAdapter<GravityOptions>(
                App.getContext(), R.layout.list_item, R.id.item_title, options);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mValue = options[position].mValue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mValue = options[0].mValue;
            }
        });
    }

    static class GravityOptions {
        String mName;
        int mValue;

        public GravityOptions(String name, int value) {
            mName = name;
            mValue = value;
        }

        @Override
        public String toString() {
            return mName;
        }
    }
}
