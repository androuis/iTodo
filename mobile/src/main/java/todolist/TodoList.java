package todolist;

import com.andreibacalu.android.itodo.user.User;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import todolist.item.Item;

public class TodoList extends RealmObject {
    @PrimaryKey
    private long id;
    @Required
    private String name;
    private RealmList<Item> items;
    private User createdBy;
    private RealmList<User> accessibleBy;

    public TodoList() {
    }

    public TodoList(long id, String name, User createdBy) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
    }

    public TodoList(long id, String name, RealmList<Item> items, User createdBy) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.createdBy = createdBy;
    }

    public TodoList(long id, String name, RealmList<Item> items, User createdBy, RealmList<User> accessibleBy) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.createdBy = createdBy;
        this.accessibleBy = accessibleBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Item> getItems() {
        return items;
    }

    public void setItems(RealmList<Item> items) {
        this.items = items;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public RealmList<User> getAccessibleBy() {
        return accessibleBy;
    }

    public void setAccessibleBy(RealmList<User> accessibleBy) {
        this.accessibleBy = accessibleBy;
    }
}
