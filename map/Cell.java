package battleship.model;

public class Cell {

    private final int row;
    private final int col;
    private CellState state;
    private Ship ship;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.state = CellState.EMPTY;
    }

    public int getRow()           { return row; }
    public int getCol()           { return col; }
    public CellState getState()   { return state; }
    public void setState(CellState state) { this.state = state; }
    public Ship getShip()         { return ship; }
    public void setShip(Ship ship){ this.ship = ship; }

    public boolean isShot() {
        return state == CellState.MISS || state == CellState.HIT;
    }
}