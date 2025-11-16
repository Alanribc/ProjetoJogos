package br.mackenzie;

public class Labirinto {
    // 0 = chão, 1 = parede, 2 = item, 3 = saída
    private int[][] mapa = {
        {1,1,1,1,1,1,1,1},
        {1,0,0,0,0,2,0,1},
        {1,0,1,1,0,1,0,1},
        {1,0,0,1,0,0,0,1},
        {1,1,0,1,1,0,3,1},
        {1,1,1,1,1,1,1,1}
    };

    public int[][] getMapa() {
        return mapa;
    }
}
