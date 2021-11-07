package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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

    public static Vector2 transformCursorEntityCoordinatesToPixels(EntityCoordinates entityCoordinates, EntityCoordinates playerPos) {
        Vector2 pixel = new Vector2();
        //Player in the middle of the screen
        int middleX = Gdx.graphics.getWidth() / 2;
        int middleY = Gdx.graphics.getHeight() / 2;
        EntityCoordinates diff = new EntityCoordinates(entityCoordinates.x - playerPos.x, entityCoordinates.y - playerPos.y);
        int offsetX = (int) (diff.x * DisplayConfig.TILE_SIZE);
        int offsetY = (int) (diff.y * DisplayConfig.TILE_SIZE);
        pixel.x = middleX + offsetX;
        pixel.y = middleY + offsetY;
        return pixel;
    }
}
