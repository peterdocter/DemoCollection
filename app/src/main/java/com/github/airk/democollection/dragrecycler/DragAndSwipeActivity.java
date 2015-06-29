package com.github.airk.democollection.dragrecycler;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.airk.democollection.BaseActivity;
import com.github.airk.democollection.R;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin on 15/6/29.
 */
public class DragAndSwipeActivity extends BaseActivity {
    @InjectView(R.id.list)
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_swipe);
        ButterKnife.inject(this);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        final DragAdapter adapter = new DragAdapter();
        list.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            RecyclerView.ViewHolder mCurrentTarget = null;

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (mCurrentTarget != null) {
                    mCurrentTarget.itemView.clearAnimation();
                    mCurrentTarget = null;
                }
                mCurrentTarget = target;
                startAnim();
                Log.d("DRAG", "VH: " + viewHolder.getAdapterPosition() + " TARGET: " + target.getAdapterPosition());
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.onDismiss(viewHolder.getAdapterPosition());
            }

            private void startAnim() {
                //TODO
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(list);
    }

    private interface ItemMoveAdapter {
        void onItemMove(int from, int to);

        void onDismiss(int position);
    }

    private class DragAdapter extends RecyclerView.Adapter<DragAdapter.SimpleHolder> implements ItemMoveAdapter {
        ArrayList<Integer> items;

        public DragAdapter() {
            items = new ArrayList<>(100);
            for (int i = 0; i < 100; i++) {
                items.add(i);
            }
        }

        @Override
        public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_single_text, parent, false);
            return new SimpleHolder(view);
        }

        @Override
        public void onBindViewHolder(SimpleHolder holder, int position) {
            holder.tv.setText("" + (items.get(position) + 1));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onItemMove(int from, int to) {
            Collections.swap(items, from, to);
            notifyItemMoved(from, to);
        }

        @Override
        public void onDismiss(int position) {
            items.remove(position);
            notifyItemRemoved(position);
        }

        class SimpleHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public SimpleHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
