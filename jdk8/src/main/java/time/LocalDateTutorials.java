package time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2019 <br/>
 * @Desc: <br/>
 * @ProjectName: some-tutorials <br/>
 * @Date: 2019/1/2 9:53 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">汪萌萌</a>
 */
public class LocalDateTutorials {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay());
        System.out.println(localDate.with(WeekFields.of(Locale.CHINA).dayOfWeek(), 7).atTime(LocalTime.MAX));
        //instant();
        //localDate();
        //localTime();
        //localDateTime();
    }

    private static void instant() {
        //时间戳(毫秒)
        long epochMilli = Instant.now().toEpochMilli();
        //时间戳(秒)
        long epochSecond = Instant.now().getEpochSecond();
    }

    private static void localDateTime() {
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //获取今天起始时间
        LocalDateTime startOfToady = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        //获取今天截止时间
        LocalDateTime endOfToady = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        //比较两个时间的先后
        boolean after = LocalDateTime.now().isAfter(LocalDateTime.MIN);
        boolean before = LocalDateTime.now().isBefore(LocalDateTime.MIN);
        //获取当前的时间戳
        //long epochSecond = Instant.now().getEpochSecond();
        long epochSecond = LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset());
        //根据时间戳获取时间
        LocalDateTime someDateTime = LocalDateTime.ofEpochSecond(epochSecond, 0, OffsetDateTime.now().getOffset());
        //LocalDateTime someDateTime=LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), OffsetDateTime.now().getOffset());
        //格式化
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static void localTime() {
        //当前时间
        LocalTime now = LocalTime.now();
        //上个小时
        LocalTime lastHourTime = now.minusHours(1);
        //下个小时
        LocalTime nextHourTime = now.plusHours(1);
        //00:00:00
        LocalTime min = LocalTime.MIN;
        //中午12:00
        LocalTime noon = LocalTime.NOON;
        //23:59:59
        LocalTime max = LocalTime.MAX;
        //指定时间
        LocalTime someTime = LocalTime.of(12, 0);
        //获取当前系统默认时区的时间
        LocalTime nowWithTimeZone = LocalTime.now(ZoneId.systemDefault());
        //获取指定时区的当前时间
        LocalTime utc = LocalTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(0)));
    }

    private static void localDate() {
        //今天
        LocalDate today = LocalDate.now();
        //昨天
        LocalDate yesday = LocalDate.now().minusDays(1);
        //明天
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        //指定年月日
        LocalDate someDay = LocalDate.of(2019, 1, 1);
        //根据字符串获取日期
        LocalDate.parse("2019-01-01");
        //今天是本年中的第几天
        int dayOfYear = LocalDate.now().getDayOfYear();
        //今天是本月中的第几天
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        //本月第一天
        LocalDate startOfThisMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        //本月最后一天
        LocalDate endOfThisMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        //两个日期相差多少天
        long untilDay = LocalDate.now().until(LocalDate.now().withDayOfMonth(1), ChronoUnit.DAYS);
        //两个日期相差多少月
        long untilMonth = LocalDate.now().until(LocalDate.now().withMonth(5), ChronoUnit.MONTHS);
        //两个日期相差多少年
        long untilYear = LocalDate.now().until(LocalDate.now().withYear(2018), ChronoUnit.YEARS);
        //过去三十天日期列表
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List<LocalDate> next30Days = Stream.generate(() -> LocalDate.now().minusDays(atomicInteger.incrementAndGet())).limit(30).collect(Collectors.toList());
    }
}
