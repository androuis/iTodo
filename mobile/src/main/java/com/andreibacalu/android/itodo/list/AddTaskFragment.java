package com.andreibacalu.android.itodo.list;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.andreibacalu.android.itodo.R;
import com.andreibacalu.android.itodo.TodoApplication;
import com.andreibacalu.android.itodo.endpoints.CreateAppEngineEndpoints;
import com.example.abacalu.itodo.backend.messaging.Messaging;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {


    private EditText task;
    private Button addTask;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        task = (EditText) view.findViewById(R.id.task);
        task.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    addTask.setEnabled(false);
                } else {
                    addTask.setEnabled(true);
                }
            }
        });
        addTask = (Button) view.findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskButtonPressed(task.getText().toString());
            }
        });
    }

    private void addTaskButtonPressed(String message) {
        new AsyncTask<String, Void, Void>() {

            ProgressDialog progressDialog = new ProgressDialog(TodoApplication.getInstance().getApplicationContext());

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
