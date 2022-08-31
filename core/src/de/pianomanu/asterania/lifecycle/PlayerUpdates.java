package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.entities.player.chat.Chat;
import de.pianomanu.asterania.inventory.item.ItemStack;
import de.pianomanu.asterania.inventory.tileproperties.TileProperties;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;
import de.pianomanu.asterania.render.ui.InventoryRenderer;
import de.pianomanu.asterania.render.ui.TileBreakingUI;
import de.pianomanu.asterania.utils.AsteraniaInputProcessor;
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

    protected static void updatePlayer(World world) {
        updateMovement(world);
        changeEnvironment(world);
        changeInventory(world);
        interactWithChat();
    }

    private static void updateMovement(World world) {
        Player player = AsteraniaMain.player;
        if (player.isMoving())
            player.updateHitbox();
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            move(world, player, Direction.UP);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            move(world, player, Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            move(world, player, Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            move(world, player, Direction.LEFT);
        }
        if (player.isMoving())
            player.checkForAnimationUpdate(Gdx.graphics.getDeltaTime());
        if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
            player.setStanding();
    }

    private static boolean move(World world, Player player, Direction direction) {
        player.setMoving();
        TileCoordinates playerTile = player.getPos().toTileCoordinates();
        TileCoordinates adjacentTileCoords = playerTile.copy().move(direction);
        if (world.findSection(playerTile).getTileAbsoluteCoordinates(playerTile).getSettings().get(TileProperties.IS_ACCESSIBLE)) {
            generateSurroundingSections(world, adjacentTileCoords);
            if (isAdjacentTileAccessible(world, player, direction) || playerStaysOnTile(player, direction, adjacentTileCoords)) {
                player.move(direction, Gdx.graphics.getDeltaTime());
                return true;
            }
            if (playerShouldStopMovement(player, direction, adjacentTileCoords)) {
                teleportPlayerToTileBorder(player, direction, adjacentTileCoords);
                if (Gdx.input.isKeyJustPressed(direction.getKeyFromDirection()))
                    player.setPlayerFacing(direction);
                return true;
            }
        }
        return false;
    }

    private static boolean isAdjacentTileAccessible(World world, Player player, Direction direction) {
        EntityCoordinates moved1 = switch (direction) {
            case RIGHT -> new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y);
            case LEFT -> new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y);
            case UP -> new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y + 1);
            case DOWN -> new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y - 1);
        };
        EntityCoordinates moved2 = switch (direction) {
            case RIGHT -> new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y + player.getPlayerZDepth());
            case LEFT -> new EntityCoordinates(player.getPlayerHitbox().start.x, player.getPlayerHitbox().start.y + player.getPlayerZDepth());
            case UP -> new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y + 1);
            case DOWN -> new EntityCoordinates(player.getPlayerHitbox().end.x, player.getPlayerHitbox().start.y - 1);
        };
        boolean moved1And2areSameTile = switch (direction) {
            case RIGHT, LEFT -> Math.ceil(player.getPlayerHitbox().start.y) == Math.ceil(player.getPlayerHitbox().start.y + player.getPlayerZDepth());
            case UP, DOWN -> Math.ceil(player.getPlayerHitbox().start.x) == Math.ceil(player.getPlayerHitbox().end.x);
        };
        boolean decorationAccessible1 = isDecorationAccessible(world, moved1);
        boolean decorationAccessible2 = isDecorationAccessible(world, moved2);
        boolean tileAccessible1 = world.findSection(moved1).getTileAbsoluteCoordinates(moved1).getSettings().get(TileProperties.IS_ACCESSIBLE);
        boolean tileAccessible2 = world.findSection(moved2).getTileAbsoluteCoordinates(moved2).getSettings().get(TileProperties.IS_ACCESSIBLE);
        if (moved1And2areSameTile)
            return decorationAccessible1 && tileAccessible1;
        return tileAccessible1 && decorationAccessible1 && tileAccessible2 && decorationAccessible2;
    }

    private static boolean playerStaysOnTile(Player player, Direction direction, TileCoordinates adjacentTileCoords) {
        return switch (direction) {
            case RIGHT -> player.getPlayerHitbox().end.x < adjacentTileCoords.getX();
            case LEFT -> player.getPlayerHitbox().start.x >= adjacentTileCoords.getX() + 1;
            case UP -> player.getPos().y + player.getStepSize() * Gdx.graphics.getDeltaTime() < adjacentTileCoords.getY() - player.getPlayerZDepth();
            case DOWN -> player.getPos().y - player.getStepSize() * Gdx.graphics.getDeltaTime() > adjacentTileCoords.getY() + 1;
        };
    }

    private static boolean playerShouldStopMovement(Player player, Direction direction, TileCoordinates adjacentTileCoords) {
        return switch (direction) {
            case RIGHT -> player.getPlayerHitbox().end.x + player.getStepSize() * Gdx.graphics.getDeltaTime() >= adjacentTileCoords.getX();
            case LEFT -> player.getPlayerHitbox().start.x - player.getStepSize() * Gdx.graphics.getDeltaTime() <= adjacentTileCoords.getX() + 1;
            case UP -> player.getPos().y + player.getStepSize() * Gdx.graphics.getDeltaTime() > adjacentTileCoords.getY() - player.getPlayerZDepth();
            case DOWN -> player.getPos().y - player.getStepSize() * Gdx.graphics.getDeltaTime() < adjacentTileCoords.getY() + 1;
        };
    }

    private static void teleportPlayerToTileBorder(Player player, Direction direction, TileCoordinates adjacentTileCoords) {
        float xHitboxWidth = player.getCharacterSize().x;
        EntityCoordinates pos = player.getPos();
        switch (direction) {
            case RIGHT -> player.setPos(adjacentTileCoords.getX() - xHitboxWidth / 2, pos.y);
            case LEFT -> player.setPos(adjacentTileCoords.getX() + 1 + xHitboxWidth / 2, pos.y);
            case UP -> player.setPos(pos.x, adjacentTileCoords.getY() - player.getPlayerZDepth());
            case DOWN -> player.setPos(pos.x, adjacentTileCoords.getY() + 1);
        }
    }

    private static void generateSurroundingSections(World world, TileCoordinates coordinatesOfNewSection) {
        if (world.findSection(coordinatesOfNewSection) == null) {
            world.preGenerateSurroundingWorldSections();
        }
    }

    private static boolean isDecorationAccessible(World world, EntityCoordinates entityCoordinates) {
        if (world.findSection(entityCoordinates).getDecorationLayerTileAbsoluteCoordinates(entityCoordinates) != null) {
            return world.findSection(entityCoordinates).getDecorationLayerTileAbsoluteCoordinates(entityCoordinates).getSettings().get(TileProperties.IS_ACCESSIBLE);
        }
        return true;
    }

    public static void changeEnvironment(World world) {
        Player player = AsteraniaMain.player;
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());

        //if (Gdx.input.isButtonPressed(KeyConfig.REPLACE_TILE)) {
        if (player.canChangeBackgroundLayer()) {
            changeBackgroundTilesLayer(world, player, mouse);
        } else {
            changeDecorationLayer(world, player, mouse);
        }
        if (Gdx.input.isButtonJustPressed(KeyConfig.PLACE_OR_INTERACT_WITH_TILE)) {
            //interact with tile or place decorative tile

            //first check if decorative tile can be placed
            Tile decorationLayerTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
            if (!player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY) && decorationLayerTile == null) {
                Tile holding = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getItem());
                if (holding instanceof DecorationTile) {
                    DecorationTile newDecorationTile = (DecorationTile) holding;
                    if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
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

    private static void changeDecorationLayer(World world, Player player, EntityCoordinates mouse) {
        Tile oldDecorationTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
        if (Gdx.input.isButtonPressed(KeyConfig.REMOVE_TILE)) {
            if (player.isInReach(mouse.toTileCoordinates())) {
                if (oldDecorationTile != null) {
                    float breakingTime = oldDecorationTile.getSettings().get(TileProperties.BREAK_TIME);
                    //check how much player is already carrying
                    if (player.getPlayerInventory().calcCurrentWeight() + Objects.requireNonNull(GameRegistry.getItem(oldDecorationTile)).getWeight() <= player.getMaxWeight()) {
                        TileBreakingUI.renderNoBreakingPossible = false;
                        if (Gdx.input.isButtonJustPressed(KeyConfig.REMOVE_TILE))
                            player.setBreakingTile(true);
                        oldDecorationTile.setBreakingLevel(oldDecorationTile.getBreakingLevel() + Gdx.graphics.getDeltaTime());
                        player.setCurrentBreakingPercentage(oldDecorationTile.getBreakingLevel() / breakingTime);
                        LOGGER.finest("Breaking level " + oldDecorationTile.getBreakingLevel() + ", BreakTime" + oldDecorationTile.getSettings().get(TileProperties.BREAK_TIME));
                        if (oldDecorationTile.getBreakingLevel() >= breakingTime) {
                            //break decoration and replace
                            removeDecorationTile();
                        }
                    } else {
                        //too much weight in inventory
                        TileBreakingUI.renderNoBreakingPossible = true;
                        TileBreakingUI.renderNoBreakingPossibleMessage = "Inventory full";
                    }
                }
            }
        } else {
            //breaking button released
            resetProgressBar(player, oldDecorationTile);
        }
        if (Gdx.input.isButtonJustPressed(KeyConfig.PLACE_OR_INTERACT_WITH_TILE)) {
            if (player.isInReach(mouse.toTileCoordinates())) {
                Tile decorationLayerTile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
                if (decorationLayerTile == null) {

                    if (!player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
                        Tile holding = GameRegistry.getTile(player.getPlayerInventory().getCurrentIOStack().getItem());
                        if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
                            world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, holding);
                            player.getPlayerInventory().getCurrentIOStack().decrement();
                        }
                    }
                } else {
                    Tile tile = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
                    tile.performAction(player, world);
                }
            }
        }
    }

    private static void resetProgressBar(Player player, Tile tile) {
        player.setCurrentBreakingPercentage(0);
        player.setBreakingTile(false);
        if (tile != null)
            tile.setBreakingLevel(0);
        TileBreakingUI.renderNoBreakingPossible = false;
    }

    private static void changeBackgroundTilesLayer(World world, Player player, EntityCoordinates mouse) {
        Tile old = world.findSection(mouse).getTileAbsoluteCoordinates(mouse);
        //TODO
    }

    private static void replaceTile(Tile newTile) {
        Player player = AsteraniaMain.player;

        if (player.getPlayerInventory().calcCurrentWeight() >= player.getMaxWeight()) {
            TileBreakingUI.renderNoBreakingPossible = false;
        } else if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
            removeTile();
            setTile(newTile);
        }

        /*old.setBreakingLevel(0);
        player.setCurrentBreakingPercentage(0);
        player.getPlayerInventory().addStack(new ItemStack(GameRegistry.getItem(old)));
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
        player.getPlayerInventory().addStack(new ItemStack(GameRegistry.getItem(old)));
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

    private static void replaceDecorationTile(Tile newTile) {
        Player player = AsteraniaMain.player;
        if (player.getPlayerInventory().calcCurrentWeight() >= player.getMaxWeight()) {
            TileBreakingUI.renderNoBreakingPossible = false;
        } else {
            removeDecorationTile();
            if (player.getPlayerInventory().getCurrentIOStack().getStackCount() >= 1 && !player.getPlayerInventory().getCurrentIOStack().equals(ItemStack.EMPTY)) {
                setDecorationTile(newTile);
            }
        }

    }

    private static void removeDecorationTile() {
        Player player = AsteraniaMain.player;
        World world = player.getCurrentWorld();
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());
        Tile old = world.findSection(mouse).getDecorationLayerTileAbsoluteCoordinates(mouse);
        old.setBreakingLevel(0);
        player.setCurrentBreakingPercentage(0);
        player.getPlayerInventory().addStack(new ItemStack(GameRegistry.getItem(old)));
        player.setBreakingTile(false);
        world.findSection(mouse).setDecorationLayerTileAbsoluteCoordinates(mouse, null);
    }

    private static void setDecorationTile(Tile newTile) {
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

    private static void interactWithChat() {
        Player player = AsteraniaMain.player;
        Chat chat = player.getChat();
        if (Gdx.input.isKeyJustPressed(KeyConfig.OPEN_CHAT) && !player.isChatOpen()) {
            player.setChatOpen(true);

            //remove any left-over input
            AsteraniaInputProcessor.getTextInput().clear();
            //render previous chat

            //do render text line
            ChatRenderer.setChatIsOpen(true);

        } else if (Gdx.input.isKeyJustPressed(KeyConfig.SEND_MESSAGE) && player.isChatOpen()) {
            player.setChatOpen(false);

            //add typed message to chat log
            chat.addLastMessageToChat();

            //do no longer render text line
            ChatRenderer.setChatIsOpen(false);

            AsteraniaInputProcessor.getTextInput().clear();
        } else if (player.isChatOpen()) {
            chat.setCurrentMessage(AsteraniaInputProcessor.getTextInput().getInputString());
            System.out.println(chat.getCurrentMessage());
        }
    }
}
