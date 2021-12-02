package de.pianomanu.asterania.render.button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Buttons {
    public static final List<Button> ALL_BUTTONS = new ArrayList<>();
    public static final List<Button> MAIN_MENU_BUTTONS = new ArrayList<>();
    public static final List<Button> LOAD_SAVES_MENU_BUTTONS = new ArrayList<>();
    public static final Button CREATE_NEW_GAME_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Create new Game"));
    public static final Button LOAD_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Load Game"));
    public static final Button EXIT_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Exit Game"));
    public static final Button START_GAME_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Start Game"));
    public static final Button BACK_TO_MAIN_MENU_BUTTON = r(new Button(Button.BUTTON64x8FORMAT, "Back to main menu"));

    private static Button r(Button b) {
        ALL_BUTTONS.add(b);
        return b;
    }

    public static void setup() {
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        MAIN_MENU_BUTTONS.add(CREATE_NEW_GAME_BUTTON);
        MAIN_MENU_BUTTONS.add(LOAD_BUTTON);
        MAIN_MENU_BUTTONS.add(EXIT_BUTTON);

        MAIN_MENU_BUTTONS.get(0).scale(5);
        for (int i = 0; i < MAIN_MENU_BUTTONS.size(); i++) {
            MAIN_MENU_BUTTONS.get(i).moveToCentered(center.x, center.y - MAIN_MENU_BUTTONS.get(i).getFormat().y * (2 + 1.5f * i));
        }

        BACK_TO_MAIN_MENU_BUTTON.moveToCentered(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
        START_GAME_BUTTON.moveToCentered(Gdx.graphics.getWidth() * 3 / 4, Gdx.graphics.getHeight() / 10);
        LOAD_SAVES_MENU_BUTTONS.add(START_GAME_BUTTON);
        LOAD_SAVES_MENU_BUTTONS.add(BACK_TO_MAIN_MENU_BUTTON);
    }

    public static void reloadButtons() {
        Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        for (int i = 0; i < MAIN_MENU_BUTTONS.size(); i++) {
            MAIN_MENU_BUTTONS.get(i).moveToCentered(center.x, center.y - MAIN_MENU_BUTTONS.get(i).getFormat().y * (2 + 1.5f * i));
        }

        BACK_TO_MAIN_MENU_BUTTON.moveToCentered(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10);
        START_GAME_BUTTON.moveToCentered(Gdx.graphics.getWidth() * 3 / 4, Gdx.graphics.getHeight() / 10);
    }
}
