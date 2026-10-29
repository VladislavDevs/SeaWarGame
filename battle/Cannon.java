package battleship.battle;

import battleship.model.Battlefield;
import battleship.model.Cell;
import battleship.model.CellState;
import battleship.model.Coordinate;
import battleship.model.Ship;

/** Орудие: выполняет выстрел по полю и возвращает результат. */
public class Cannon {

    private final Battlefield field;

    public Cannon(Battlefield field) {
        this.field = field;
    }

    public ShotResult fire(Coordinate coord) {
        Cell cell = field.getCell(coord.getRow(), coord.getCol());
        if (cell == null)            return ShotResult.INVALID;
        if (cell.isShot())           return ShotResult.ALREADY_SHOT;

        if (cell.getState() == CellState.SHIP) {
            cell.setState(CellState.HIT);
            Ship ship = cell.getShip();
            return ship.isSunk() ? ShotResult.SUNK : ShotResult.HIT;
        }

        cell.setState(CellState.MISS);
        return ShotResult.MISS;
    }
}