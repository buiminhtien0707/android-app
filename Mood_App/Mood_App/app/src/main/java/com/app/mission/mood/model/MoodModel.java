package com.app.mission.mood.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MoodModel {

    private String id;
    private String name;

    public MoodModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MoodModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
