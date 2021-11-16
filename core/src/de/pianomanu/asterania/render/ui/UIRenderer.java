package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.world.World;

public class UIRenderer {
    public static void renderAll(World world, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        TileBreakingUI.renderTileBreakingUI(world, shapeRenderer);
        HotbarRenderer.renderHotbar(world, batch, shapeRenderer);
        InventoryRenderer.renderInventory(world, batch, shapeRenderer);
    }
}
