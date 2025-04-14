package com.app.mission.mood.click;

import com.app.mission.mood.model.MessageModel;

public interface ClickMessage {

    void onClick(int pos, MessageModel item);
    void onDelete(int pos, MessageModel item);

}
