package SeaWarProject.battle;

import SeaWarProject.map.Battlefield;
import SeaWarProject.map.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {

    private final Random random = new Random();
    private final List<Coordinate> priorityTargets = new ArrayList<>();

    /** Выбирает координаты для следующего выстрела. */
    public Coordinate chooseTarget(Battlefield field) {
        // Убираем уже обстрелянные клетки из приоритета
        priorityTargets.removeIf(c -> field.getCell(c.getRow(), c.getCol()).isShot());

        if (!priorityTargets.isEmpty()) {
            return priorityTargets.remove(0);
        }

        // Если приоритетов нет — стреляем случайно
        int row, col;
        do {
            row = random.nextInt(Battlefield.SIZE);
            col = random.nextInt(Battlefield.SIZE);
        } while (field.getCell(row, col).isShot());

        return new Coordinate(row, col);
    }

    /** Обновляет внутреннюю статистику ИИ после выстрела. */
    public void processResult(Battlefield field, Coordinate coord, ShotResult result) {
        if (result == ShotResult.HIT || result == ShotResult.SUNK) {
            addNeighbors(field, coord);
        }
    }

    private void addNeighbors(Battlefield field, Coordinate c) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : dirs) {
            int nr = c.getRow() + d[0];
            int nc = c.getCol() + d[1];
            if (nr >= 0 && nr < Battlefield.SIZE && nc >= 0 && nc < Battlefield.SIZE) {
                Coordinate neighbor = new Coordinate(nr, nc);
                if (!field.getCell(nr, nc).isShot() && !priorityTargets.contains(neighbor)) {
                    priorityTargets.add(neighbor);
                }
            }
        }
    }
}