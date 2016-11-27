/*********************************************************************
 *
 * $Id: WakeUpSchedule.java 26014 2016-11-24 13:52:08Z seb $
 *
 * Implements WakeUpSchedule wrapper for Android toolbox
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing
 *  with Yoctopuce products.
 *
 *  You may reproduce and distribute copies of this file in
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/

package com.yoctopuce.yoctopucetoolbox.functions;
import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YWakeUpSchedule;

//--- (YWakeUpSchedule class start)
/**
 * YWakeUpSchedule Class: WakeUpSchedule function interface
 *
 * The WakeUpSchedule function implements a wake up condition. The wake up time is
 * specified as a set of months and/or days and/or hours and/or minutes when the
 * wake up should happen.
 */
 @SuppressWarnings("UnusedDeclaration")
public class WakeUpSchedule extends Function
{
// valueCallbackWakeUpSchedule
    protected int _minutesA =  YWakeUpSchedule.MINUTESA_INVALID;
    protected int _minutesB =  YWakeUpSchedule.MINUTESB_INVALID;
    protected int _hours =  YWakeUpSchedule.HOURS_INVALID;
    protected int _weekDays =  YWakeUpSchedule.WEEKDAYS_INVALID;
    protected int _monthDays =  YWakeUpSchedule.MONTHDAYS_INVALID;
    protected int _months =  YWakeUpSchedule.MONTHS_INVALID;
    protected long _nextOccurence =  YWakeUpSchedule.NEXTOCCURENCE_INVALID;
    protected YWakeUpSchedule _ywakeupschedule;

    public WakeUpSchedule(YWakeUpSchedule yfunc)
    {
       super(yfunc);
       _ywakeupschedule = yfunc;
    }

    public WakeUpSchedule(String hwid)
    {
       super(hwid);
       _ywakeupschedule = YWakeUpSchedule.FindWakeUpSchedule(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _minutesA = _ywakeupschedule.get_minutesA();
        _minutesB = _ywakeupschedule.get_minutesB();
        _hours = _ywakeupschedule.get_hours();
        _weekDays = _ywakeupschedule.get_weekDays();
        _monthDays = _ywakeupschedule.get_monthDays();
        _months = _ywakeupschedule.get_months();
        _nextOccurence = _ywakeupschedule.get_nextOccurence();
    }
    /**
     * Returns the minutes in the 00-29 interval of each hour scheduled for wake up.
     *
     * @return an integer corresponding to the minutes in the 00-29 interval of each hour scheduled for wake up
     *
     * On failure, throws an exception or returns Y_MINUTESA_INVALID.
     */
    public int getMinutesA()
    {
        return _minutesA;
    }

    /**
     * Changes the minutes in the 00-29 interval when a wake up must take place.
     *
     * @param newval : an integer corresponding to the minutes in the 00-29 interval when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMinutesABg(int newval) throws YAPI_Exception
    {
        _minutesA = newval;
        _ywakeupschedule.set_minutesA(newval);
    }

    /**
     * Returns the minutes in the 30-59 intervalof each hour scheduled for wake up.
     *
     * @return an integer corresponding to the minutes in the 30-59 intervalof each hour scheduled for wake up
     *
     * On failure, throws an exception or returns Y_MINUTESB_INVALID.
     */
    public int getMinutesB()
    {
        return _minutesB;
    }

    /**
     * Changes the minutes in the 30-59 interval when a wake up must take place.
     *
     * @param newval : an integer corresponding to the minutes in the 30-59 interval when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMinutesBBg(int newval) throws YAPI_Exception
    {
        _minutesB = newval;
        _ywakeupschedule.set_minutesB(newval);
    }

    /**
     * Returns the hours scheduled for wake up.
     *
     * @return an integer corresponding to the hours scheduled for wake up
     *
     * On failure, throws an exception or returns Y_HOURS_INVALID.
     */
    public int getHours()
    {
        return _hours;
    }

    /**
     * Changes the hours when a wake up must take place.
     *
     * @param newval : an integer corresponding to the hours when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setHoursBg(int newval) throws YAPI_Exception
    {
        _hours = newval;
        _ywakeupschedule.set_hours(newval);
    }

    /**
     * Returns the days of the week scheduled for wake up.
     *
     * @return an integer corresponding to the days of the week scheduled for wake up
     *
     * On failure, throws an exception or returns Y_WEEKDAYS_INVALID.
     */
    public int getWeekDays()
    {
        return _weekDays;
    }

    /**
     * Changes the days of the week when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the week when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setWeekDaysBg(int newval) throws YAPI_Exception
    {
        _weekDays = newval;
        _ywakeupschedule.set_weekDays(newval);
    }

    /**
     * Returns the days of the month scheduled for wake up.
     *
     * @return an integer corresponding to the days of the month scheduled for wake up
     *
     * On failure, throws an exception or returns Y_MONTHDAYS_INVALID.
     */
    public int getMonthDays()
    {
        return _monthDays;
    }

    /**
     * Changes the days of the month when a wake up must take place.
     *
     * @param newval : an integer corresponding to the days of the month when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMonthDaysBg(int newval) throws YAPI_Exception
    {
        _monthDays = newval;
        _ywakeupschedule.set_monthDays(newval);
    }

    /**
     * Returns the months scheduled for wake up.
     *
     * @return an integer corresponding to the months scheduled for wake up
     *
     * On failure, throws an exception or returns Y_MONTHS_INVALID.
     */
    public int getMonths()
    {
        return _months;
    }

    /**
     * Changes the months when a wake up must take place.
     *
     * @param newval : an integer corresponding to the months when a wake up must take place
     *
     * @return YAPI_SUCCESS if the call succeeds.
     *
     * On failure, throws an exception or returns a negative error code.
     */
    public void setMonthsBg(int newval) throws YAPI_Exception
    {
        _months = newval;
        _ywakeupschedule.set_months(newval);
    }

    /**
     * Returns the date/time (seconds) of the next wake up occurence.
     *
     * @return an integer corresponding to the date/time (seconds) of the next wake up occurence
     *
     * On failure, throws an exception or returns Y_NEXTOCCURENCE_INVALID.
     */
    public long getNextOccurence()
    {
        return _nextOccurence;
    }

//--- (end of YWakeUpSchedule class start)
}

