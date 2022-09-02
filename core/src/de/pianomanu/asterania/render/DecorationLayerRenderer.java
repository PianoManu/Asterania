package de.pianomanu.asterania.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.world.tile.Tile;

public class DecorationLayerRenderer {
    public static void addDecorations(SpriteBatch batch, Tile decoration, int xTile, int yTile) {
        int numberOfTextures = decoration.getNumberOfDifferentTextures();
        if (numberOfTextures == 1)
            batch.draw(decoration.getTexture(AsteraniaMain.assetManager.get(Atlases.DECORATION_ATLAS_LOCATION, TextureAtlas.class)), xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        else {
            //pseudo-random selection of stone coast texture using x and y position
            //x*3, y*5+1: prevent patterns
            int textureNumber = (((xTile * 3 % numberOfTextures) + (yTile * 5 + 1 % numberOfTextures)) % numberOfTextures) + 1; //add 1 for intervall [1, 4]
            TextureRegion decorationTexture = AsteraniaMain.assetManager.get(Atlases.DECORATION_ATLAS_LOCATION, TextureAtlas.class).findRegion(decoration.getSaveFileString() + textureNumber);
            batch.draw(decorationTexture, xTile, yTile, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        }
    }
}
