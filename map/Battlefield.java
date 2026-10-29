package battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Battlefield {

    public static final int SIZE = 10;

    private final Cell[][] grid;
    private final List<Ship> ships;

    public Battlefield() {
        this.grid  = new Cell[SIZE][SIZE];
        this.ships = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                grid[r][c] = new Cell(r, c);
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return null;
        return grid[row][col];
    }

    public Ship placeShip(int row, int col, int size, boolean horizontal) {
        Ship ship = new Ship();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row      : row + i;
            int c = horizontal ? col + i  : col;
            Cell cell = grid[r][c];
            cell.setState(CellState.SHIP);
            ship.addCell(cell);
        }
        ships.add(ship);
        return ship;
    }

    public List<Ship> getShips() { return ships; }

    public boolean isAllSunk() {
        for (Ship s : ships) if (!s.isSunk()) return false;
        return true;
    }

    public int countSunkShips() {
        int n = 0;
        for (Ship s : ships) if (s.isSunk()) n++;
        return n;
    }

    public static Battlefield createDefault() {
        Battlefield field = new Battlefield();

        // 1 четырёхпалубный (A1-D1, гориз.)
        field.placeShip(0, 0, 4, true);

        // 2 трёхпалубных
        field.placeShip(2, 2, 3, true);
        field.placeShip(5, 6, 3, false);

        // 3 двухпалубных
        field.placeShip(8, 0, 2, true);
        field.placeShip(4, 8, 2, false);
        field.placeShip(6, 3, 2, true);

        // 4 однопалубных
        field.placeShip(9, 9, 1, true);
        field.placeShip(0, 9, 1, true);
        field.placeShip(3, 0, 1, true);
        field.placeShip(7, 9, 1, true);

        return field;
    }
}