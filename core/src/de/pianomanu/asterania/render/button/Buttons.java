package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Buttons {
    public static final Button START_BUTTON = new Button(Button.BUTTON64x8FORMAT, "Start Game");
    public static final Button EXIT_BUTTON = new Button(Button.BUTTON64x8FORMAT, "Exit Game");

    public static void setup() {
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        START_BUTTON.scale(5).moveToCentered(center.x, center.y - START_BUTTON.getFormat().y * 2);
        EXIT_BUTTON.moveToCentered(center.x, center.y - EXIT_BUTTON.getFormat().y * 4);
    }

    public static void reloadButtons() {
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        START_BUTTON.moveToCentered(center.x, center.y - START_BUTTON.getFormat().y * 2);
        EXIT_BUTTON.moveToCentered(center.x, center.y - EXIT_BUTTON.getFormat().y * 4);
    }
}
