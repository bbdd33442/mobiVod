package edu.csu.basetools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.text.format.Time;

@SuppressLint("SimpleDateFormat") public class GetDate {
	public String GetYearMonDayWeek(){
		String mYear,mMonth, mDay, mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="日";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "\t"+mYear + "年" + mMonth + "月" + mDay+"日"+"\t星期"+mWay;
    }

    public String GetYear() {
        String mYear;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // ?????????  
        return mYear;
    }

    public String GetNow() {
        Time t = new Time();
        t.setToNow();
        return t.year + "-" + (1 + t.month) + "-" + t.monthDay + " " + t.hour + ":" + t.minute + ":" + t.second;
    }

    public String GetDayDiffer(String now, String another) {
        String result = null;
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            java.util.Date Date1 = localSimpleDateFormat.parse(now);
            java.util.Date Date2 = localSimpleDateFormat.parse(another);
            long l = Date1.getTime() - Date2.getTime();
            l = Math.abs(l);
            long day = l / (24 * 60 * 60 * 1000);
            result = String.valueOf(day);
        } catch (ParseException e) {
        }
        return result;
    }

    public String Differ() {
        return GetDayDiffer(GetNow(), GetYear() + "-06-07 00:00:00");
    }

    public String GetTimeFromNow(String paramString) {
        String result = null;
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            Time t = new Time();
            t.setToNow();
            java.util.Date Date1 = localSimpleDateFormat.parse(t.year + "-" + (1 + t.month) + "-" + t.monthDay + " " + t.hour + ":" + t.minute + ":" + t.second);
            java.util.Date Date2 = localSimpleDateFormat.parse(paramString);
            long l = Date1.getTime() - Date2.getTime();
            l = Math.abs(l);
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = ((l / 1000) - day * 24 * 60 - hour * 60 - min * 60);
            if (day > 30) result = day + "???";
            else if (day < 30 && day >= 1) result = day + "???";
            else if (day < 1 && hour >= 1) result = hour + "????";
            else if (hour < 1 && min >= 1) result = min + "?????";
            else result = sec + "???";
        } catch (ParseException e) {
        }
        return result;
    }
}
