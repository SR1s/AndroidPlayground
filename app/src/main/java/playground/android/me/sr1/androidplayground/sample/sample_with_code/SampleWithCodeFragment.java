package playground.android.me.sr1.androidplayground.sample.sample_with_code;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import playground.android.me.sr1.androidplayground.toolbox.component.fragment.CodeFragment;

/**
 * 代码片段样例
 * Created by sr1 on 15/9/23.
 */
public class SampleWithCodeFragment extends CodeFragment {

    private static final String CODE =
            "final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);\n" +
            "\n" +
            "view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {\n" +
            "    @Override\n" +
            "    public void onClick(View v) {\n" +
            "        Slide slide = new Slide();\n" +
            "\n" +
            "        slide.setSlideEdge(mValue);\n" +
            "\n" +
            "        ViewGroup root = (ViewGroup) getActivity().findViewById(android.R.id.content);\n" +
            "        TransitionManager.beginDelayedTransition(root, slide);\n" +
            "\n" +
            "        if (imageView.getVisibility() == View.VISIBLE) {\n" +
            "            imageView.setVisibility(View.INVISIBLE);\n" +
            "        } else {\n" +
            "            imageView.setVisibility(View.VISIBLE);\n" +
            "        }\n" +
            "    }\n" +
            "});";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        setSourceCode(DataHelper.getChapterData().get(0).mContent);
        setSourceCode(CODE);
    }
}
