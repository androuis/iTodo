package todolist.item;

import com.andreibacalu.android.itodo.user.User;

import io.realm.RealmObject;

public class Item extends RealmObject {
    private long id;
    private String description;
    private User createdBy;

    public Item() {
    }

    public Item(long id, String description, User createdBy) {
        this.id = id;
        this.description = description;
        this.createdBy = createdBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
