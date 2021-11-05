package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.world.EntityCoordinates;

public class CursorUtils {
    public static EntityCoordinates cursorToEntityCoordinates(int mouseX, int mouseY, EntityCoordinates playerPos) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        EntityCoordinates mouseECoordinates = new EntityCoordinates((float) mouseX / DisplayConfig.TILE_SIZE, (float) mouseY / DisplayConfig.TILE_SIZE);
        mouseECoordinates.x -= width / (DisplayConfig.TILE_SIZE * 2f) - playerPos.getX();
        mouseECoordinates.y -= height / (DisplayConfig.TILE_SIZE * 2f) - playerPos.getY();
        return mouseECoordinates;
    }
}
