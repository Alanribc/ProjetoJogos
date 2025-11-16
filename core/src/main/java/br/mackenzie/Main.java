package br.mackenzie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

    String palavra = "A";
    SpriteBatch batch;
    Player player;
    MapRenderer mapRenderer;
    OrthographicCamera camera;
    Niveis niveis;

    @Override
    public void create() {
        batch = new SpriteBatch();
        niveis = new Niveis();

        mapRenderer = new MapRenderer(niveis.getNivelAtual());
        player = new Player(mapRenderer, niveis);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapRenderer.getMapa()[0].length, mapRenderer.getMapa().length);
        camera.update();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        player.update(dt);

        // Câmera segue o jogador
        camera.position.set(player.getX() + 0.5f, player.getY() + 0.5f, 0);
        camera.update();


        batch.setProjectionMatrix(camera.combined);

        ScreenUtils.clear(Color.BLACK);
        batch.begin();
        mapRenderer.render(batch);
        player.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        mapRenderer.dispose();
        player.dispose();
    }
}
