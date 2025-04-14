package com.app.mission.mood.click;


import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.model.MoodModel;

public interface ClickMission {

    void onClick(int pos, MissionModel item);
    void onDelete(int pos, MissionModel item);

}
