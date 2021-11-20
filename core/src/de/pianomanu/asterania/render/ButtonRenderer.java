package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.render.button.Button;
import de.pianomanu.asterania.render.button.Buttons;
import de.pianomanu.asterania.render.text.TextRenderer;

public class ButtonRenderer {
    public static void renderMainMenuButtons(SpriteBatch batch) {
        //renderExitButton(batch);
        renderButton(batch, Buttons.EXIT_BUTTON);
        renderButton(batch, Buttons.START_BUTTON);
    }

    private static void renderExitButton(SpriteBatch batch) {
        TextureRegion exitButton = AsteraniaMain.assetManager.get(Atlases.BUTTON_ATLAS_LOCATION, TextureAtlas.class).findRegion(Button.BUTTON64x8NAME);
        int widthMiddle = Gdx.graphics.getWidth() / 2;
        int heightMiddle = Gdx.graphics.getHeight() / 2;
        int startX = widthMiddle - widthMiddle / 4;
        int startY = heightMiddle + heightMiddle / 4;
        batch.begin();
        batch.draw(exitButton, startX, startY, widthMiddle / 2f, heightMiddle / 2f);
        batch.end();
    }

    private static void renderButton(SpriteBatch batch, Button button) {
        batch.begin();
        TextureRegion buttonTexture = AsteraniaMain.assetManager.get(Atlases.BUTTON_ATLAS_LOCATION, TextureAtlas.class).findRegion(button.getTextureName());
        batch.draw(buttonTexture, button.getStart().x, button.getStart().y, button.getFormat().x, button.getFormat().y);
        batch.end();
        TextRenderer.renderText((int) (button.getStart().x + button.getFormat().x / 2), ((int) (button.getStart().y + button.getFormat().y / 2)), button.getButtonText());
    }
}
