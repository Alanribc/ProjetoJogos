package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject {

    private Viewport viewport;
    private MapManager mapManager;

    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport, MapManager mapManager) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        this.mapManager = mapManager;
    }

    @Override
    public void update(float dt) {
        float speed = 4f * dt;
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveX += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveY -= speed;

        float nextX = sprite.getX() + moveX;
        float nextY = sprite.getY() + moveY;

        Rectangle nextBounds = new Rectangle(nextX, nextY, sprite.getWidth(), sprite.getHeight());

        // Verifica colisão com todas as paredes do mapa
        for (Rectangle wall : mapManager.getCollisionRectangles()) {
            if (Intersector.overlaps(nextBounds, wall)) {
                return; // Colidiu, não se move
            }
        }

        sprite.setPosition(nextX, nextY);

        // Mantém dentro da tela
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));
    }
}
