package br.mackenzie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture personagemTexture;
    Texture backgroundTexture;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Player player;

    @Override
    public void create() {
        backgroundTexture = new Texture("backgroundSprite.jpg");
        personagemTexture = new Texture("exemploSprite.jpg");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        player = new Player(personagemTexture, 3.5f, 0.5f, 1, 1, viewport);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        updateGameObjetcs(dt);

        drawGameObjects();

    }

    private void drawGameObjects(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        player.draw(spriteBatch);

        spriteBatch.end();
    }

    private void updateGameObjetcs(float dt){
        player.update(dt);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        personagemTexture.dispose();
        backgroundTexture.dispose();
    }
}
