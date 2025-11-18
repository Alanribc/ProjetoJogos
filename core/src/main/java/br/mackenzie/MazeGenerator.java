package br.mackenzie;

/*
Simple maze provider that returns coherent mazes for 3 levels.
0 = floor, 1 = wall, 2 = key, 3 = exit
All mazes have start tile at (1,1) (bottom-left-ish),
and an exit tile placed somewhere reachable.
*/

public class MazeGenerator {

    public int[][] getMazeForLevel(int level){
        switch(level){
            case 1: return level1();
            case 2: return level2();
            case 3: return level3();
            default: return level1();
        }
    }

    private int[][] level1(){
        // 10 cols x 8 rows
        return new int[][]{
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,3,1},
            {1,0,1,1,0,1,1,0,1,1},
            {1,0,1,0,0,0,1,0,0,1},
            {1,0,1,0,1,0,1,1,0,1},
            {1,0,0,0,1,0,0,2,0,1}, // key at (7,5)
            {1,1,1,0,1,1,0,1,0,1},
            {1,1,1,1,1,1,1,1,1,1},
        };
    }

    private int[][] level2(){
        // a bit more twisty
        return new int[][]{
            {1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,0,3,1},
            {1,0,1,0,1,0,1,1,0,1,1},
            {1,0,1,0,0,0,1,0,0,0,1},
            {1,0,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,1,0,0,0,1,0,1},
            {1,1,1,0,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,1,2,1}, // key near exit but with walls
            {1,1,1,1,1,1,1,1,1,1,1},
        };
    }

    private int[][] level3(){
        return new int[][]{
            {1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,1,0,0,0,1,0,0,0,3,1},
            {1,0,1,0,1,0,1,0,1,1,0,1},
            {1,0,0,0,1,0,0,0,1,0,0,1},
            {1,1,1,0,1,1,1,0,1,0,1,1},
            {1,0,0,0,0,0,1,0,0,0,2,1},
            {1,0,1,1,1,0,1,1,1,0,0,1},
            {1,0,0,0,1,0,0,0,1,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1},
        };
    }
}
