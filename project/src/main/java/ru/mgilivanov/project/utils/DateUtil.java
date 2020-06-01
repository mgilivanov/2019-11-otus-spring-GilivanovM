package ru.mgilivanov.project.utils;

import java.time.LocalDate;

public class DateUtil {
    public static int getDaysInYear(LocalDate date) {
        return date.lengthOfYear();
    }

    private static LocalDate setDayOfMonth(int stmtDay, LocalDate date){
        if (stmtDay > date.lengthOfMonth()) {
            return LocalDate.of(date.getYear(), date.getMonth(), date.lengthOfMonth());
        }
        return LocalDate.of(date.getYear(), date.getMonth(), stmtDay);
    }

    public static LocalDate getNextPeriod(int stmtDay, LocalDate date) {
        if ((stmtDay <= date.getDayOfMonth()) || ((stmtDay > date.getDayOfMonth()) && (date.getDayOfMonth() == date.lengthOfMonth()))){
            return setDayOfMonth(stmtDay,  date.plusMonths(1));
        }
        else
        {
            return setDayOfMonth(stmtDay, date);
        }
    }
}
