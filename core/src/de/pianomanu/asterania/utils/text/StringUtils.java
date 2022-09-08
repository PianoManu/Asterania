package de.pianomanu.asterania.utils.text;

public class StringUtils {
    public static int countSymbolOccurances(String str, char symbol) {
        int occurances = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == symbol)
                occurances++;
        }
        return occurances;
    }
}
