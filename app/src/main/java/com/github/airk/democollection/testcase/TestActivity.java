package com.github.airk.democollection.testcase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.airk.democollection.R;
import com.github.airk.democollection.testcase.widget.RippleImageView;
import com.github.airk.democollection.BaseActivity;

/**
 * Created by kevin on 15/4/1.
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);

        final Button btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText("123Changed");
            }
        });

        RippleImageView view = (RippleImageView) findViewById(R.id.ripple_view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("TEST", "ASD");
//                Toast.makeText(TestActivity.this, "asd", Toast.LENGTH_SHORT).show();
//            }
//        });
        view.setImageResource(R.drawable.ic_launcher);
    }
}
