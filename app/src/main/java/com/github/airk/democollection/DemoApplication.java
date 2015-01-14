package com.github.airk.democollection;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;

/**
 * Created by kevin on 15/1/14.
 */
public class DemoApplication extends Application {
    private OkHttpClient mHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        initGlide();
    }

    private void initGlide() {
        Glide.get(this).register(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(getHttpClient()));
    }

    public OkHttpClient getHttpClient() {
        if (mHttpClient == null) {
            synchronized (DemoApplication.class) {
                if (mHttpClient == null) {
                    mHttpClient = new OkHttpClient();
                }
            }
        }
        return mHttpClient;
    }
}
