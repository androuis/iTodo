package com.andreibacalu.android.itodo.task.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.todolist.TodoList;
import com.andreibacalu.android.itodo.utils.ModelUtils;
import com.andreibacalu.android.itodo.utils.realm.RealmRecyclerViewAdapter;

public class ViewTaskAdapter extends RealmRecyclerViewAdapter<TodoList> {

    private class TodoListViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView summary;
        private TextView creator;

        public TodoListViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            summary = (TextView) itemView.findViewById(R.id.summary);
            creator = (TextView) itemView.findViewById(R.id.creator);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_display_todolist, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TodoList todoList = getItem(position);
        TodoListViewHolder todoListViewHolder = (TodoListViewHolder) holder;
        todoListViewHolder.name.setText(todoList.getName());
        todoListViewHolder.summary.setText(ModelUtils.getItemsAsString(todoList));
        todoListViewHolder.creator.setText(ModelUtils.getDisplayName(todoList.getCreatedBy()));
    }
}
