package com.ift604.bingo.util;

import com.ift604.bingo.model.Participant;

import java.util.ArrayList;
import java.util.Arrays;

public class Util {
    public static final ArrayList<String> bingo = new ArrayList(Arrays.asList("B", "I", "N", "G", "O"));

    public static String formatPlayerName(Participant player) {
        return String.format("%s %s", player.getFirstName(), player.getLastName());
    }
}
