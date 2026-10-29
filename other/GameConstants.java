package battleship.util;

public final class GameConstants {

    private GameConstants() {}

    public static final int    FIELD_SIZE          = 10;
    public static final String DEFAULT_PLAYER_NAME = "Неизвестный Игрок";

    public static final String[] COLUMN_LABELS = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
    };

    public static final String PROMPT_AIM       = "Введите координаты для удара (например Е4): ";
    public static final String MSG_MISS         = "Выстрел мимо!";
    public static final String MSG_HIT          = "Попадание в корабль!";
    public static final String MSG_SUNK         = "Корабль потоплен!";
    public static final String MSG_ALREADY_SHOT = "По этим координатам уже стреляли!";
    public static final String MSG_INVALID      = "Неверные координаты. Формат: буква+цифра (например Е4).";
    public static final String MSG_WIN          = "Поздравляем! Все корабли противника потоплены!";
}