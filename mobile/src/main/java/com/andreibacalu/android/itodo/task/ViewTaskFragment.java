package com.andreibacalu.android.itodo.task;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.task.adapters.ViewTaskAdapter;
import com.andreibacalu.android.itodo.task.adapters.ViewTaskRealmAdapter;
import com.andreibacalu.android.itodo.todolist.TodoList;
import com.andreibacalu.android.itodo.user.User;

import io.realm.Realm;
import io.realm.RealmResults;

import com.andreibacalu.android.itodo.todolist.item.Item;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTaskFragment extends Fragment {

    private Realm realm;
    private RecyclerView recyclerView;
    private ViewTaskAdapter recyclerViewAdapter;

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
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewAdapter = new ViewTaskAdapter();
        recyclerView = (RecyclerView) view.findViewById(R.id.todos_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<TodoList> realmResults = realm.where(TodoList.class).findAll();
        ViewTaskRealmAdapter viewTaskRealmAdapter = new ViewTaskRealmAdapter(getActivity().getApplicationContext(), realmResults, true);
        recyclerViewAdapter.setRealmModelAdapter(viewTaskRealmAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
