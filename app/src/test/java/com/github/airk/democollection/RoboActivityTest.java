package com.github.airk.democollection;

import android.os.Build;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;

import com.github.airk.democollection.testcase.Person;
import com.github.airk.democollection.testcase.TestActivity;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

/**
 * Created by kevin on 15/4/1.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(emulateSdk = Build.VERSION_CODES.LOLLIPOP, constants = BuildConfig.class)
public class RoboActivityTest {
    TestActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(TestActivity.class);
    }

    @Test
    public void testButtonTextChange() throws Exception {
        Button btn = (Button) activity.findViewById(R.id.btn);
        String originStr = btn.getText().toString();

        btn.performClick();

        String afterStr = btn.getText().toString();

        assertNotSame(originStr, afterStr);
    }

}
