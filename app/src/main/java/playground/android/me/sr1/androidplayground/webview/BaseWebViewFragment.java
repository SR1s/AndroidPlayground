package playground.android.me.sr1.androidplayground.webview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import playground.android.me.sr1.androidplayground.R;

public class BaseWebViewFragment extends Fragment {

    private static final String TAG = "BaseWebViewFragment";

    private Button mCloseBtn;
    private Button mShareBtn;
    private Button mGoBackBtn;
    private Button mGoForwardBtn;
    private Button mRefreshBtn;
    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    protected void initData(Bundle data) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCloseBtn = (Button) view.findViewById(R.id.close);
        mShareBtn = (Button) view.findViewById(R.id.share);
        mGoBackBtn = (Button) view.findViewById(R.id.goback);
        mGoForwardBtn = (Button) view.findViewById(R.id.goforward);
        mRefreshBtn = (Button) view.findViewById(R.id.refresh);

        mCloseBtn.setOnClickListener(clickListener);
        mShareBtn.setOnClickListener(clickListener);
        mGoBackBtn.setOnClickListener(clickListener);
        mGoForwardBtn.setOnClickListener(clickListener);
        mRefreshBtn.setOnClickListener(clickListener);
        view.findViewById(R.id.invokeJs).setOnClickListener(clickListener);

        mWebView = (WebView) view.findViewById(R.id.webview);

        settingWebView();
        loadHomePage();
    }

    protected void loadHomePage() {
        mWebView.loadUrl("file:///android_asset/webview_js.html");
    }

    protected View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.close:
                    close();
                    break;
                case R.id.share:
                    share();
                    break;
                case R.id.goback:
                    goback();
                    break;
                case R.id.goforward:
                    goforward();
                    break;
                case R.id.refresh:
                    refresh();
                    break;
                case R.id.invokeJs:
                    mWebView.loadUrl("javascript:callFromAndroid();");
                    break;
                default:
                    Log.i(TAG, "no id match");
            }
        }
    };

    protected void close() {
        getActivity().finish();
    }

    protected void share() {
        Toast.makeText(getActivity().getApplicationContext(), "share button clicked", Toast.LENGTH_LONG).show();
    }

    protected void goback() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            Log.i(TAG, "mWebView can't goback");
        }
    }

    protected void goforward() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        } else {
            Log.i(TAG, "mWebView can't goforward");
        }
    }

    protected void refresh() {
        mWebView.reload();
    }

    protected void settingWebView() {
        if (mWebView != null) {
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new AndroidInterfaceForJs(getActivity().getApplication()), "AndroidInterface");
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    BaseWebViewFragment.this.getActivity().setTitle(mWebView.getTitle());
                }
            });

        }
    }

    public static class AndroidInterfaceForJs {

        private final Context mContext;

        public AndroidInterfaceForJs(Context ctx) {
            mContext = ctx;
        }

        @JavascriptInterface
        public void toastMessage(String message) {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void sum(int a, int b) {
            Toast.makeText(mContext, "result is " + (a+b), Toast.LENGTH_LONG).show();
        }
    }
}
