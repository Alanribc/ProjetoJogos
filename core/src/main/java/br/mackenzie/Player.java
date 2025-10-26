package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject{

    private Vector2 touchPos;
    private Viewport viewport;

    public Player(Texture texture, float x, float y, float width, float heigth, Viewport viewport) {
        super(texture, x, y, width, heigth);
        this.viewport = viewport;
        this.touchPos = new Vector2();
    }

    @Override
    public void update(float dt){
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float personagemWidth = sprite.getWidth();

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            sprite.translateX(speed * dt);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            sprite.translateX(-speed * dt);
        }

        sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - personagemWidth));

    }
}
