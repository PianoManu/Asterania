package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public static final List<Button> ALL_BUTTONS = new ArrayList<>();
    public static final Button START_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Start Game"));
    public static final Button LOAD_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Load Game"));
    public static final Button EXIT_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Exit Game"));

    private static Button r(Button b) {
        ALL_BUTTONS.add(b);
        return b;
    }

    public static void setup() {
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        ALL_BUTTONS.get(0).scale(5);
        for (int i = 0; i < ALL_BUTTONS.size(); i++) {
            ALL_BUTTONS.get(i).moveToCentered(center.x, center.y - ALL_BUTTONS.get(i).getFormat().y * (2 + 1.5f * i));
        }
    }

    public static void reloadButtons() {
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        for (int i = 0; i < ALL_BUTTONS.size(); i++) {
            ALL_BUTTONS.get(i).moveToCentered(center.x, center.y - ALL_BUTTONS.get(i).getFormat().y * (2 + 1.5f * i));
        }
    }
}
