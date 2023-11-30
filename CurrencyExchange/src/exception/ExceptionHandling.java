package exception;

import interfaces.*;
public class ExceptionHandling {

    private static ExceptionHandling exceptionHandler = new ExceptionHandling();

    public static void handleException(Exception e) {
        // Логирование исключения
        logException(e);

        // Вывод информации об ошибке пользователю
        System.out.println("Произошла ошибка: " + e.getMessage());
    }

    private static void logException(Exception e) {
        // Здесь можно добавить логирование исключений, например, запись в файл или отправку в систему мониторинга
        System.err.println("Исключение зарегистрировано: " + e);
    }
}
