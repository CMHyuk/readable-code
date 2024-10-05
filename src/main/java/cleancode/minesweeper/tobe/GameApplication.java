package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Advanced;

public class GameApplication {

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper(new Advanced());
        minesweeper.run();
    }
}
