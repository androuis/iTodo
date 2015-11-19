package com.andreibacalu.android.itodo.task;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.activities.TodosActivity;
import com.andreibacalu.android.itodo.endpoints.CreateAppEngineEndpoints;
import com.andreibacalu.android.itodo.task.adapters.AddTaskAdapter;
import com.andreibacalu.android.itodo.user.User;
import com.example.abacalu.itodo.backend.messaging.Messaging;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import todolist.TodoList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {

    public static final String BACKSTACK_NAME = AddTaskFragment.class.getSimpleName();

    private EditText titleTask;
    private RecyclerView recyclerView;
    private Realm realm;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddTaskFragment.
     */
    public static AddTaskFragment newInstance() {
        AddTaskFragment fragment = new AddTaskFragment();
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
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupViews(view);
        setupViewListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TodoList todoList = new TodoList();
                todoList.setId(realm.allObjects(TodoList.class).size() + 1);
                todoList.setName(titleTask.getText().toString());
                todoList.setCreatedBy(User.ANONYMOUS);
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onError(Exception e) {
                super.onError(e);
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
            }
        });
        realm.close();
        super.onDestroy();
    }

    private void setupViews(View rootView) {
        titleTask = (EditText) rootView.findViewById(R.id.add_title_task);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new AddTaskAdapter());
    }

    private void setupViewListeners() {

    }

    private void addTaskButtonPressed(String message) {
        new AsyncTask<String, Void, Void>() {

            ProgressDialog progressDialog = new ProgressDialog(getActivity());

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(String[] params) {
                if (params.length == 1) {
                    Messaging messaging = CreateAppEngineEndpoints.createMessagingEndpoint();
                    try {
                        messaging.messagingEndpoint().sendMessage(params[0]).execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
            }
        }.execute(message);
    }
}
