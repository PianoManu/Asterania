package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;

public class UIRenderer {
    public static void renderAll(Player player, SpriteBatch batch) {
        TileBreakingUI.renderTileBreakingUI(player);
        HotbarRenderer.renderHotbar(player, batch);
        ChatRenderer.renderAll(player);
        InventoryRenderer.renderInventory(player, batch);
    }
}
