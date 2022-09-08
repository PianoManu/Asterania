package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Null;

import java.util.List;

public class LanguageFileUtils {
    private final String language;
    private final List<TranslatableString> textComponents;

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
}
