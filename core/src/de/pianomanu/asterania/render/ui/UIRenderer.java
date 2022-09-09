package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;
import de.pianomanu.asterania.world.entities.Player;

public class UIRenderer {
    public static void renderAll(Player player, SpriteBatch batch) {
        TileBreakingUI.renderTileBreakingUI(player);
        HotbarRenderer.renderHotbar(player, batch);
        ChatRenderer.renderAll(player);
        InventoryRenderer.renderInventory(player, batch);
    }
}
