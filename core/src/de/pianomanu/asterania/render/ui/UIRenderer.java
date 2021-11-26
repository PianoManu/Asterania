package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UIRenderer {
    public static void renderAll(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        TileBreakingUI.renderTileBreakingUI(shapeRenderer);
        HotbarRenderer.renderHotbar(batch, shapeRenderer);
        InventoryRenderer.renderInventory(batch, shapeRenderer);
    }
}
