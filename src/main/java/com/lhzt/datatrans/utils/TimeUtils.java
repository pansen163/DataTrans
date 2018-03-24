package com.lhzt.datatrans.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by pansen on 2017/11/15.
 */
public class TimeUtils {

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static Calendar cal = Calendar.getInstance();

  public static String getFormatTime(Long millisecond) {
    if(millisecond==null){
      return null;
    }
    cal.setTimeInMillis(millisecond);
    return sdf.format(cal.getTime());
  }
}
