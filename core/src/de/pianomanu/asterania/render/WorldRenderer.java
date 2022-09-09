package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.render.atlas.Atlases;
import de.pianomanu.asterania.utils.math.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.coordinates.WorldSectionCoordinates;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;
import de.pianomanu.asterania.world.tile.tileutils.LayerType;
import de.pianomanu.asterania.world.worldsections.WorldSection;

public class WorldRenderer {

    public static void renderAll(World world, Player player) {
        renderTerrain(world, player);
        renderHovering(player);
        PlayerRenderer.render(player);
    }

    private static void renderTerrain(World world, Player player) {
        EntityCoordinates playerCoordinates = player.getPos();
        EntityCoordinates bottomLeftEntityCoordinates = new EntityCoordinates(playerCoordinates.x - 32, playerCoordinates.y - 32);
        EntityCoordinates topRightEntityCoordinates = new EntityCoordinates(playerCoordinates.x + 32, playerCoordinates.y + 32);
        WorldSectionCoordinates bottomLeftWorldSectionCoordinates = bottomLeftEntityCoordinates.toWorldSectionCoordinates();
        WorldSectionCoordinates topRightWorldSectionCoordinates = topRightEntityCoordinates.toWorldSectionCoordinates();

        SpriteBatchUtils.getInstance().begin();
        TileCoordinates bottomLeftTile = bottomLeftWorldSectionCoordinates.startToTileCoordinates();
        TileCoordinates topRightTile = topRightWorldSectionCoordinates.endToTileCoordinates();
        for (int x = bottomLeftTile.getX(); x < topRightTile.getX(); x++) {
            for (int y = bottomLeftTile.getY(); y < topRightTile.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                TileCoordinates tileCoordinates = new TileCoordinates(x, y);
                WorldSection section = world.findSection(tileCoordinates);
                if (isTileVisibleOnScreen(xTile, yTile)) {
                    try {
                        Tile tile = section.getTileAbsoluteCoordinates(x, y);
                        Tile decoration = section.getDecorationLayerTileAbsoluteCoordinates(x, y);
                        if (tile != null) {
                            renderTile(tile, xTile, yTile, x, y);
                            addOverlay(world, x, y, xTile, yTile);
                        }
                        if (decoration != null) {
                            renderTile(decoration, xTile, yTile, x, y);
                        }

                    } catch (NullPointerException e) {
                        //Error trying to find the correct section: preGenerate all adjacent sections
                        world.preGenerateSurroundingWorldSections();
                    }
                }
            }
        }

        SpriteBatchUtils.getInstance().end();
    }

    private static boolean isTileVisibleOnScreen(int xTile, int yTile) {
        return xTile >= -DisplayConfig.TILE_SIZE && xTile < Gdx.graphics.getWidth() && yTile >= -DisplayConfig.TILE_SIZE && yTile < Gdx.graphics.getHeight();
    }

    private static void renderTile(Tile tile, int xTile, int yTile, int x, int y) {
        if (tile.getTileType() == LayerType.BACKGROUND) {
            SpriteBatchUtils.getInstance().drawPlain(tile.getTexture(AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        }
        if (tile.getTileType() == LayerType.DECORATION) {
            DecorationLayerRenderer.addDecorations(tile, xTile, yTile, x, y);
        }
    }

    private static void addOverlay(World world, int x, int y, int xTile, int yTile) {
        OverlayRenderer.addOverlay(world, x, y, xTile, yTile, Tiles.SOIL_TILE, Tiles.GRASS);
        OverlayRenderer.addOverlay(world, x, y, xTile, yTile, Tiles.ROCK, Tiles.GRASS);
        //OverlayRenderer.addOverlay(world, worldSection, x, y, xTile, yTile, Tiles.WATER_TILE, Tiles.SOIL_TILE);

        OverlayRenderer.addStoneCoastOverlay(world, x, y, xTile, yTile);
    }

    private static void renderHovering(Player player) {
        EntityCoordinates playerPos = player.getPos();
        EntityCoordinates mousePos = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), playerPos);
        boolean isInReach = player.isInReach(mousePos.toTileCoordinates());
        int hoveringXStart = (int) Math.floor(mousePos.x);
        int hoveringYStart = (int) Math.floor(mousePos.y);
        EntityCoordinates hoveringStart = new EntityCoordinates(hoveringXStart, hoveringYStart);
        Vector2 startPixelPos = CoordinatesUtils.transformEntityCoordinatesToPixels(hoveringStart, playerPos);

        ShapeRendererUtils.enableTransparency();
        renderHoveringBackground(startPixelPos, isInReach);
        renderHoveringBorder(startPixelPos, isInReach);

        ShapeRendererUtils.disableTransparency();
    }

    private static void renderHoveringBackground(Vector2 startPixelPos, boolean isInReach) {
        if (isInReach)
            ShapeRendererUtils.getInstance().rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, new Color(0.6f, 1, 0.6f, 0.2f));
        else
            ShapeRendererUtils.getInstance().rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, new Color(1, 1, 1, 0.2f));
    }

    private static void renderHoveringBorder(Vector2 startPixelPos, boolean isInReach) {
        Color hoverBorderColor;
        if (isInReach) {
            hoverBorderColor = new Color(0f, 0.5f, 0f, 1f);
        } else {
            hoverBorderColor = new Color(1f, 1f, 1f, 0.2f);
        }
        ShapeRendererUtils.getInstance().rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, hoverBorderColor, ShapeRenderer.ShapeType.Line);
        ShapeRendererUtils.getInstance().rect(startPixelPos.x + 1, startPixelPos.y + 1, DisplayConfig.TILE_SIZE - 2, DisplayConfig.TILE_SIZE - 2, hoverBorderColor, ShapeRenderer.ShapeType.Line);
        ShapeRendererUtils.getInstance().rect(startPixelPos.x + 2, startPixelPos.y + 2, DisplayConfig.TILE_SIZE - 4, DisplayConfig.TILE_SIZE - 4, hoverBorderColor, ShapeRenderer.ShapeType.Line);
        ShapeRendererUtils.getInstance().rect(startPixelPos.x + 3, startPixelPos.y + 3, DisplayConfig.TILE_SIZE - 6, DisplayConfig.TILE_SIZE - 6, hoverBorderColor, ShapeRenderer.ShapeType.Line);
    }

}
