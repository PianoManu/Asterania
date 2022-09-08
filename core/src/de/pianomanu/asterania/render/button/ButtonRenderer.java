package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.render.atlas.Atlases;
import de.pianomanu.asterania.render.text.TextRenderer;

import java.util.List;

public class ButtonRenderer {
    public static void renderButtons(SpriteBatch batch, List<Button> buttons) {
        for (Button b :
                buttons) {
            renderButton(batch, b);
        }
    }

    private static void renderButton(SpriteBatch batch, Button button) {
        batch.begin();
        TextureRegion buttonTexture = AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.BUTTON_ATLAS_LOCATION, TextureAtlas.class).findRegion(button.getTextureName());
        batch.draw(buttonTexture, button.getStart().x, button.getStart().y, button.getFormat().x, button.getFormat().y);
        batch.end();
        TextRenderer.getInstance().renderText((int) (button.getStart().x + button.getFormat().x / 2), ((int) (button.getStart().y + button.getFormat().y / 2)), button.getButtonText().getTextValue());
    }
}
