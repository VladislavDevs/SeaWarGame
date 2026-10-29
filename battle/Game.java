package battleship.battle;

import battleship.model.Battlefield;
import battleship.model.Coordinate;
import battleship.reader.CoordinateReader;
import battleship.util.GameConstants;
import battleship.writer.ConsoleHelper;
import battleship.writer.FieldWriter;

public class Game {

    private final Battlefield field;
    private final Cannon      cannon;
    private final CoordinateReader   coordinateReader;
    private final FieldWriter fieldWriter;
    private final String      playerName;

    private int shotCount;

    public Game(String playerName) {
        this.playerName   = playerName;
        this.field        = Battlefield.createDefault();
        this.cannon       = new Cannon(field);
        this.coordinateReader    = new CoordinateReader();
        //this.fieldWriter  = new FieldWriter(true);
        this.fieldWriter  = new FieldWriter();
        this.shotCount    = 0;
    }

    public void start() {
        while (!field.isAllSunk()) {
            ConsoleHelper.clear();
            printHeader();
            fieldWriter.printField(field);
            printStatus();

            Coordinate aim = coordinateReader.readAim();
            if (aim == null) {
                System.out.println(GameConstants.MSG_INVALID);
                pause(1200);
                continue;
            }

            ShotResult result = cannon.fire(aim);
            System.out.println(getMessage(result));

            if (result == ShotResult.MISS || result == ShotResult.HIT || result == ShotResult.SUNK) {
                shotCount++;
            }
            pause(900);
        }

        ConsoleHelper.clear();
        printHeader();
        fieldWriter.printField(field);
        System.out.println();
        System.out.println(GameConstants.MSG_WIN);
        System.out.println("Всего выстрелов: " + shotCount);
    }

    private void printHeader() {
        System.out.println("Игрок " + playerName);
        System.out.println("Морской бой (базовая версия)");
        System.out.println();
    }

    private void printStatus() {
        System.out.printf("Выстрелов: %d | Потоплено: %d из %d%n%n",
                shotCount, field.countSunkShips(), field.getShips().size());
    }

    private String getMessage(ShotResult result) {
        switch (result) {
            case MISS:         return GameConstants.MSG_MISS;
            case HIT:          return GameConstants.MSG_HIT;
            case SUNK:         return GameConstants.MSG_SUNK;
            case ALREADY_SHOT: return GameConstants.MSG_ALREADY_SHOT;
            default:           return GameConstants.MSG_INVALID;
        }
    }

    private void pause(int ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}