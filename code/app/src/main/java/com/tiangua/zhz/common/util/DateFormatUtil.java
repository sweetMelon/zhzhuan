package com.tiangua.zhz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil
{
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
                                                                     "yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date)
    {
        return sdf.format(date);
    }

    public static Date parse(String strDate)
    {
        Date date = null;
        try
        {
            date = sdf.parse(strDate);
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }
}
