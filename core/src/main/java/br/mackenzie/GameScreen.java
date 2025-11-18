package br.mackenzie;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;



public class GameScreen implements Screen {

    private final MainGame game;
    private int levelIndex;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport vp;
    private final float WORLD_TILE = 64f; // pixel size per tile in viewport mapping
    private int[][] mapa;
    private MapRenderer mapRenderer;
    private Player player;
    private MazeGenerator generator;
    private BitmapFont font;
    private Music bgm;
    private Sound keySound;
    private boolean paused = false;

    // Pause UI rectangles (in world pixels)
    private Rectangle pauseResumeRect, pauseMenuRect;
    private Texture pauseBgTex, resumeBtnTex, menuBtnTex;

    // viewport units chosen to match 1280x720 world coordinates
    private final float WORLD_WIDTH = 1280f;
    private final float WORLD_HEIGHT = 720f;

    public GameScreen(MainGame game, int levelIndex){
        this.game = game;
        this.levelIndex = levelIndex;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        vp = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        vp.apply();
        camera.position.set(WORLD_WIDTH/2f, WORLD_HEIGHT/2f, 0);
        camera.update();

        // load assets
        font = new BitmapFont();
        font.getData().setScale(0.8f); // smaller font for level name

        // audio
        try {
            bgm = Gdx.audio.newMusic(Gdx.files.internal("audio/bgm.mp3")); // your file
            bgm.setLooping(true);
            bgm.setVolume(0.4f);
            bgm.play();
        } catch(Exception e){
            Gdx.app.log("GameScreen", "bgm not found: " + e.getMessage());
        }
        try {
            keySound = Gdx.audio.newSound(Gdx.files.internal("audio/bip.mp3"));
        } catch(Exception e){
            keySound = null;
            Gdx.app.log("GameScreen", "key sound not found: " + e.getMessage());
        }

        // generate map
        generator = new MazeGenerator();
        mapa = generator.getMazeForLevel(levelIndex);
        mapRenderer = new MapRenderer(mapa, WORLD_WIDTH, WORLD_HEIGHT);

        player = new Player(mapRenderer, generator, levelIndex, keySound, game);

        // pause button assets
        pauseBgTex = new Texture("pause_bg.jpg");
        resumeBtnTex = new Texture("resume_button.png");
        menuBtnTex = new Texture("menu_button.png");

        // Buttons centered
        float bw = 360, bh = 100;
        pauseResumeRect = new Rectangle(WORLD_WIDTH/2f - bw/2f, WORLD_HEIGHT/2f + 40, bw, bh);
        pauseMenuRect = new Rectangle(WORLD_WIDTH/2f - bw/2f, WORLD_HEIGHT/2f - 80, bw, bh);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        if(!paused) {
            player.update(delta);
            if(player.hasWon()){
                // stop music and show win screen
                if(bgm != null) bgm.stop();
                game.nextLevel();
                return;
            }
        }

        // Pause toggle keys
        if(Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)){
            paused = true;
        }

        // Render
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        mapRenderer.render(batch);
        player.draw(batch);

        // HUD: level small on top-left
        font.getData().setScale(0.9f);
        font.draw(batch, "Level: " + levelIndex, 20, WORLD_HEIGHT - 10);

        // show instruction for pause
        font.getData().setScale(0.6f);
        font.draw(batch, "P = Pause", WORLD_WIDTH - 120, WORLD_HEIGHT - 10);

        // If paused draw pause overlay and buttons
        if(paused){
            batch.draw(pauseBgTex, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(resumeBtnTex, pauseResumeRect.x, pauseResumeRect.y, pauseResumeRect.width, pauseResumeRect.height);
            batch.draw(menuBtnTex, pauseMenuRect.x, pauseMenuRect.y, pauseMenuRect.width, pauseMenuRect.height);

            // handle clicks
            if(Gdx.input.justTouched()){
                Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
                camera.unproject(touch);
                if(pauseResumeRect.contains(touch.x, touch.y)){
                    paused = false;
                } else if(pauseMenuRect.contains(touch.x, touch.y)){
                    // go to menu
                    if(bgm != null) { bgm.stop(); bgm.dispose(); }
                    game.goToMenu();
                    dispose();
                    return;
                }
            }
        }

        batch.end();
    }

    @Override public void resize(int width, int height) { vp.update(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        mapRenderer.dispose();
        player.dispose();
        font.dispose();
        if(bgm != null) bgm.dispose();
        if(keySound != null) keySound.dispose();
        pauseBgTex.dispose();
        resumeBtnTex.dispose();
        menuBtnTex.dispose();
    }
}
