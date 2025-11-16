package br.mackenzie;

public class Niveis {
    private int[][][] niveis;
    private int atual = 0;

    public Niveis(){
        niveis = new int[3][][];

        // Nível 1
        niveis[0] = new int[][]{
            {1,1,1,1,1,1,1,1},
            {1,0,0,0,0,2,0,1},
            {1,0,1,1,0,1,0,1},
            {1,0,0,1,0,0,0,1},
            {1,1,0,1,1,0,3,1},
            {1,1,1,1,1,1,1,1}
        };

        // Nível 2
        niveis[1] = new int[][]{
            {1,1,1,1,1,1,1,1},
            {1,0,1,0,0,2,0,1},
            {1,0,1,1,0,1,0,1},
            {1,0,0,1,0,1,0,1},
            {1,1,0,1,1,0,3,1},
            {1,1,1,1,1,1,1,1}
        };

        // Nível 3
        niveis[2] = new int[][]{
            {1,1,1,1,1,1,1,1},
            {1,0,1,0,1,2,0,1},
            {1,0,1,1,0,1,0,1},
            {1,0,1,1,0,1,0,1},
            {1,1,0,1,1,0,3,1},
            {1,1,1,1,1,1,1,1}
        };
    }

    public int[][] getNivelAtual(){
        return niveis[atual];
    }

    public boolean proximoNivel(){
        if(atual + 1 < niveis.length){
            atual++;
            return true;
        }
        return false;
    }

    public int getNivelIndex(){
        return atual + 1;
    }
}
