package com.andreibacalu.android.itodo.user;

import android.location.Location;
import android.text.TextUtils;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import com.andreibacalu.android.itodo.todolist.TodoList;

public class User extends RealmObject {

    public static final User ANONYMOUS = new User("anonymous", "anonymous", "anonymous", new Location(""));

    @PrimaryKey
    private String userName;
    @Required
    private String firstName;
    @Required
    private String lastName;
    private RealmList<TodoList> lists;
    @Ignore
    private Location location;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String firstName, String lastName) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String userName, String firstName, String lastName, Location location) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RealmList<TodoList> getLists() {
        return lists;
    }

    public void setLists(RealmList<TodoList> lists) {
        this.lists = lists;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
