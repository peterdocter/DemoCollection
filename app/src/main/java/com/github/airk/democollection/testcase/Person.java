package com.github.airk.democollection.testcase;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by kevin on 15/4/1.
 */
public class Person {
    private static final String TAG = "Person";
    String name;
    int age;
    String address;
    int tel;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", tel=" + tel +
                '}';
    }

    public String getName() {
        return name;
    }

    public boolean hasName() {
        if (TextUtils.isEmpty(name)) {
            Log.e(TAG, "don't have a valid name.");
            return false;
        }
        return true;
    }
}
