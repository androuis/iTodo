package com.andreibacalu.android.itodo.utils;

import android.text.TextUtils;

import com.andreibacalu.android.itodo.todolist.TodoList;
import com.andreibacalu.android.itodo.todolist.item.Item;
import com.andreibacalu.android.itodo.user.User;

import java.util.List;

import io.realm.Realm;

public class ModelUtils {

    public static String getItemsAsString(TodoList todoList) {
        List<Item> items = todoList.getItems();
        StringBuilder sb = new StringBuilder();
        Item item;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            sb.append(item.getDescription());
            if (i != items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static String getDisplayName(User user) {
        String displayName = user.getFirstName();
        if (!TextUtils.isEmpty(user.getLastName())) {
            displayName += TextUtils.isEmpty(displayName) ? user.getLastName() : " " + user.getLastName();
        }
        if (TextUtils.isEmpty(displayName)) {
            displayName = user.getUserName();
        }
        return displayName;
    }

    public static User getMyUser(Realm realm) {
        //FIXME: return Anonymous when user is not logged in and actual user after login (hint: use id: 0) and also cache it!!!
        return realm.where(User.class).findFirst();
    }
}
