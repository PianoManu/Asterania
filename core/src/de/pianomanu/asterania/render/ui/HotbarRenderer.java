package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.Atlases;
import de.pianomanu.asterania.render.RendererUtils;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.world.tile.LayerType;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class HotbarRenderer {

    public static void renderHotbar(Player player, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int startX = width / 20;
        int startY = height * 4 / 5 + height / 12;
        int hWidth = 64;//height / 12;
        int hHeight = 64;//height / 12;

        renderHotbarBase(shapeRenderer, startX, startY, hWidth, hHeight);
        Tile t = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getItem());
        if (t.equals(Tiles.WHITE)) {
            renderHotbarEmpty(shapeRenderer, startX, startY, hWidth, hHeight);
        } else {
            TextureRegion tileTexture = getTileTextureRegion(t);
            renderHotbarItem(player, tileTexture, batch, startX, startY, hWidth, hHeight);
        }
    }

    private static void renderHotbarBase(ShapeRenderer shapeRenderer, int startX, int startY, int hWidth, int hHeight) {
        int innerOffset = 5;//hWidth/10;

        RendererUtils.rect(shapeRenderer, startX, startY, hWidth, hHeight, new Color(0.3f, 0.3f, 0.3f, 1));
        RendererUtils.rect(shapeRenderer, startX + innerOffset, startY + innerOffset, hWidth - innerOffset * 2, hHeight - innerOffset * 2, new Color(0.6f, 0.6f, 0.6f, 1));
    }

    private static void renderHotbarEmpty(ShapeRenderer shapeRenderer, int startX, int startY, int hWidth, int hHeight) {
        int innerOffset = 5;//hWidth/10;
        RendererUtils.rect(shapeRenderer, startX + innerOffset * 3, startY + innerOffset * 3, hWidth - innerOffset * 6, hHeight - innerOffset * 6, new Color(0.7f, 0.7f, 0.7f, 1));
    }

    private static void renderHotbarItem(Player player, TextureRegion tileTexture, SpriteBatch batch, int startX, int startY, int hWidth, int hHeight) {
        int tileStartX = startX + hWidth / 8;
        int tileStartY = startY + hHeight / 8;
        int tileWidth = hWidth * 3 / 4;
        int tileHeight = hHeight * 3 / 4;

        batch.begin();
        batch.draw(tileTexture, tileStartX, tileStartY, tileWidth, tileHeight);
        batch.end();

        TextRenderer.renderText(startX + hWidth / 2, startY + hWidth / 3, player.getPlayerInventory().getCurrentIOStack().getStackCount() + "", Color.WHITE);
    }

    private static TextureRegion getTileTextureRegion(Tile tile) {
        if (tile.getTileType() == LayerType.BACKGROUND) {
            return getTileTextureFromAtlas(tile, Atlases.TILE_ATLAS_LOCATION);
        } else if (tile.getTileType() == LayerType.DECORATION) {
            return getTileTextureFromAtlas(tile, Atlases.DECORATION_ATLAS_LOCATION);
        }
        return null;
    }

    private static TextureRegion getTileTextureFromAtlas(Tile tile, String atlasLocation) {
        return tile.getTexture(AsteraniaMain.INSTANCE.getAssetManager().get(atlasLocation, TextureAtlas.class));
    }
}
