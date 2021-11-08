package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.entities.hitboxes.SimpleHitbox;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.EntityCoordinates;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class DebugScreenRenderer {
    public static void renderDebugText(World world, int frames) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int xOffset = 4;
        int yOffset = 4;
        TextRenderer.renderText(xOffset, height - yOffset, "FPS: " + frames);

        TextRenderer.renderText(xOffset, height - yOffset - 16, "Position: X=" + world.getPlayer().getCharacterPos().x + ", Y=" + world.getPlayer().getCharacterPos().y);
        TextRenderer.renderText(xOffset, height - yOffset - 32, "Feet position: X=" + world.getPlayer().getFootPos().x + ", Y=" + world.getPlayer().getFootPos().y);

        int mouseX = Gdx.input.getX();
        int mouseY = height - Gdx.input.getY();
        TextRenderer.renderText(xOffset, height - yOffset - 48, "Cursor position: X=" + mouseX + ", Y=" + mouseY);
        EntityCoordinates mouseECoordinates = CoordinatesUtils.pixelToEntityCoordinates(mouseX, mouseY, world.getPlayer().getCharacterPos());
        TextRenderer.renderText(xOffset, height - yOffset - 64, "Cursor position as Game coordinates: X=" + mouseECoordinates.x + ", Y=" + mouseECoordinates.y);
        Tile tile;
        try {
            tile = world.getTile(mouseECoordinates.toTileCoordinates().getX(), mouseECoordinates.toTileCoordinates().getY());
        } catch (ArrayIndexOutOfBoundsException e) {
            tile = Tiles.ROCK;
        }
        TextRenderer.renderText(xOffset, height - yOffset - 80, "Tile at cursor position: " + tile.toString());
    }

    public static void renderHitbox(World world, ShapeRenderer shapeRenderer) {
        Player player = world.getPlayer();
        SimpleHitbox hitbox = player.getPlayerHitbox();
        Vector2 start = CoordinatesUtils.transformEntityCoordinatesToPixels(hitbox.start, player.getCharacterPos());//.add(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        Vector2 end = player.getCharacterSizeInPixels();//.add(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.rect(start.x, start.y, end.x, end.y);
        shapeRenderer.end();
    }
}
