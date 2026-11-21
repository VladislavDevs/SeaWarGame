package SeaWarProject.writer;

import SeaWarProject.map.Battlefield;
import SeaWarProject.map.Cell;
import SeaWarProject.map.CellState;
import SeaWarProject.map.Coordinate;
import SeaWarProject.other.GameConstants;

public class FieldWriter {

    // lastComputerShot нужен для отрисовки кружочка
    public void printBothFields(Battlefield playerField, Battlefield enemyField, Coordinate lastComputerShot) {
        printHeader();
        printBorder();
        for (int r = 0; r < Battlefield.SIZE; r++) {
            printRow(playerField, enemyField, r, lastComputerShot);
            if (r < Battlefield.SIZE - 1) printBorder();
        }
        printBorder();
        System.out.println("      ВАШЕ ПОЛЕ (корабли видны)                        ПОЛЕ ПРОТИВНИКА (скрыто)");
    }

    private void printHeader() {
        System.out.print("    ");
        for (int i = 0; i < 10; i++) System.out.print(" " + GameConstants.COLUMN_LABELS[i] + "  ");
        System.out.print("     ");
        System.out.print("    ");
        for (int i = 0; i < 10; i++) System.out.print(" " + GameConstants.COLUMN_LABELS[i] + "  ");
        System.out.println();
    }

    private void printBorder() {
        System.out.print("   +");
        for (int i = 0; i < 10; i++) { System.out.print("---"); if (i < 9) System.out.print("+"); }
        System.out.print("+     ");
        System.out.print("   +");
        for (int i = 0; i < 10; i++) { System.out.print("---"); if (i < 9) System.out.print("+"); }
        System.out.println("+");
    }

    private void printRow(Battlefield pField, Battlefield eField, int r, Coordinate lastShot) {
        // Левое поле (Игрока)
        System.out.printf("%2d |", r + 1);
        for (int c = 0; c < Battlefield.SIZE; c++) {
            String sym = playerSymbol(pField.getCell(r, c));

            // Если это координаты последнего выстрела компьютера — рисуем кружочек
            if (lastShot != null && lastShot.getRow() == r && lastShot.getCol() == c) {
                if (pField.getCell(r, c).getState() == CellState.MISS) sym = "o"; // маленький кружок (мимо)
                if (pField.getCell(r, c).getState() == CellState.HIT) sym = "O";  // большой кружок (попал)
            }
            System.out.print(" " + sym + " |");
        }

        System.out.print("     ");

        // Правое поле (Противника)
        System.out.printf("%2d |", r + 1);
        for (int c = 0; c < Battlefield.SIZE; c++) {
            System.out.print(" " + enemySymbol(eField.getCell(r, c)) + " |");
        }
        System.out.println();
    }

    private String playerSymbol(Cell cell) {
        return switch (cell.getState()) {
            case SHIP -> "■";
            case MISS -> "·";
            case HIT -> "X";
            default -> " ";
        };
    }

    private String enemySymbol(Cell cell) {
        return switch (cell.getState()) {
            case MISS -> "·";
            case HIT -> "X";
            default -> " ";
        };
    }
}