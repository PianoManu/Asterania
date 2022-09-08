package de.pianomanu.asterania.utils.fileutils;

import java.util.ArrayList;
import java.util.List;

public class ParserUtils {
    public static List<String> splitString(String playerData, char separatingCharacter) {
        List<String> lines = new ArrayList<>();
        int prevLineBreak = 0;
        for (int i = 0; i < playerData.length(); i++) {
            if (playerData.charAt(i) == separatingCharacter) {
                String sub = playerData.substring(prevLineBreak, i);
                if (!sub.isEmpty())
                    lines.add(sub);
                prevLineBreak = i + 1;
            }
            if (i == playerData.length() - 1) {
                String sub = playerData.substring(prevLineBreak, i + 1);
                if (!sub.isEmpty())
                    lines.add(sub);
            }
        }
        return lines;
    }
}
