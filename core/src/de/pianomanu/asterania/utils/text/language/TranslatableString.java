package de.pianomanu.asterania.utils.text.language;

public class TranslatableString {

    private final String key;
    private final String textValue;

    public TranslatableString(String key, String value) {
        this.key = key;
        this.textValue = value;
    }

    public String getKey() {
        return key;
    }

    public String getTextValue() {
        return textValue;
    }
}
