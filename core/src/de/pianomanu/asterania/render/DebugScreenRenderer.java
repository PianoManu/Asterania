package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.RendererUtils;
import de.pianomanu.asterania.utils.math.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.entities.hitboxes.SimpleHitbox;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.ArrayList;
import java.util.List;

public class DebugScreenRenderer {
    private static float deltaCounter = 0;
    private static int passCounter = 0;
    private static int fps = 0;

    public static void render(World world, Player player, float delta) {
        calculateFPS(delta);

        //DebugScreenRenderer.renderGrid(player);
        DebugScreenRenderer.renderHitbox(player);
        DebugScreenRenderer.renderDebugText(world, player, fps);
        DebugScreenRenderer.renderReachCircle(player);
        //DebugScreenRenderer.renderCenterDot(player); //TODO causes lag drop, fix this
    }

    private static void renderReachCircle(Player player) {
        float reach = player.getReach(); //reach: radius
        float diam = 2 * reach; //diameter = 2*r
        Gdx.gl.glLineWidth(3);
        RendererUtils.getInstance().ellipse((Gdx.graphics.getWidth() - diam * DisplayConfig.TILE_SIZE) / 2f, (Gdx.graphics.getHeight() - diam * DisplayConfig.TILE_SIZE) / 2f, diam * DisplayConfig.TILE_SIZE, diam * DisplayConfig.TILE_SIZE, Color.GREEN, ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(1);
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

    private static void renderDebugText(World world, Player player, int frames) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int xOffset = 4;
        int yOffset = 4;

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();

        EntityCoordinates mouseECoordinates = CoordinatesUtils.pixelToEntityCoordinates(mouseX, mouseY, player.getPos());

        Tile tile;
        try {
            tile = world.findSection(mouseECoordinates).getTileAbsoluteCoordinates(mouseECoordinates.toTileCoordinates().getX(), mouseECoordinates.toTileCoordinates().getY());
        } catch (ArrayIndexOutOfBoundsException e) {
            tile = Tiles.ROCK;
        }

        List<String> debugStuff = new ArrayList<>();
        debugStuff.add("FPS: " + frames);
        //debugStuff.add("Position: X=" + player.getCharacterPos().x + ", Y=" + player.getCharacterPos().y);
        debugStuff.add("Feet position: X=" + player.getPos().x + ", Y=" + player.getPos().y);
        debugStuff.add("Cursor position: X=" + mouseX + ", Y=" + (height - mouseY));
        debugStuff.add("Cursor position as Game coordinates: X=" + mouseECoordinates.x + ", Y=" + mouseECoordinates.y);
        debugStuff.add("Tile at cursor position: " + tile.toString());
        debugStuff.add("Hitbox position: X_1=" + player.getPlayerHitbox().start.x + ", Y_1=" + player.getPlayerHitbox().start.y + "; X_2=" + player.getPlayerHitbox().end.x + ", Y_2=" + player.getPlayerHitbox().end.y);

        for (int i = 0; i < debugStuff.size(); i++) {
            if (i < debugStuff.size() - 1)
                TextRenderer.getInstance().renderText(xOffset, height - yOffset - 16 * i, debugStuff.get(i), false, DisplayConfig.TEXT_SIZE, false, Color.WHITE, null);
            else
                TextRenderer.getInstance().renderText(xOffset, 20, debugStuff.get(i), false, DisplayConfig.TEXT_SIZE, false, Color.WHITE, null);
        }
    }

    private static void renderGrid(Player player) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        RendererUtils.getInstance().begin(ShapeRenderer.ShapeType.Line);
        EntityCoordinates playerCoordinates = player.getPos();
        WorldSectionCoordinates centerSection = playerCoordinates.toWorldSectionCoordinates();
        WorldSectionCoordinates bottomleftSection = new WorldSectionCoordinates(centerSection.x - 1, centerSection.y - 1);
        WorldSectionCoordinates topRightSection = new WorldSectionCoordinates(centerSection.x + 1, centerSection.y + 1);
        TileCoordinates bottomLeftTile = bottomleftSection.startToTileCoordinates();
        TileCoordinates topRightTile = topRightSection.endToTileCoordinates();
        for (int x = bottomLeftTile.getX(); x < topRightTile.getX(); x++) {
            for (int y = bottomLeftTile.getY(); y < topRightTile.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                RendererUtils.getInstance().linePlain(0, yTile, width, yTile, Color.BLACK);
                RendererUtils.getInstance().linePlain(xTile, 0, xTile, height, Color.BLACK);
            }
        }
        RendererUtils.getInstance().end();
    }

    private static void renderHitbox(Player player) {
        SimpleHitbox hitbox = player.getPlayerHitbox();
        Vector2 start = CoordinatesUtils.transformEntityCoordinatesToPixels(hitbox.start, player.getPos());
        Vector2 end = player.getCharacterSizeInPixels();
        RendererUtils.getInstance().rect(start.x, start.y, end.x, end.y, Color.RED, ShapeRenderer.ShapeType.Line);
    }

    private static void renderCenterDot(Player player) {
        RendererUtils.getInstance().begin(ShapeRenderer.ShapeType.Line);
        EntityCoordinates playerCoordinates = player.getPos();
        WorldSectionCoordinates centerSection = playerCoordinates.toWorldSectionCoordinates();
        WorldSectionCoordinates bottomleftSection = new WorldSectionCoordinates(centerSection.x - 1, centerSection.y - 1);
        WorldSectionCoordinates topRightSection = new WorldSectionCoordinates(centerSection.x + 1, centerSection.y + 1);
        TileCoordinates bottomLeftTile = bottomleftSection.startToTileCoordinates();
        TileCoordinates topRightTile = topRightSection.endToTileCoordinates();
        for (int x = bottomLeftTile.getX(); x < topRightTile.getX(); x++) {
            for (int y = bottomLeftTile.getY(); y < topRightTile.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                RendererUtils.getInstance().ellipsePlain(xTile + DisplayConfig.TILE_SIZE / 2f, yTile + DisplayConfig.TILE_SIZE / 2f, 2, 2, Color.BLACK);
            }
        }
        RendererUtils.getInstance().end();
    }
}
