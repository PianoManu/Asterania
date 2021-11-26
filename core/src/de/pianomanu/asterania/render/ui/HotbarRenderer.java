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
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

public class HotbarRenderer {

    public static void renderHotbar(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int startX = width / 20;
        int startY = height * 4 / 5 + height / 12;
        int hWidth = 64;//height / 12;
        int hHeight = 64;//height / 12;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 1);
        shapeRenderer.rect(startX, startY, hWidth, hHeight);

        int innerOffset = 5;//hWidth/10;
        shapeRenderer.setColor(0.6f, 0.6f, 0.6f, 1);
        shapeRenderer.rect(startX + innerOffset, startY + innerOffset, hWidth - innerOffset * 2, hHeight - innerOffset * 2);
        shapeRenderer.end();

        int tileStartX = startX + hWidth / 8;
        int tileStartY = startY + hHeight / 8;
        int tileWidth = hWidth * 3 / 4;
        int tileHeight = hHeight * 3 / 4;
        Player player = AsteraniaMain.player;
        Tile t = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getInventoryObject());
        if (t.equals(Tiles.WHITE)) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1);
            shapeRenderer.rect(startX + innerOffset * 3, startY + innerOffset * 3, hWidth - innerOffset * 6, hHeight - innerOffset * 6);
            shapeRenderer.end();
        } else {
            TextureRegion tileTexture = t.getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class));
            batch.begin();
            batch.draw(tileTexture, tileStartX, tileStartY, tileWidth, tileHeight);
            batch.end();

            TextRenderer.renderText(startX + hWidth / 2, startY + hWidth / 3, player.getPlayerInventory().getCurrentIOStack().getStackCount() + "", Color.WHITE);
        }
    }
}
