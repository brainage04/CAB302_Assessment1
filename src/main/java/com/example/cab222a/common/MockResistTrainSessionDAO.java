package com.example.cab222a.common;

import java.util.ArrayList;
import java.util.List;

public class MockResistTrainSessionDAO implements IResistTrainSessionDAO {
    public static final ArrayList<ResistTrainSession> items = new ArrayList<>();
    private static int autoIncrementedId = 0;

    @Override
    public void addItem(ResistTrainSession item) {
        item.setId(autoIncrementedId);
        autoIncrementedId++;
        items.add(item);
    }

    @Override
    public void updateItem(ResistTrainSession item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == item.getId()) {
                items.set(i, item);
                break;
            }
        }
    }

    @Override
    public void deleteItem(ResistTrainSession item) {
        items.remove(item);
    }

    @Override
    public ResistTrainSession getItem(int id) {
        for (ResistTrainSession item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<ResistTrainSession> getAllItems() {
        return new ArrayList<>(items);
    }
}