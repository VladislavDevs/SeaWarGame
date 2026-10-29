package battleship.reader;

import battleship.model.Coordinate;
import battleship.util.GameConstants;

import java.util.Locale;
import java.util.Scanner;

public class CoordinateReader {

    private static final String CYRILLIC_COLS = "АБВГДЕЖЗИК"; // без Й
    private static final String LATIN_COLS    = "ABCDEFGHIJ";

    private final Scanner scanner = new Scanner(System.in);

    public Coordinate readAim() {
        System.out.print(GameConstants.PROMPT_AIM);
        String input = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
        return parse(input);
    }

    private Coordinate parse(String input) {
        if (input == null || input.length() < 2) return null;

        char colChar = input.charAt(0);
        int col = CYRILLIC_COLS.indexOf(colChar);
        if (col < 0) col = LATIN_COLS.indexOf(colChar);
        if (col < 0) return null;

        try {
            int row = Integer.parseInt(input.substring(1)) - 1;
            if (row < 0 || row >= GameConstants.FIELD_SIZE) return null;
            return new Coordinate(row, col);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}