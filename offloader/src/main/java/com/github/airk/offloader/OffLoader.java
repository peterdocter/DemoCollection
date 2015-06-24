package com.github.airk.offloader;

import android.support.annotation.Nullable;
import android.webkit.WebView;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by kevin on 15/4/16.
 * <p/>
 * OffLoader
 */
public final class OffLoader {
    final static String TAG = "OffLoader";
    //exactly match
    private static final MatchFactory DEFAULT_MATCHER = new MatchFactory() {
        @Override
        public String match(String src) {
            return src;
        }
    };
    private static OffLoader sInstance;
    private MatchFactory defaultMatcher = DEFAULT_MATCHER;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private OffLoader() {
    }

    public static OffLoader getInstance() {
        if (sInstance == null) {
            synchronized (OffLoader.class) {
                if (sInstance == null) {
                    sInstance = new OffLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * Set default matcher.
     *
     * @param m {@link MatchFactory}
     * @return OffLoader instance
     */
    public OffLoader setDefaultMatcher(MatchFactory m) {
        defaultMatcher = m;
        return this;
    }

    /**
     * Load URL with offline resource into WebView with default mather,
     * must be invoked at UI Thread.
     *
     * @param webView  {@link WebView}
     * @param indexUrl local index html file
     * @param dir      local resource dir path
     * @return {@link Loader} for cancel this request
     */
    public Loader load(WebView webView, File indexUrl, String dir) {
        return load(webView, indexUrl, dir, defaultMatcher);
    }

    /**
     * Load URL with offline resource into WebView with given mather,
     * must be invoked at UI Thread.
     *
     * @param webView  {@link WebView}
     * @param indexUrl local index html file
     * @param dir      local resource dir path
     * @param matcher  for match the real file name
     * @return {@link Loader} for cancel this request
     */
    @Nullable
    public Loader load(WebView webView, File indexUrl, String dir, MatchFactory matcher) {
        if (!Utils.isMainThread()) {
            throw new RuntimeException("This method must be invoked at UI thread.");
        }
        Future f = null;
        try {
            f = executor.submit(new Finder(webView, indexUrl, dir, matcher));
        } catch (RejectedExecutionException ignore) {}
        if (f != null) {
            return new Loader(f);
        }
        return null;
    }
}
