package battleship.writer;

import battleship.model.Battlefield;
import battleship.model.Cell;
import battleship.util.GameConstants;

public class FieldWriter {

    private final boolean revealShips;

    public FieldWriter(boolean revealShips) {
        this.revealShips = revealShips;
    }

    public FieldWriter() {
        this(false);
    }

    public void printField(Battlefield field) {
        printHeader();
        printTopBorder();
        for (int r = 0; r < Battlefield.SIZE; r++) {
            printRow(field, r);
            if (r < Battlefield.SIZE - 1) printMiddleBorder();
        }
        printBottomBorder();
    }

    private void printHeader() {
        System.out.print("    ");
        for (int i = 0; i < 10; i++) {
            System.out.print(GameConstants.COLUMN_LABELS[i]);
            if (i < 9) System.out.print("   ");
        }
        System.out.println();
    }

    private void printTopBorder() {
        System.out.print("  ┌");
        for (int i = 0; i < 10; i++) {
            System.out.print("───");
            if (i < 9) System.out.print("┬");
        }
        System.out.println("┐");
    }

    private void printMiddleBorder() {
        System.out.print("  ├");
        for (int i = 0; i < 10; i++) {
            System.out.print("───");
            if (i < 9) System.out.print("┼");
        }
        System.out.println("┤");
    }

    private void printBottomBorder() {
        System.out.print("  └");
        for (int i = 0; i < 10; i++) {
            System.out.print("───");
            if (i < 9) System.out.print("┴");
        }
        System.out.println("┘");
    }

    private void printRow(Battlefield field, int row) {
        System.out.printf("%2d│", row + 1);
        for (int c = 0; c < 10; c++) {
            System.out.print(" " + symbol(field.getCell(row, c)) + " ");
            if (c < 9) System.out.print("│");
        }
        System.out.println("│");
    }

    private String symbol(Cell cell) {
        switch (cell.getState()) {
            case MISS: return "·";
            case HIT:  return "X";
            case SHIP: return revealShips ? "■" : " ";
            default:   return " ";
        }
    }
}