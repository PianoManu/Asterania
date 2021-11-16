package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.world.World;

public class UIRenderer {
    public static void renderAll(World world, ShapeRenderer shapeRenderer) {
        TileBreakingUI.renderTileBreakingUI(world, shapeRenderer);
    }
}
