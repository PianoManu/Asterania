package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.inventory.objects.InventoryObjectStack;
import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.text.TextRenderer;
import de.pianomanu.asterania.render.ui.InventoryRenderer;
import de.pianomanu.asterania.render.ui.TileBreakingUI;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.tile.Tile;
import de.pianomanu.asterania.world.tile.Tiles;

import java.util.Objects;
import java.util.logging.Logger;

public class PlayerUpdates extends GameLifeCycleUpdates {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static int timesScrolled = 0;

    protected static void updatePlayer(World world, float delta) {
        updateMovement(world, delta);
        changeEnvironment(world, delta);
        changeInventory(world);
    }

    private static void updateMovement(World world, float delta) {
        Player player = world.getPlayer();
        EntityCoordinates playerFootPos = player.getFootPos();
        TileCoordinates playerTile = playerFootPos.toTileCoordinates();
        TileCoordinates left = playerTile.copy().moveLeft();
        TileCoordinates right = playerTile.copy().moveRight();
        TileCoordinates up = playerTile.copy().moveUp();
        TileCoordinates down = playerTile.copy().moveDown();
        float distanceFromInaccessibleBlocks = 0.000001f;
        if (player.isMoving())
            player.updateHitbox();
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(up) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                EntityCoordinates up1 = new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y + 1);
                EntityCoordinates up2 = new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y + 1);
                boolean isAccessible1 = world.findSection(up1).getTile(up1).getSettings().get(TileProperties.IS_ACCESSIBLE);
                boolean isAccessible2 = world.findSection(up2).getTile(up2).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if ((isAccessible1 && isAccessible2) || playerFootPos.y + player.getStepSize() * delta < up.getY()) {
                    player.moveUp(delta);
                }
                if ((!isAccessible1 || !isAccessible2) && playerFootPos.y + player.getStepSize() * delta > up.getY()) {
                    player.setFootPos(player.getCharacterPos().x, up.getY() - distanceFromInaccessibleBlocks);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_UP))
                        player.setPlayerFacing(Direction.UP);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(down) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                EntityCoordinates down1 = new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y - 1);
                EntityCoordinates down2 = new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y - 1);
                boolean isAccessible1 = world.findSection(down1).getTile(down1).getSettings().get(TileProperties.IS_ACCESSIBLE);
                boolean isAccessible2 = world.findSection(down2).getTile(down2).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if ((isAccessible1 && isAccessible2) || playerFootPos.y - player.getStepSize() * delta > down.getY() + 1) {
                    player.moveDown(delta);
                }
                if ((!isAccessible1 || !isAccessible2) && playerFootPos.y - player.getStepSize() * delta < down.getY() + 1) {
                    player.setFootPos(player.getCharacterPos().x, down.getY() + 1);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_DOWN))
                        player.setPlayerFacing(Direction.DOWN);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(right) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                boolean rightAccessible = world.findSection(right).getTile(right).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if (rightAccessible || player.getPlayerHitbox().end.x < right.getX()) {
                    player.moveRight(delta);
                }
                if (!rightAccessible && player.getPlayerHitbox().end.x + player.getStepSize() * delta > right.getX()) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setFootPos(right.getX() - xHitboxWidth / 2, player.getFootPos().y);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_RIGHT))
                        player.setPlayerFacing(Direction.RIGHT);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            player.setMoving();
            if (world.findSection(playerTile).getTile(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(left) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                boolean leftAccessible = world.findSection(left).getTile(left).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if (leftAccessible || player.getPlayerHitbox().start.x > left.getX() + 1) {
                    player.moveLeft(delta);
                }
                if (!leftAccessible && player.getPlayerHitbox().start.x - player.getStepSize() * delta < left.getX() + 1) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setFootPos(left.getX() + 1 + xHitboxWidth / 2, player.getFootPos().y);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_LEFT))
                        player.setPlayerFacing(Direction.LEFT);
                }
            }
        }
        if (player.isMoving())
            player.checkForAnimationUpdate(delta);
            if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
                player.setStanding();
    }

    public static void changeEnvironment(World world, float delta) {
        Player player = world.getPlayer();
        if (Gdx.input.isButtonJustPressed(KeyConfig.SET_TILE)) {
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getCharacterPos());
            if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
                world.findSection(mouse).setTile(mouse, GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getInventoryObject()));
                player.getPlayerInventory().getCurrentIOStack().decrement();
            }
            if (player.getPlayerInventory().calcCurrentWeight() >= player.getMaxWeight()) {
                TileBreakingUI.renderNoBreakingPossible = false;
            }
        }
        //TODO remove inventory debug console log
        //System.out.println(player.getPlayerInventory().toString());

        if (Gdx.input.isButtonPressed(KeyConfig.BREAK_TILE)) {
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getCharacterPos());
            Tile old = world.findSection(mouse).getTile(mouse);
            if (!old.equals(Tiles.DEFAULT_TILE)) {
                float breakingTime = old.getSettings().get(TileProperties.BREAK_TIME);
                if (player.getPlayerInventory().calcCurrentWeight() + Objects.requireNonNull(GameRegistry.getInventoryObject(old)).getWeight() <= player.getMaxWeight()) {
                    TileBreakingUI.renderNoBreakingPossible = false;
                    if (Gdx.input.isButtonJustPressed(KeyConfig.BREAK_TILE))
                        player.setBreakingTile(true);
                    old.setBreakingLevel(old.getBreakingLevel() + delta);
                    player.setCurrentBreakingPercentage(old.getBreakingLevel() / breakingTime);
                    LOGGER.finest("Breaking level " + old.getBreakingLevel() + ", BreakTime" + old.getSettings().get(TileProperties.BREAK_TIME));
                    if (old.getBreakingLevel() >= breakingTime) {
                        world.findSection(mouse).setTile(mouse, Tiles.DEFAULT_TILE);
                        old.setBreakingLevel(0);
                        player.setCurrentBreakingPercentage(0);
                        player.getPlayerInventory().addStack(new InventoryObjectStack(GameRegistry.getInventoryObject(old)));
                        //TODO is this useful?
                        player.setBreakingTile(false);
                    }
                } else {
                    //TODO
                    TileBreakingUI.renderNoBreakingPossible = true;
                    TextRenderer.renderText(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 100, "Inventory full");
                }
            }
        } else {
            player.setCurrentBreakingPercentage(0);
            player.setBreakingTile(false);
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getCharacterPos());
            Tile old = world.findSection(mouse).getTile(mouse);
            if (old != null)
                old.setBreakingLevel(0);
            TileBreakingUI.renderNoBreakingPossible = false;
        }
    }

    public static void changeInventory(World world) {
        Player player = world.getPlayer();
        if (timesScrolled > 0) {
            player.setPlayerHoldNextIOStack();
            timesScrolled = 0;
        }
        if (timesScrolled < 0) {
            player.setPlayerHoldPreviousIOStack();
            timesScrolled = 0;
        }
        if (Gdx.input.isKeyJustPressed(KeyConfig.OPEN_OR_CLOSE_INVENTORY)) {
            boolean toggle = !InventoryRenderer.isInventoryOpen();
            InventoryRenderer.changeInventoryOpen(toggle);
        }
    }

    public static int getTimesScrolled() {
        return timesScrolled;
    }

    public static void setTimesScrolled(int timesScrolled) {
        PlayerUpdates.timesScrolled = timesScrolled;
    }
}
