package com.andreibacalu.android.itodo.todolist.item;

import com.andreibacalu.android.itodo.user.User;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Item extends RealmObject {
    @Required
    private String description;
    private User createdBy;

    public Item() {
    }

    public Item(String description, User createdBy) {
        this.description = description;
        this.createdBy = createdBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
