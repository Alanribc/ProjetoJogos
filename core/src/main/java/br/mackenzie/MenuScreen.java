package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

/*
Assets expected:
- menu_background.png (1280x720 recommended)
- play_button.png
- exit_button.png
*/

public class MenuScreen implements Screen {

    private final MainGame game;
    private SpriteBatch batch;
    private Texture bg, playBtn, exitBtn;
    private OrthographicCamera camera;
    private FitViewport vp;
    private Rectangle playRect, exitRect;
    private final float WORLD_WIDTH = 1280f, WORLD_HEIGHT = 720f;

    public MenuScreen(MainGame game){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        vp = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        vp.apply();
        camera.position.set(WORLD_WIDTH/2f, WORLD_HEIGHT/2f, 0);
        camera.update();

        bg = new Texture("menu_background.png");
        playBtn = new Texture("play_button.png");
        exitBtn = new Texture("exit_button.png");

        // button rectangles centered
        float bw = 360, bh = 100;
        playRect = new Rectangle(WORLD_WIDTH/2f - bw/2f, WORLD_HEIGHT/2f + 30, bw, bh);
        exitRect = new Rectangle(WORLD_WIDTH/2f - bw/2f, WORLD_HEIGHT/2f - 110, bw, bh);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bg, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        batch.draw(playBtn, playRect.x, playRect.y, playRect.width, playRect.height);
        batch.draw(exitBtn, exitRect.x, exitRect.y, exitRect.width, exitRect.height);
        batch.end();

        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touch);
            if(playRect.contains(touch.x, touch.y)){
                game.startGameAt(1);
            } else if(exitRect.contains(touch.x, touch.y)){
                Gdx.app.exit();
            }
        }

        // also allow Enter to start
        if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)){
            game.startGameAt(1);
        }
    }

    @Override public void resize(int width, int height) { vp.update(width, height); camera.position.set(WORLD_WIDTH/2f, WORLD_HEIGHT/2f, 0); camera.update();}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        playBtn.dispose();
        exitBtn.dispose();
    }
}
