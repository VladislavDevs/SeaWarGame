package battleship;

import battleship.battle.Game;
import battleship.util.GameConstants;

public class Main {

    public static void main(String[] args) {
        String playerName = args.length > 0
                ? String.join(" ", args)
                : GameConstants.DEFAULT_PLAYER_NAME;

        new Game(playerName).start();
    }
}