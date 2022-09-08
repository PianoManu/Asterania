package de.pianomanu.asterania.utils.text.language;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Null;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.utils.JsonParserUtils;

import java.util.List;
import java.util.logging.Logger;

public class LanguageFileUtils {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

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
        LOGGER.warning("Could not find translatable string for key " + key); //TODO what if value missing
        return null;
    }

    public static String getLanguageString(String key) {
        TranslatableString str = getInstance().getTextComponent(key);
        return str == null ? "" : str.getTextValue();
    }
}
