package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.inventory.objects.InventoryObjectStack;
import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.ui.InventoryRenderer;
import de.pianomanu.asterania.render.ui.TileBreakingUI;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.tile.DecorationTile;
import de.pianomanu.asterania.world.tile.Tile;

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
        Player player = AsteraniaMain.player;
        EntityCoordinates playerFootPos = player.getPos();
        TileCoordinates playerTile = playerFootPos.toTileCoordinates();
        TileCoordinates left = playerTile.copy().moveLeft();
        TileCoordinates right = playerTile.copy().moveRight();
        TileCoordinates up = playerTile.copy().moveUp();
        TileCoordinates down = playerTile.copy().moveDown();
        float distanceFromInaccessibleBlocks = 1 / 4f;
        if (player.isMoving())
            player.updateHitbox();
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            player.setMoving();
            if (world.findSection(playerTile).getTileAbsoluteCoordinates(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(up) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                EntityCoordinates up1 = new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y + 1);
                EntityCoordinates up2 = new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y + 1);
                boolean isAccessible1 = world.findSection(up1).getTileAbsoluteCoordinates(up1).getSettings().get(TileProperties.IS_ACCESSIBLE);
                boolean isAccessible2 = world.findSection(up2).getTileAbsoluteCoordinates(up2).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if ((isAccessible1 && isAccessible2) || playerFootPos.y + player.getStepSize() * delta < up.getY() - distanceFromInaccessibleBlocks) {
                    player.moveUp(delta);
                }
                if ((!isAccessible1 || !isAccessible2) && playerFootPos.y + player.getStepSize() * delta > up.getY() - distanceFromInaccessibleBlocks) {
                    player.setPos(player.getPos().x, up.getY() - distanceFromInaccessibleBlocks);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_UP))
                        player.setPlayerFacing(Direction.UP);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            player.setMoving();
            if (world.findSection(playerTile).getTileAbsoluteCoordinates(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(down) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                EntityCoordinates down1 = new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y - 1);
                EntityCoordinates down2 = new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y - 1);
                boolean isAccessible1 = world.findSection(down1).getTileAbsoluteCoordinates(down1).getSettings().get(TileProperties.IS_ACCESSIBLE);
                boolean isAccessible2 = world.findSection(down2).getTileAbsoluteCoordinates(down2).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if ((isAccessible1 && isAccessible2) || playerFootPos.y - player.getStepSize() * delta > down.getY() + 1) {
                    player.moveDown(delta);
                }
                if ((!isAccessible1 || !isAccessible2) && playerFootPos.y - player.getStepSize() * delta < down.getY() + 1) {
                    player.setPos(player.getPos().x, down.getY() + 1);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_DOWN))
                        player.setPlayerFacing(Direction.DOWN);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            player.setMoving();
            if (world.findSection(playerTile).getTileAbsoluteCoordinates(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(right) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                boolean rightAccessible = world.findSection(right).getTileAbsoluteCoordinates(right).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if (rightAccessible || player.getPlayerHitbox().end.x < right.getX()) {
                    player.moveRight(delta);
                }
                if (!rightAccessible && player.getPlayerHitbox().end.x + player.getStepSize() * delta > right.getX()) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setPos(right.getX() - xHitboxWidth / 2, player.getPos().y);
                    if (Gdx.input.isKeyJustPressed(KeyConfig.MOVE_RIGHT))
                        player.setPlayerFacing(Direction.RIGHT);
                }
            }
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            player.setMoving();
            if (world.findSection(playerTile).getTileAbsoluteCoordinates(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
                if (world.findSection(left) == null) {
                    world.preGenerateSurroundingWorldSections();
                }
                boolean leftAccessible = world.findSection(left).getTileAbsoluteCoordinates(left).getSettings().get(TileProperties.IS_ACCESSIBLE);
                if (leftAccessible || player.getPlayerHitbox().start.x > left.getX() + 1) {
                    player.moveLeft(delta);
                }
                if (!leftAccessible && player.getPlayerHitbox().start.x - player.getStepSize() * delta < left.getX() + 1) {
                    float xHitboxWidth = player.getCharacterSize().x;
                    player.setPos(left.getX() + 1 + xHitboxWidth / 2, player.getPos().y);
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
        Player player = AsteraniaMain.player;

        if (Gdx.input.isButtonPressed(KeyConfig.REPLACE_TILE)) {
            //destroy and place normal tile or destroy decorative tile
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
            Tile old = world.findSection(mouse).getTileAbsoluteCoordinates(mouse);
            DecorationTile oldDecorationTile = (DecorationTile) world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
            //no decoration: replace tile
            if (oldDecorationTile == null) {
                if (!player.getPlayerInventory().getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
                    Tile holding = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getInventoryObject());
                    if (!old.equals(holding) && !(holding instanceof DecorationTile)) {
                        float breakingTime = old.getSettings().get(TileProperties.BREAK_TIME);
                        //check how much player is already carrying
                        if (player.getPlayerInventory().calcCurrentWeight() + Objects.requireNonNull(GameRegistry.getInventoryObject(old)).getWeight() <= player.getMaxWeight()) {
                            TileBreakingUI.renderNoBreakingPossible = false;
                            if (Gdx.input.isButtonJustPressed(KeyConfig.REPLACE_TILE))
                                player.setBreakingTile(true);
                            old.setBreakingLevel(old.getBreakingLevel() + delta);
                            player.setCurrentBreakingPercentage(old.getBreakingLevel() / breakingTime);
                            LOGGER.finest("Breaking level " + old.getBreakingLevel() + ", BreakTime" + old.getSettings().get(TileProperties.BREAK_TIME));
                            if (old.getBreakingLevel() >= breakingTime) {
                                replaceTile(holding);

                            }
                        } else {
                            //too much weight in inventory
                            TileBreakingUI.renderNoBreakingPossible = true;
                            TileBreakingUI.renderNoBreakingPossibleMessage = "Inventory full";
                        }
                    }
                }
            } else {
                //break decoration tile first
                float breakingTime = oldDecorationTile.getSettings().get(TileProperties.BREAK_TIME);
                //check how much player is already carrying
                if (player.getPlayerInventory().calcCurrentWeight() + Objects.requireNonNull(GameRegistry.getInventoryObject(oldDecorationTile)).getWeight() <= player.getMaxWeight()) {
                    TileBreakingUI.renderNoBreakingPossible = false;
                    if (Gdx.input.isButtonJustPressed(KeyConfig.REPLACE_TILE))
                        player.setBreakingTile(true);
                    oldDecorationTile.setBreakingLevel(oldDecorationTile.getBreakingLevel() + delta);
                    player.setCurrentBreakingPercentage(oldDecorationTile.getBreakingLevel() / breakingTime);
                    LOGGER.finest("Breaking level " + oldDecorationTile.getBreakingLevel() + ", BreakTime" + oldDecorationTile.getSettings().get(TileProperties.BREAK_TIME));
                    if (oldDecorationTile.getBreakingLevel() >= breakingTime) {
                        removeDecorationTile();

                    }
                } else {
                    //too much weight in inventory
                    TileBreakingUI.renderNoBreakingPossible = true;
                    TileBreakingUI.renderNoBreakingPossibleMessage = "Inventory full";
                }
            }
        } else {
            //breaking button released
            player.setCurrentBreakingPercentage(0);
            player.setBreakingTile(false);
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
            Tile old = world.findSection(mouse).getTileAbsoluteCoordinates(mouse);
            if (old != null)
                old.setBreakingLevel(0);
            Tile decoTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
            if (decoTile != null)
                decoTile.setBreakingLevel(0);
            TileBreakingUI.renderNoBreakingPossible = false;
        }
        if (Gdx.input.isButtonJustPressed(KeyConfig.INTERACT_WITH_TILE)) {
            //interact with tile or place decorative tile

            //first check if decorative tile can be placed
            EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
            Tile decorationLayerTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
            if (!player.getPlayerInventory().getCurrentIOStack().equals(InventoryObjectStack.EMPTY) && decorationLayerTile == null) {
                Tile holding = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getInventoryObject());
                if (holding instanceof DecorationTile) {
                    DecorationTile newDecorationTile = (DecorationTile) holding;
                    if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
                        world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, newDecorationTile);
                        player.getPlayerInventory().getCurrentIOStack().decrement();
                    }
                }
            }
            if (decorationLayerTile != null) {
                Tile tile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
                tile.performAction(player, world);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            LOGGER.fine("Changing world...");
            AsteraniaMain.player.changeCurrentWorld(AsteraniaMain.saveFile.getUniverse().getNextWorld(), player.getPos().toTileCoordinates());
        }
    }

    private static void replaceTile(Tile newTile) {
        Player player = AsteraniaMain.player;

        if (player.getPlayerInventory().calcCurrentWeight() >= player.getMaxWeight()) {
            TileBreakingUI.renderNoBreakingPossible = false;
        } else if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
            removeTile();
            setTile(newTile);
        }

        /*old.setBreakingLevel(0);
        player.setCurrentBreakingPercentage(0);
        player.getPlayerInventory().addStack(new InventoryObjectStack(GameRegistry.getInventoryObject(old)));
        player.setBreakingTile(false);
        world.findSection(mouse).getTile(mouse).runPlacementEvents(world, player, mouse.toTileCoordinates());*/
    }

    private static void removeTile() {
        Player player = AsteraniaMain.player;
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
        Tile old = world.findSection(mouse).getTileAbsoluteCoordinates(mouse);
        old.setBreakingLevel(0);
        player.setCurrentBreakingPercentage(0);
        player.getPlayerInventory().addStack(new InventoryObjectStack(GameRegistry.getInventoryObject(old)));
        player.setBreakingTile(false);
        world.findSection(mouse).setTileAbsoluteCoordinates(mouse, null);
    }

    private static void setTile(Tile newTile) {
        Player player = AsteraniaMain.player;
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());

        world.findSection(mouse).setTileAbsoluteCoordinates(mouse, newTile);
        world.findSection(mouse).getTileAbsoluteCoordinates(mouse).runPlacementEvents(world, player, mouse.toTileCoordinates());
    }

    private static void replaceDecorationTile(DecorationTile newTile) {
        Player player = AsteraniaMain.player;
        if (player.getPlayerInventory().calcCurrentWeight() >= player.getMaxWeight()) {
            TileBreakingUI.renderNoBreakingPossible = false;
        } else if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(InventoryObjectStack.EMPTY)) {
            removeDecorationTile();
            setDecorationTile(newTile);
        }

    }

    private static void removeDecorationTile() {
        Player player = AsteraniaMain.player;
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
        Tile old = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
        old.setBreakingLevel(0);
        player.setCurrentBreakingPercentage(0);
        player.getPlayerInventory().addStack(new InventoryObjectStack(GameRegistry.getInventoryObject(old)));
        player.setBreakingTile(false);
        world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, null);
    }

    private static void setDecorationTile(DecorationTile newTile) {
        Player player = AsteraniaMain.player;
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());

        world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, newTile);
        world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse).runPlacementEvents(world, player, mouse.toTileCoordinates());
    }

    public static void changeInventory(World world) {
        Player player = AsteraniaMain.player;
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
