package ru.mgilivanov.project.exception;

import lombok.Getter;

public class ApplicationException extends RuntimeException {
    @Getter
    private final int code;
    public static int TOO_MANY_EODS_CODE = -50;
    public static String TOO_MANY_EODS_MESSAGE = "Открыто более одного опер.дня";
    public static int NO_EODS_CODE = -51;
    public static String NO_EODS_MESSAGE = "Не найден текущий опер. день";
    public static int NO_BANK_ACC_DT_CODE = -52;
    public static String NO_BANK_ACC_DT_MESSAGE = "Не найден технический счёт ДТ";
    public static int NO_BANK_ACC_KT_CODE = -53;
    public static String NO_BANK_ACC_KT_MESSAGE = "Не найден технический счёт КТ";
    public static int NO_BANK_ACC_PERCENTS_CODE = -54;
    public static String NO_BANK_ACC_PERCENTS_MESSAGE = "Не найден технический счёт процентов";
    public static int NO_BANK_ACC_PENALTY_CODE = -54;
    public static String NO_BANK_ACC_PENALTY_MESSAGE = "Не найден технический счёт штрафов";
    public static int TOO_MANY_CLOSING_EODS_CODE = -55;
    public static String TOO_MANY_CLOSING_EODS_MESSAGE = "Закрывается более одного опер.дня";
    public static int NO_CLOSING_EODS_CODE = -56;
    public static String NO_CLOSING_EODS_MESSAGE = "Не найден текущий закрываемый опер. день";

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
