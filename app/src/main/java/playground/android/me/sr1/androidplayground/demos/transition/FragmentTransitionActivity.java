package playground.android.me.sr1.androidplayground.demos.transition;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.Window;

import playground.android.me.sr1.androidplayground.R;
import playground.android.me.sr1.androidplayground.demos.transition.fragment.FragmentTransitionFragment;

/**
 * Fragment之间跳转的Transition示例
 * Created by SR1 on 15/10/27.
 */
public class FragmentTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demos_transition_fragment_transition);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, FragmentTransitionFragment.newInstance(fragmentManager));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }
}
