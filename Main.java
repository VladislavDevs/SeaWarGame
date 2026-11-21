package battleship;

import battleship.battle.Game;
import battleship.util.GameConstants;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        boolean randomPlacement = true;
        boolean debugMode = false;
        Long seed = null;
        List<String> nameParts = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if ("--random".equalsIgnoreCase(arg)) randomPlacement = true;
            else if ("--static".equalsIgnoreCase(arg)) randomPlacement = false;
            else if ("--debug".equalsIgnoreCase(arg)) debugMode = true;
            else if ("--seed".equalsIgnoreCase(arg) && i + 1 < args.length) {
                seed = Long.parseLong(args[++i]);
            } else nameParts.add(arg);
        }

        String playerName = nameParts.isEmpty() ? GameConstants.DEFAULT_PLAYER_NAME : String.join(" ", nameParts);
        new Game(playerName, randomPlacement, debugMode, seed).start();
    }
}