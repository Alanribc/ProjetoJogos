package br.mackenzie;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener {

    Texture personagemTexture;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Player player;
    MapManager mapManager;

    @Override
    public void create() {
        personagemTexture = new Texture("exemploSprite.jpg");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Carrega mapa
        mapManager = new MapManager("mapas/fase1.tmx");

        // Cria jogador
        player = new Player(personagemTexture, 1, 1, 1, 1, viewport, mapManager);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();

        player.update(dt);

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        // Renderiza mapa
        mapManager.render(viewport);

        // Renderiza jogador
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        player.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        personagemTexture.dispose();
        mapManager.dispose();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }
}
