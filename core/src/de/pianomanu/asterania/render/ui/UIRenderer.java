package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;

public class UIRenderer {
    public static void renderAll(Player player, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        TileBreakingUI.renderTileBreakingUI(player, shapeRenderer);
        HotbarRenderer.renderHotbar(player, batch, shapeRenderer);
        ChatRenderer.renderAll(player, shapeRenderer);
        InventoryRenderer.renderInventory(player, batch, shapeRenderer);
    }
}
