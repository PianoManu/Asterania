package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.SpriteBatchUtils;
import de.pianomanu.asterania.render.atlas.Atlases;
import de.pianomanu.asterania.render.text.TextRenderer;

import java.util.List;

public class ButtonRenderer {
    public static void renderButtons(List<Button> buttons) {
        for (Button b :
                buttons) {
            renderButton(b);
            renderHovering(b);
        }
    }

    private static void renderButton(Button button) {
        TextureRegion buttonTexture = AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.BUTTON_ATLAS_LOCATION, TextureAtlas.class).findRegion(button.getTextureName());
        SpriteBatchUtils.getInstance().draw(buttonTexture, (int) button.getStart().x, (int) button.getStart().y, (int) button.getFormat().x, (int) button.getFormat().y);
        TextRenderer.getInstance().renderText((int) (button.getStart().x + button.getFormat().x / 2), ((int) (button.getStart().y + button.getFormat().y / 2)), button.getButtonText().getTextValue());
    }

    public static void renderHovering(Button button) {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        ShapeRendererUtils.enableTransparency();
        if (mouseX >= button.getStart().x && mouseY >= button.getStart().y && mouseX <= button.getEnd().x && mouseY <= button.getEnd().y) {
            ShapeRendererUtils.getInstance().rect(button.getStart().x, button.getStart().y, button.getFormat().x, button.getFormat().y, new Color(1, 1, 1, 0.2f));
        }
        ShapeRendererUtils.disableTransparency();
    }
}
