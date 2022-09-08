package de.pianomanu.asterania.utils;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import de.pianomanu.asterania.utils.fileutils.FileIO;
import de.pianomanu.asterania.utils.text.language.TranslatableString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonParserUtils {
    private static final JsonReader jsonReader = new JsonReader();
    private static final String nullValue = "\0";

    public static JsonValue readJsonFile(File file) {
        String jsonContentRaw = String.valueOf(FileIO.readFile(file)).strip();
        String jsonContent = removeNullValues(jsonContentRaw);
        return jsonReader.parse(jsonContent);
    }

    public static List<TranslatableString> readLanguageFile(File file) {
        JsonValue baseJsonValue = readJsonFile(file);
        List<TranslatableString> translatableStrings = new ArrayList<>();
        for (int i = 0; i < baseJsonValue.size; i++) {
            if (baseJsonValue.get(i).isString()) {
                translatableStrings.add(createTranslatableStringFromJsonValue(baseJsonValue, i));
            }
        }
        return translatableStrings;
    }

    public static TranslatableString createTranslatableStringFromJsonValue(JsonValue jsonValue, int pos) {
        String key = jsonValue.get(pos).name;
        String value = jsonValue.getString(pos);
        return new TranslatableString(key, value);
    }

    private static String removeNullValues(String jsonContent) {
        if (jsonContent.contains(nullValue)) {
            int firstNull = jsonContent.indexOf(nullValue);
            return jsonContent.substring(0, firstNull);
        }
        return jsonContent;
    }
}
