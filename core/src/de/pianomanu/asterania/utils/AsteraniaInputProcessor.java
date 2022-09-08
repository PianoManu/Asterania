package de.pianomanu.asterania.utils;

import com.badlogic.gdx.InputProcessor;
import de.pianomanu.asterania.lifecycle.PlayerUpdates;
import de.pianomanu.asterania.utils.text.TextInput;

public class AsteraniaInputProcessor implements InputProcessor {
    private static final TextInput textInput = new TextInput();

    public static TextInput getTextInput() {
        return textInput;
    }

    @Override
    public boolean keyDown(int keycode) {
        textInput.setLastPressedKey(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        textInput.setLastReleasedKey(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (textInput.isValidCharacter(character)) {
            textInput.setLastTypedChar(character);
            textInput.setCharacterHasBeenTyped(true);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (amountY > 0) {
            PlayerUpdates.setTimesScrolled(PlayerUpdates.getTimesScrolled() + 1);
            return true;
        }
        if (amountY < 0) {
            PlayerUpdates.setTimesScrolled(PlayerUpdates.getTimesScrolled() - 1);
            return true;
        }
        return false;
    }
}
