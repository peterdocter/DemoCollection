package com.github.airk.democollection.webview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.airk.democollection.R;
import com.github.airk.offloader.Loader;
import com.github.airk.offloader.MatchFactory;
import com.github.airk.offloader.OffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/4/9.
 */
public class WebViewActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    public static Type useWebViewType = Type.NONE;
    static Type lastType = null;
        private final String URL = "http://www.cnbeta.com/";
    @InjectView(R.id.webview_type)
    Spinner webviewType;
    @InjectView(R.id.init)
    TextView init;
    @InjectView(R.id.load)
    TextView load;
    @InjectView(R.id.webview_xml)
    WebView webviewXml;
    @InjectView(R.id.webview_container)
    FrameLayout webviewContainer;
    WebView webViewCache;
    boolean voidShake = true;
    private long initStart = -1L;

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStart = System.currentTimeMillis();
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
        webviewType.setOnItemSelectedListener(this);

        switch (useWebViewType) {
            case XML:
            case LOAD:
                webviewXml.setWebViewClient(new TestClient());
                webviewXml.getSettings().setUserAgentString("FreeStore");
                webviewXml.loadUrl(URL);
                break;
            case CACHE:
                webViewCache = new WebView(this);
                webViewCache.loadUrl("");
                initStart = System.currentTimeMillis();

                webviewContainer.addView(webViewCache, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                webViewCache.setWebViewClient(new TestClient());
                webViewCache.loadUrl(URL);
                break;
            case LOCAL:
                webviewXml.setWebViewClient(new TestClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }

                });
                webviewXml.getSettings().setAllowFileAccess(true);
                try {
                    final String html = getStringFromFile("/sdcard/test.html");
                    String[] results = OfflineLoadKit.findAll(html);
//                    String[] results = OfflineLoadKit.findContainer(html);
                    if (results != null) {
                        for (String r : results) {
                            Log.e("OFFLINE", r);
                        }
                    }

//                    String replace = OfflineLoadKit.replaceExact("http://www.cnbeta.com/favicon.ico", "HHHHHHHHHH-----test-----HHHHHHHHHH", html);
//
//                    Log.d("TEST", replace);
                    webviewXml.loadDataWithBaseURL("file:///sdcard/", html, "text/html", "utf-8", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                webviewXml.loadUrl("file:///" + Environment.getExternalStorageDirectory().toString() + "/" + "local_webview_test.html");
                break;
            case OFFLOADER:
                File file = new File("/sdcard/test.html");
                Loader loader = OffLoader.getInstance().load(webviewXml, file, "/sdcard/testres/", new MatchFactory() {
                    @Override
                    public String match(String src) {
                        String[] ret = src.split("\\?");
                        return ret[0];
                    }
                });
                loader.cancel();
                break;
            case NONE:
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (voidShake) {
            voidShake = false;
            return;
        }
        switch (position) {
            case 0:
                useWebViewType = Type.NONE;
                break;
            case 1:
                useWebViewType = Type.XML;
                break;
            case 2:
                useWebViewType = Type.CACHE;
                break;
            case 3:
                useWebViewType = Type.LOAD;
                break;
            case 4:
                useWebViewType = Type.LOCAL;
                break;
            case 5:
                useWebViewType = Type.OFFLOADER;
            default:
                break;
        }
        if (lastType != null && lastType == useWebViewType) {
            return;
        }
        lastType = useWebViewType;
        recreate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public enum Type {
        NONE,
        XML,
        CACHE,
        LOAD,
        LOCAL,
        OFFLOADER,
    }

    class TestClient extends WebViewClient {
        long cost;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            cost = System.currentTimeMillis();
            init.setText("" + (System.currentTimeMillis() - initStart) + " ms");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            load.setText("" + (System.currentTimeMillis() - cost) + " ms");
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.d("TEST", url);
            return super.shouldInterceptRequest(view, url);
        }

    }

}
