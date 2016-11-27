package com.yoctopuce.yoctopucetoolbox.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seb on 22.11.2016.
 */
public class MiscHelper
{
    public static String UnixTimeToHumanReadable(long unixtime)
    {
        //todo: use default date format of the OS
        SimpleDateFormat ft = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
        return ft.format(new Date(unixtime));
    }
}
