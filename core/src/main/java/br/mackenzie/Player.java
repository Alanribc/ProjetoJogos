package br.mackenzie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    //declarando:
    private Sprite sprite;
    private MapRenderer mapRenderer;
    private Niveis niveis;
    private boolean hasItem = false;

    public Player(MapRenderer mapRenderer, Niveis niveis){
        this.mapRenderer = mapRenderer;
        this.niveis = niveis;
        sprite = new Sprite(new Texture("player.jpg"));
        sprite.setSize(1f,1f);
        sprite.setPosition(1f,1f);
    }

    public void update(float dt){
        float speed = 4f * dt;
        float nextX = sprite.getX();
        float nextY = sprite.getY();

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) nextX += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) nextX -= speed;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) nextY += speed;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) nextY -= speed;

        float width = sprite.getWidth();
        float height = sprite.getHeight();

        // Verifica colisão nos quatro cantos
        int tile1 = mapRenderer.getTile((int)nextX, (int)nextY);
        int tile2 = mapRenderer.getTile((int)(nextX + width - 0.01f), (int)nextY);
        int tile3 = mapRenderer.getTile((int)nextX, (int)(nextY + height - 0.01f));
        int tile4 = mapRenderer.getTile((int)(nextX + width - 0.01f), (int)(nextY + height - 0.01f));

        if(tile1 != 1 && tile2 != 1 && tile3 != 1 && tile4 != 1){
            sprite.setPosition(nextX, nextY);

            // Item
            if(tile1 == 2 || tile2 == 2 || tile3 == 2 || tile4 == 2){
                int ix = (tile1==2)? (int)nextX : (tile2==2)? (int)(nextX+width-0.01f) : (tile3==2)? (int)nextX : (int)(nextX+width-0.01f);
                int iy = (tile1==2)? (int)nextY : (tile2==2)? (int)nextY : (tile3==2)? (int)(nextY+height-0.01f) : (int)(nextY+height-0.01f);
                mapRenderer.setTile(ix, iy, 0);
                hasItem = true;
                System.out.println("Item coletado!");
            }

            // Saída
            if(tile1 == 3 || tile2 == 3 || tile3 == 3 || tile4 == 3){
                if(hasItem){
                    System.out.println("Você venceu o nível " + niveis.getNivelIndex() + "!");
                    if(niveis.proximoNivel()){
                        mapRenderer.setMapa(niveis.getNivelAtual());
                        sprite.setPosition(1f,1f);
                        hasItem = false;
                    } else {
                        System.out.println("Parabéns! Todos os níveis completos!");
                    }
                } else {
                    System.out.println("Precisa do item para sair!");
                }
            }
        }
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void dispose(){
        sprite.getTexture().dispose();
    }

    public float getX(){ return sprite.getX(); }
    public float getY(){ return sprite.getY(); }
}
