package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.render.SpriteBatchUtils;
import de.pianomanu.asterania.render.atlas.Atlases;
import de.pianomanu.asterania.render.text.TextRenderer;

import java.util.List;

public class ButtonRenderer {
    public static void renderButtons(List<Button> buttons) {
        for (Button b :
                buttons) {
            renderButton(b);
        }
    }

    private static void renderButton(Button button) {
        TextureRegion buttonTexture = AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.BUTTON_ATLAS_LOCATION, TextureAtlas.class).findRegion(button.getTextureName());
        SpriteBatchUtils.getInstance().draw(buttonTexture, (int) button.getStart().x, (int) button.getStart().y, (int) button.getFormat().x, (int) button.getFormat().y);
        TextRenderer.getInstance().renderText((int) (button.getStart().x + button.getFormat().x / 2), ((int) (button.getStart().y + button.getFormat().y / 2)), button.getButtonText().getTextValue());
    }
}
