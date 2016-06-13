package com.efun.core.utils;

import com.efun.core.config.Configuration;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DateFormatUtils
 *
 * @author Galen
 * @since 2016/05/30.
 */
public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

    public static final String Format_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final FastDateFormat dateFormat;

    private static final FastDateFormat dateTimeFormat;

    private static final FastDateFormat timeFormat;

    private DateFormatUtils() {
    }

    static {
        dateTimeFormat = FastDateFormat.getInstance(Configuration.getPatternDateTime());

        dateFormat = FastDateFormat.getInstance(Configuration.getPatternDate());

        timeFormat = FastDateFormat.getInstance(Configuration.getPatternTime());
    }

    /**
     * 将string转化为日期
     *
     * @param dateString
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    /**
     * 将string转化为时间
     *
     * @param dateString
     * @throws ParseException
     */
    public static Date parseTime(String dateString) throws ParseException {
        return timeFormat.parse(dateString);
    }

    /**
     * 将string转化为日期时间
     *
     * @param dateString
     * @throws ParseException
     */
    public static Date parseDateTime(String dateString) throws ParseException {
        return dateTimeFormat.parse(dateString);
    }

    /**
     * 将ISO8601转化为日期时间
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseISO8601(String dateString) throws ParseException {
        String regex = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2}).(\\d{3})(Z|([+|-])(\\d{2}):?(\\d{2}))";
        if (dateString.matches(regex)) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dateString);
            while (m.find()) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(m.group(1)));
                cal.set(Calendar.MONTH, Integer.parseInt(m.group(2)) - 1);
                cal.set(Calendar.DATE, Integer.parseInt(m.group(3)));
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(m.group(4)));
                cal.set(Calendar.MINUTE, Integer.parseInt(m.group(5)));
                cal.set(Calendar.SECOND, Integer.parseInt(m.group(6)));
                cal.set(Calendar.MILLISECOND, Integer.parseInt(m.group(7)));
                if ("Z".equals(m.group(8))) {
                    cal.setTimeZone(TimeZone.getTimeZone("GMT"));
                } else {
                    cal.setTimeZone(TimeZone.getTimeZone("GMT" + m.group(9) + m.group(10) + ":" + m.group(11)));
                }
                return cal.getTime();
            }
        }
        throw new ParseException(dateString + "is not a iso 8601 format date", 0);
    }

    /**
     * 根据日期字符串是否含有时间决定转换为日期还是日期时间还是时间 可以支持ISO8601格式
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseAll(String dateString) throws ParseException {
        try {
            return parseISO8601(dateString);
        } catch (ParseException ex) {
            try {
                return parseDateTime(dateString);
            } catch (ParseException ex1) {
                try {
                    return parseDate(dateString);
                } catch (ParseException ex2) {
                    return parseTime(dateString);
                }
            }
        }
    }

    /**
     * 按格式输出date到string
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 按格式输出time到string
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 按格式输出DateTime到string
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * 按格式输出date到string,按照日期类型自动判断
     *
     * @param date
     * @return
     */
    public static String formatAll(Date date) {
        if (date instanceof java.sql.Timestamp) {
            return formatDateTime(date);
        } else if (date instanceof java.sql.Time) {
            return formatTime(date);
        } else if (date instanceof java.sql.Date) {
            return formatDate(date);
        }
        return formatDateTime(date);
    }

    /**
     * 按格式输出string到date
     *
     * @param dateString
     * @param style
     *            格式化参数
     * @return
     * @throws ParseException
     */
    public Date parse(String dateString, String pattern) throws ParseException {
        FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
        return dateFormat.parse(dateString);
    }

}
