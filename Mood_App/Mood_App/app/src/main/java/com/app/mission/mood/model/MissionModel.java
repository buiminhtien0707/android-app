package com.app.mission.mood.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MissionModel {

    private String id;
    private String mood_id;
    private String content;


    public MissionModel() {
    }

    public MissionModel(String id, String mood_id, String content) {
        this.id = id;
        this.mood_id = mood_id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMood_id() {
        return mood_id;
    }

    public void setMood_id(String mood_id) {
        this.mood_id = mood_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
