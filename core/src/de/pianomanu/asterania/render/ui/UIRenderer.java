package de.pianomanu.asterania.render.ui;

import de.pianomanu.asterania.render.text.chat.ChatRenderer;
import de.pianomanu.asterania.world.entities.Player;

public class UIRenderer {
    public static void renderAll(Player player) {
        TileBreakingUI.renderTileBreakingUI(player);
        HotbarRenderer.renderHotbar(player);
        ChatRenderer.renderAll(player);
        InventoryRenderer.renderInventory(player);
    }
}
