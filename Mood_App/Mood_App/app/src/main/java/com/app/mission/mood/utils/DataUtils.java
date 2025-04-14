package com.app.mission.mood.utils;

import com.app.mission.mood.model.MessageModel;
import com.app.mission.mood.model.MissionModel;
import com.app.mission.mood.model.MoodModel;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class DataUtils {

    public static ArrayList<MoodModel> listMood = new ArrayList<>();

    public static ArrayList<MessageModel> listMessage = new ArrayList<>();
    public static ArrayList<MissionModel> listMission = new ArrayList<>();

    public static MoodModel clickMood = new MoodModel();


    public static String deAccent(String data) {
        String nfdNormalizedString = Normalizer.normalize(data, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").replace("@", "");
    }



}
