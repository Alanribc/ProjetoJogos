package br.mackenzie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MapManager {
    private int faseAtual;
    private Texture backgroundTexture;
    private float limiteEsquerda;
    private float limiteDireita;

    private Viewport viewport;

    public MapManager(Viewport viewport){
        this.viewport = viewport;
        this.faseAtual = 1;
        carregarFase();
    }

    public void carregarFase(){

        if(backgroundTexture != null){
            backgroundTexture.dispose();
        }

        switch (faseAtual){
            case 1:
                backgroundTexture = new Texture("fase1.jpg");
                limiteEsquerda = 0;
                limiteDireita = viewport.getWorldWidth();
        }
    }
}
