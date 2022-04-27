package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;

public class UIRenderer {
    public static void renderAll(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        TileBreakingUI.renderTileBreakingUI(shapeRenderer);
        HotbarRenderer.renderHotbar(batch, shapeRenderer);
        ChatRenderer.renderAll(shapeRenderer);
        InventoryRenderer.renderInventory(batch, shapeRenderer);
    }
}
