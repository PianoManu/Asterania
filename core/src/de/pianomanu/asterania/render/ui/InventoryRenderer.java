package de.pianomanu.asterania.render.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.ShapeRendererUtils;
import de.pianomanu.asterania.render.SpriteBatchUtils;
import de.pianomanu.asterania.render.atlas.Atlases;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.entities.player.inventory.Inventory;
import de.pianomanu.asterania.world.entities.player.inventory.item.ItemStack;
import de.pianomanu.asterania.world.tile.Tile;

public class InventoryRenderer {
    public static final Vector2 SLOT_SIZE = new Vector2(40, 40);
    public static final int INTER_SLOT_DISTANCE = 6;
    public static final int ROWS = 4;
    public static final int COLUMNS = 12;
    private static boolean isInventoryOpen = false;

    public static void renderInventory(Player player) {
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
                ShapeRendererUtils.enableTransparency();
            }
            ShapeRendererUtils.getInstance().begin();
            ShapeRendererUtils.getInstance().rectPlain(xStart - INTER_SLOT_DISTANCE, yStart - INTER_SLOT_DISTANCE, inventoryWidth + 2 * INTER_SLOT_DISTANCE, inventoryHeight + 2 * INTER_SLOT_DISTANCE, new Color(0.2f, 0.2f, 0.2f, 0.4f));
            Color slotBackground = new Color(0.3f, 0.3f, 0.3f, 0.4f);
            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    ShapeRendererUtils.getInstance().rectPlain(xStart + x * (SLOT_SIZE.x + INTER_SLOT_DISTANCE), yStart + y * (SLOT_SIZE.y + INTER_SLOT_DISTANCE), SLOT_SIZE.x, SLOT_SIZE.y, slotBackground);
                }
            }
            int playerInventoryIOStackPointer = player.getPlayerInventory().getItemStackPointer();
            int xPos = playerInventoryIOStackPointer % COLUMNS;
            int yPos = playerInventoryIOStackPointer / COLUMNS;

            ShapeRendererUtils.getInstance().rectPlain(xStart + xPos * (SLOT_SIZE.x + INTER_SLOT_DISTANCE) - 2, yStart + yPos * (SLOT_SIZE.y + INTER_SLOT_DISTANCE) - 2, SLOT_SIZE.x + 4, SLOT_SIZE.y + 4, new Color(0.5f, 0.4f, 0, 0.5f));
            ShapeRendererUtils.getInstance().end();

            if (mouseInsideOfInventory && DisplayConfig.ENABLE_TRANSPARENT_INVENTORY) {
                ShapeRendererUtils.disableTransparency();
            }

            Inventory inv = player.getPlayerInventory();
            SpriteBatchUtils.getInstance().begin();
            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    ItemStack iO = inv.getStackAtPos(x + y * COLUMNS);
                    if (!iO.equals(ItemStack.EMPTY) && iO.getStackCount() > 0) {
                        Tile tile = GameRegistry.getTile(iO.getItem());
                        TextureRegion texture = tile.getTexture();
                        if (tile.getNumberOfDifferentTextures() > 1) { //TODO fix for items with multiple textures
                            texture = AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.DECORATION_ATLAS_LOCATION, TextureAtlas.class).findRegion(iO.getItem().getName() + "1");
                        }
                        SpriteBatchUtils.getInstance().drawPlain(texture, (int) (xStart + x * (SLOT_SIZE.x + INTER_SLOT_DISTANCE) + 4), (int) (yStart + y * (SLOT_SIZE.y + INTER_SLOT_DISTANCE) + 4), 32, 32);
                    }
                }
            }
            SpriteBatchUtils.getInstance().end();

            for (int x = 0; x < COLUMNS; x++) {
                for (int y = 0; y < ROWS; y++) {
                    ItemStack iO = inv.getStackAtPos(x + y * COLUMNS);
                    if (!iO.equals(ItemStack.EMPTY) && iO.getStackCount() > 0)
                        TextRenderer.getInstance().renderText((int) (xStart + x * (SLOT_SIZE.x + INTER_SLOT_DISTANCE) + SLOT_SIZE.x / 2), (int) (yStart + y * (SLOT_SIZE.y + INTER_SLOT_DISTANCE) + SLOT_SIZE.y / 4), iO.getStackCount() + "", Color.WHITE);
                }
            }

            renderWeight(player);
        }
    }

    public static boolean isInventoryOpen() {
        return isInventoryOpen;
    }

    public static void changeInventoryOpen(boolean isInventoryOpen) {
        InventoryRenderer.isInventoryOpen = isInventoryOpen;
    }

    private static void renderWeight(Player player) {
        float weight = player.getPlayerInventory().calcCurrentWeight();

        int xWindowBorderOffset = 100;
        int yWindowBorderOffset = 80;
        int borderWidth = 10;
        int borderHeight = 10;

        Vector2 textDim = TextRenderer.getInstance().getTextDimensions(weight + " kg");
        ShapeRendererUtils.getInstance().rect(Gdx.graphics.getWidth() - xWindowBorderOffset - borderWidth - textDim.x / 2, Gdx.graphics.getHeight() - yWindowBorderOffset - borderHeight - textDim.y / 2, textDim.x + borderWidth * 2, textDim.y + borderHeight * 2, new Color(0.2f, 0.2f, 0.2f, 0.6f));

        TextRenderer.getInstance().renderText(Gdx.graphics.getWidth() - xWindowBorderOffset, Gdx.graphics.getHeight() - yWindowBorderOffset, weight + " kg", Color.WHITE, new Color(0.3f, 0.3f, 0.3f, 0.4f));
    }
}
