package ru.mgilivanov.project.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException {
    @Getter
    private final int code;
    public static int CLIENT_NOT_FOUND_CODE = -1;
    public static String CLIENT_NOT_FOUND_MESSAGE = "Клиент не найден";

    public static int OPERATION_DAY_CLOSED_CODE = -2;
    public static String OPERATION_DAY_CLOSED_MESSAGE = "Операционный день закрыт. Невозможно провести документ.";

    public static int CREDIT_NOT_FOUND_CODE = -3;
    public static String CREDIT_NOT_FOUND_MESSAGE = "Кредитный договор не найден";

    public static int DOCUMENT_ALREADY_PROCESSED_CODE = -4;
    public static String DOCUMENT_ALREADY_PROCESSED_MESSAGE = "Документ уже проведен.";
    public static int DOCUMENT_NOT_FOUND_DT_CODE = -5;
    public static String DOCUMENT_NOT_FOUND_DT_MESSAGE = "Не найден счёт ДТ";
    public static int DOCUMENT_NOT_FOUND_KT_CODE = -6;
    public static String DOCUMENT_NOT_FOUND_KT_MESSAGE = "Не найден счёт КТ";
    public static int DOCUMENT_TOO_MANY_ACC_DT_CODE = -7;
    public static String DOCUMENT_TOO_MANY_ACC_DT_MESSAGE = "Одновременно указан внутренний и внешний счёт ДТ";
    public static int DOCUMENT_TOO_MANY_ACC_KT_CODE = -8;
    public static String DOCUMENT_TOO_MANY_ACC_KT_MESSAGE = "Одновременно указан внутренний и внешний счёт КТ";
    public static int DOCUMENT_NO_ACC_DT_CODE = -9;
    public static String DOCUMENT_NO_ACC_DT_MESSAGE = "Не указан счёт ДТ";
    public static int DOCUMENT_NO_ACC_KT_CODE = -10;
    public static String DOCUMENT_NO_ACC_KT_MESSAGE = "Не указан счёт КТ";
    public static int DOCUMENT_DAY_CLOSING_CODE = -11;
    public static String DOCUMENT_DAY_CLOSING_MESSAGE = "Идёт закрытие опер.дня, повторите запрос позже";
    public static int DOCUMENT_EXTERNAL_CREDIT_PROHIBITED_CODE = -12;
    public static String DOCUMENT_EXTERNAL_CREDIT_PROHIBITED_MESSAGE = "Запрещены внешние операции с кредитными счетами";

    public static int PREPAYMENT_NO_SUM_CODE = -12;
    public static String PREPAYMENT_NO_SUM_MESSAGE = "Не указана сумма частичного погашения";
    public static int PREPAYMENT_BEFORE_EODDATE_CODE = -13;
    public static String PREPAYMENT_BEFORE_EODDATE_MESSAGE = "Дата погашения не может быть меньше текущего опер.дня";

    public static int EOD_NOT_THIS_CODE = -14;
    public static String EOD_NOT_THIS_MESSAGE = "Закрываемый день не соответствует текущему открытому опер.дню";
    public static int EOD_IS_ALREADY_CLOSING_CODE = -15;
    public static String EOD_IS_ALREADY_CLOSING_MESSAGE = "Завершение операционного дня уже запущено";



    public BusinessLogicException(int code, String message) {
        super(message);
        this.code = code;
    }
}
