package SeaWarProject.battle;

import SeaWarProject.map.Battlefield;
import SeaWarProject.map.Cell;
import SeaWarProject.map.CellState;
import SeaWarProject.map.Coordinate;
import SeaWarProject.map.Ship;

/**
 * Орудие: выполняет выстрел по полю и возвращает результат.
 */
public record Cannon(Battlefield field) {

    public ShotResult fire(Coordinate coord) {
        Cell cell = field.getCell(coord.getRow(), coord.getCol());
        if (cell == null) return ShotResult.INVALID;
        if (cell.isShot()) return ShotResult.ALREADY_SHOT;

        if (cell.getState() == CellState.SHIP) {
            cell.setState(CellState.HIT);
            Ship ship = cell.getShip();
            return ship.isSunk() ? ShotResult.SUNK : ShotResult.HIT;
        }

        cell.setState(CellState.MISS);
        return ShotResult.MISS;
    }
}