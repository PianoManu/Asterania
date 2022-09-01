package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.entities.player.chat.Chat;
import de.pianomanu.asterania.entities.player.interaction.PlayerMovement;
import de.pianomanu.asterania.entities.player.interaction.environment.DecorationLayerInteraction;
import de.pianomanu.asterania.inventory.item.ItemStack;
import de.pianomanu.asterania.registry.GameRegistry;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;
import de.pianomanu.asterania.render.ui.InventoryRenderer;
import de.pianomanu.asterania.render.ui.TileBreakingUI;
import de.pianomanu.asterania.utils.AsteraniaInputProcessor;
import de.pianomanu.asterania.utils.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.tile.Tile;

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
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_UP)) {
            PlayerMovement.move(world, player, Direction.UP);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN)) {
            PlayerMovement.move(world, player, Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT)) {
            PlayerMovement.move(world, player, Direction.RIGHT);
        }
        if (Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT)) {
            PlayerMovement.move(world, player, Direction.LEFT);
        }
        if (player.isMoving())
            player.checkForAnimationUpdate(Gdx.graphics.getDeltaTime());
        if (!Gdx.input.isKeyPressed(KeyConfig.MOVE_RIGHT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_LEFT) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_UP) && !Gdx.input.isKeyPressed(KeyConfig.MOVE_DOWN))
            player.setStanding();
    }

    public static void changeEnvironment(World world) {
        Player player = AsteraniaMain.player;
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());

        //if (Gdx.input.isButtonPressed(KeyConfig.REPLACE_TILE)) {
        if (player.canChangeBackgroundLayer()) {
            changeBackgroundTilesLayer(world, player, mouse);
        } else {
            DecorationLayerInteraction.checkChangeDecorationLayer(world, player, mouse);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            LOGGER.fine("Changing world...");
            AsteraniaMain.player.changeCurrentWorld(AsteraniaMain.saveFile.getUniverse().getNextWorld(), player.getPos().toTileCoordinates());
        }
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
