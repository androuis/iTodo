package com.andreibacalu.android.itodo.task.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andreibacalu.android.itodo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import todolist.TodoList;


public class AddTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> tasks = new ArrayList<>();

    public static final int TYPE_STABLE = 1;
    public static final int TYPE_IN_EDIT = 2;
    public static final int TYPE_BEFORE_EDIT = 3;

    private EditTextVH editTextVH;
    private ButtonVH buttonVH;

    public AddTaskAdapter() {
        tasks.add("");
        tasks.add("");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_STABLE) {
            return new StableVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_edit_item, parent, false));
        } else if (viewType == TYPE_IN_EDIT) {
            if (editTextVH == null) {
                initTaskInEdit(parent.getContext());
            }
            return editTextVH;
        } else if (viewType == TYPE_BEFORE_EDIT) {
            if (buttonVH == null) {
                initTaskBeforeEdit(parent.getContext());
            }
            return buttonVH;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_STABLE:
                prepareStableVH((StableVH) holder, position);
                break;
            case TYPE_IN_EDIT:
                prepareEditTextVH();
                break;
            case TYPE_BEFORE_EDIT:
                break;
        }
    }

    private void prepareStableVH(StableVH holder, final int position) {
        holder.textView.setText(tasks.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void prepareEditTextVH() {
        editTextVH.editText.getText().clear();
        editTextVH.editText.requestFocus();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == tasks.size() - 1) {
            return TYPE_BEFORE_EDIT;
        } else if (position == tasks.size() - 2) {
            return TYPE_IN_EDIT;
        } else {
            return TYPE_STABLE;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public Set<String> getTasks() {
        return new HashSet<>(tasks);
    }

    private void initTaskInEdit(Context context) {
        editTextVH = new EditTextVH(new EditText(context));
    }

    private void initTaskBeforeEdit(Context context) {
        Button button = new Button(context);
        button.setText("+");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextVH.editText.getText())) {
                    tasks.add(tasks.size() - 2, String.valueOf(editTextVH.editText.getText()));
                    notifyDataSetChanged();
                }
            }
        });
        buttonVH = new ButtonVH(button);
    }

    private static class StableVH extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        public StableVH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.task_info);
            button = (Button) itemView.findViewById(R.id.task_remove);
        }
    }

    private static class ButtonVH extends RecyclerView.ViewHolder {
        Button button;

        public ButtonVH(Button itemView) {
            super(itemView);
            button = itemView;
        }
    }

    private static class EditTextVH extends RecyclerView.ViewHolder {
        EditText editText;

        public EditTextVH(EditText itemView) {
            super(itemView);
            editText = itemView;
        }
    }
}
