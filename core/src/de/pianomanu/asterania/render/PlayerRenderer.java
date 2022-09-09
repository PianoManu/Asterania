package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.render.atlas.Atlases;
import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.entities.Player;

public class PlayerRenderer {
    public static void render(Player player) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        TextureRegion playerTexture = getTexture(player);
        SpriteBatchUtils.getInstance().draw(playerTexture, (int) (width / 2 - player.getCharacterSizeInPixels().x / 2), (int) height / 2, (int) player.getCharacterSizeInPixels().x, (int) player.getCharacterSizeInPixels().y);
    }

    private static TextureRegion getTexture(Player player) {
        int animationTextureNumber = 0;
        if (player.getMovingAnimationCounter() == 0 || player.getMovingAnimationCounter() == 2)
            animationTextureNumber = 1;
        if (player.getMovingAnimationCounter() == 1)
            animationTextureNumber = 2;
        if (player.getMovingAnimationCounter() == 3)
            animationTextureNumber = 3;
        Direction playerFacing = player.getPlayerFacing();
        String textureName = "p_" + playerFacing.toString() + (animationTextureNumber);
        return AsteraniaMain.INSTANCE.getAssetManager().get(Atlases.PLAYER_ATLAS_LOCATION, TextureAtlas.class).findRegion(textureName);
    }
}
