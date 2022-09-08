package de.pianomanu.asterania.lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.KeyConfig;
import de.pianomanu.asterania.render.text.chat.ChatRenderer;
import de.pianomanu.asterania.render.ui.InventoryRenderer;
import de.pianomanu.asterania.utils.AsteraniaInputProcessor;
import de.pianomanu.asterania.utils.math.CoordinatesUtils;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.EntityCoordinates;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.entities.player.chat.Chat;
import de.pianomanu.asterania.world.entities.player.interaction.PlayerMovement;
import de.pianomanu.asterania.world.entities.player.interaction.environment.BackgroundLayerInteraction;
import de.pianomanu.asterania.world.entities.player.interaction.environment.DecorationLayerInteraction;

import java.util.logging.Logger;

public class PlayerUpdates extends GameLifeCycleUpdates {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    private static int timesScrolled = 0;

    protected static void updatePlayer(World world, Player player) {
        updateMovement(world, player);
        changeEnvironment(world, player);
        changeInventory(player);
        interactWithChat(player);
    }

    private static void updateMovement(World world, Player player) {
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

    public static void changeEnvironment(World world, Player player) {
        EntityCoordinates mouse = CoordinatesUtils.pixelToEntityCoordinates(Gdx.input.getX(), Gdx.input.getY(), player.getPos());

        if (player.canChangeBackgroundLayer()) {
            BackgroundLayerInteraction.replaceTile(world, player, mouse);
        } else {
            DecorationLayerInteraction.checkChangeDecorationLayer(world, player, mouse);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            LOGGER.fine("Changing world...");
            player.changeCurrentWorld(AsteraniaMain.INSTANCE.getCurrentActiveSavegame().getUniverse().getNextWorld(), player.getPos().toTileCoordinates());
        }
    }

    public static void changeInventory(Player player) {
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

    private static void interactWithChat(Player player) {
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
