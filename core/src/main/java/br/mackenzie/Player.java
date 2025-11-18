package br.mackenzie;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

/*
Player is tile-size aware: sprite size is slightly smaller than a tile for better collisions.
Start position is tile (1,1).
When key taken, plays keySound (if provided).
On reaching exit with key, triggers level progression via MainGame reference.
*/

public class Player {

    private Sprite sprite;
    private MapRenderer mapRenderer;
    private MazeGenerator generator;
    private boolean hasKey = false;
    private boolean won = false;
    private Sound keySound;
    private MainGame game;
    private int currentLevel;

    // position in world pixels but we map them to tile grid
    public Player(MapRenderer mapRenderer, MazeGenerator generator, int currentLevel, Sound keySound, MainGame game){
        this.mapRenderer = mapRenderer;
        this.generator = generator;
        this.keySound = keySound;
        this.game = game;
        this.currentLevel = currentLevel;

        sprite = new Sprite(new Texture("player.png"));
        // sprite scaled to tile size minus small margin
        float tw = mapRenderer.getTileWidth();
        float th = mapRenderer.getTileHeight();
        float size = Math.min(tw, th) * 0.85f;
        sprite.setSize(size, size);

        // start at tile (1,1)
        float startX = 1 * tw + (tw - size) / 2f;
        float startY = 1 * th + (th - size) / 2f;
        sprite.setPosition(startX, startY);
    }

    public void update(float dt){
        float speed = Math.min(mapRenderer.getTileWidth(), mapRenderer.getTileHeight()) * 3.0f; // pixels per second
        float vx = 0, vy = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) vx += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) vx -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) vy += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) vy -= speed;

        float dx = vx * dt;
        float dy = vy * dt;

        // move axis-separated (X then Y)
        moveAxis(dx, 0);
        moveAxis(0, dy);
    }

    private void moveAxis(float dx, float dy){
        float nextX = sprite.getX() + dx;
        float nextY = sprite.getY() + dy;
        float w = sprite.getWidth();
        float h = sprite.getHeight();

        // convert to tile indices
        int leftTile = (int)Math.floor((nextX) / mapRenderer.getTileWidth());
        int rightTile = (int)Math.floor((nextX + w - 1) / mapRenderer.getTileWidth());
        int bottomTile = (int)Math.floor((nextY) / mapRenderer.getTileHeight());
        int topTile = (int)Math.floor((nextY + h - 1) / mapRenderer.getTileHeight());

        // clamp inside map bounds
        leftTile = MathUtils.clamp(leftTile, 0, mapRenderer.getCols()-1);
        rightTile = MathUtils.clamp(rightTile, 0, mapRenderer.getCols()-1);
        bottomTile = MathUtils.clamp(bottomTile, 0, mapRenderer.getRows()-1);
        topTile = MathUtils.clamp(topTile, 0, mapRenderer.getRows()-1);

        boolean collision = false;
        int[] tiles = new int[]{
            mapRenderer.getTile(leftTile, bottomTile),
            mapRenderer.getTile(rightTile, bottomTile),
            mapRenderer.getTile(leftTile, topTile),
            mapRenderer.getTile(rightTile, topTile)
        };
        for(int t : tiles) if(t == 1) collision = true;

        if(!collision){
            sprite.setPosition(nextX, nextY);

            // pickup key if any of the corner tiles is 2
            for(int tx = leftTile; tx <= rightTile; tx++){
                for(int ty = bottomTile; ty <= topTile; ty++){
                    if(mapRenderer.getTile(tx, ty) == 2){
                        mapRenderer.setTile(tx, ty, 0);
                        hasKey = true;
                        if(keySound != null) keySound.play(0.9f);
                        Gdx.app.log("Player", "Chave coletada!");
                    }
                }
            }

            // check exit tile presence
            boolean atExit = false;
            for(int tx = leftTile; tx <= rightTile; tx++){
                for(int ty = bottomTile; ty <= topTile; ty++){
                    if(mapRenderer.getTile(tx, ty) == 3){
                        atExit = true;
                    }
                }
            }
            if(atExit){
                if(hasKey){
                    // if more levels exist, MainGame.nextLevel() will be called from GameScreen's hasWon check
                    // But we can mark won true to indicate to GameScreen to call nextLevel
                    won = true;
                } else {
                    Gdx.app.log("Player", "Necessário a chave para sair!");
                }
            }
        } else {
            // collision: do not move on that axis
        }
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void dispose(){
        sprite.getTexture().dispose();
    }

    public boolean hasWon(){ return won; }
}
