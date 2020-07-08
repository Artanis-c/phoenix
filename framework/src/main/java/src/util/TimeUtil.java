package src.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 *@description: 时间工具类
 *@author: tom.cui
 *@date: 2020/3/23 13:50
 */
public class TimeUtil {
    /**
     * @description: 根据指定格式化时间字符串
     * @author: tom.cui
     * @date: 2020/2/25 15:14
     * @params: [dateTime, formatString]
     * @return: java.lang.String
     */
    public static String formatLocalDateTime(LocalDateTime dateTime, String formatString)  {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatString);
        String result = dtf.format(dateTime);
        return result;
    }

    /**
     * @description: 格式化 yyyy-MM-dd HH:mm:ss 格式
     * @author: tom.cui
     * @date: 2020/2/25 17:44
     * @params: [dateTime]
     * @return: java.lang.String
     */
    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String result = dtf.format(dateTime);
        return result;
    }

    /**
     * @description: 获取相差的天数
     * @author: tom.cui
     * @date: 2020/2/25 18:20
     * @params: []
     * @return: int
     */
    public static long getDiffDays(LocalDateTime dateTime1, LocalDateTime localDateTime2) {
        Duration different = Duration.between(dateTime1, localDateTime2);
        return Math.abs(different.toDays());
    }

    /**
     * @description: 获取相差的小时数
     * @author: tom.cui
     * @date: 2020/2/25 18:33
     * @params: [dateTime1, localDateTime2]
     * @return: long
     */
    public static long getDiffHours(LocalDateTime dateTime1, LocalDateTime localDateTime2) {
        Duration different = Duration.between(dateTime1, localDateTime2);
        return Math.abs(different.toHours());
    }

    /**
     * @description: 获取相差分钟数
     * @author: tom.cui
     * @date: 2020/2/25 18:34
     * @params: [dateTime1, localDateTime2]
     * @return: long
     */
    public static long getDiffMinutes(LocalDateTime dateTime1, LocalDateTime localDateTime2) {
        Duration different = Duration.between(dateTime1, localDateTime2);
        return Math.abs(different.toMinutes());
    }

    /**
     * @description: 获取相差秒数
     * @author: tom.cui
     * @date: 2020/2/25 18:35
     * @params: [dateTime1, localDateTime2]
     * @return: long
     */
    public static long getDiffSeconds(LocalDateTime dateTime1, LocalDateTime localDateTime2) {
        Duration different = Duration.between(dateTime1, localDateTime2);
        return Math.abs(different.getSeconds());
    }
}
