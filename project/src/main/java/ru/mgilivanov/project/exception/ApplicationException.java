package ru.mgilivanov.project.exception;

import lombok.Getter;

public class ApplicationException extends RuntimeException {
    @Getter
    private final int code;
    public static int TOO_MANY_EODS_CODE = -50;
    public static String TOO_MANY_EODS_MESSAGE = "Открыто более одного опер.дня";
    public static int NO_EODS_CODE = -51;
    public static String NO_EODS_MESSAGE = "Не найден текущий опер. день";
    public static int TOO_MANY_CLOSING_EODS_CODE = -55;
    public static String TOO_MANY_CLOSING_EODS_MESSAGE = "Закрывается более одного опер.дня";
    public static int NO_CLOSING_EODS_CODE = -56;
    public static String NO_CLOSING_EODS_MESSAGE = "Не найден текущий закрываемый опер. день";

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
