package com.andreibacalu.android.itodo.task;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.endpoints.CreateAppEngineEndpoints;
import com.andreibacalu.android.itodo.task.adapters.AddTaskAdapter;
import com.andreibacalu.android.itodo.todolist.item.Item;
import com.andreibacalu.android.itodo.user.User;
import com.andreibacalu.android.itodo.utils.ModelUtils;
import com.example.abacalu.itodo.backend.messaging.Messaging;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;

import com.andreibacalu.android.itodo.todolist.TodoList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment implements OnBackPressedListener {

    public static final String BACKSTACK_NAME = AddTaskFragment.class.getSimpleName();

    private EditText titleTask;
    private RecyclerView recyclerView;
    private AddTaskAdapter recyclerViewAdapter;
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
        setupViewListeners(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    private void setupViews(View rootView) {
        titleTask = (EditText) rootView.findViewById(R.id.add_title_task);
        recyclerViewAdapter = new AddTaskAdapter();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setupViewListeners(View view) {

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

    @Override
    public void onBackPressed() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TodoList todoList = realm.createObject(TodoList.class);
                todoList.setId(realm.allObjects(TodoList.class).size() + 1);
                todoList.setName(titleTask.getText().toString());
                Set<String> items = recyclerViewAdapter.getTasks();
                RealmList<Item> realmList = new RealmList<>();
                for (String itemString: items) {
                    Item item = realm.createObject(Item.class);
                    item.setCreatedBy(ModelUtils.getMyUser(realm));
                    item.setDescription(itemString);
                    realmList.add(item);
                }
                todoList.setItems(realmList);
                todoList.setCreatedBy(ModelUtils.getMyUser(realm));
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onError(Exception e) {
                super.onError(e);
                e.printStackTrace();
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                getFragmentManager().popBackStack();
            }
        });
    }
}
