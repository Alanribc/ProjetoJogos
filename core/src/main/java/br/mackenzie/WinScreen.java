package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.BitmapFont;



public class WinScreen implements Screen {

    private final MainGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport vp;
    private final float WORLD_WIDTH = 1280f, WORLD_HEIGHT = 720f;
    private Texture bg, replayBtn, menuBtn;
    private Rectangle replayRect, menuRect;
    private BitmapFont font;

    public WinScreen(MainGame game){
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        vp = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        vp.apply();
        camera.position.set(WORLD_WIDTH/2f, WORLD_HEIGHT/2f, 0);
        camera.update();

        bg = new Texture("pause_bg.jpg");
        replayBtn = new Texture("resume_button.png");
        menuBtn = new Texture("menu_button.png");

        float bw = 360, bh = 100;
        replayRect = new Rectangle(WORLD_WIDTH/2f - bw/2f, WORLD_HEIGHT/2f + 30, bw, bh);
        menuRect = new Rectangle(WORLD_WIDTH/2f - bw/2f, WORLD_HEIGHT/2f - 110, bw, bh);

        font = new BitmapFont();
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
        font.getData().setScale(2f);
        font.draw(batch, "Parabéns! Você venceu!", WORLD_WIDTH/2f - 320, WORLD_HEIGHT/2f + 150);
        font.getData().setScale(0.9f);
        font.draw(batch, "Escolha uma opção:", WORLD_WIDTH/2f - 150, WORLD_HEIGHT/2f + 80);

        batch.draw(replayBtn, replayRect.x, replayRect.y, replayRect.width, replayRect.height);
        batch.draw(menuBtn, menuRect.x, menuRect.y, menuRect.width, menuRect.height);
        batch.end();

        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touch);
            if(replayRect.contains(touch.x, touch.y)){
                game.replay();
            } else if(menuRect.contains(touch.x, touch.y)){
                game.goToMenu();
            }
        }

        // also allow ENTER to replay and ESC to menu
        if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)){
            game.replay();
        }
        if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)){
            game.goToMenu();
        }
    }

    @Override public void resize(int width, int height) { vp.update(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        replayBtn.dispose();
        menuBtn.dispose();
        font.dispose();
    }
}
