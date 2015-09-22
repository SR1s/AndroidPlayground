package playground.android.me.sr1.androidplayground.toolbox.component.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import playground.android.me.sr1.androidplayground.R;

/**
 * 用于显示示例代码 <br/>
 * 利用前端的代码高亮实现
 * Created by sr1 on 15/9/23.
 */
public class CodeFragment extends Fragment {

    public static final String TAG = "CodeFragment";

    private static final String HTML_CONTENT = "" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "  <head>\n" +
            "    <meta charset='utf8'>\n" +
            "    <link rel='stylesheet' type='text/css' href='css/code_highlight/highlight.css'>\n" +
            "    <script src='js/code_highlight/jquery-1.11.0.min.js'></script>\n" +
            "    <script src='js/code_highlight/prettify.js'></script>\n" +
            "    <style>\n" +
            "       body {\n" +
            "         background-color: #333;\n" +
            "         white-space: normal;\n" +
            "         overflow-wrap: break-word;\n" +
            "         margin: 0;\n" +
            "         padding: 0;\n" +
            "         border: 0;\n" +
            "         outline: 0;\n" +
            "         font-weight: inherit;\n" +
            "         font-style: inherit;\n" +
            "         font-size: 100%%;\n" +
            "         font-family: inherit;\n" +
            "         vertical-align: baseline;\n" +
            "       }\n" +
            "       pre, code {\n" +
            "         display: flex;\n" +
            "         width: 100%%;\n" +
            "         overflow-wrap: break-word;\n" +
            "         font-family: monospace;\n" +
            "         white-space: pre-wrap;\n" +
            "         margin: 0px;\n" +
            "       }" +
            "    </style>" +
            "    <script type=\"text/javascript\">\n" +
            "      window.onload = function() {\n" +
            "        $(\"pre>code\").addClass(\"prettyprint linenums\");\n" +
            "        prettyPrint();\n" +
            "      }\n" +
            "    </script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <pre>\n" +
            "      <code>%s\n" +
            "      </code>\n" +
            "    </pre></body>\n" +
            "</html>";

    private String mSourceCode;
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbox_fragment_sample_with_code, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mWebView = (WebView) view.findViewById(R.id.toolbox_code_area_wv);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
    }

    public void setSourceCode(String sourceCode) {
        WebView webView = mWebView;
        if (webView != null && !TextUtils.isEmpty(sourceCode)) {
            mSourceCode = sourceCode;
            webView.loadDataWithBaseURL("file:///android_asset/",
                    String.format(HTML_CONTENT, Html.escapeHtml(sourceCode)),
                    "text/html", "utf-8", "");
            Log.d(TAG, "content: " + String.format(HTML_CONTENT, Html.escapeHtml(sourceCode.trim())));
        } else {
            Log.w(TAG, "[CodeView haven't been initialized or sourceCode is empty, skip setSourceCode]");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        WebView webView = mWebView;
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(false);
            webView.destroyDrawingCache();
            webView.destroy();
            mWebView = null;
        }
    }
}
