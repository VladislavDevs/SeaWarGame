package SeaWarProject.writer;

public class ConsoleHelper {
    public static void clear() {
        try {
            // Определяем операционную систему
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("win")) {
                // Для Windows используем cmd /c cls.
                // inheritIO() заставляет команду очищать именно консоль IDEA/терминала
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Для Linux/Mac используем системную команду clear
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Если что-то пошло не так (например, нет прав), просто прокручиваем экран пустыми строками
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}