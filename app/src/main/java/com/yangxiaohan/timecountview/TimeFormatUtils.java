package com.yangxiaohan.timecountview;

/**
 * Created by ${yangxiaohan} on 2016/8/31.
 */
public class TimeFormatUtils {
    public static String formatMillis(long time){
        long minutes = time/(1000*60);
        long hours = 0;
        long seconds = time/1000;
        if(minutes>=60){
            hours = minutes/60;
            minutes = minutes%60;
            seconds = time/1000-hours*60*60-minutes*60;
            return String.format(hours+"时"+minutes+"分"+seconds+"秒");
        }else{
            seconds = time/1000-minutes*60;
            return String.format(minutes+"分"+seconds+"秒");
        }
    }
//    public static String formatMillis(long time) {
//        long minutes = time / (1000 * 60);
//        long hours = minutes / 60;
//        long day = 0;
//        if (hours > 24) {
//            day = hours / 24;
//            hours = hours % 24;
//            minutes = minutes % 60;
//            return String.format(day + "天" + hours + "时" + minutes + "分");
//        } else {
//            return String.format(hours + "时" + minutes + "分");
//        }
//    }

}
