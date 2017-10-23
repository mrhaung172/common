package com.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

public class DateUtil {
    private static final Logger logger = Logger.getLogger(DateUtil.class);

    /**
     * 获取当前时间的字符串 格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentDateTimeStr() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }

    /**
     * 获取当前时间的字符串 格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String date2Str(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    /**
     * 获取当前时间的字符串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String dateFormatter(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timeString = dataFormat.format(date);
        return timeString;
    }

    public static Date strToDate(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        if(date.length()==10){
        	date += " 00:00:00";
        }

        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        try {
            return dataFormat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取月差（不足月不计算）
     *
     * @return
     * @author hejun
     * @created 2015-10-27 下午02:05:12
     */
    public static int getMonthSpace(Date baseDate, Date followDate) {
        if (null == baseDate || null == followDate) {
            logger.error("计算剩余月份，传入参数为空！");
            return 0;
        }

        if (baseDate.getTime() > followDate.getTime()) {
            return 0;
        }

        int diffMoths = 0;

        try {
            Calendar baseCalendar = Calendar.getInstance();
            baseCalendar.setTime(baseDate);
            int baseYear = baseCalendar.get(Calendar.YEAR);
            int baseMoth = baseCalendar.get(Calendar.MONTH);
            int baseDay = baseCalendar.get(Calendar.DATE);

            Calendar finalCalendar = Calendar.getInstance();
            finalCalendar.setTime(followDate);
            int finalRepayYear = finalCalendar.get(Calendar.YEAR);
            int finalRepayMoth = finalCalendar.get(Calendar.MONTH);
            int finalRepayDay = finalCalendar.get(Calendar.DATE);

            diffMoths = (finalRepayYear - baseYear) * 12 + finalRepayMoth - baseMoth + (finalRepayDay - baseDay >= 0 ? 0 : -1);
        } catch (Exception e) {
            logger.error("计算剩余月份异常！", e);
        }

        return diffMoths;
    }

    /**
     * 取相差天数（忽略月）
     *
     * @return
     * @author hejun
     * @created 2015-10-27 下午03:30:17
     */
    public static int getDaySpace(Date baseDate, Date followDate) {
        if (null == baseDate || null == followDate) {
            logger.error("计算剩余天数，传入参数为空！");
            return 0;
        }

        if (baseDate.getTime() > followDate.getTime()) {
            return 0;
        }

        int diffDays = 0;

        try {
            Calendar baseCalendar = Calendar.getInstance();
            baseCalendar.setTime(baseDate);
            int baseDay = baseCalendar.get(Calendar.DATE);

            Calendar finalCalendar = Calendar.getInstance();
            finalCalendar.setTime(followDate);
            int finalRepayDay = finalCalendar.get(Calendar.DATE);

            if (finalRepayDay < baseDay) {
                finalCalendar.add(Calendar.MONTH, -1);
                Date preMonth = finalCalendar.getTime();
                long mDays = (followDate.getTime() - preMonth.getTime()) / 86400000;
                diffDays = (int) (mDays + finalRepayDay - baseDay);
            } else {
                diffDays = finalRepayDay - baseDay;
            }
        } catch (Exception e) {
            logger.error("计算剩余天数异常！", e);
        }

        return diffDays;
    }

    /**
     * 获取当前时间的字符串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeString = sdf.format(date);
        return timeString;
    }

	/**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
	 * @throws ParseException
     */
	public static int daysBetween(Date smdate,Date bdate) {
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            smdate=sdf.parse(sdf.format(smdate));
            bdate=sdf.parse(sdf.format(bdate));
            long between_days=(bdate.getTime()-smdate.getTime())/(1000*3600*24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

	// java.time.LocalDateTime --> java.util.Date
	public static Date LocalDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

	/**
     * 计算两个时间之间相差的天数，不满一天按一天算
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        long start = startDate.getTime();
        long end = endDate.getTime();
		double days = Math.ceil((end - start) / 86400000d);	// 一天的毫秒数1000 * 60 * 60 * 24=86400000
        return Long.parseLong(String.format("%.0f", days));
    }

    /**
	/** 某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    /**
     * 获取该月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.truncate(date, Calendar.DATE));
        calendar.add(Calendar.MONTH, 1);    //再减一天即为上个月最后一天
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        return calendar.getTime();
    }

    /**
     * 获取该日期 本周的最后一天
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.truncate(date, Calendar.DATE));
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekDay == Calendar.SUNDAY) {
            return calendar.getTime();
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.add(Calendar.DATE, 6);
        }
        return calendar.getTime();
    }

    /**
     * 获取最近90天全日期的记录
     *
     * @param date
     * @return
     */
    public static List<Date> getThreeMonthDays(Date date) {
        date = DateUtils.truncate(date, Calendar.DATE);
        List<Date> list = new ArrayList<Date>();
        for (int i = 1; i <= 90; i++) {
            Calendar riqi = Calendar.getInstance();
            riqi.setTime(date);
            riqi.add(Calendar.DATE, -i);
            Date dayDate = DateUtils.truncate(riqi.getTime(), Calendar.DATE);
            list.add(dayDate);
        }
        return list;
    }

    public static List<Date> getPastTwelveWeek(Date date) {
        List<Date> list = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTime(DateUtils.truncate(date, Calendar.DATE));
        for (int i = 0; i < 13; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            list.add(calendar.getTime());
            calendar.add(Calendar.DATE, -7);
        }
        return list;
    }

    /**
     * 获取当前date的当天起始时间点
     * @param date
     * @return
     */
    public static Date getStartTimePointOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar cal = new Calendar.Builder().setDate(year, month, day)
                .setTimeOfDay(0,0,0,0)
                .build();
        return cal.getTime();
    }

    /**
     * 获取当前date的当天最末时间点
     * @param date
     * @return
     */
    public static Date getEndTimePointOfCurrentDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Calendar cal = new Calendar.Builder().setDate(year, month, day)
                .setTimeOfDay(23,59,59,999)
                .build();
        return cal.getTime();
    }

    public static Timestamp nowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 格式化字符串为时间
     * 支持如下格式
     * yyyy-MM-dd hh:mm:ss
     * yyyy-MM-dd
     *
     * @param dateString
     * @return
     */
    public static Date strFormatDate(String format, String dateString){
        if(null == dateString || "" == dateString)
            return null;
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static long getUnixTimestamp(String format, String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(date).getTime() / 1000;
    }

    /**
     * 获取当前时间的字符串 格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String strDate) {
        if (strDate == null) {
            return null;
        }
        try {
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dataFormat.parse(strDate);
            return date;
        } catch (Exception e) {
            logger.error("日期转换失败  str2Date ");
            return null;
        }

    }

}
