package de.pianomanu.asterania.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.pianomanu.asterania.config.DisplayConfig;
import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;

public class RenderWorld {
    public static void renderTerrain(World world, SpriteBatch batch, TextureAtlas atlas) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float posX = world.getPlayer().getCharacterPos().x / DisplayConfig.TILE_SIZE;
        float posY = world.getPlayer().getCharacterPos().y / DisplayConfig.TILE_SIZE;
        int startRenderingX = (int) (world.getPlayer().getCharacterPos().x - DisplayConfig.DISPLAY_WIDTH / 2f - DisplayConfig.TILE_SIZE) / DisplayConfig.TILE_SIZE;
        int stopRenderingX = (int) (world.getPlayer().getCharacterPos().x + DisplayConfig.DISPLAY_WIDTH / 2f + DisplayConfig.TILE_SIZE) / DisplayConfig.TILE_SIZE;
        int startRenderingY = (int) (world.getPlayer().getCharacterPos().y - DisplayConfig.DISPLAY_HEIGHT / 2f - DisplayConfig.TILE_SIZE) / DisplayConfig.TILE_SIZE;
        int stopRenderingY = (int) (world.getPlayer().getCharacterPos().y + DisplayConfig.DISPLAY_HEIGHT / 2f + DisplayConfig.TILE_SIZE) / DisplayConfig.TILE_SIZE;
        startRenderingX = (int) world.getPlayer().getCharacterPos().x - width / DisplayConfig.TILE_SIZE + 6;
        stopRenderingX = (int) world.getPlayer().getCharacterPos().x + width / DisplayConfig.TILE_SIZE + 2;
        startRenderingY = (int) world.getPlayer().getCharacterPos().y - height / DisplayConfig.TILE_SIZE + 4;
        stopRenderingY = (int) world.getPlayer().getCharacterPos().y + height / DisplayConfig.TILE_SIZE + 2;
        batch.begin();
        //System.out.println(startRenderingX+", "+stopRenderingX+", "+startRenderingY+", "+stopRenderingY);
        for (int x = startRenderingX; x < stopRenderingX; x++) {
            for (int y = startRenderingY; y < stopRenderingY; y++) {
                try {
                    batch.draw(world.getTile(x, y).getTexture(atlas), x * DisplayConfig.TILE_SIZE - world.getPlayer().getCharacterPos().x * DisplayConfig.TILE_SIZE, y * DisplayConfig.TILE_SIZE - world.getPlayer().getCharacterPos().y * DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
                } catch (ArrayIndexOutOfBoundsException e) {
                    //System.out.println("Catch  "+x+", "+y);
                }
            }
        }
        batch.end();
    }

    public static void renderPlayer(World world, SpriteBatch batch, TextureRegion playerTexture) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        Player player = world.getPlayer();
        batch.begin();
        //batch.draw(img, player.getCharacterPos().x - player.getCharacterSize().x/2f, player.getCharacterPos().y - player.getCharacterSize().y/2f, player.getCharacterSize().x,player.getCharacterSize().y);
        batch.draw(playerTexture, width / 2f - player.getCharacterSize().x / 2f, height / 2f - player.getCharacterSize().y / 2f, player.getCharacterSize().x, player.getCharacterSize().y);
        batch.end();
    }

    public static void renderHovering(World world, ShapeRenderer shapeRenderer) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        int mouseX = Gdx.input.getX();
        int mouseY = height - Gdx.input.getY();
        float playerX = world.getPlayer().getCharacterPos().x;
        float playerY = world.getPlayer().getCharacterPos().y;

        System.out.println(mouseX + ", " + mouseY + "        " + playerX + ", " + playerY);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 0.2f);
        shapeRenderer.rect(Math.round(mouseX / DisplayConfig.TILE_SIZE) * DisplayConfig.TILE_SIZE - (playerX * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE, Math.round(mouseY / DisplayConfig.TILE_SIZE) * DisplayConfig.TILE_SIZE - (playerY * DisplayConfig.TILE_SIZE) % DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE, DisplayConfig.TILE_SIZE);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
