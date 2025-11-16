package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapRenderer {
    private int[][] mapa;
    private Texture parede, chao, item, saida;
    private float tileSize = 1f;

    public MapRenderer(int[][] mapa) {
        this.mapa = mapa;
        parede = new Texture("wall.png");
        chao = new Texture("floor.png");
        item = new Texture("item.png");
        saida = new Texture("exit.png");
    }

    public void render(SpriteBatch batch) {
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[0].length; x++) {
                Texture t;
                switch (mapa[y][x]) {
                    case 1: t = parede; break;
                    case 2: t = item; break;
                    case 3: t = saida; break;
                    default: t = chao; break;
                }
                batch.draw(t, x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    public int getTile(int x, int y){
        if(x < 0 || y < 0 || y >= mapa.length || x >= mapa[0].length) return 1;
        return mapa[y][x];
    }

    public void setTile(int x, int y, int value){
        mapa[y][x] = value;
    }

    public void setMapa(int[][] novoMapa){
        this.mapa = novoMapa;
    }

    public int[][] getMapa(){
        return mapa;
    }

    public void dispose(){
        parede.dispose();
        chao.dispose();
        item.dispose();
        saida.dispose();
    }
}
