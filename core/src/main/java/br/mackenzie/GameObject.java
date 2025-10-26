package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {

    protected Sprite sprite;
    protected Rectangle bounds;

    public GameObject(Texture texture, float x, float y, float width, float heigth) {
        this.bounds = new Rectangle(x, y, width, heigth);
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, heigth);
        this.sprite.setPosition(x, y);
    }

    public abstract void update(float dt);

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public Rectangle getBounds(){
        bounds.setPosition(sprite.getX(), sprite.getY());
        return bounds;
    }

    public void setPosition(float x, float y){
        sprite.setPosition(x, y);
    }




}
