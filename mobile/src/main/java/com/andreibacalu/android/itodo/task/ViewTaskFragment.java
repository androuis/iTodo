package com.andreibacalu.android.itodo.task;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andreibacalu.android.itodo.R;

import io.realm.Realm;
import todolist.TodoList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTaskFragment extends Fragment {

    private Realm realm;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewTaskFragment.
     */
    public static ViewTaskFragment newInstance() {
        ViewTaskFragment fragment = new ViewTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_task, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.test)).append(" size: " + realm.allObjects(TodoList.class).size());
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
