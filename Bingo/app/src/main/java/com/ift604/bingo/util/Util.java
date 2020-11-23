package com.ift604.bingo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ift604.bingo.model.Participant;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    public static final ArrayList<String> bingo = new ArrayList(Arrays.asList("B", "I", "N", "G", "O"));
    public static final String SharedPreferenceUserId = "USER_ID";
    public static String formatPlayerName(Participant player) {
        return String.format("%s %s", player.getFirstName(), player.getLastName());
    }

    public static SharedPreferences sharedPref(Context context) {
        return  context.getSharedPreferences(
                "bingo_shared_pref", Context.MODE_PRIVATE);
    }

    public static int  getConnectedUserId(Context context) {
        return sharedPref(context).getInt(SharedPreferenceUserId, 0);
    }
}
