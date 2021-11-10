package de.pianomanu.asterania.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

public class CoordinatesUtils {
    public static EntityCoordinates pixelToEntityCoordinates(int pixelX, int pixelY, EntityCoordinates playerPos) {
        pixelY = Gdx.graphics.getHeight() - pixelY;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        EntityCoordinates mouseECoordinates = new EntityCoordinates((float) pixelX / DisplayConfig.TILE_SIZE, (float) pixelY / DisplayConfig.TILE_SIZE);
        mouseECoordinates.x -= width / (DisplayConfig.TILE_SIZE * 2f) - playerPos.getX();
        mouseECoordinates.y -= height / (DisplayConfig.TILE_SIZE * 2f) - playerPos.getY();
        return mouseECoordinates;
    }

    public static Vector2 transformEntityCoordinatesToPixels(EntityCoordinates entityCoordinates, EntityCoordinates playerPos) {
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

    public static Vector2 transformTileCoordinatesToPixels(TileCoordinates tileCoordinates, EntityCoordinates playerPos) {
        Vector2 pixel = new Vector2();
        //Player in the middle of the screen
        int middleX = Gdx.graphics.getWidth() / 2;
        int middleY = Gdx.graphics.getHeight() / 2;
        EntityCoordinates diff = new EntityCoordinates(tileCoordinates.getX() - playerPos.x, tileCoordinates.getY() - playerPos.y);
        int offsetX = (int) (diff.x * DisplayConfig.TILE_SIZE);
        int offsetY = (int) (diff.y * DisplayConfig.TILE_SIZE);
        pixel.x = middleX + offsetX;
        pixel.y = middleY + offsetY;
        return pixel;
    }
}
