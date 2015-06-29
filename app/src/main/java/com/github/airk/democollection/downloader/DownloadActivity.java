package com.github.airk.democollection.downloader;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.github.airk.democollection.R;
import com.github.airk.democollection.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/3/24.
 */
public class DownloadActivity extends BaseActivity {
    @InjectView(R.id.image)
    ImageView image;

    String URL = "http://t12.baidu.com/it/u=4095575894,102452705&fm=32&s=A98AA55F526172A6F6A058E50300A060&w=623&h=799&img.JPEG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloader);
        ButterKnife.inject(this);

        OkHttpDownloader downloader = new OkHttpDownloader();
        downloader.execute(this, Uri.parse(URL), new OkHttpDownloader.Callback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                image.setImageBitmap(bitmap);
            }

            @Override
            public void onException(Exception e) {
            }
        });
    }
}
