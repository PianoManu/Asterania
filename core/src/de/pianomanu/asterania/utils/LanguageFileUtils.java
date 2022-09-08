package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Null;
import de.pianomanu.asterania.AsteraniaMain;

import java.util.List;

public class LanguageFileUtils {
    private final String language;
    private final List<TranslatableString> textComponents;

    public static LanguageFileUtils getInstance() {
        return AsteraniaMain.INSTANCE.getLanguageFileUtils();
    }

    public LanguageFileUtils(String language) {
        this.language = language;
        this.textComponents = JsonParserUtils.readLanguageFile(Gdx.files.internal("lang\\" + language + ".json").file());
    }

    public String getLanguage() {
        return this.language;
    }

    public List<TranslatableString> getTextComponents() {
        return this.textComponents;
    }

    @Null
    public TranslatableString getTextComponent(String key) {
        for (TranslatableString t :
                this.getTextComponents()) {
            if (t.getKey().equals(key))
                return t;
        }
        return null;
    }

    public static String getLanguageString(String key) {
        TranslatableString str = getInstance().getTextComponent(key);
        return str == null ? "" : str.getTextValue();
    }
}
