package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.inventory.Inventory;
import de.pianomanu.asterania.inventory.objects.InventoryObjectStack;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.Atlases;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.world.World;

public class InventoryRenderer {
    public static final Vector2 SLOT_SIZE = new Vector2(40, 40);
    public static final int INTER_SLOT_DISTANCE = 6;
    public static final int ROWS = 4;
    public static final int COLUMNS = 12;
    private static boolean isInventoryOpen = false;

    public static void renderInventory(World world, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        if (isInventoryOpen) {
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getHeight();
            int inventoryWidth = (int) (COLUMNS * SLOT_SIZE.x + INTER_SLOT_DISTANCE * (COLUMNS - 1));
            int inventoryHeight = (int) (ROWS * SLOT_SIZE.y + INTER_SLOT_DISTANCE * (ROWS - 1));
            int xStart = width / 2 - inventoryWidth / 2;
            int yStart = inventoryHeight / 4;
            int mX = Gdx.input.getX();
            int mY = height - Gdx.input.getY();
            //if mouse somewhere inside the inventory: goes transparent to enable seeing through the inventory
            boolean mouseInsideOfInventory = mX >= xStart - INTER_SLOT_DISTANCE && mX <= xStart - INTER_SLOT_DISTANCE + inventoryWidth + 2 * INTER_SLOT_DISTANCE && mY >= yStart - INTER_SLOT_DISTANCE && mY <= yStart - INTER_SLOT_DISTANCE + inventoryHeight + 2 * INTER_SLOT_DISTANCE;
            if (mouseInsideOfInventory && DisplayConfig.ENABLE_TRANSPARENT_INVENTORY) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 0.4f);
            shapeRenderer.rect(xStart - INTER_SLOT_DISTANCE, yStart - INTER_SLOT_DISTANCE, inventoryWidth + 2 * INTER_SLOT_DISTANCE, inventoryHeight + 2 * INTER_SLOT_DISTANCE);
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 0.4f);
            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    shapeRenderer.rect(xStart + x * (SLOT_SIZE.x + INTER_SLOT_DISTANCE), yStart + y * (SLOT_SIZE.y + INTER_SLOT_DISTANCE), SLOT_SIZE.x, SLOT_SIZE.y);
                }
            }
            Player player = world.getPlayer();
            int playerInventoryIOStackPointer = player.getPlayerInventory().getiOStackPointer();
            int xPos = playerInventoryIOStackPointer % COLUMNS;
            int yPos = playerInventoryIOStackPointer / COLUMNS;

            shapeRenderer.setColor(0.5f, 0.4f, 0, 0.5f);
            shapeRenderer.rect(xStart + xPos * (SLOT_SIZE.x + INTER_SLOT_DISTANCE) - 2, yStart + yPos * (SLOT_SIZE.y + INTER_SLOT_DISTANCE) - 2, SLOT_SIZE.x + 4, SLOT_SIZE.y + 4);
            shapeRenderer.end();

            if (mouseInsideOfInventory && DisplayConfig.ENABLE_TRANSPARENT_INVENTORY) {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }

            Inventory inv = player.getPlayerInventory();
            batch.begin();
            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    InventoryObjectStack iO = inv.getStackAtPos(x + y * COLUMNS);
                    if (!iO.equals(InventoryObjectStack.EMPTY) && iO.getStackCount() > 0) {
                        TextureRegion texture = GameRegistry.getTile(iO.getInventoryObject()).getTexture(AsteraniaMain.assetManager.get(Atlases.TILE_ATLAS_LOCATION, TextureAtlas.class));
                        batch.draw(texture, xStart + x * (SLOT_SIZE.x + INTER_SLOT_DISTANCE) + 4, yStart + y * (SLOT_SIZE.y + INTER_SLOT_DISTANCE) + 4, 32, 32);
                    }
                }
            }
            batch.end();

            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    InventoryObjectStack iO = inv.getStackAtPos(x + y * COLUMNS);
                    if (!iO.equals(InventoryObjectStack.EMPTY) && iO.getStackCount() > 0)
                        TextRenderer.renderText((int) (xStart + x * (SLOT_SIZE.x + INTER_SLOT_DISTANCE) + 12), (int) (yStart + y * (SLOT_SIZE.y + INTER_SLOT_DISTANCE) + 4), iO.getStackCount() + "");
                }
            }

            renderWeight(world, shapeRenderer);
        }
    }

    public static boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public static void changeInventoryOpen(boolean isInventoryOpen) {
        InventoryRenderer.isInventoryOpen = isInventoryOpen;
    }

    private static void renderWeight(World world, ShapeRenderer shapeRenderer) {
        Player player = world.getPlayer();
        float weight = player.getPlayerInventory().calcCurrentWeight();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 0.6f);
        shapeRenderer.rect(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100, 50, 50);
        shapeRenderer.end();

        TextRenderer.renderText(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 80, weight + " kg");
    }
}
