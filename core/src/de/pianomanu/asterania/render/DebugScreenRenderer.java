package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.entities.hitboxes.SimpleHitbox;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.ArrayList;
import java.util.List;

public class DebugScreenRenderer {
    private static float deltaCounter = 0;
    private static int passCounter = 0;
    private static int fps = 0;

    private static ShapeRenderer gridRenderer = new ShapeRenderer();

    public static void reloadGridRenderer() {
        gridRenderer.dispose();
        gridRenderer = new ShapeRenderer();
    }

    public static void render(World world, ShapeRenderer shapeRenderer, float delta) {
        calculateFPS(delta);

        DebugScreenRenderer.renderHitbox(world, shapeRenderer);
        DebugScreenRenderer.renderGrid(world);
        DebugScreenRenderer.renderDebugText(world, fps);
    }

    private static void calculateFPS(float delta) {
        passCounter++;
        deltaCounter += delta;

        if (deltaCounter > 1) {
            deltaCounter--;
            fps = passCounter;
            passCounter = 0;
        }
    }

    private static void renderDebugText(World world, int frames) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int xOffset = 4;
        int yOffset = 4;

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        EntityCoordinates mouseECoordinates = CoordinatesUtils.pixelToEntityCoordinates(mouseX, mouseY, world.getPlayer().getCharacterPos());

        Tile tile;
        try {
            tile = world.findSection(mouseECoordinates).getTile(mouseECoordinates.toTileCoordinates().getX(), mouseECoordinates.toTileCoordinates().getY());
        } catch (ArrayIndexOutOfBoundsException e) {
            tile = Tiles.ROCK;
        }

        List<String> debugStuff = new ArrayList<>();
        debugStuff.add("FPS: " + frames);
        debugStuff.add("Position: X=" + world.getPlayer().getCharacterPos().x + ", Y=" + world.getPlayer().getCharacterPos().y);
        debugStuff.add("Feet position: X=" + world.getPlayer().getFootPos().x + ", Y=" + world.getPlayer().getFootPos().y);
        debugStuff.add("Cursor position: X=" + mouseX + ", Y=" + (height - mouseY));
        debugStuff.add("Cursor position as Game coordinates: X=" + mouseECoordinates.x + ", Y=" + mouseECoordinates.y);
        debugStuff.add("Tile at cursor position: " + tile.toString());
        debugStuff.add("Hitbox position: X_1=" + world.getPlayer().getPlayerHitbox().start.x + ", Y_1=" + world.getPlayer().getPlayerHitbox().start.y + "; X_2=" + world.getPlayer().getPlayerHitbox().end.x + ", Y_2=" + world.getPlayer().getPlayerHitbox().end.y);

        for (int i = 0; i < debugStuff.size(); i++) {
            if (i < debugStuff.size() - 1)
                TextRenderer.renderText(xOffset, height - yOffset - 16 * i, debugStuff.get(i), false, DisplayConfig.TEXT_SIZE, false, Color.WHITE, null);
            else
                TextRenderer.renderText(xOffset, 20, debugStuff.get(i), false, DisplayConfig.TEXT_SIZE, false, Color.WHITE, null);
        }
    }

    private static void renderGrid(World world) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        gridRenderer.begin(ShapeRenderer.ShapeType.Line);
        gridRenderer.setColor(0, 0, 0, 1);
        EntityCoordinates playerCoordinates = world.getPlayer().getCharacterPos();
        WorldSectionCoordinates centerSection = playerCoordinates.toWorldSectionCoordinates();
        WorldSectionCoordinates bottomleftSection = new WorldSectionCoordinates(centerSection.x - 1, centerSection.y - 1);
        WorldSectionCoordinates topRightSection = new WorldSectionCoordinates(centerSection.x + 1, centerSection.y + 1);
        TileCoordinates bottomLeftTile = bottomleftSection.startToTileCoordinates();
        TileCoordinates topRightTile = topRightSection.endToTileCoordinates();
        for (int x = bottomLeftTile.getX(); x < topRightTile.getX(); x++) {
            for (int y = bottomLeftTile.getY(); y < topRightTile.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                gridRenderer.line(0, yTile, width, yTile);
                gridRenderer.line(xTile, 0, xTile, height);
            }
        }
        gridRenderer.end();
    }

    private static void renderHitbox(World world, ShapeRenderer shapeRenderer) {
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
