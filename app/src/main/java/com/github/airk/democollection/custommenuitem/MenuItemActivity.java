package com.github.airk.democollection.custommenuitem;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.airk.democollection.R;
import com.github.airk.democollection.ui.BaseActivity;

/**
 * Created by kevin on 15/3/9.
 */
public class MenuItemActivity extends BaseActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_custom);
        item.setActionView(R.layout.menu_custom);
        item.getActionView().findViewById(android.R.id.text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomClick(v);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_simple) {
            Toast.makeText(this, "Simple", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCustomClick(View view) {
        Toast.makeText(this, "Custom", Toast.LENGTH_SHORT).show();
    }
}
