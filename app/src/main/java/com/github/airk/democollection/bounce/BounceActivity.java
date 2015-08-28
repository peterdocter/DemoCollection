package com.github.airk.democollection.bounce;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.github.airk.democollection.BaseActivity;
import com.github.airk.democollection.R;

/**
 * @author liukai(kevinliukai@diditaxi.com)
 * @since 15/8/10.
 */
public class BounceActivity extends BaseActivity {

    private View mAnimView;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimView, "translationY", 0, 40f);
                    animator.setInterpolator(new LittleBounceInterpolator());
                    animator.setDuration(500);
                    animator.start();
                    sendEmptyMessageDelayed(1, 1000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce);
        mAnimView = findViewById(R.id.image);
        mHandler.obtainMessage(1).sendToTarget();
    }
}
