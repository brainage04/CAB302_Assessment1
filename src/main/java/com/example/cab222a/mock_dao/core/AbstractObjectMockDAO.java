package com.example.cab222a.mock_dao.core;

import com.example.cab222a.dao.core.IObjectDAO;
import com.example.cab222a.model.core.IdentifiedObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for a Data Access Object that handles the
 * CRUD operations for generic classes within memory.
 */
public class AbstractObjectMockDAO<T extends IdentifiedObject> implements IObjectDAO<T> {
    protected List<T> items;
    protected int autoIncrementedId;

    public AbstractObjectMockDAO() {
        resetTable();
    }

    public int addItem(T item) {
        item.setId(autoIncrementedId);
        items.add(item);
        autoIncrementedId++;
        return autoIncrementedId - 1;
    }

    public T getItem(int id) {
        for (T item : items) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    public List<T> getAllItems() {
        return items;
    }

    public void updateItem(T item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                items.set(i, item);
            }
        }
    }

    public void deleteItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
            }
        }
    }

    public void resetTable() {
        items = new ArrayList<>();
        autoIncrementedId = 1;
    }
}