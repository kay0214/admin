package com.personal.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 日期处理
 */
public class DateUtils {
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public static final String DATE_PATTERN_10 = "yyyy-MM-dd";

    /**
     * 时间格式(yyyyMMdd)
     */
    public static final String DATE_PATTERN_8 = "yyyyMMdd";

    /**
     * 时间格式(HH:mm:ss)
     * */
    public static final String TIME_PATTERN_8 = "HH:mm:ss";

    /**
     * 时间格式(HHmmss)
     * */
    public static final String TIME_PATTERN_6 = "HHmmss";

    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public static final String DATE_TIME_PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 时间格式(yyyyMMddHHmmss)
     */
    public static final String DATE_TIME_PATTERN_14 = "yyyyMMddHHmmss";

    public static Date getFromLocalDateTime(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime getLocalDateTime(Date date){
        if(date == null){
            return null;
        }else{
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

    public static LocalTime getLocalTime(Date date){
        if(date == null){
            return null;
        }else{
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        }
    }

    public static LocalDate getLocalDate(Date date){
        if(date == null){
            return null;
        }else{
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
    }

    public static String getLocalDateTime(){
        return format(LocalDateTime.now());
    }

    public static String getLocalDateTimeShort(){
        return formatShort(LocalDateTime.now());
    }

    public static String getLocalTime(){
        return format(LocalTime.now());
    }

    public static String getLocalTimeShort(){
        return formatShort(LocalTime.now());
    }

    public static String getLocalDate(){
        return format(LocalDate.now());
    }

    public static String getLocalDateShort(){
        return formatShort(LocalDate.now());
    }

    public static LocalDateTime getLocalDateTime(String localDateTime){
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_19));
    }

    public static LocalDateTime getLocalDateTimeShort(String localDateTime){
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_14));
    }

    public static LocalTime getLocalTime(String localTime){
        return LocalTime.parse(localTime, DateTimeFormatter.ofPattern(TIME_PATTERN_8));
    }

    public static LocalTime getLocalTimeShort(String localTime){
        return LocalTime.parse(localTime, DateTimeFormatter.ofPattern(TIME_PATTERN_6));
    }

    public static LocalDate getLocalDate(String localDate){
        return LocalDate.parse(localDate, DateTimeFormatter.ofPattern(DATE_PATTERN_10));
    }

    public static LocalDate getLocalDateShort(String localDate){
        return LocalDate.parse(localDate, DateTimeFormatter.ofPattern(DATE_PATTERN_8));
    }

    public static String format(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_19));
    }

    public static String formatShort(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_14));
    }

    public static String format(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern(TIME_PATTERN_8));
    }

    public static String formatShort(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern(TIME_PATTERN_6));
    }

    public static String format(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN_10));
    }

    public static String formatShort(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN_8));
    }
}
