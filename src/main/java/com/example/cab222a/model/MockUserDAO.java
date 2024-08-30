package com.example.cab222a.model;

import java.util.ArrayList;
import java.util.List;

public class MockUserDAO implements IUserDAO {
    public static final ArrayList<User> items = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addItem(User item) {
        item.setId(autoIncrementedId);
        autoIncrementedId++;
        items.add(item);
    }

    @Override
    public void updateItem(User item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                items.set(i, item);
                break;
            }
        }
    }

    @Override
    public void deleteItem(User item) {
        items.remove(item);
    }

    @Override
    public User getItem(int id) {
        for (User user : items) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllItems() {
        return new ArrayList<>(items);
    }
}