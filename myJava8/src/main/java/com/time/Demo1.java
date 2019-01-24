package com.time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * java 时间
 */
public class Demo1 {

    /**
     * LocalDate,LocalTime,LocalDateTime
     */
    @Test
    public void test1(){
        System.out.println("当前日期："+LocalDate.now());
        System.out.println("当前时间："+LocalTime.now());
        System.out.println("当前日期和时间："+LocalDateTime.now());

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("再过两年是："+ldt.plusYears(2).getYear());
        System.out.println("再过两周是："+ldt.plusWeeks(2).getMonthValue()+"月"+ldt.plusWeeks(2).getDayOfMonth()+"日");
        System.out.println("今天是周几："+ldt.getDayOfWeek());
    }

    /**
     * Instant：时间戳（以 Unix 元年：1970年1月1日 00:00:00 到磨沟时间之间的毫秒值）
     */
    @Test
    public void test2(){
        System.out.println(Instant.now());// 默认时间是以 UTC 时区

        System.out.println(Instant.now().atOffset(ZoneOffset.ofHours(8))); // 加上时差-获取国内时间

        System.out.println(Instant.now().plusSeconds(10));// 下十秒的时间
    }

    /**
     * Duration：计算两个时间之间的间隔
     * Period：计算两个日期之间的间隔
     */
    @Test
    public void test3(){
        // 获取起始时间和结束时间差多少毫秒
        Instant in1 = Instant.now();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant in2 = Instant.now();
        System.out.println(Duration.between(in1,in2).toMillis());

        System.out.println("=========================");

        // 获取开始时间和结束时间差多少天
        LocalDate ld1 =LocalDate.now().minusDays(2);
        LocalDate ld2 =LocalDate.now();
        System.out.println( Period.between(ld1,ld2).getDays());
    }

    /**
     * TemporalAdjuster: 时间校正器
     */
    @Test
    public void test4(){
        LocalDateTime ldt = LocalDateTime.now();

        System.out.println("下周末是："+ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));

        System.out.println("这个月的第一天是星期几："+ldt.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek());

        System.out.println("这个月的最后一个周日："+ldt.with(TemporalAdjusters.lastInMonth(DayOfWeek.SUNDAY)));
    }

    /**
     *  DateTimeFormatter：格式化时间/日期
     */
    @Test
    public void test5(){
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE;

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("格式化一："+formatter1.format(LocalDateTime.now()));
        System.out.println("格式化二："+formatter2.format(LocalDateTime.now()));

        String strDate = "2019-01-23 18:25:01";
        LocalDateTime ldt = LocalDateTime.parse(strDate,formatter2);
        System.out.println(ldt);
    }
}
