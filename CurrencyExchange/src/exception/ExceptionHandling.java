package exception;

import interfaces.*;
public class ExceptionHandling implements IExceptionHandling {

    @Override
    public void handleException(Exception e) {
        // Логирование исключения
        logException(e);

        // Вывод информации об ошибке пользователю
        System.out.println("Произошла ошибка: " + e.getMessage());
    }

    private void logException(Exception e) {
        // Здесь можно добавить логирование исключений, например, запись в файл или отправку в систему мониторинга
        System.err.println("Исключение зарегистрировано: " + e);
    }
}
