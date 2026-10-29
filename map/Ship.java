package battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private final List<Cell> cells = new ArrayList<>();

    public void addCell(Cell cell) {
        cells.add(cell);
        cell.setShip(this);
    }

    public List<Cell> getCells() { return cells; }
    public int getSize()         { return cells.size(); }

    public boolean isSunk() {
        for (Cell cell : cells) {
            if (cell.getState() != CellState.HIT) return false;
        }
        return true;
    }
}