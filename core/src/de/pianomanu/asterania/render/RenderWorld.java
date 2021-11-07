package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.atlas.PlayerAtlas;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.EntityCoordinates;
import de.pianomanu.asterania.world.TileCoordinates;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.tile.Tiles;

public class RenderWorld {
    private static ShapeRenderer gridRenderer = new ShapeRenderer();

    public static void reloadGridRenderer() {
        gridRenderer.dispose();
        gridRenderer = new ShapeRenderer();
    }

    public static void renderTerrain(World world, SpriteBatch batch) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int startRenderingX = world.getPlayer().getCharacterPos().toTileCoordinates().getX() - 3 * width / (2 * DisplayConfig.TILE_SIZE);
        int stopRenderingX = world.getPlayer().getCharacterPos().toTileCoordinates().getX() + 3 * width / (2 * DisplayConfig.TILE_SIZE);
        int startRenderingY = world.getPlayer().getCharacterPos().toTileCoordinates().getY() - 3 * height / (2 * DisplayConfig.TILE_SIZE);
        int stopRenderingY = world.getPlayer().getCharacterPos().toTileCoordinates().getY() + 3 * height / (2 * DisplayConfig.TILE_SIZE);
        TileCoordinates startRendering = new TileCoordinates(startRenderingX, startRenderingY);
        TileCoordinates stopRendering = new TileCoordinates(stopRenderingX, stopRenderingY);
        EntityCoordinates playerCoordinates = world.getPlayer().getCharacterPos();
        batch.begin();
        gridRenderer.begin(ShapeRenderer.ShapeType.Line);
        gridRenderer.setColor(0, 0, 0, 1);
        for (int x = startRendering.getX(); x < stopRendering.getX(); x++) {
            for (int y = startRendering.getY(); y < stopRendering.getY(); y++) {
                int xTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).x;
                int yTile = (int) CoordinatesUtils.transformTileCoordinatesToPixels(new TileCoordinates(x, y), playerCoordinates).y;
                try {
                    //batch.draw(world.getTile(x, y).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), x * DisplayConfig.TILE_SIZE - playerCoordinates.x * DisplayConfig.TILE_SIZE, y * DisplayConfig.TILE_SIZE - playerCoordinates.y * DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                    batch.draw(world.getTile(x, y).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (ArrayIndexOutOfBoundsException e) {
                    batch.draw(Tiles.ROCK.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (NullPointerException ignored) {

                }
                if (DisplayConfig.showDebugInfo) {
                    gridRenderer.line(0, yTile, width, yTile);
                    gridRenderer.line(xTile, 0, xTile, height);
                }
            }
        }
        batch.end();
        gridRenderer.end();
    }

    public static void renderPlayer(World world, SpriteBatch batch) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        Player player = world.getPlayer();
        TextureRegion playerTexture = AsteraniaMain.assetManager.get(Atlases.PLAYER_ATLAS_LOCATION, TextureAtlas.class).findRegion(PlayerAtlas.STANDING_FRONT);
        batch.begin();
        //batch.draw(img, player.getCharacterPos().x - player.getCharacterSize().x/2f, player.getCharacterPos().y - player.getCharacterSize().y/2f, player.getCharacterSize().x,player.getCharacterSize().y);
        batch.draw(playerTexture, width / 2f - player.getCharacterSize().x / 2f, height / 2f - player.getCharacterSize().y / 2f, player.getCharacterSize().x, player.getCharacterSize().y);
        batch.end();
    }

    public static void renderHovering(World world, ShapeRenderer shapeRenderer) {
        /*int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int mouseX = Gdx.input.getX();
        int mouseY = height - Gdx.input.getY();
        float playerX = world.getPlayer().getCharacterPos().x;
        float playerY = world.getPlayer().getCharacterPos().y;
        float mouseXrelative = (playerX - (float) mouseX) % DisplayConfig.TILE_SIZE;
        float mouseYrelative = (playerY - (float) mouseY) % DisplayConfig.TILE_SIZE;
        EntityCoordinates playerECoord = world.getPlayer().getCharacterPos();
        EntityCoordinates mouseECoord = CoordinatesUtils.cursorToEntityCoordinates(mouseX, mouseY, playerECoord);
        EntityCoordinates distPlayerToMouse = new EntityCoordinates(mouseECoord.x - playerECoord.x, mouseECoord.y - playerECoord.y);
        float lowerXECoord = (float) Math.floor(mouseECoord.getX());
        float upperX = (float) Math.ceil(mouseXrelative);
        float lowerYECoord = (float) Math.floor(mouseECoord.getY());
        float upperY = (float) Math.ceil(mouseYrelative);
        float lowerXTileSize = lowerXECoord*DisplayConfig.TILE_SIZE; //hovering rectangle "jumps" TileSize pixels
        float lowerYTileSize = lowerYECoord*DisplayConfig.TILE_SIZE;
        mouseECoord.x = lowerXTileSize;
        mouseECoord.y = lowerYTileSize;
        float relativeXOffset = (playerX * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE;
        float relativeYOffset = (playerY * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE;
        mouseECoord.x += relativeXOffset;
        mouseECoord.y += relativeYOffset;


        //System.out.println(mouseX + ", " + mouseY + "        " + playerX + ", " + playerY);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f);
        //TODO find hovering issue
        //shapeRenderer.rect( (float) Math.floor(mouseECoord.getX())*DisplayConfig.TILE_SIZE - (playerX * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE, (float) Math.floor(mouseECoord.getY())*DisplayConfig.TILE_SIZE - (playerY * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        //shapeRenderer.rect((Math.round(mouseX / (float) DisplayConfig.TILE_SIZE) * DisplayConfig.TILE_SIZE - (playerX * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE), (Math.round(mouseY / (float) DisplayConfig.TILE_SIZE) * DisplayConfig.TILE_SIZE - (playerY * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE), DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        //shapeRenderer.rect(lowerX*DisplayConfig.TILE_SIZE + (distPlayerToMouse.getX() * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE, lowerY*DisplayConfig.TILE_SIZE + (distPlayerToMouse.getY() * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        //Transform ECoord back to Pixels
        //INPUT: PIXELS
        Vector2 mouseInPixels = CoordinatesUtils.transformEntityCoordinatesToPixels(mouseECoord, playerECoord);
        //shapeRenderer.rect(lowerXECoord - relativeXOffset, lowerYECoord - relativeYOffset, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.rect(mouseInPixels.x, mouseInPixels.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);*/
        EntityCoordinates playerPos = world.getPlayer().getCharacterPos();
        EntityCoordinates mousePos = CoordinatesUtils.cursorToEntityCoordinates(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), playerPos);
        int hoveringXStart = (int) Math.floor(mousePos.x);
        int hoveringYStart = (int) Math.floor(mousePos.y);
        EntityCoordinates hoveringStart = new EntityCoordinates(hoveringXStart, hoveringYStart);
        Vector2 startPixelPos = CoordinatesUtils.transformEntityCoordinatesToPixels(hoveringStart, playerPos);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f);
        shapeRenderer.rect(startPixelPos.x, startPixelPos.y, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public static void renderDebugText(World world, int frames) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int xOffset = 4;
        int yOffset = 4;
        TextRenderer.renderText(xOffset, height - yOffset, "FPS: " + frames);

        TextRenderer.renderText(xOffset, height - yOffset - 16, "Position: X=" + world.getPlayer().getCharacterPos().x + ", Y=" + world.getPlayer().getCharacterPos().y);

        int mouseX = Gdx.input.getX();
        int mouseY = height - Gdx.input.getY();
        TextRenderer.renderText(xOffset, height - yOffset - 32, "Cursor position: X=" + mouseX + ", Y=" + mouseY);
        EntityCoordinates mouseECoordinates = CoordinatesUtils.cursorToEntityCoordinates(mouseX, mouseY, world.getPlayer().getCharacterPos());
        TextRenderer.renderText(xOffset, height - yOffset - 48, "Cursor position as Game coordinates: X=" + mouseECoordinates.x + ", Y=" + mouseECoordinates.y);
    }

    public static void renderGrid(World world, ShapeRenderer shapeRenderer) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float playerX = world.getPlayer().getCharacterPos().x;
        float playerY = world.getPlayer().getCharacterPos().y;
        float difX = playerX % DisplayConfig.TILE_SIZE;
        float difY = playerY % DisplayConfig.TILE_SIZE;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int x = 0; x < width / DisplayConfig.TILE_SIZE + 1; x++) {
            for (int y = 0; y < height / DisplayConfig.TILE_SIZE + 1; y++) {
                shapeRenderer.line(0, y * DisplayConfig.TILE_SIZE - world.getPlayer().getCharacterPos().y * DisplayConfig.TILE_SIZE, width, y * DisplayConfig.TILE_SIZE - world.getPlayer().getCharacterPos().y * DisplayConfig.TILE_SIZE);
                shapeRenderer.line(x * DisplayConfig.TILE_SIZE - world.getPlayer().getCharacterPos().x * DisplayConfig.TILE_SIZE, 0, x * DisplayConfig.TILE_SIZE - world.getPlayer().getCharacterPos().x * DisplayConfig.TILE_SIZE, height);
            }
        }
        shapeRenderer.end();
    }
}
