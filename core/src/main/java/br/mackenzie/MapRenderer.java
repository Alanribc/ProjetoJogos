package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
Draws tiles scaled to the screen world (1280x720).
It will scale each tile so the entire maze fits comfortably on screen.
*/
public class MapRenderer {

    private int[][] mapa;
    private Texture wall, floor, item, exit;
    private float tileW, tileH;
    private float offsetX = 0, offsetY = 0;
    private float worldWidth = 1280f, worldHeight = 720f;

    public MapRenderer(int[][] mapa, float worldWidth, float worldHeight){
        this.mapa = mapa;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        wall = new Texture("wall.png");
        floor = new Texture("floor.png");
        item = new Texture("item.png");
        exit = new Texture("exit.png");

        int cols = mapa[0].length;
        int rows = mapa.length;
        // tile sizes so maze occupies most of screen leaving some HUD top
        tileW = worldWidth / cols;
        tileH = (worldHeight - 80f) / rows; // reserve ~80px for HUD at top
    }

    public void render(SpriteBatch batch){
        int rows = mapa.length;
        int cols = mapa[0].length;
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < cols; x++){
                Texture t = floor;
                int v = mapa[y][x];
                if(v == 1) t = wall;
                else if(v == 2) t = item;
                else if(v == 3) t = exit;
                float drawX = x * tileW;
                float drawY = y * tileH;
                batch.draw(t, drawX, drawY, tileW, tileH);
            }
        }
    }

    public int getTileAtPixel(float px, float py){
        // not used by Player; Player uses tile indices directly
        int col = (int)(px / tileW);
        int row = (int)(py / tileH);
        return getTile(col, row);
    }

    public int getTile(int tx, int ty){
        if(tx < 0 || ty < 0 || ty >= mapa.length || tx >= mapa[0].length) return 1;
        return mapa[ty][tx];
    }

    public void setTile(int tx, int ty, int value){
        if(tx < 0 || ty < 0 || ty >= mapa.length || tx >= mapa[0].length) return;
        mapa[ty][tx] = value;
    }

    public int[][] getMapa(){
        return mapa;
    }

    public int getCols(){ return mapa[0].length; }
    public int getRows(){ return mapa.length; }
    public float getTileWidth(){ return tileW; }
    public float getTileHeight(){ return tileH; }

    public void dispose(){
        wall.dispose();
        floor.dispose();
        item.dispose();
        exit.dispose();
    }
}
