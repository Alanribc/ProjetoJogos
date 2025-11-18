package br.mackenzie;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

    // current level index (1..N)
    private int currentLevel = 1;
    public static final int MAX_LEVEL = 3;

    @Override
    public void create() {
        // start on menu
        setScreen(new MenuScreen(this));
    }

    public void startGameAt(int level){
        if(level < 1) level = 1;
        if(level > MAX_LEVEL) level = MAX_LEVEL;
        this.currentLevel = level;
        setScreen(new GameScreen(this, currentLevel));
    }

    public void startGameFromMenu(){
        startGameAt(1);
    }

    public void nextLevel(){
        if(currentLevel < MAX_LEVEL){
            currentLevel++;
            setScreen(new GameScreen(this, currentLevel));
        } else {
            // finished all
            setScreen(new WinScreen(this));
        }
    }

    public void replay(){
        currentLevel = 1;
        setScreen(new GameScreen(this, currentLevel));
    }

    public void goToMenu(){
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
