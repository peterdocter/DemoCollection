package com.github.airk.democollection.rxjava;

import android.util.Log;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by kevin on 15/3/3.
 */
public class RxUse {
    private final String TAG = RxUse.class.getSimpleName();

    public void firstUse() {
        Observable<String> o = Observable.from(new String[]{"1", "hello", "asd"});
        o.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, s);
            }
        });
    }

    public void createUse() {
        Observable<String> o = Observable.from(new String[]{"2", "World", "CCC"});
    }

}
