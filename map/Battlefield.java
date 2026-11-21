package battleship.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battlefield {

    public static final int SIZE = 10;

    // Флот: 1x4, 2x3, 3x2, 4x1. Сортируем по убыванию размера для более эффективной расстановки
    private static final int[] FLEET_SIZES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

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

        public static Battlefield createRandom() {
        return createRandom(new Random().nextLong());
    }

    // Перегрузка с фиксированным seed для тестирования
    public static Battlefield createRandom(long seed) {
        Random random = new Random(seed);
        System.out.println("[DEBUG] Генерация поля с seed: " + seed); // Для проверки
        
        for (int fleetAttempt = 0; fleetAttempt < 200; fleetAttempt++) {
            Battlefield field = tryCreateRandom(random);
            if (field != null) return field;
        }
        throw new IllegalStateException("Не удалось разместить флот случайным образом");
    }

    private static Battlefield tryCreateRandom(Random random) {
        Battlefield field = new Battlefield();
        for (int size : FLEET_SIZES) {
            if (!placeShipRandomly(field, size, random)) {
                return null;
            }
        }
        return field;
    }

    private static boolean placeShipRandomly(Battlefield field, int size, Random random) {
        for (int attempt = 0; attempt < 1000; attempt++) {
            boolean horizontal = random.nextBoolean();
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (fits(field, row, col, size, horizontal)) {
                field.placeShip(row, col, size, horizontal);
                return true;
            }
        }
        return false;
    }

    private static boolean fits(Battlefield field, int row, int col, int size, boolean horizontal) {
        int endRow = horizontal ? row : row + size - 1;
        int endCol = horizontal ? col + size - 1 : col;
        if (endRow >= SIZE || endCol >= SIZE) return false;

        for (int r = row - 1; r <= endRow + 1; r++) {
            for (int c = col - 1; c <= endCol + 1; c++) {
                if (r < 0 || r >= SIZE || c < 0 || c >= SIZE) continue;
                if (field.getCell(r, c).getState() == CellState.SHIP) return false;
            }
        }
        return true;
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