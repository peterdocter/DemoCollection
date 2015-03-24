package com.github.airk.democollection.downloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kevin on 15/3/24.
 */
public class OkHttpDownloader {
    private OkHttpClient client;
    Handler uiHandler;

    public OkHttpDownloader() {
        client = new OkHttpClient();
        uiHandler = new Handler(Looper.getMainLooper());
    }

    public interface Callback {
        void onSuccess(Bitmap bitmap);

        void onException(Exception e);
    }

    public void execute(final Context context, final Uri uri, final Callback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Request request = new Request.Builder().url(uri.toString()).build();

                try {
                    Response response = client.newCall(request).execute();

                    InputStream is = response.body().byteStream();
                    MarkableInputStream markableInputStream = new MarkableInputStream(is);
                    is = markableInputStream;

                    long mark = markableInputStream.savePosition(65535);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(is, null, options);

                    calculateSampleSize(context, options);

                    options.inJustDecodeBounds = false;

                    markableInputStream.reset(mark);
                    final Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(bitmap);
                        }
                    });
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onException(new Exception("OOM"));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onException(new IOException("IO"));
                        }
                    });
                }
                return null;
            }
        }.execute();

    }

    private void calculateSampleSize(Context context, BitmapFactory.Options options) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int sample = 1;
        if (options.outHeight > dm.heightPixels || options.outWidth > dm.widthPixels) {
            int hf = options.outHeight / dm.heightPixels;
            int wf = options.outWidth / dm.widthPixels;
            sample = Math.max(hf, wf);
        }
        options.inSampleSize = sample;
    }
}
