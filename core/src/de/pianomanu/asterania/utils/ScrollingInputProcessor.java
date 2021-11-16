package de.pianomanu.asterania.utils;

import com.badlogic.gdx.InputProcessor;
import de.pianomanu.asterania.lifecycle.PlayerUpdates;

public class ScrollingInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
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
