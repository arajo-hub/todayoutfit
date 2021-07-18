package com.ara.todayoutfit.board;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class TimeService {

    public static Date getTodayToDate() {

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String ss = sdf.format(new java.util.Date());
//
//        return Date.valueOf(ss);
        Calendar now = Calendar.getInstance();

        // 시간 초기화
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        return now.getTime();

    }

    public static Date getNow() {

        Calendar now = Calendar.getInstance();

        return now.getTime();

    }

    public static LocalDateTime getTomorrow(LocalDateTime today) {

        int year = today.getYear();
        int month = today.getMonthValue();
        int date = today.getDayOfMonth();

        return LocalDateTime.of(year, month, date + 1, 0, 0, 0);

    }
}
