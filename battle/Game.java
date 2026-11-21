package SeaWarProject.battle;

import SeaWarProject.map.Battlefield;
import SeaWarProject.map.Coordinate;
import SeaWarProject.coordinateReader.CoordinateReader;
import SeaWarProject.other.GameConstants;
import SeaWarProject.writer.ConsoleHelper;
import SeaWarProject.writer.FieldWriter;

import java.util.Scanner;

public class Game {

    private final Battlefield playerField;
    private final Battlefield enemyField;

    private final Cannon playerCannon;
    private final Cannon enemyCannon;

    private final CoordinateReader coordinateReader;
    private final FieldWriter fieldWriter;
    private final ComputerPlayer computerPlayer;

    private final String playerName;
    private final boolean demoMode;
    private Coordinate lastComputerShot;

    public Game(String playerName, boolean randomPlacement, boolean debugMode, Long seed, boolean demoMode) {
        this.playerName = playerName;
        this.demoMode = demoMode;

        this.playerField = randomPlacement ? Battlefield.createRandom() : Battlefield.createDefault();
        this.enemyField  = Battlefield.createRandom(seed != null ? seed : System.currentTimeMillis());

        this.playerCannon = new Cannon(enemyField);
        this.enemyCannon  = new Cannon(playerField);

        this.coordinateReader = new CoordinateReader();
        this.fieldWriter = new FieldWriter();
        this.computerPlayer = new ComputerPlayer();
        this.lastComputerShot = null;

        if (debugMode) {
            ConsoleHelper.clear();
            System.out.println("=== РЕЖИМ ОТЛАДКИ ===");
            System.out.println("Поле игрока:");
            fieldWriter.printBothFields(playerField, enemyField, null);
            System.out.print("Нажмите Enter, чтобы начать игру...");
            new Scanner(System.in).nextLine();
        }
    }

    public void start() {
        boolean playerTurn = true;

        while (!playerField.isAllSunk() && !enemyField.isAllSunk()) {
            // 1. Очищаем и рисуем актуальное состояние перед ходом
            ConsoleHelper.clear();
            printHeader();
            fieldWriter.printBothFields(playerField, enemyField, lastComputerShot);
            printStatus();

            if (playerTurn) {
                System.out.println("--- ВАШ ХОД ---");
                playerTurn = processPlayerShot();
            } else {
                System.out.println("--- ХОД КОМПЬЮТЕРА ---");
                // Перед выстрелом делаем паузу, чтобы игрок понял, что ход перешел
                pause(1000);
                playerTurn = !processComputerShot();
            }
        }

        ConsoleHelper.clear();
        printHeader();
        fieldWriter.printBothFields(playerField, enemyField, lastComputerShot);
        System.out.println();
        if (enemyField.isAllSunk()) {
            System.out.println("ПОБЕДА! Вы уничтожили флот противника!");
        } else {
            System.out.println("ПОРАЖЕНИЕ! Ваш флот уничтожен.");
        }
    }

    private boolean processPlayerShot() {
        Coordinate aim = coordinateReader.readAim();
        if (aim == null) {
            System.out.println(GameConstants.MSG_INVALID);
            pause(1500);
            return true;
        }

        ShotResult result = playerCannon.fire(aim);
        System.out.println(getMessage(result));

        // Сбрасываем подсветку компа, так как игрок сделал свой ход
        lastComputerShot = null;

        pause(1200);
        return result == ShotResult.HIT || result == ShotResult.SUNK;
    }

    private boolean processComputerShot() {
        Coordinate aim = computerPlayer.chooseTarget(playerField);
        lastComputerShot = aim; // Запоминаем для кружочка

        System.out.println("Компьютер стреляет в: " + formatCoord(aim));
        ShotResult result = enemyCannon.fire(aim);
        System.out.println(getMessage(result));
        computerPlayer.processResult(playerField, aim, result);

        // 2. Очищаем и перерисовываем экран СРАЗУ ПОСЛЕ выстрела, чтобы показать кружочек
        pause(800);
        ConsoleHelper.clear();
        printHeader();
        fieldWriter.printBothFields(playerField, enemyField, lastComputerShot);
        printStatus();
        System.out.println("--- ХОД КОМПЬЮТЕРА ---");
        System.out.println("Компьютер стреляет в: " + formatCoord(aim));
        System.out.println(getMessage(result));

        pause(2000); // Даем игроку время увидеть результат
        return result == ShotResult.HIT || result == ShotResult.SUNK;
    }

    private String formatCoord(Coordinate coord) {
        String[] letters = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};
        return letters[coord.getCol()] + (coord.getRow() + 1);
    }

    private void printHeader() {
        System.out.println("Игрок: " + playerName + " | Игра против компьютера");
        System.out.println();
    }

    private void printStatus() {
        System.out.printf("Ваши корабли: %d/%d | Корабли врага: %d/%d%n%n",
                playerField.getShips().size() - playerField.countSunkShips(), playerField.getShips().size(),
                enemyField.getShips().size() - enemyField.countSunkShips(), enemyField.getShips().size());
    }

    private String getMessage(ShotResult result) {
        switch (result) {
            case MISS: return GameConstants.MSG_MISS;
            case HIT: return GameConstants.MSG_HIT;
            case SUNK: return GameConstants.MSG_SUNK;
            case ALREADY_SHOT: return GameConstants.MSG_ALREADY_SHOT;
            default: return GameConstants.MSG_INVALID;
        }
    }

    private void pause(int ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}